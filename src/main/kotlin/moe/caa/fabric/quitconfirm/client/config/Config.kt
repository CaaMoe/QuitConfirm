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
    var confirmEnableInFinalQuit: Boolean = true
    @ConfigEntry
    var confirmEnableInSinglePlayer: Boolean = true
    @ConfigEntry
    var confirmEnableInMultiplayer: Boolean = true
    @ConfigEntry
    var enableScreenShortcutKey: Boolean = true
    @ConfigEntry
    var keepDarkInConfirmScreenTime: Long = 1000L
    @ConfigEntry
    var confirmScreenDisplayType: ConfirmScreenDisplayTypeEnum = ConfirmScreenDisplayTypeEnum.BEDROCK


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
}