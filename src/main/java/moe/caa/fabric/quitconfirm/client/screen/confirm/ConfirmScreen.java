package moe.caa.fabric.quitconfirm.client.screen.confirm;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.screen.confirm.style.BaseStyle;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfirmScreen extends Screen {
    private final Text message;
    private final ButtonWidget.PressAction onCancel;
    private final ButtonWidget.PressAction onConfirm;
    private final long openTime;
    private final BaseStyle style = Config.config.confirmScreenDisplayType.baseStyleSupplier.get();
    private ButtonWidget cancel;
    private ButtonWidget confirm;

    private boolean confirmed = false;

    public boolean isConfirmed() {
        return confirmed;
    }

    public ConfirmScreen(Screen parentScreen, Text message, Runnable confirm) {
        this(parentScreen, message, button -> confirm.run());
    }

    public ConfirmScreen(Screen parentScreen, Text message, ButtonWidget.PressAction confirm) {
        super(Text.literal("你确定？"));
        openTime = System.currentTimeMillis();
        this.message = message;
        onCancel = (buttonWidget) -> this.client.setScreen(parentScreen);
        onConfirm = confirm;
    }

    @Override
    protected void init() {
        initButton();
    }

    @Override
    public void tick() {
        if (openTime + Config.config.keepDarkInConfirmScreenTime < System.currentTimeMillis()) {
            cancel.active = true;
            confirm.active = true;
        }
    }

    private void initButton() {
        confirm = style.generateConfirmButtons(this, button -> {
            confirmed = true;
            confirm.onPress();
        });

        cancel = style.generateCancelButtons(this, onCancel);

        confirm.active = false;
        cancel.active = false;
        this.addDrawableChild(confirm);
        this.addDrawableChild(cancel);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        style.render(this.client, this.textRenderer, this, title, message, matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Config.config.enableScreenShortcutKey && keyCode == 257 /* ENTER */) {
            onConfirm.onPress(confirm);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return Config.config.enableScreenShortcutKey;
    }
}