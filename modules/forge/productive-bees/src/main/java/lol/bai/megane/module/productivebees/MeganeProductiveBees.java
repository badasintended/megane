package lol.bai.megane.module.productivebees;

import cy.jdkdigital.productivebees.common.block.entity.AdvancedBeehiveBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.BreedingChamberBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.CentrifugeBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.IncubatorBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.InventoryHandlerHelper;
import cy.jdkdigital.productivebees.common.block.entity.JarBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.SolitaryNestBlockEntity;
import cy.jdkdigital.productivebees.common.entity.bee.ProductiveBee;
import it.unimi.dsi.fastutil.ints.IntList;
import lol.bai.megane.module.productivebees.provider.AdvancedBeehiveProvider;
import lol.bai.megane.module.productivebees.provider.JarProvider;
import lol.bai.megane.module.productivebees.provider.ProductiveBeeProvider;
import lol.bai.megane.module.productivebees.provider.RecipeProcessingProvider;
import lol.bai.megane.module.productivebees.provider.SolitaryNestProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.resources.ResourceLocation;

public class MeganeProductiveBees implements IWailaPlugin {

    public static final ResourceLocation CONFIG_BEE_TYPE = id("bee_type");
    public static final ResourceLocation CONFIG_BEE_PRODUCTIVITY = id("bee_productivity");
    public static final ResourceLocation CONFIG_BEE_TOLERANCE = id("bee_tolerance");
    public static final ResourceLocation CONFIG_BEE_BEHAVIOR = id("bee_behavior");
    public static final ResourceLocation CONFIG_BEE_ENDURANCE = id("bee_endurance");
    public static final ResourceLocation CONFIG_BEE_TEMPER = id("bee_temper");

    public static final ResourceLocation CONFIG_HIVE_HONEY_LEVEL = id("hive_honey_level");
    public static final ResourceLocation CONFIG_HIVE_OCCUPANTS = id("hive_occupants");
    public static final ResourceLocation CONFIG_HIVE_COOLDOWN = id("hive_cooldown");

    public static ResourceLocation id(String path) {
        return new ResourceLocation("megane:productive_bees." + path);
    }

    @Override
    public void register(IRegistrar registrar) {
        var jarProvider = new JarProvider();
        registrar.addDataType(JarProvider.DATA, JarProvider.Data.class, JarProvider.Data::new);
        registrar.addBlockData(jarProvider, JarBlockEntity.class);
        registrar.addComponent(jarProvider, TooltipPosition.HEAD, JarBlockEntity.class);
        registrar.addComponent(jarProvider, TooltipPosition.TAIL, JarBlockEntity.class);

        var beeProvider = new ProductiveBeeProvider();
        registrar.addFeatureConfig(CONFIG_BEE_TYPE, false);
        registrar.addFeatureConfig(CONFIG_BEE_PRODUCTIVITY, false);
        registrar.addFeatureConfig(CONFIG_BEE_TOLERANCE, false);
        registrar.addFeatureConfig(CONFIG_BEE_BEHAVIOR, false);
        registrar.addFeatureConfig(CONFIG_BEE_ENDURANCE, false);
        registrar.addFeatureConfig(CONFIG_BEE_TEMPER, false);
        registrar.addDataType(ProductiveBeeProvider.DATA, ProductiveBeeProvider.Data.class, ProductiveBeeProvider.Data::new);
        registrar.addEntityData(beeProvider, ProductiveBee.class);
        registrar.addComponent(beeProvider, TooltipPosition.BODY, ProductiveBee.class);

        var advBeehiveProvider = new AdvancedBeehiveProvider();
        registrar.addFeatureConfig(CONFIG_HIVE_HONEY_LEVEL, true);
        registrar.addFeatureConfig(CONFIG_HIVE_OCCUPANTS, false);
        registrar.addDataType(AdvancedBeehiveProvider.OCCUPANTS_DATA, AdvancedBeehiveProvider.OccupantsData.class, AdvancedBeehiveProvider.OccupantsData::new);
        registrar.addBlockData(advBeehiveProvider, AdvancedBeehiveBlockEntity.class);
        registrar.addComponent(advBeehiveProvider, TooltipPosition.BODY, AdvancedBeehiveBlockEntity.class);

        var solitaryNestProvider = new SolitaryNestProvider();
        registrar.addFeatureConfig(CONFIG_HIVE_COOLDOWN, false);
        registrar.addDataType(SolitaryNestProvider.DATA, SolitaryNestProvider.Data.class, SolitaryNestProvider.Data::new);
        registrar.addBlockData(solitaryNestProvider, SolitaryNestBlockEntity.class, 900);
        registrar.addComponent(solitaryNestProvider, TooltipPosition.BODY, SolitaryNestBlockEntity.class);

        registrar.addBlockData(new RecipeProcessingProvider<>(IntList.of(0, 1, 2, 3, 4), IntList.of(5)), BreedingChamberBlockEntity.class);
        registrar.addBlockData(new RecipeProcessingProvider<>(IntList.of(InventoryHandlerHelper.INPUT_SLOT), IntList.of(InventoryHandlerHelper.OUTPUT_SLOTS)), CentrifugeBlockEntity.class);
        registrar.addBlockData(new RecipeProcessingProvider<>(IntList.of(0, 1), IntList.of(2)), IncubatorBlockEntity.class);
    }

}
