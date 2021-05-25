package badasintended.megane.runtime.renderer;

import java.awt.Dimension;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.CompoundTag;

import static badasintended.megane.runtime.util.Keys.S_ID;
import static badasintended.megane.runtime.util.Keys.S_LV_STR;
import static badasintended.megane.runtime.util.Keys.S_SIZE;
import static badasintended.megane.runtime.util.RuntimeUtils.textRenderer;

public class StatusEffectRenderer implements ITooltipRenderer {

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        int size = data.getInt(S_SIZE);
        for (int i = 0; i < size; i++) {
            int id = data.getInt(S_ID + i);
            if (id == -1) {
                size--;
                i--;
            }
        }
        if (size <= 0)
            return new Dimension();
        return new Dimension(size * 20, 20);
    }

    @Override
    public void draw(MatrixStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        StatusEffectSpriteManager manager = MinecraftClient.getInstance().getStatusEffectSpriteManager();

        int size = data.getInt(S_SIZE);
        for (int i = 0; i < size; i++) {
            String lv = data.getString(S_LV_STR + i);
            StatusEffect statusEffect = StatusEffect.byRawId(data.getInt(S_ID + i));
            if (statusEffect != null) {
                Sprite sprite = manager.getSprite(statusEffect);
                MinecraftClient.getInstance().getTextureManager().bindTexture(sprite.getAtlas().getId());
                DrawableHelper.drawSprite(matrices, x + 20 * i, y, 0, 18, 18, sprite);
                textRenderer().drawWithShadow(matrices, lv, x + 20 + (20 * i) - textRenderer().getWidth(lv), y + 20 - textRenderer().fontHeight, 0xFFFFFF);
            } else {
                size--;
                i--;
            }
        }
    }

}
