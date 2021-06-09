package moe.caa.fabric.quitconfirm.client.screen;

import moe.caa.fabric.quitconfirm.client.Config;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SettleScreen extends Screen {
    private final Screen parent;
    private ButtonWidget finalE;
    private ButtonWidget integratedE;
    private ButtonWidget reamsE;
    private ButtonWidget dedicatedE;
    private ButtonWidget shortcutKet;
    private TextFieldWidget keepInActive;
    private ListWidget listWidget;

    private ButtonWidget back;

    public SettleScreen(Screen parent) {
        super(new TranslatableText("gui.quitconfirm.settle.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        finalE = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.finalE = !Config.CONFIG.finalE;
        });
        integratedE = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.integratedE = !Config.CONFIG.integratedE;
        });
        reamsE = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.reamsE = !Config.CONFIG.reamsE;
        });
        dedicatedE = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.dedicatedE = !Config.CONFIG.dedicatedE;
        });
        shortcutKet = new ButtonWidget(0, 0, 44, 20, LiteralText.EMPTY, (buttonWidget) -> {
            Config.CONFIG.shortcutKet = !Config.CONFIG.shortcutKet;
        });
        keepInActive = new TextFieldWidget(SettleScreen.this.client.textRenderer, 0, 0, 42, 20, LiteralText.EMPTY);
        keepInActive.setText(String.valueOf(Config.CONFIG.keepInAction));

        this.addDrawableChild(listWidget = new ListWidget());
        this.addDrawableChild(back = new ButtonWidget(this.width / 2 - 100, this.height - 29, 200, 20, ScreenTexts.BACK, (buttonWidget) -> {
            Config.CONFIG.pushSave();
            client.openScreen(parent);
        }));

        keepInActive.setChangedListener(s -> {
            try {
                long value = Long.parseLong(s);
                if (value >= 0) {
                    keepInActive.setEditableColor(14737632);
                    Config.CONFIG.keepInAction = value;
                    back.active = true;
                    return;
                }
            } catch (Exception ignored) {
            }
            back.active = false;
            keepInActive.setEditableColor(16711680);
        });
    }

    @Override
    public void tick() {
        finalE.setMessage(Config.CONFIG.finalE ? ScreenTexts.ON : ScreenTexts.OFF);
        integratedE.setMessage(Config.CONFIG.integratedE ? ScreenTexts.ON : ScreenTexts.OFF);
        reamsE.setMessage(Config.CONFIG.reamsE ? ScreenTexts.ON : ScreenTexts.OFF);
        dedicatedE.setMessage(Config.CONFIG.dedicatedE ? ScreenTexts.ON : ScreenTexts.OFF);
        shortcutKet.setMessage(Config.CONFIG.shortcutKet ? ScreenTexts.ON : ScreenTexts.OFF);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //this.listWidget.render(matrices, mouseX, mouseY, delta);

        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        drawStringWithShadow(matrices, this.textRenderer, "QuitConfirm v1.3", 2, this.height - 10, 5592405);
        back.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        Config.CONFIG.pushSave();
    }

    public abstract static class Entry extends ElementListWidget.Entry<SettleScreen.Entry> {
    }

    public class ListWidget extends ElementListWidget<SettleScreen.Entry> {

        public ListWidget() {
            super(SettleScreen.this.client, SettleScreen.this.width, SettleScreen.this.height, 43, SettleScreen.this.height - 30, 24);
            this.addEntry(new TextListEntry(new TranslatableText("text.quitconfirm.settle.whenappear"), 11184810));
            this.addEntry(new BooleanListEntry(SettleScreen.this.finalE, new TranslatableText("button.quitconfirm.opt.final"), null));
            this.addEntry(new BooleanListEntry(SettleScreen.this.integratedE, new TranslatableText("button.quitconfirm.opt.integrate"), null));
            this.addEntry(new BooleanListEntry(SettleScreen.this.reamsE, new TranslatableText("button.quitconfirm.opt.reams"), null));
            this.addEntry(new BooleanListEntry(SettleScreen.this.dedicatedE, new TranslatableText("button.quitconfirm.opt.dedicated"), null));
            this.addEntry(new TextListEntry(new TranslatableText("text.quitconfirm.settle.other"), 11184810));
            this.addEntry(new BooleanListEntry(SettleScreen.this.shortcutKet, new TranslatableText("button.quitconfirm.opt.shortcut"), null));
            this.addEntry(new TextInputListEntry(SettleScreen.this.keepInActive, new TranslatableText("button.quitconfirm.opt.keepinactive"), null));
        }
    }

    public class BooleanListEntry extends Entry {
        private final ButtonWidget settleButton;
        private final List<OrderedText> describeText;
        private final List<OrderedText> hoverText;

        public BooleanListEntry(ButtonWidget settleButton, Text describeText, Text hoverText) {
            this.settleButton = settleButton;
            this.describeText = SettleScreen.this.client.textRenderer.wrapLines(describeText, 170);
            this.hoverText = null;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(settleButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            if (this.describeText.size() == 1) {
                SettleScreen.this.client.textRenderer.draw(matrices, describeText.get(0), x, y + 5, 16777215);
            } else {
                SettleScreen.this.client.textRenderer.draw(matrices, describeText.get(0), x, y, 16777215);
                SettleScreen.this.client.textRenderer.draw(matrices, describeText.get(1), x, y + 10, 16777215);
            }

            this.settleButton.x = entryWidth - 45 + x;
            this.settleButton.y = y;
            this.settleButton.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return settleButton.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return settleButton.mouseReleased(mouseX, mouseY, button);
        }

        @Override
        public List<? extends Selectable> method_37025() {
            return Collections.singletonList(settleButton);
        }
    }

    public class TextInputListEntry extends Entry {
        private final TextFieldWidget fieldWidget;
        private final Text describeText;
        private final List<OrderedText> hoverText;

        public TextInputListEntry(TextFieldWidget fieldWidget, Text describeText, Text hoverText) {
            this.fieldWidget = fieldWidget;
            this.describeText = describeText;
            this.hoverText = null;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(fieldWidget);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            SettleScreen.this.client.textRenderer.draw(matrices, describeText, x, y + 5, 16777215);
            this.fieldWidget.x = entryWidth - 44 + x;
            this.fieldWidget.y = y;
            this.fieldWidget.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        public List<? extends Selectable> method_37025() {
            return Collections.singletonList(fieldWidget);
        }
    }

    public class TextListEntry extends Entry {
        private final Text text;
        private final int textWidth;
        private final int color;

        public TextListEntry(Text text, int color) {
            this.text = text;
            this.textWidth = SettleScreen.this.client.textRenderer.getWidth(this.text);
            this.color = color;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }

        @Override
        public boolean changeFocus(boolean lookForwards) {
            return false;
        }

        @SuppressWarnings("all")
        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer renderer = SettleScreen.this.client.textRenderer;
            double startX = (SettleScreen.this.client.currentScreen.width / 2.0 - this.textWidth / 2.0);
            int startY = y + entryHeight - 10;
            renderer.draw(matrices, text, (float) startX, startY, color);
        }

        @Override
        public List<? extends Selectable> method_37025() {
            return new ArrayList<>(0);
        }
    }
}
