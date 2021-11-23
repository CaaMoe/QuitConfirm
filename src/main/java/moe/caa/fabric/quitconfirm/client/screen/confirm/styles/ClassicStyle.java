package moe.caa.fabric.quitconfirm.client.screen.confirm.styles;

import moe.caa.fabric.quitconfirm.client.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ClassicStyle extends BaseStyle {
    private static final Text DISPLAY_NAME = new TranslatableText("gui.quitconfirm.confirm.style.classic");

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
    public Text getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public ButtonWidget[] generateButtons(Screen screen, ButtonWidget.PressAction onCancel, ButtonWidget.PressAction onConfirm) {
        ButtonWidget cancel = new ButtonWidget(
                screen.width / 2 - buttonWidth - buttonFMargin,
                screen.height - buttonHeight - buttonBMargin,
                buttonWidth, buttonHeight, new TranslatableText("gui.cancel"), onCancel);

        ButtonWidget confirm = new ButtonWidget(
                screen.width / 2 + buttonFMargin,
                screen.height - buttonHeight - buttonBMargin,
                buttonWidth, buttonHeight, new TranslatableText("gui.ok"), onConfirm);
        return new ButtonWidget[]{cancel, confirm};
    }

    @Override
    public void render(MinecraftClient client, TextRenderer textRenderer, Screen screen, Text title, Text message,
                       MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawBackground(client, screen, matrices);
        drawTextAndMessage(textRenderer, screen, title, message, matrices);
    }

    private void drawBackground(MinecraftClient client, Screen screen, MatrixStack matrices) {
        if (client.world != null) {
            if (Config.config.get().transparentBackground) {
                this.fillGradient(matrices, 0, 0, screen.width, screen.height, -1072689136, -804253680);
                return;
            }
        }
        this.renderBackground(0, 0, screen.width, screen.height);
    }

    private void drawTextAndMessage(TextRenderer textRenderer, Screen screen, Text title, Text message, MatrixStack matrices) {
        drawCenteredText(matrices, textRenderer, title,
                screen.width / 2,
                titleTMargin,
                16777215);

        drawCenteredText(matrices, textRenderer, message,
                screen.width / 2,
                screen.height / 2 - 30,
                10526880);
    }
}
