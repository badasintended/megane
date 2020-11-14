package badasintended.megane.runtime.component.entity;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.RuntimeUtils.toRoman;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class StatusEffectComponent extends EntityComponent {

    public StatusEffectComponent() {
        super(() -> config().effect);
    }

    @Override
    protected void append(List<Text> tooltip, IEntityAccessor accessor) {
        CompoundTag data = accessor.getServerData();

        for (int i = 0; i < data.getInt(key("effectSize")); i++) {
            int lv = data.getInt(key("effectLv" + i));
            String str = lv <= 1 || !config().effect.getLevel() ? "" : lv > 64 || !config().effect.isRoman() ? "" + lv : toRoman(lv);
            data.putString(key("effectLvStr" + i), str);
        }

        tooltip.add(new RenderableTextComponent(MeganeWaila.EFFECT, data));
    }

}
