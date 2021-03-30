package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.Megane;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.Keys.B_COLOR;
import static badasintended.megane.runtime.util.Keys.B_MAX;
import static badasintended.megane.runtime.util.Keys.B_PREFIX;
import static badasintended.megane.runtime.util.Keys.B_STORED;
import static badasintended.megane.util.MeganeUtils.config;

public class BeeHiveComponent extends BlockComponent {

    public static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(B_COLOR, 0xE57B24);
    }

    public BeeHiveComponent() {
        super(() -> config().fluid);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        BlockState state = accessor.getBlockState();
        int level = state.get(BeehiveBlock.HONEY_LEVEL);
        TAG.putDouble(B_STORED, level);
        TAG.putDouble(B_MAX, 5);
        TAG.putString(B_PREFIX, I18n.translate("megane.level"));
        tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
    }

}
