package moe.caa.fabric.quitconfirm.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

@SuppressWarnings("all")
public final class SettleScreen extends Screen {
    private final Screen parent;
    private ButtonWidget finalE;
    private ButtonWidget integratedE;
    private ButtonWidget reamsE;
    private ButtonWidget dedicatedE;
    private ButtonWidget shortcutKet;

    public SettleScreen(Screen parent) {
        super(new TranslatableText("gui.quitconfirm.settle.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        finalE = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 24 - 6, 150, 20,  LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.finalE = !Config.CONFIG.finalE;
        }));
        integratedE = this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 24 - 6, 150, 20,   LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.integratedE = !Config.CONFIG.integratedE;
        }));
        reamsE = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20,   LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.reamsE = !Config.CONFIG.reamsE;
        }));
        dedicatedE = this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20,  LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.dedicatedE = !Config.CONFIG.dedicatedE;
        }));
        shortcutKet = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20,  LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.shortcutKet = !Config.CONFIG.shortcutKet;
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, ScreenTexts.BACK, (buttonWidget) -> {
            Config.CONFIG.pushSave();

            client.openScreen(parent);
        }));
    }

    @Override
    public void tick() {
        finalE.setMessage(new TranslatableText("button.quitconfirm.opt.final", (Config.CONFIG.finalE ? ScreenTexts.ON : ScreenTexts.OFF).getString()));
        integratedE.setMessage(new TranslatableText("button.quitconfirm.opt.integrate", (Config.CONFIG.integratedE ? ScreenTexts.ON : ScreenTexts.OFF).getString()));
        reamsE.setMessage(new TranslatableText("button.quitconfirm.opt.reams", (Config.CONFIG.reamsE ? ScreenTexts.ON : ScreenTexts.OFF).getString()));
        dedicatedE.setMessage(new TranslatableText("button.quitconfirm.opt.dedicated", (Config.CONFIG.dedicatedE ? ScreenTexts.ON : ScreenTexts.OFF).getString()));
        shortcutKet.setMessage(new TranslatableText("button.quitconfirm.opt.shortcut", (Config.CONFIG.shortcutKet ? ScreenTexts.ON : ScreenTexts.OFF).getString()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        client.openScreen(parent);
    }
}
