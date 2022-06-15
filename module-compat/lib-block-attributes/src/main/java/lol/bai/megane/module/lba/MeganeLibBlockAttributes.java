package lol.bai.megane.module.lba;

import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.lba.provider.LibBlockAttributesFluidProvider;
import net.minecraft.block.entity.BlockEntity;

@SuppressWarnings("unused")
public class MeganeLibBlockAttributes implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addFluid(1100, BlockEntity.class, new LibBlockAttributesFluidProvider());
    }

}
