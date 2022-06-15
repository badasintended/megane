package lol.bai.megane.module.fabrictransfer.provider;

import lol.bai.megane.api.provider.FluidInfoProvider;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;

@SuppressWarnings("UnstableApiUsage")
public class FabricFluidInfoProvider extends FluidInfoProvider<Fluid> {

    FluidVariantRenderHandler handler;
    FluidVariant variant;

    @Override
    protected void init() {
        handler = FluidVariantRendering.getHandler(getObject());
        variant = handler != null ? FluidVariant.of(getObject(), getNbt()) : null;
    }

    @Override
    public boolean hasFluidInfo() {
        return handler != null;
    }

    @Override
    public int getColor() {
        return handler.getColor(variant, getWorld(), getPos());
    }

    @Override
    public Text getName() {
        return FluidVariantAttributes.getName(variant);
    }

}
