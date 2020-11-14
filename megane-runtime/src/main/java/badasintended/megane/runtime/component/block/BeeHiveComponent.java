package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class BeeHiveComponent extends BlockComponent {

    public static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(key("color"), 0xE57B24);
    }

    public BeeHiveComponent() {
        super(() -> config().fluid);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        BlockState state = accessor.getBlockState();
        int level = state.get(BeehiveBlock.HONEY_LEVEL);
        TAG.putDouble(key("stored"), level);
        TAG.putDouble(key("max"), 5);
        TAG.putString(key("prefix"), I18n.translate("megane.level"));
        tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
    }

}
