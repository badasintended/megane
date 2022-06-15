package lol.bai.megane.runtime.provider.entity;

import lol.bai.megane.runtime.component.StatusEffectComponent;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.S_LV;
import static lol.bai.megane.runtime.util.Keys.S_LV_STR;
import static lol.bai.megane.runtime.util.Keys.S_SIZE;
import static lol.bai.megane.runtime.util.MeganeUtils.config;
import static lol.bai.megane.runtime.util.MeganeUtils.toRoman;

public class StatusEffectComponentProvider extends EntityComponentProvider {

    public StatusEffectComponentProvider() {
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

        tooltip.addLine(new StatusEffectComponent(data));
    }

}
