package badasintended.megane.runtime.component.entity;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class PetOwnerComponent extends EntityComponent {

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(key("valColor"), 0xFFAAAAAA);
    }

    public PetOwnerComponent() {
        super(() -> config().petOwner);
    }

    @Override
    protected void append(List<Text> tooltip, IEntityAccessor accessor) {
        CompoundTag data = accessor.getServerData();
        if (data.getBoolean(key("hasOwner"))) {
            TAG.putString(key("key"), I18n.translate("megane.owner"));
            TAG.putString(key("value"), data.getString(key("owner")));
            tooltip.add(new RenderableTextComponent(MeganeWaila.ALIGNED, TAG));
        }
    }

}
