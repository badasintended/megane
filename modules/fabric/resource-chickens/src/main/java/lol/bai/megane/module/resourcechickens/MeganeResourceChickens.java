package lol.bai.megane.module.resourcechickens;

import lol.bai.megane.module.resourcechickens.provider.ChickenProvider;
import lol.bai.megane.module.resourcechickens.provider.NestProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.resources.ResourceLocation;
import wallywhip.resourcechickens.blocks.NestTileEntity;
import wallywhip.resourcechickens.entity.ResourceChickenEntity;

public class MeganeResourceChickens implements IWailaPlugin {

    public static final ResourceLocation CONFIG_SHOW_GAIN = new ResourceLocation("megane:resource_chickens.gain");
    public static final ResourceLocation CONFIG_SHOW_GROWTH = new ResourceLocation("megane:resource_chickens.growth");
    public static final ResourceLocation CONFIG_SHOW_STRENGTH = new ResourceLocation("megane:resource_chickens.strength");
    public static final ResourceLocation CONFIG_SHOW_GROW = new ResourceLocation("megane:resource_chickens.grow");
    public static final ResourceLocation CONFIG_SHOW_DROP = new ResourceLocation("megane:resource_chickens.drop");
    public static final ResourceLocation CONFIG_SHOW_NEST_FOOD = new ResourceLocation("megane:resource_chickens.food");
    public static final ResourceLocation CONFIG_SHOW_CONVERSION = new ResourceLocation("megane:resource_chickens.conversion");

    @Override
    public void register(IRegistrar registrar) {
        registrar.addFeatureConfig(CONFIG_SHOW_GAIN, true);
        registrar.addFeatureConfig(CONFIG_SHOW_GROWTH, true);
        registrar.addFeatureConfig(CONFIG_SHOW_STRENGTH, true);

        registrar.addFeatureConfig(CONFIG_SHOW_GROW, false);
        registrar.addFeatureConfig(CONFIG_SHOW_DROP, false);
        registrar.addFeatureConfig(CONFIG_SHOW_NEST_FOOD, false);
        registrar.addFeatureConfig(CONFIG_SHOW_CONVERSION, false);

        var nestProvider = new NestProvider();
        registrar.addDataType(NestProvider.DATA, NestProvider.Data.class, NestProvider.Data::new);
        registrar.addBlockData(nestProvider, NestTileEntity.class);
        registrar.addComponent(nestProvider, TooltipPosition.HEAD, NestTileEntity.class);
        registrar.addComponent(nestProvider, TooltipPosition.BODY, NestTileEntity.class);

        var chickenProvider = new ChickenProvider();
        registrar.addDataType(ChickenProvider.DATA_TIMER, ChickenProvider.Timer.class, ChickenProvider.Timer::new);
        registrar.addDataType(ChickenProvider.DATA_MUTATION, ChickenProvider.Mutation.class, ChickenProvider.Mutation::new);
        registrar.addEntityData(chickenProvider, ResourceChickenEntity.class);
        registrar.addComponent(chickenProvider, TooltipPosition.BODY, ResourceChickenEntity.class);
    }

}
