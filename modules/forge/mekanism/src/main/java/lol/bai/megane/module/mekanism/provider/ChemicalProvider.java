package lol.bai.megane.module.mekanism.provider;

import java.util.ArrayList;
import java.util.List;

import lol.bai.megane.module.mekanism.MeganeMekaninsm;
import lol.bai.megane.module.mekanism.mixin.AccessLookingAtUtils;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IData;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.WailaHelper;
import mcp.mobius.waila.api.component.PairComponent;
import mcp.mobius.waila.api.component.SpriteBarComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.data.FluidData.Unit;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.ChemicalUtils;
import mekanism.api.chemical.merged.MergedChemicalTank;
import mekanism.api.math.FloatingLong;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.integration.lookingat.LookingAtHelper;
import mekanism.common.lib.multiblock.MultiblockData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class ChemicalProvider implements IBlockComponentProvider, IDataProvider<BlockEntity> {

    public static final ResourceLocation DATA = MeganeMekaninsm.id("chemical");

    private static final String INFINITE = "∞";

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var data = accessor.getData().get(Data.class);
        if (data == null || !config.getBoolean(MeganeMekaninsm.CONFIG_SHOW_CHEMICALS)) return;

        var displayUnit = config.<Unit>getEnum(FluidData.CONFIG_DISPLAY_UNIT);

        for (var tank : data.tanks) {
            var stack = tank.stack;
            if (stack.isEmpty()) continue;

            var stored = stack.getAmount();
            var capacity = tank.capacity;
            var ratio = Double.isInfinite(capacity) ? 1f : (float)  stored / capacity;

            String text;
            if (Double.isInfinite(stored)) text = INFINITE;
            else {
                text = WailaHelper.suffix((long) Unit.convert(Unit.MILLIBUCKETS, displayUnit, stored));
                if (Double.isFinite(capacity)) text += "/" + WailaHelper.suffix((long) Unit.convert(Unit.MILLIBUCKETS, displayUnit, capacity));
            }

            text += " " + displayUnit.symbol;

            var sprite = MekanismRenderer.getChemicalTexture(stack.getType());
            tooltip.addLine(new PairComponent(
                new WrappedComponent(stack.getTextComponent()),
                new SpriteBarComponent(ratio, sprite, 16, 16, stack.getChemicalTint(), Component.literal(text))));
        }
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(Data.class, res -> addChemicals(res, accessor.getTarget(), null));
    }

    public static void addChemicals(IDataWriter.Result<Data> res, BlockEntity tile, @Nullable MultiblockData structure) {
        var info = new Data(new ArrayList<>());
        AccessLookingAtUtils.megane_addInfo(tile, structure, Capabilities.GAS_HANDLER, multiblock -> multiblock.getGasTanks(null), info, MekanismLang.GAS, MergedChemicalTank.Current.GAS, MergedTank.CurrentType.GAS);
        AccessLookingAtUtils.megane_addInfo(tile, structure, Capabilities.INFUSION_HANDLER, multiblock -> multiblock.getInfusionTanks(null), info, MekanismLang.INFUSE_TYPE, MergedChemicalTank.Current.INFUSION, MergedTank.CurrentType.INFUSION);
        AccessLookingAtUtils.megane_addInfo(tile, structure, Capabilities.PIGMENT_HANDLER, multiblock -> multiblock.getPigmentTanks(null), info, MekanismLang.PIGMENT, MergedChemicalTank.Current.PIGMENT, MergedTank.CurrentType.PIGMENT);
        AccessLookingAtUtils.megane_addInfo(tile, structure, Capabilities.SLURRY_HANDLER, multiblock -> multiblock.getSlurryTanks(null), info, MekanismLang.SLURRY, MergedChemicalTank.Current.SLURRY, MergedTank.CurrentType.SLURRY);
        if (!info.tanks.isEmpty()) res.add(info);
    }

    public static final class Blocker implements IDataProvider<BlockEntity> {

        @Override
        public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
            if (!config.getBoolean(MeganeMekaninsm.CONFIG_SHOW_CHEMICALS)) data.blockAll(Data.class);
        }

    }

    public record Data(List<Tank> tanks) implements IData, LookingAtHelper {

        public record Tank(ChemicalStack<?> stack, long capacity) {

        }

        public Data(FriendlyByteBuf buf) {
            this(buf.readList(b -> {
                var type = b.readEnum(ChemicalType.class);
                var stack = switch (type) {
                    case GAS -> ChemicalUtils.readGasStack(b);
                    case INFUSION -> ChemicalUtils.readInfusionStack(b);
                    case PIGMENT -> ChemicalUtils.readPigmentStack(b);
                    case SLURRY -> ChemicalUtils.readSlurryStack(b);
                };
                var capacity = b.readVarLong();
                return new Tank(stack, capacity);
            }));
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeCollection(tanks, (b, t) -> {
                b.writeEnum(ChemicalType.getTypeFor(t.stack));
                ChemicalUtils.writeChemicalStack(b, t.stack);
                b.writeVarLong(t.capacity);
            });
        }

        @Override
        public void addChemicalElement(ChemicalStack<?> stored, long capacity) {
            tanks.add(new Tank(stored, capacity));
        }

        @Override
        public void addText(Component text) {
        }

        @Override
        public void addEnergyElement(FloatingLong energy, FloatingLong maxEnergy) {
        }

        @Override
        public void addFluidElement(FluidStack stored, int capacity) {
        }

    }

}
