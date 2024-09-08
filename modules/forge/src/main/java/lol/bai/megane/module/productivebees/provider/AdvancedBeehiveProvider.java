package lol.bai.megane.module.productivebees.provider;

import java.util.ArrayList;
import java.util.List;

import cy.jdkdigital.productivebees.common.block.entity.AdvancedBeehiveBlockEntityAbstract;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import lol.bai.megane.module.productivebees.MeganeProductiveBees;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.PairComponent;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class AdvancedBeehiveProvider implements IBlockComponentProvider, IDataProvider<AdvancedBeehiveBlockEntityAbstract> {

    public static final ResourceLocation OCCUPANTS_DATA = MeganeProductiveBees.id("occupants");

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var occupants = accessor.getData().get(OccupantsData.class);
        if (occupants != null && config.getBoolean(MeganeProductiveBees.CONFIG_HIVE_OCCUPANTS)) {
            var names = new Object2IntLinkedOpenHashMap<String>(occupants.occupants.size());

            for (var occupant : occupants.occupants) {
                Component component = null;
                if (occupant.customName != null) component = Component.Serializer.fromJson(occupant.customName);
                if (component == null) component = occupant.entityType.getDescription();

                var name = component.getString();
                names.put(name, names.getOrDefault(name, 0) + 1);
            }

            for (var entry : names.object2IntEntrySet()) {
                var name = entry.getKey();
                var count = entry.getIntValue();
                if (count > 1) tooltip.addLine(Component.literal(count + " " + name));
                else tooltip.addLine(Component.literal(name));
            }
        }

        if (config.getBoolean(MeganeProductiveBees.CONFIG_HIVE_HONEY_LEVEL)) {
            var state = accessor.getBlockState();
            if (state.hasProperty(BeehiveBlock.HONEY_LEVEL)) tooltip.addLine(new PairComponent(
                Component.translatable("tooltip.waila.honey_level"),
                Component.literal(state.getValue(BeehiveBlock.HONEY_LEVEL).toString())));
        }
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<AdvancedBeehiveBlockEntityAbstract> accessor, IPluginConfig config) {
        if (config.getBoolean(MeganeProductiveBees.CONFIG_HIVE_OCCUPANTS)) data.add(OccupantsData.class, res -> {
            var stored = accessor.getTarget().getBeeList();
            if (!stored.isEmpty()) {
                var occupants = new ArrayList<OccupantsData.Occupant>(stored.size());

                for (var beeData : stored) {
                    var beeNbt = beeData.nbt;

                    var entityType = EntityType.by(beeNbt);
                    if (entityType.isEmpty()) continue;

                    var customName = beeNbt.contains("CustomName", Tag.TAG_STRING)
                        ? beeNbt.getString("CustomName")
                        : null;

                    occupants.add(new OccupantsData.Occupant(entityType.get(), customName));
                }

                if (!occupants.isEmpty()) res.add(new OccupantsData(occupants));
            }
        });
    }

    public record OccupantsData(
        List<Occupant> occupants
    ) implements IData {

        public OccupantsData(FriendlyByteBuf buf) {
            this(buf.readList(b -> new Occupant(
                b.readRegistryIdUnsafe(ForgeRegistries.ENTITY_TYPES),
                b.readNullable(FriendlyByteBuf::readUtf))));
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeCollection(occupants, (b, occupant) -> {
                b.writeRegistryIdUnsafe(ForgeRegistries.ENTITY_TYPES, occupant.entityType);
                b.writeNullable(occupant.customName, FriendlyByteBuf::writeUtf);
            });
        }

        public record Occupant(EntityType<?> entityType, @Nullable String customName) {

        }

    }

}
