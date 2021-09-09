package badasintended.megane.runtime.component.entity;

import badasintended.megane.runtime.Megane;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;

import static badasintended.megane.runtime.util.Keys.S_LV;
import static badasintended.megane.runtime.util.Keys.S_LV_STR;
import static badasintended.megane.runtime.util.Keys.S_SIZE;
import static badasintended.megane.runtime.util.RuntimeUtils.toRoman;
import static badasintended.megane.util.MeganeUtils.config;

public class StatusEffectComponent extends EntityComponent {

    public StatusEffectComponent() {
        super(() -> config().effect);
    }

    @Override
    protected void append(ITooltip tooltip, IEntityAccessor accessor) {
        NbtCompound data = accessor.getServerData();

        for (int i = 0; i < data.getInt(S_SIZE); i++) {
            int lv = data.getInt(S_LV + i);
            String str = lv <= 1 || !config().effect.getLevel() ? "" : lv > 64 || !config().effect.isRoman() ? "" + lv : toRoman(lv);
            data.putString(S_LV_STR + i, str);
        }

        tooltip.addDrawable(Megane.EFFECT, data);
    }

}
