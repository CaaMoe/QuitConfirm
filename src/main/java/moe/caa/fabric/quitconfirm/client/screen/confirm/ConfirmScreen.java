package moe.caa.fabric.quitconfirm.client.screen.confirm;

import moe.caa.fabric.quitconfirm.client.main.QuitConfirm;
import moe.caa.fabric.quitconfirm.client.screen.confirm.styles.BaseStyle;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.TimerTask;

public class ConfirmScreen extends Screen {
    private final Screen parentScreen;
    private final Text message;
    private final Runnable confirm;

    private BaseStyle style;

    public ConfirmScreen(Screen parentScreen, Text message, Runnable confirm) {
        super(new TranslatableText("gui.quitconfirm.window.title"));
        this.parentScreen = parentScreen;
        this.message = message;
        this.confirm = confirm;
    }

    @Override
    protected void init() {
        style = StyleFactory.createStyleFromConfig();
        initButton();
    }

    private void initButton() {
        ButtonWidget[] buttonWidgets = style.generateButtons(this,
                (buttonWidget) -> this.client.setScreen(parentScreen),
                (buttonWidget) -> confirm.run()
        );

        for (ButtonWidget widget : buttonWidgets) {
            widget.active = false;
            this.addDrawableChild(widget);
        }

        QuitConfirm.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                for (ButtonWidget widget : buttonWidgets) {
                    widget.active = true;
                }
            }
        }, 1000);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        style.render(this, title, message, matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
