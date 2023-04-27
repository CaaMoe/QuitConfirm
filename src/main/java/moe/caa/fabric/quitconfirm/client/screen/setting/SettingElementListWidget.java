package moe.caa.fabric.quitconfirm.client.screen.setting;

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

public class SettingElementListWidget extends ElementListWidget<SettingElementListWidget.Entry>{
    public SettingElementListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
    }

    @Override
    public int addEntry(Entry entry) {
        return super.addEntry(entry);
    }

    public class CategoryEntry extends Entry {
        private final Text text;
        private final int textWidth;
        private final int textColor;

        public CategoryEntry(Text text, int color) {
            this.text = text;
            this.textWidth = SettingElementListWidget.this.client.textRenderer.getWidth(text);
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
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            // 居中
            assert client.currentScreen != null;
            int dx = client.currentScreen.width / 2 - this.textWidth / 2;
            int dy = y + entryHeight;
            client.textRenderer.draw(matrices, this.text, dx, dy - client.textRenderer.fontHeight, textColor);
        }
    }

    public class InputListEntry extends Entry {
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
            client.textRenderer.draw(matrices, describeText, x, (int) (y + client.textRenderer.fontHeight / 2.0), 16777215);
            this.fieldWidget.setX(entryWidth - fieldWidget.getWidth() + x);
            this.fieldWidget.setY(y);
            this.fieldWidget.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return Collections.singletonList(fieldWidget);
        }
    }


    public class ButtonListEntry extends Entry {
        private final ButtonWidget button;
        private final Text describeText;

        public ButtonListEntry(ButtonWidget settleButton, Text describeText) {
            this.button = settleButton;
            this.describeText = describeText;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(button);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            SettingElementListWidget.this.client.textRenderer.draw(matrices, describeText, x, y + 5, 16777215);
            this.button.setX(entryWidth - button.getWidth() + x);
            this.button.setY(y);
            this.button.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return this.button.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.button.mouseReleased(mouseX, mouseY, button);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return Collections.singletonList(button);
        }
    }

    public abstract static class Entry extends ElementListWidget.Entry<Entry> { }
}
