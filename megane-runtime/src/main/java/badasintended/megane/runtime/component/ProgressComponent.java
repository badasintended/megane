package badasintended.megane.runtime.component;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class ProgressComponent implements IComponentProvider {

    public static final ProgressComponent INSTANCE = new ProgressComponent();

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!config().progress.isEnabled()) return;

        CompoundTag data = accessor.getServerData();

        if (data.getBoolean(key("hasProgress")) && data.getInt(key("percentage")) > 0) {
            tooltip.add(new RenderableTextComponent(MeganeWaila.PROGRESS, data));
        }
    }

}
