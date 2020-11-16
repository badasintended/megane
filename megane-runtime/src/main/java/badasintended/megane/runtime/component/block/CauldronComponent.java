package badasintended.megane.runtime.component.block;

import java.util.List;

import badasintended.megane.runtime.MeganeWaila;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class CauldronComponent extends BlockComponent {

    public static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(key("color"), 0xFF0D0D59);
    }

    public CauldronComponent() {
        super(() -> config().fluid);
    }

    @Override
    protected void append(List<Text> tooltip, IDataAccessor accessor) {
        BlockState state = accessor.getBlockState();
        int level = state.get(CauldronBlock.LEVEL);
        TAG.putDouble(key("stored"), level);
        TAG.putDouble(key("max"), 3);
        TAG.putString(key("prefix"), I18n.translate("megane.level"));
        tooltip.add(new RenderableTextComponent(MeganeWaila.BAR, TAG));
    }

}