package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import lol.bai.megane.module.create.mixin.AccessBasinBlockEntity;
import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;

public class BasinProvider implements IDataProvider<BasinBlockEntity> {

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BasinBlockEntity> accessor, IPluginConfig config) {
//        data.add(FluidData.class, res -> {
//            var target = (BasinBlockEntity & AccessBasinBlockEntity) accessor.getTarget();
//            var fluidData = FluidData.of(FluidData.Unit.MILLIBUCKETS, 2);
//
//            for (SmartFluidTankBehaviour tank : target.getTanks()) {
//                var stack = tank.getPrimaryHandler().getFluid();
//                fluidData.add(stack.getFluid(), stack.getTag(), stack.getAmount(), FluidType.BUCKET_VOLUME);
//            }
//
//            res.add(fluidData);
//        });

//        data.add(ItemData.class, res -> {
//            var target = (BasinBlockEntity & AccessBasinBlockEntity) accessor.getTarget();
//            var itemData = ItemData.of(config);
//
//            for (SmartInventory inv : target.getInvs()) {
//                itemData.getter(inv::getStackInSlot, inv.getSlots());
//            }
//
//            res.add(itemData);
//        });

        data.add(ProgressData.class, res -> {
            var target = (BasinBlockEntity & AccessBasinBlockEntity) accessor.getTarget();
            var operator = target.megane_getOperator().orElse(null);
            if (!(operator instanceof MechanicalMixerBlockEntity mixer)) return;

            var recipeTicks = ((MechanicalMixerProvider.Access) mixer).megane_getRecipeTicks();
            if (recipeTicks <= 0) return;

            var progressData = ProgressData.ratio(1 - ((float) mixer.processingTicks / recipeTicks));

            for (SmartInventory inv : target.getInvs()) {
                for (int i = 0; i < inv.getSlots(); i++) {
                    progressData.input(inv.getStackInSlot(i));
                }
            }

            res.add(progressData);
        });
    }

}
