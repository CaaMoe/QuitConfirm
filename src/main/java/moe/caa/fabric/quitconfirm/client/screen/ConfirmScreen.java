package moe.caa.fabric.quitconfirm.client.screen;

import moe.caa.fabric.quitconfirm.client.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("all")
@Environment(EnvType.CLIENT)
public final class ConfirmScreen extends Screen {
    public static boolean confirm = false;
    private static final Timer timer = new Timer("QuitConfirm");

    protected final Screen parent;
    private final Text message;
    private final Text confirmMessage;
    private final Runnable run;

    public ConfirmScreen(Screen parent, Text message, Text confirmMessage, Runnable run) {
        super(new TranslatableText("gui.quitconfirm.title"));
        this.parent = parent;
        this.message = message;
        this.run = run;
        this.confirmMessage = confirmMessage;
    }

    @Override
    protected void init() {
        ButtonWidget cancel = new ButtonWidget(this.width / 2 - 155, this.height / 4 + 120 + 12, 150, 20, new TranslatableText("gui.cancel"), (buttonWidget) -> this.client.openScreen(parent));
        ButtonWidget confirm = new ButtonWidget(this.width / 2 - 155 + 160, this.height / 4 + 120 + 12, 150, 20,  confirmMessage, (buttonWidget) -> this.run.run());
        cancel.active = false;
        confirm.active = false;
        ConfirmScreen.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cancel.active = true;
                confirm.active = true;
            }
        }, 1000L);
        this.addButton(cancel);
        this.addButton(confirm);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(Config.CONFIG.shortcutKet && keyCode == 257){
            run.run();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean shouldCloseOnEsc() {
        return Config.CONFIG.shortcutKet;
    }

    @Override
    public void onClose() {
        client.openScreen(parent);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        this.renderBackground(stack);
        drawCenteredText( stack,this.textRenderer, title, this.width / 2, this.height / 4 - 40, 16777215);
        drawCenteredText(stack, this.textRenderer, message, this.width / 2  , this.height / 3 , 10526880);
        super.render(stack,mouseX, mouseY, delta);
    }
}
