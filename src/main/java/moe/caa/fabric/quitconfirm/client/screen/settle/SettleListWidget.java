package moe.caa.fabric.quitconfirm.client.screen.settle;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.List;

public class SettleListWidget extends ElementListWidget<SettleListWidget.Entry> {

    public SettleListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
    }

    @Override
    public int addEntry(Entry entry) {
        return super.addEntry(entry);
    }

    public abstract static class Entry extends ElementListWidget.Entry<Entry> {
    }

    public class ButtonListEntry extends Entry {
        private final ButtonWidget settleButton;
        private final Text describeText;

        public ButtonListEntry(ButtonWidget settleButton, Text describeText) {
            this.settleButton = settleButton;
            this.describeText = describeText;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(settleButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            client.textRenderer.draw(matrices, describeText, x, y + 5, 16777215);
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
        public List<? extends Selectable> selectableChildren() {
            return Collections.singletonList(settleButton);
        }

        @Override
        public boolean changeFocus(boolean lookForwards) {
            return false;
        }
    }

    public class InputListEntry extends SettleListWidget.Entry {
        private final TextFieldWidget fieldWidget;
        private final Text describeText;

        public InputListEntry(TextFieldWidget fieldWidget, Text describeText) {
            this.fieldWidget = fieldWidget;
            this.describeText = describeText;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(fieldWidget);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            client.textRenderer.draw(matrices, describeText, x, y + 5, 16777215);
            this.fieldWidget.x = entryWidth - 44 + x;
            this.fieldWidget.y = y;
            this.fieldWidget.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return Collections.singletonList(fieldWidget);
        }

        @Override
        public boolean changeFocus(boolean lookForwards) {
            return false;
        }
    }

    public class CategoryEntry extends SettleListWidget.Entry {
        private final Text text;
        private final int textWidth;
        private final int textColor;

        public CategoryEntry(Text text, int color) {
            this.text = text;
            this.textWidth = SettleListWidget.this.client.textRenderer.getWidth(text);
            this.textColor = color;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(new Selectable() {
                public SelectionType getType() {
                    return SelectionType.HOVERED;
                }

                public void appendNarrations(NarrationMessageBuilder builder) {
                    builder.put(NarrationPart.TITLE, text);
                }
            });
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }

        @Override
        public boolean changeFocus(boolean lookForwards) {
            return false;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            // 居中
            int dx = client.currentScreen.width / 2 - this.textWidth / 2;
            int dy = y + entryHeight;
            client.textRenderer.draw(matrices, this.text, dx, dy - 10, textColor);
        }
    }
}
