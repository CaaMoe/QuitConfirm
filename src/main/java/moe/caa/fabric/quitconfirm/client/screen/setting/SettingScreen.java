package moe.caa.fabric.quitconfirm.client.screen.setting;

import moe.caa.fabric.quitconfirm.client.config.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class SettingScreen extends Screen {
    private final Screen parentScreen;
    private ButtonWidget back;

    public SettingScreen(Screen screen) {
        super(Text.literal("设置"));
        parentScreen = screen;
    }

    @Override
    protected void init() {

        back = ButtonWidget.builder(ScreenTexts.BACK, (button) -> {
            Config.save();
            client.setScreen(parentScreen);
        }).dimensions(this.width / 2 - 100, this.height - 30, 200, 20).build();


        SettingElementListWidget listWidget = new SettingElementListWidget(this.client, this.width, this.height, 30 /* 上边距 */, height - 40/* 下边距 */, 24);
        {
            MutableText category = Text.literal("设置退出二次确认的方式");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Text.literal(Config.config.confirmTypeInFinalQuit.displayName), (button) -> {
                        Config.config.confirmTypeInFinalQuit = Config.config.nextEnum(Config.ConfirmTypeEnum.class, Config.config.confirmTypeInFinalQuit);
                        button.setMessage(Text.literal(Config.config.confirmTypeInFinalQuit.displayName));
                    })
                    .dimensions(0, 0, 40, 20).build(), Text.literal("关闭游戏窗口时的确认方式")));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Text.literal(Config.config.confirmTypeInSinglePlayer.displayName), (button) -> {
                        Config.config.confirmTypeInSinglePlayer = Config.config.nextEnum(Config.ConfirmTypeEnum.class, Config.config.confirmTypeInSinglePlayer);
                        button.setMessage(Text.literal(Config.config.confirmTypeInSinglePlayer.displayName));
                    })
                    .dimensions(0, 0, 40, 20).build(), Text.literal("退出单人游戏时的确认方式")));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Text.literal(Config.config.confirmTypeInMultiplayer.displayName), (button) -> {
                        Config.config.confirmTypeInMultiplayer = Config.config.nextEnum(Config.ConfirmTypeEnum.class, Config.config.confirmTypeInMultiplayer);
                        button.setMessage(Text.literal(Config.config.confirmTypeInMultiplayer.displayName));
                    })
                    .dimensions(0, 0, 40, 20).build(), Text.literal("退出多人游戏时的确认方式")));
        }
        {
            MutableText category = Text.literal("二次确认界面屏幕设定");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Text.literal(Config.config.confirmScreenDisplayType.displayName), (button) -> {
                        Config.config.confirmScreenDisplayType = Config.config.nextEnum(Config.ConfirmScreenDisplayTypeEnum.class, Config.config.confirmScreenDisplayType);
                        button.setMessage(Text.literal(Config.config.confirmScreenDisplayType.displayName));
                    })
                    .dimensions(0, 0, 40, 20).build(), Text.literal("确认屏幕样式")));


            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Config.config.enableScreenShortcutKey ? ScreenTexts.ON : ScreenTexts.OFF, (button) -> {
                        Config.config.enableScreenShortcutKey = !Config.config.enableScreenShortcutKey;
                        button.setMessage(Config.config.enableScreenShortcutKey ? ScreenTexts.ON : ScreenTexts.OFF);
                    })
                    .dimensions(0, 0, 40, 20).build(), Text.literal("允许在二次确认屏幕上使用快捷键")));

            TextFieldWidget keepDark = new TextFieldWidget(client.textRenderer, 0, 0, 38, 20, Text.empty());
            keepDark.setText(String.valueOf(Config.config.keepDarkInConfirmScreenTime));
            keepDark.setChangedListener(new PositiveLongParser(keepDark, (it) -> Config.config.keepDarkInConfirmScreenTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(keepDark, Text.literal("保持确认屏幕按钮不可用时长")));
        }
        {
            MutableText category = Text.literal("二次确认土司设定");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            TextFieldWidget toastDisplayTime = new TextFieldWidget(client.textRenderer, 0, 0, 38, 20, Text.empty());
            toastDisplayTime.setText(String.valueOf(Config.config.toastConfirmDisplayTime));
            toastDisplayTime.setChangedListener(new PositiveLongParser(toastDisplayTime, (it) -> Config.config.toastConfirmDisplayTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastDisplayTime, Text.literal("土司持续时间")));

            TextFieldWidget toastStartAliveTime = new TextFieldWidget(client.textRenderer, 0, 0, 38, 20, Text.empty());
            toastStartAliveTime.setText(String.valueOf(Config.config.toastConfirmStartAliveTime));
            toastStartAliveTime.setChangedListener(new PositiveLongParser(toastStartAliveTime, (it) -> Config.config.toastConfirmStartAliveTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastStartAliveTime, Text.literal("土司响应开始时间")));

            TextFieldWidget toastEndAliveTime = new TextFieldWidget(client.textRenderer, 0, 0, 38, 20, Text.empty());
            toastEndAliveTime.setText(String.valueOf(Config.config.toastConfirmEndAliveTime));
            toastEndAliveTime.setChangedListener(new PositiveLongParser(toastEndAliveTime, (it) -> Config.config.toastConfirmEndAliveTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastEndAliveTime, Text.literal("土司响应结束时间")));
        }
        this.addDrawableChild(listWidget);
        this.addDrawableChild(back);
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
        Config.save();
        client.setScreen(parentScreen);
    }

    class PositiveLongParser implements Consumer<String> {
        private final TextFieldWidget fieldWidget;
        private final Consumer<Long> longConsumer;

        PositiveLongParser(TextFieldWidget fieldWidget, Consumer<Long> longConsumer) {
            this.fieldWidget = fieldWidget;
            this.longConsumer = longConsumer;
        }

        @Override
        public void accept(String s) {
            try {
                long parsed = Long.parseLong(s);
                if (parsed >= 0) {
                    fieldWidget.setEditableColor(14737632);
                    longConsumer.accept(parsed);
                    back.active = true;
                    return;
                }
            } catch (Exception ignored) {
            }
            back.active = false;
            fieldWidget.setEditableColor(16711680);
        }
    }
}
