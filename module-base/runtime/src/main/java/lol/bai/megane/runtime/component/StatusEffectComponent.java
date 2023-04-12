package lol.bai.megane.runtime.component;

import com.mojang.blaze3d.systems.RenderSystem;
import lol.bai.megane.runtime.util.Keys;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;

public class StatusEffectComponent implements ITooltipComponent {

    private final NbtCompound data;
    private final int size;

    public StatusEffectComponent(NbtCompound data) {
        this.data = data;

        int size = data.getInt(Keys.S_SIZE);
        for (int i = 0; i < size; i++) {
            int id = data.getInt(Keys.S_ID + i);
            if (id == -1) {
                size--;
                i--;
            }
        }
        this.size = size;
    }

    @Override
    public int getWidth() {
        return size <= 0 ? 0 : size * 20;
    }

    @Override
    public int getHeight() {
        return size <= 0 ? 0 : 20;
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float delta) {
        StatusEffectSpriteManager manager = MinecraftClient.getInstance().getStatusEffectSpriteManager();

        int size = this.size;
        for (int i = 0; i < size; i++) {
            String lv = data.getString(Keys.S_LV_STR + i);
            StatusEffect statusEffect = StatusEffect.byRawId(data.getInt(Keys.S_ID + i));
            if (statusEffect != null) {
                Sprite sprite = manager.getSprite(statusEffect);
                RenderSystem.setShaderTexture(0, sprite.getAtlasId());
                DrawableHelper.drawSprite(matrices, x + 20 * i, y, 0, 18, 18, sprite);
                MeganeUtils.textRenderer().drawWithShadow(matrices, lv, x + 20 + (20 * i) - MeganeUtils.textRenderer().getWidth(lv), y + 20 - MeganeUtils.textRenderer().fontHeight, 0xFFFFFF);
            } else {
                size--;
                i--;
            }
        }
    }

}
