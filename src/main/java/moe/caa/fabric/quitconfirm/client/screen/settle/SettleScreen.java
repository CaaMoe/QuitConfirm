package moe.caa.fabric.quitconfirm.client.screen.settle;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.main.QuitConfirm;
import moe.caa.fabric.quitconfirm.client.screen.confirm.StyleFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class SettleScreen extends Screen {
    private final Screen parentScreen;
    private ButtonWidget inFinal;
    private ButtonWidget inIntegrated;
    private ButtonWidget inReams;
    private ButtonWidget inDedicated;
    private ButtonWidget shortcutKet;
    private TextFieldWidget keepInActive;
    private ButtonWidget confirmScreenStyle;

    private ButtonWidget back;

    public SettleScreen(Screen parentScreen) {
        super(new TranslatableText("gui.quitconfirm.settle.title"));
        this.parentScreen = parentScreen;
    }

    @Override
    public void tick() {
        inFinal.setMessage(Config.config.get().inFinal ? ScreenTexts.ON : ScreenTexts.OFF);
        inIntegrated.setMessage(Config.config.get().inIntegrated ? ScreenTexts.ON : ScreenTexts.OFF);
        inReams.setMessage(Config.config.get().inReams ? ScreenTexts.ON : ScreenTexts.OFF);
        inDedicated.setMessage(Config.config.get().inDedicated ? ScreenTexts.ON : ScreenTexts.OFF);
        shortcutKet.setMessage(Config.config.get().shortcutKet ? ScreenTexts.ON : ScreenTexts.OFF);
        confirmScreenStyle.setMessage(StyleFactory.createStyleFromConfig().getDisplayName());
    }

    @Override
    protected void init() {
        initWidgets();
        initListEntry();
    }

    private void initWidgets() {
        inFinal = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) ->
                Config.config.get().inFinal = !Config.config.get().inFinal);

        inIntegrated = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) ->
                Config.config.get().inIntegrated = !Config.config.get().inIntegrated);

        inReams = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) ->
                Config.config.get().inReams = !Config.config.get().inReams);

        inDedicated = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) ->
                Config.config.get().inDedicated = !Config.config.get().inDedicated);

        shortcutKet = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) ->
                Config.config.get().shortcutKet = !Config.config.get().shortcutKet);

        confirmScreenStyle = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) ->
                StyleFactory.nextStyleFromConfig());

        keepInActive = new TextFieldWidget(this.client.textRenderer, 0, 0, 42, 20, LiteralText.EMPTY);
        keepInActive.setText(String.valueOf(Config.config.get().keepInAction));

        back = new ButtonWidget(this.width / 2 - 100, this.height - 30, 200, 20, ScreenTexts.BACK, (buttonWidget) -> {
            Config.saveConfig();
            client.setScreen(parentScreen);
        });
        this.addDrawableChild(back);

        keepInActive.setChangedListener(s -> {
            try {
                long value = Long.parseLong(s);
                if (value >= 0) {
                    keepInActive.setEditableColor(14737632);
                    Config.config.get().keepInAction = value;
                    this.back.active = true;
                    return;
                }
            } catch (Exception ignored) {
            }
            this.back.active = false;
            keepInActive.setEditableColor(16711680);
        });
    }

    private void initListEntry() {
        /* 上边距 */
        /* 下边距 */
        SettleListWidget listWidget = new SettleListWidget(this.client, this.width, this.height, 30 /* 上边距 */, height - 40/* 下边距 */, 24);

        listWidget.addEntry(listWidget.new CategoryEntry(new TranslatableText("text.quitconfirm.settle.whenappear"), 11184810));
        listWidget.addEntry(listWidget.new ButtonListEntry(inFinal, new TranslatableText("button.quitconfirm.opt.final")));
        listWidget.addEntry(listWidget.new ButtonListEntry(inIntegrated, new TranslatableText("button.quitconfirm.opt.integrate")));
        listWidget.addEntry(listWidget.new ButtonListEntry(inReams, new TranslatableText("button.quitconfirm.opt.reams")));
        listWidget.addEntry(listWidget.new ButtonListEntry(inDedicated, new TranslatableText("button.quitconfirm.opt.dedicated")));

        listWidget.addEntry(listWidget.new CategoryEntry(new TranslatableText("text.quitconfirm.settle.other"), 11184810));
        listWidget.addEntry(listWidget.new ButtonListEntry(confirmScreenStyle, new TranslatableText("button.quitconfirm.opt.style")));
        listWidget.addEntry(listWidget.new ButtonListEntry(shortcutKet, new TranslatableText("button.quitconfirm.opt.shortcut")));
        listWidget.addEntry(listWidget.new InputListEntry(keepInActive, new TranslatableText("button.quitconfirm.opt.keepinactive")));
        this.addDrawableChild(listWidget);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        drawStringWithShadow(matrices, this.textRenderer, QuitConfirm.DO_NAME, 2, this.height - 10, 5592405);
        back.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        Config.saveConfig();
    }
}
