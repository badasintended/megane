/*
package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.Megane;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import static badasintended.megane.runtime.util.Keys.B_COLOR;
import static badasintended.megane.runtime.util.Keys.B_MAX;
import static badasintended.megane.runtime.util.Keys.B_PREFIX;
import static badasintended.megane.runtime.util.Keys.B_STORED;
import static badasintended.megane.util.MeganeUtils.config;

public class CauldronComponent extends BlockComponent {

    public static final NbtCompound TAG = new NbtCompound();

    static {
        TAG.putInt(B_COLOR, 0xFF0D0D59);
    }

    public CauldronComponent() {
        super(() -> config().fluid);
    }

    @Override
    protected void append(List<Text> tooltip, IBlockAccessor accessor) {
        BlockState state = accessor.getBlockState();
        int level = state.get(CauldronBlock.);
        TAG.putDouble(B_STORED, level);
        TAG.putDouble(B_MAX, 3);
        TAG.putString(B_PREFIX, I18n.translate("megane.level"));
        tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
    }

}
*/