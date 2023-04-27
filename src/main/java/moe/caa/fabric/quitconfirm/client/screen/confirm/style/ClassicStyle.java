package moe.caa.fabric.quitconfirm.client.screen.confirm.style;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ClassicStyle extends BaseStyle {
    // 按钮宽度
    private static final int buttonWidth = 150;
    // 按钮长度
    private static final int buttonHeight = 20;
    // 按钮间隔
    private static final int buttonFMargin = 10;
    // 按钮下边距
    private static final int buttonBMargin = 40;
    // 标题上边距
    private static final int titleTMargin = 30;

    @Override
    public ButtonWidget generateConfirmButtons(Screen screen, ButtonWidget.PressAction onConfirm) {
        return ButtonWidget.builder(ScreenTexts.YES, onConfirm)
                .dimensions(screen.width / 2 - buttonWidth - buttonFMargin,
                        screen.height - buttonHeight - buttonBMargin,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public ButtonWidget generateCancelButtons(Screen screen, ButtonWidget.PressAction onCancel) {
        return ButtonWidget.builder(ScreenTexts.NO, onCancel)
                .dimensions(screen.width / 2 + buttonFMargin,
                        screen.height - buttonHeight - buttonBMargin,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public void render(MinecraftClient client, TextRenderer textRenderer, Screen screen, Text title, Text message,
                       MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices, screen);
        drawTextAndMessage(textRenderer, screen, title, message, matrices);
    }

    public void renderBackground(MatrixStack matrices, Screen screen) {
        if (MinecraftClient.getInstance().world != null) {
            fillGradient(matrices, 0, 0, screen.width, screen.height, -1072689136, -804253680);
        } else {
            screen.renderBackgroundTexture(matrices);
        }
    }

    private void drawTextAndMessage(TextRenderer textRenderer, Screen screen, Text title, Text message, MatrixStack matrices) {
        drawCenteredTextWithShadow(matrices, textRenderer, title,
                screen.width / 2,
                titleTMargin,
                16777215);

        drawCenteredTextWithShadow(matrices, textRenderer, message,
                screen.width / 2,
                screen.height / 2 - 30,
                10526880);
    }
}