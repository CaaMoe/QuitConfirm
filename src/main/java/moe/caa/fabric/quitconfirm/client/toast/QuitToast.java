package moe.caa.fabric.quitconfirm.client.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class QuitToast extends BaseToast {
    public QuitToast(Text message, long keepTime) {
        super(Text.translatable("toast.quitconfirm.confirm.title"), message, keepTime);
    }

    @Override
    public int getWidth() {
        return 241;
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    protected void drawToast(MatrixStack matrices, ToastManager manager) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, getWidth(), getHeight());
        DrawableHelper.drawTexture(matrices, 8, 0, 242, 0, 15, 30);
        manager.getClient().textRenderer.drawWithShadow(matrices, (title), 35.0f, 7.0f, Color.WHITE.getRGB());
        manager.getClient().textRenderer.drawWithShadow(matrices, (message), 35.0f, 18.0f, Color.WHITE.getRGB());
    }
}
