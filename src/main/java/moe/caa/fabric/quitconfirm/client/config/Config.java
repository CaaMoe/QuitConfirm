package moe.caa.fabric.quitconfirm.client.config;

import com.google.gson.Gson;
import moe.caa.fabric.quitconfirm.client.main.QuitConfirm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Config {
    public static Config config = new Config();
    private static final Gson gson = new Gson();
    private static final Path path = Path.of("config/quitconfirm.json");

    public ConfirmType confirmTypeInFinalQuit = ConfirmType.SCREEN;
    public ConfirmType confirmTypeInSinglePlayer = ConfirmType.TOAST;
    public ConfirmType confirmTypeInMultiplayer = ConfirmType.TOAST;
    public boolean enableScreenShortcutKey = true;
    public long keepDarkInConfirmScreenTime = 1000L;
    public ConfirmScreenDisplayTypeEnum confirmScreenDisplayType = ConfirmScreenDisplayTypeEnum.BEDROCK;
    public long toastConfirmDisplayTime = 5000L;
    public long toastConfirmStartAliveTime = 500L;
    public long toastConfirmEndAliveTime = 5000L;

    public static void load(){
        try {
            if (Files.notExists(path)){
                save();
                return;
            }
            config = gson.fromJson(Files.readString(path), Config.class);
        } catch (Exception e){
            QuitConfirm.LOGGER.error("Failed to read " + path, e);
        }
    }

    public static void save(){
        try {
            Files.writeString(path, gson.toJson(config), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            QuitConfirm.LOGGER.error("Failed to save " + path, e);
        }
    }

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
