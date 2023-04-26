package moe.caa.fabric.quitconfirm.client

import dev.isxander.yacl.api.ConfigCategory
import dev.isxander.yacl.api.Option
import dev.isxander.yacl.api.OptionGroup
import dev.isxander.yacl.api.YetAnotherConfigLib
import dev.isxander.yacl.gui.controllers.BooleanController
import dev.isxander.yacl.gui.controllers.cycling.EnumController
import dev.isxander.yacl.gui.controllers.slider.LongSliderController
import moe.caa.fabric.quitconfirm.client.config.Config
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

fun generateConfigScreen(parent: Screen): Screen = YetAnotherConfigLib.createBuilder()
    .title(Text.literal("设置界面"))
    .category(ConfigCategory.createBuilder()
        .name(Text.literal("基础配置"))
        .group(OptionGroup.createBuilder()
            .name(Text.literal("在什么地方启用什么功能"))
            .option(Option.createBuilder(Config.ConfirmType::class.java)
                .name(Text.literal("在最终退出游戏时"))
                .binding(
                    Config.ConfirmType.SCREEN,
                    { return@binding Config.gsonConfigInstance.config.confirmTypeInFinalQuit }) {
                    Config.gsonConfigInstance.config.confirmTypeInFinalQuit = it
                }
                .controller { it -> EnumController(it) { Text.literal(it.displayName) } }
                .build())
            .option(Option.createBuilder(Config.ConfirmType::class.java)
                .name(Text.literal("在退出单人游戏时"))
                .binding(
                    Config.ConfirmType.TOAST,
                    { return@binding Config.gsonConfigInstance.config.confirTypeInSinglePlayer }) {
                    Config.gsonConfigInstance.config.confirTypeInSinglePlayer = it
                }
                .controller { it -> EnumController(it) { Text.literal(it.displayName) } }
                .build())
            .option(Option.createBuilder(Config.ConfirmType::class.java)
                .name(Text.literal("在退出多人游戏时"))
                .binding(
                    Config.ConfirmType.TOAST,
                    { return@binding Config.gsonConfigInstance.config.confirmTypeInMultiplayer }) {
                    Config.gsonConfigInstance.config.confirmTypeInMultiplayer = it
                }
                .controller { it -> EnumController(it) { Text.literal(it.displayName) } }
                .build())
            .build())
        .group(OptionGroup.createBuilder()
            .name(Text.of("确认界面屏幕设置"))
            .option(Option.createBuilder(Boolean::class.javaPrimitiveType)
                .name(Text.literal("确认屏幕响应快捷键"))
                .binding(
                    true, { return@binding Config.gsonConfigInstance.config.enableScreenShortcutKey }) {
                    Config.gsonConfigInstance.config.enableScreenShortcutKey = it
                }
                .controller { BooleanController(it) }
                .build())
            .option(Option.createBuilder(Long::class.javaPrimitiveType)
                .name(Text.literal("保持确认屏幕的按钮灰度时间"))
                .binding(
                    1000L, { return@binding Config.gsonConfigInstance.config.keepDarkInConfirmScreenTime }) {
                    Config.gsonConfigInstance.config.keepDarkInConfirmScreenTime = it
                }
                .controller { LongSliderController(it, 0, 10000, 500) }
                .build())
            .option(Option.createBuilder(Config.ConfirmScreenDisplayTypeEnum::class.java)
                .name(Text.literal("确认界面窗口样式"))
                .binding(
                    Config.ConfirmScreenDisplayTypeEnum.BEDROCK,
                    { return@binding Config.gsonConfigInstance.config.confirmScreenDisplayType }) {
                    Config.gsonConfigInstance.config.confirmScreenDisplayType = it
                }
                .controller { it -> EnumController(it) { Text.literal(it.displayName) } }
                .build())
            .build())
        .group(OptionGroup.createBuilder()
            .name(Text.of("确认界面土司设置"))
            .option(Option.createBuilder(Long::class.javaPrimitiveType)
                .name(Text.literal("土司停留时长"))
                .binding(
                    5000L, { return@binding Config.gsonConfigInstance.config.toastConfirmDisplayTime }) {
                    Config.gsonConfigInstance.config.toastConfirmDisplayTime = it
                }
                .controller { LongSliderController(it, 1000, 10000, 500) }
                .build())
            .option(Option.createBuilder(Long::class.javaPrimitiveType)
                .name(Text.literal("土司响应开始时间"))
                .binding(
                    500L, { return@binding Config.gsonConfigInstance.config.toastConfirmStartAliveTime }) {
                    Config.gsonConfigInstance.config.toastConfirmStartAliveTime = it
                }
                .controller { LongSliderController(it, 0, 10000, 500) }
                .build())
            .option(Option.createBuilder(Long::class.javaPrimitiveType)
                .name(Text.literal("土司响应结束时间"))
                .binding(
                    5000L,
                    { return@binding Config.gsonConfigInstance.config.toastConfirmEndAliveTime }) {
                    Config.gsonConfigInstance.config.toastConfirmEndAliveTime = it
                }
                .controller { LongSliderController(it, 1000, 10000, 500) }
                .build())
            .build())
        .build())
    .category(ConfigCategory.createBuilder()
        .name(Text.literal("杂项配置"))
        .group(OptionGroup.createBuilder()
            .name(Text.literal("启动耗时提醒"))
            .option(Option.createBuilder(Boolean::class.javaPrimitiveType)
                .name(Text.literal("启用启动耗时提醒"))
                .binding(
                    true,
                    { return@binding Config.gsonConfigInstance.config.enableStartTimeConsumeToast }) {
                    Config.gsonConfigInstance.config.enableStartTimeConsumeToast = it
                }
                .controller { BooleanController(it) }
                .build())

            .option(Option.createBuilder(Long::class.javaPrimitiveType)
                .name(Text.literal("耗时提醒土司时长"))
                .binding(
                    5000L,
                    { return@binding Config.gsonConfigInstance.config.startTimToastKeepTime }) {
                    Config.gsonConfigInstance.config.startTimToastKeepTime = it
                }
                .controller { LongSliderController(it, 1000, 10000, 500) }
                .build())
            .build())
        .build())
    .save { Config.gsonConfigInstance.save() }
    .build()
    .generateScreen(parent)