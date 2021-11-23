package moe.caa.fabric.quitconfirm.client.screen.confirm;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.main.QuitConfirm;
import moe.caa.fabric.quitconfirm.client.screen.confirm.styles.BaseStyle;
import moe.caa.fabric.quitconfirm.client.screen.settle.SettleScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.TimerTask;

public class ConfirmScreen extends Screen {
    private static final Identifier SETTLE_ICON_TEXTURE = new Identifier("quitconfirm:textures/gui/settle/settle.png");

    private final Text message;
    private final ButtonWidget.PressAction onCancel;
    private final ButtonWidget.PressAction onConfirm;

    private ButtonWidget cancel;
    private ButtonWidget confirm;

    private BaseStyle style;

    public ConfirmScreen(Screen parentScreen, Text message, Runnable confirm) {
        this(parentScreen, message, button -> confirm.run());
    }

    public ConfirmScreen(Screen parentScreen, Text message, ButtonWidget.PressAction confirm) {
        super(new TranslatableText("gui.quitconfirm.window.title"));
        this.message = message;

        onCancel = (buttonWidget) -> this.client.setScreen(parentScreen);
        onConfirm = confirm;
    }

    @Override
    protected void init() {
        style = StyleFactory.createStyleFromConfig();
        initButton();
        initSettleButton();
    }

    private void initSettleButton() {
        this.addDrawableChild(new TexturedButtonWidget(this.width - 22, this.height - 22, 20, 20, 0, 0, 20, SETTLE_ICON_TEXTURE, 32, 64,
                (buttonWidget) -> this.client.setScreen(new SettleScreen(this)), new TranslatableText("gui.quitconfirm.settle.title")));
    }

    private void initButton() {
        ButtonWidget[] buttonWidgets = style.generateButtons(this, onCancel, onConfirm);

        cancel = buttonWidgets[0];
        confirm = buttonWidgets[1];
        for (ButtonWidget widget : buttonWidgets) {
            widget.active = false;
            this.addDrawableChild(widget);
        }

        QuitConfirm.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (buttonWidgets) {
                    for (ButtonWidget widget : buttonWidgets) {
                        widget.active = true;
                    }
                }
            }
        }, Config.config.get().keepInAction);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        style.render(this.client, this.textRenderer, this, title, message, matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Config.config.get().shortcutKet && keyCode == 257 /* ENTER */) {
            onConfirm.onPress(confirm);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return Config.config.get().shortcutKet;
    }
}
