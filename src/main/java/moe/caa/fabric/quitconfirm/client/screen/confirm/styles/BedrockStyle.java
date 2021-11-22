package moe.caa.fabric.quitconfirm.client.screen.confirm.styles;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.awt.*;

public class BedrockStyle extends BaseStyle {
    private static final Identifier WINDOW_TEXTURE = new Identifier("quitconfirm:textures/gui/window/window.png");

    // 窗口宽度
    private static final int windowWidth = 252;
    // 窗口长度
    private static final int windowHeight = 140;
    // 按钮旁边距
    private static final int buttonLRMargin = 20;
    // 按钮下边距
    private static final int buttonBMargin = 20;
    // 按钮宽度
    private static final int buttonWidth = 100;
    // 按钮长度
    private static final int buttonHeight = 20;
    // 信息文本下边距
    private static final int messageBMargin = 100;

    @Override
    public Text getDisplayName() {
        return new TranslatableText("gui.quitconfirm.confirm.style.bedrock");
    }

    @Override
    public ButtonWidget[] generateButtons(Screen screen, ButtonWidget.PressAction onCancel, ButtonWidget.PressAction onConfirm) {
        ButtonWidget cancelButtonWidget = new ButtonWidget(
                (screen.width - windowWidth) / 2 + buttonLRMargin,
                (screen.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                buttonWidth, buttonHeight, new TranslatableText("gui.cancel"), onCancel);
        ButtonWidget confirmButtonWidget = new ButtonWidget(
                (screen.width - windowWidth) / 2 + windowWidth - buttonWidth - buttonLRMargin,
                (screen.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                buttonWidth, buttonHeight, new TranslatableText("gui.ok"), onConfirm);
        return new ButtonWidget[]{
                cancelButtonWidget, confirmButtonWidget
        };
    }

    @Override
    public void render(Screen screen, Text title, Text message,
                       MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawBackground(screen, matrices);
        drawWindow(title, matrices, (screen.width - windowWidth) / 2, (screen.height - windowHeight) / 2);
        drawMessage(screen, message, matrices);
    }

    private void drawBackground(Screen screen, MatrixStack matrices) {
        if (MinecraftClient.getInstance().world != null) {
            this.fillGradient(matrices, 0, 0, screen.width, screen.height, -1072689136, -804253680);
        } else {
            this.renderBackground(0, 0, screen.width, screen.height);
            DrawableHelper.fill(matrices, 0, 0, screen.width, screen.height, new Color(16, 16, 16, 179).getRGB());
        }
    }

    private void drawWindow(Text title, MatrixStack matrices, int x, int y) {
        renderBackground(x, y, x + windowWidth, y + windowHeight);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WINDOW_TEXTURE);
        this.drawTexture(matrices, x, y, 0, 0, 252, 140);
        MinecraftClient.getInstance().textRenderer.draw(matrices, title, (float) (x + 8), (float) (y + 6), 4210752);
    }

    private void drawMessage(Screen screen, Text message, MatrixStack matrices) {
        drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, message,
                screen.width / 2,
                (screen.height - windowHeight) / 2 + windowHeight - messageBMargin,
                10526880);
    }
}
