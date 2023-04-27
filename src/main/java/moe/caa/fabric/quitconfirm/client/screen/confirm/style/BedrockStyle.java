package moe.caa.fabric.quitconfirm.client.screen.confirm.style;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class BedrockStyle extends BaseStyle {
    private static final Identifier WINDOW_TEXTURE = new Identifier("quitconfirm", "textures/gui/bedrock/window.png");
    private static final Identifier BACKGROUND = new Identifier("quitconfirm", "textures/gui/bedrock/background.png");

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
    public ButtonWidget generateConfirmButtons(Screen screen, ButtonWidget.PressAction onConfirm) {
        return ButtonWidget.builder(ScreenTexts.YES, onConfirm)
                .dimensions((screen.width - windowWidth) / 2 + windowWidth - buttonWidth - buttonLRMargin,
                        (screen.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public ButtonWidget generateCancelButtons(Screen screen, ButtonWidget.PressAction onCancel) {
        return ButtonWidget.builder(ScreenTexts.NO, onCancel)
                .dimensions((screen.width - windowWidth) / 2 + buttonLRMargin,
                        (screen.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                        buttonWidth, buttonHeight).build();
    }


    @Override
    public void render(MinecraftClient client, TextRenderer textRenderer, Screen screen, Text title, Text message,
                       MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawBackground(client, screen, matrices);
        drawWindow(textRenderer, title, matrices, (screen.width - windowWidth) / 2, (screen.height - windowHeight) / 2);
        drawMessage(textRenderer, screen, message, matrices);
    }

    private void drawBackground(MinecraftClient client, Screen screen, MatrixStack matrices) {
        if (client.world != null) {
            fillGradient(matrices, 0, 0, screen.width, screen.height, -1072689136, -804253680);
        } else {
            screen.renderBackground(matrices);
            DrawableHelper.fill(matrices, 0, 0, screen.width, screen.height, new Color(16, 16, 16, 179).getRGB());
        }
    }

    private void drawWindow(TextRenderer textRenderer, Text title, MatrixStack matrices, int x, int y) {
        renderBackground(x, y, x + windowWidth, y + windowHeight, BACKGROUND);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, WINDOW_TEXTURE);
        drawTexture(matrices, x, y, 0, 0, 252, 140);
        textRenderer.draw(matrices, title, (float) (x + 8), (float) (y + 6), 4210752);
    }


    private void drawMessage(TextRenderer textRenderer, Screen screen, Text message, MatrixStack matrices) {
        drawCenteredTextWithShadow(matrices, textRenderer, message,
                screen.width / 2,
                (screen.height - windowHeight) / 2 + windowHeight - messageBMargin,
                10526880);
    }
}