package badasintended.megane.runtime.component.entity;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.Keys.O_HAS;
import static badasintended.megane.runtime.util.Keys.O_NAME;
import static badasintended.megane.runtime.util.Keys.T_KEY;
import static badasintended.megane.runtime.util.Keys.T_VAL;
import static badasintended.megane.runtime.util.Keys.T_VAL_COL;
import static badasintended.megane.util.MeganeUtils.config;

public class PetOwnerComponent extends EntityComponent {

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(T_VAL_COL, 0xFFAAAAAA);
    }

    public PetOwnerComponent() {
        super(() -> config().petOwner);
    }

    @Override
    protected void append(List<Text> tooltip, IEntityAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(O_HAS)) {
            TAG.putString(T_KEY, I18n.translate("megane.owner"));
            TAG.putString(T_VAL, data.getString(O_NAME));
            tooltip.add(new RenderableTextComponent(MeganeWaila.ALIGNED, TAG));
        }
    }

}
