package moe.caa.fabric.quitconfirm.client;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
import dev.isxander.yacl.gui.controllers.slider.LongSliderController;
import moe.caa.fabric.quitconfirm.client.config.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class Screens {
    public static Screen generateConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("设置界面"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("基础配置"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("在什么地方启用什么功能"))
                                .option(Option.createBuilder(Config.ConfirmType.class)
                                        .name(Text.literal("在最终退出游戏时"))
                                        .binding(Config.ConfirmType.SCREEN,
                                                () -> Config.gsonConfigInstance.getConfig().confirmTypeInFinalQuit,
                                                (it) -> Config.gsonConfigInstance.getConfig().confirmTypeInFinalQuit = it)
                                        .controller(it -> new EnumController<>(it, (e -> Text.literal(e.displayName))))
                                        .build())
                                .option(Option.createBuilder(Config.ConfirmType.class)
                                        .name(Text.literal("在退出单人游戏时"))
                                        .binding(Config.ConfirmType.SCREEN,
                                                () -> Config.gsonConfigInstance.getConfig().confirmTypeInSinglePlayer,
                                                (it) -> Config.gsonConfigInstance.getConfig().confirmTypeInSinglePlayer = it)
                                        .controller(it -> new EnumController<>(it, (e -> Text.literal(e.displayName))))
                                        .build())
                                .build())
                        .option(Option.createBuilder(Config.ConfirmType.class)
                                .name(Text.literal("在退出多人游戏时"))
                                .binding(Config.ConfirmType.SCREEN,
                                        () -> Config.gsonConfigInstance.getConfig().confirmTypeInMultiplayer,
                                        (it) -> Config.gsonConfigInstance.getConfig().confirmTypeInMultiplayer = it)
                                .controller(it -> new EnumController<>(it, (e -> Text.literal(e.displayName))))
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("确认界面屏幕设置"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("确认屏幕响应快捷键"))
                                        .binding(true, () -> Config.gsonConfigInstance.getConfig().enableScreenShortcutKey,
                                                (it) -> Config.gsonConfigInstance.getConfig().enableScreenShortcutKey = it)
                                        .controller(BooleanController::new)
                                        .build())
                                .option(Option.createBuilder(long.class)
                                        .name(Text.literal("保持确认屏幕的按钮灰度时间"))
                                        .binding(
                                                1000L, () -> Config.gsonConfigInstance.getConfig().keepDarkInConfirmScreenTime, (it) -> Config.gsonConfigInstance.getConfig().keepDarkInConfirmScreenTime = it)
                                        .controller(e -> new LongSliderController(e, 0, 10000, 500))
                                        .build())
                                .option(Option.createBuilder(Config.ConfirmScreenDisplayTypeEnum.class)
                                        .name(Text.literal("确认界面窗口样式"))
                                        .binding(
                                                Config.ConfirmScreenDisplayTypeEnum.BEDROCK,
                                                () -> Config.gsonConfigInstance.getConfig().confirmScreenDisplayType,
                                                (it) -> Config.gsonConfigInstance.getConfig().confirmScreenDisplayType = it)
                                        .controller(e -> new EnumController<>(e, it -> Text.literal(it.displayName)))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("确认界面土司设置"))
                                .option(Option.createBuilder(long.class)
                                        .name(Text.literal("土司停留时长"))
                                        .binding(
                                                5000L, () -> Config.gsonConfigInstance.getConfig().toastConfirmDisplayTime,
                                                it -> Config.gsonConfigInstance.getConfig().toastConfirmDisplayTime = it)
                                        .controller(e -> new LongSliderController(e, 1000, 10000, 500))
                                        .build())
                                .option(Option.createBuilder(long.class)
                                        .name(Text.literal("土司响应开始时间"))
                                        .binding(
                                                500L, () -> Config.gsonConfigInstance.getConfig().toastConfirmStartAliveTime,
                                                (it) -> Config.gsonConfigInstance.getConfig().toastConfirmStartAliveTime = it)
                                        .controller(e -> new LongSliderController(e, 0, 10000, 500))
                                        .build())
                                .option(Option.createBuilder(long.class)
                                        .name(Text.literal("土司响应结束时间"))
                                        .binding(
                                                5000L,
                                                () -> Config.gsonConfigInstance.getConfig().toastConfirmEndAliveTime,
                                                (it) -> Config.gsonConfigInstance.getConfig().toastConfirmEndAliveTime = it)
                                        .controller(e -> new LongSliderController(e, 1000, 10000, 500))
                                        .build())
                                .build())
                        .build())
                .save(Config.gsonConfigInstance::save)
                .build()
                .generateScreen(parent);
    }
}
