package com.cumulusmc.artofalchemy.fluid;

import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.util.MeganeUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/**
 * at first i thought of opening a pr
 * but i can't really explain why other mod
 * need to access this other than megane
 */
@Environment(EnvType.CLIENT)
public class MeganeAoAEssentiaFluidInfo {

    private static final Text ALKAHEST_NAME = new TranslatableText("fluid.artofalchemy.alkahest");

    public static void register(MeganeClientRegistrar registrar) {
        registrar
            .fluid(FluidAlkahest.class, FluidInfoProvider.of(0x7A0457, ALKAHEST_NAME))
            .fluid(FluidEssentia.class, FluidInfoProvider.of(f -> f.essentia.getColor(), MeganeUtils::fluidName));
    }

}
