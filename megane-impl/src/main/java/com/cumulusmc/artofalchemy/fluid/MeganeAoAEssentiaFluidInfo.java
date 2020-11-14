package com.cumulusmc.artofalchemy.fluid;

import badasintended.megane.api.provider.FluidInfoProvider;
import badasintended.megane.util.MeganeUtils;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static badasintended.megane.api.registry.TooltipRegistry.FLUID_INFO;

/**
 * at first i thought of opening a pr
 * but i can't really explain why other mod
 * need to access this other than megane
 */
public class MeganeAoAEssentiaFluidInfo {

    private static final Text ALKAHEST_NAME = new TranslatableText("fluid.artofalchemy.alkahest");

    public static void register() {
        FLUID_INFO.register(FluidAlkahest.class, FluidInfoProvider.of(0x7A0457, ALKAHEST_NAME));
        FLUID_INFO.register(FluidEssentia.class, FluidInfoProvider.of(f -> f.essentia.getColor(), MeganeUtils::fluidName));
    }

}
