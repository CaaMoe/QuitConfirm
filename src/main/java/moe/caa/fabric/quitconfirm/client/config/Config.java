package moe.caa.fabric.quitconfirm.client.config;

import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.GsonConfigInstance;

public class Config {
    public static final GsonConfigInstance<Config> gsonConfigInstance = GsonConfigInstance.<Config>createBuilder(
            Config.class
    ).build();

    @ConfigEntry
    public ConfirmType confirmTypeInFinalQuit = ConfirmType.SCREEN;
    @ConfigEntry
    public ConfirmType confirmTypeInSinglePlayer = ConfirmType.TOAST;
    @ConfigEntry
    public ConfirmType confirmTypeInMultiplayer = ConfirmType.TOAST;
    @ConfigEntry
    public boolean enableScreenShortcutKey = true;
    @ConfigEntry
    public long keepDarkInConfirmScreenTime = 1000L;
    @ConfigEntry
    public ConfirmScreenDisplayTypeEnum confirmScreenDisplayType = ConfirmScreenDisplayTypeEnum.BEDROCK;
    @ConfigEntry
    public long toastConfirmDisplayTime = 5000L;
    @ConfigEntry
    public long toastConfirmStartAliveTime = 500L;
    @ConfigEntry
    public long toastConfirmEndAliveTime = 5000L;
    @ConfigEntry
    public boolean enableStartTimeConsumeToast = true;
    @ConfigEntry
    public long startTimToastKeepTime = 5000L;


    public enum ConfirmScreenDisplayTypeEnum {
        CLASSIC("经典"),
        CLASSIC_OPAQUE("经典(不透明)"),
        BEDROCK("基岩"),
        BEDROCK_OPAQUE("基岩(不透明)");
        public final String displayName;

        ConfirmScreenDisplayTypeEnum(String displayName) {
            this.displayName = displayName;
        }
    }


    public enum ConfirmType {
        TOAST("土司"),
        SCREEN("屏幕"),
        NONE("关闭");
        public final String displayName;

        ConfirmType(String displayName) {
            this.displayName = displayName;
        }
    }
}
