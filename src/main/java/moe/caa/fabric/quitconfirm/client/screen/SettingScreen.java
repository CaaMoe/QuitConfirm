package moe.caa.fabric.quitconfirm.client.screen;

import moe.caa.fabric.quitconfirm.client.config.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class SettingScreen extends Screen {
    private final Screen parentScreen;

    public SettingScreen(Screen screen) {
        super(Text.literal("设置"));
        parentScreen = screen;
    }

    @Override
    protected void init() {
        SettingElementListWidget listWidget = new SettingElementListWidget(this.client, this.width, this.height, 30 /* 上边距 */, height - 40/* 下边距 */, 24);

        listWidget.addEntry(listWidget.new CategoryEntry(Text.literal("设置退出二次确认的方式"), 11184810));

        listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                .builder(Text.literal(Config.ConfirmType.TOAST.displayName), (button) -> {})
                .dimensions(0, 0, 35, 20).build(), Text.literal("关闭游戏窗口时的确认方式")));
        listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                .builder(Text.literal(Config.ConfirmType.TOAST.displayName), (button) -> {})
                .dimensions(0, 0, 35, 20).build(), Text.literal("退出单人游戏时的确认方式")));
        listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                .builder(Text.literal(Config.ConfirmType.TOAST.displayName), (button) -> {})
                .dimensions(0, 0, 35, 20).build(), Text.literal("退出多人游戏时的确认方式")));

        this.addDrawableChild(listWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredTextWithShadow(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);

        MutableText literal = Text.literal("QuitConfirm");
        drawTextWithShadow(matrices, textRenderer, literal, 2, this.height - textRenderer.fontHeight, 5592405);
    }

    @Override
    public void close() {
        client.setScreen(parentScreen);
    }
}
