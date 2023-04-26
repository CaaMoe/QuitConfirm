package moe.caa.fabric.quitconfirm.client.config

import dev.isxander.yacl.config.ConfigEntry
import dev.isxander.yacl.config.GsonConfigInstance
import kotlin.io.path.Path


class Config {
    companion object {
        @JvmStatic
        val gsonConfigInstance: GsonConfigInstance<Config> = GsonConfigInstance.createBuilder(Config::class.java)
            .setPath(Path("config/quitconfirm.json"))
            .build()
    }

    @ConfigEntry
    var confirmTypeInFinalQuit: ConfirmType = ConfirmType.SCREEN

    @ConfigEntry
    var confirTypeInSinglePlayer: ConfirmType = ConfirmType.TOAST

    @ConfigEntry
    var confirmTypeInMultiplayer: ConfirmType = ConfirmType.TOAST

    @ConfigEntry
    var enableScreenShortcutKey: Boolean = true

    @ConfigEntry
    var keepDarkInConfirmScreenTime: Long = 1000L

    @ConfigEntry
    var confirmScreenDisplayType: ConfirmScreenDisplayTypeEnum = ConfirmScreenDisplayTypeEnum.BEDROCK

    @ConfigEntry
    var toastConfirmDisplayTime: Long = 5000L;

    @ConfigEntry
    var toastConfirmStartAliveTime: Long = 500L;

    @ConfigEntry
    var toastConfirmEndAliveTime: Long = 5000L;


    @ConfigEntry
    var enableStartTimeConsumeToast: Boolean = true

    @ConfigEntry
    var startTimToastKeepTime: Long = 5000L

    enum class ConfirmScreenDisplayTypeEnum(
        val displayName: String
    ) {
        CLASSIC("经典"),
        CLASSIC_OPAQUE("经典(不透明)"),
        BEDROCK("基岩"),
        BEDROCK_OPAQUE("基岩(不透明)");
    }

    enum class ConfirmType(
        val displayName: String
    ) {
        TOAST("土司"),
        SCREEN("屏幕"),
        NONE("关闭")
    }
}