package moe.caa.fabric.quitconfirm.client.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import moe.caa.fabric.quitconfirm.client.main.QuitConfirm;
import moe.caa.fabric.quitconfirm.client.screen.confirm.style.BaseStyle;
import moe.caa.fabric.quitconfirm.client.screen.confirm.style.BedrockStyle;
import moe.caa.fabric.quitconfirm.client.screen.confirm.style.ClassicStyle;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;

public class Config {
    public static final Config config = new Config();
    private static final Gson gson = new Gson();
    private static final Path path = Path.of("config/quitconfirm.json");
    public ConfirmTypeEnum confirmTypeInFinalQuit = ConfirmTypeEnum.SCREEN;
    public ConfirmTypeEnum confirmTypeInSinglePlayer = ConfirmTypeEnum.TOAST;
    public ConfirmTypeEnum confirmTypeInMultiplayer = ConfirmTypeEnum.TOAST;
    public boolean enableScreenShortcutKey = true;
    public long keepDarkInConfirmScreenTime = 1000L;
    public ConfirmScreenDisplayTypeEnum confirmScreenDisplayType = ConfirmScreenDisplayTypeEnum.BEDROCK;
    public long toastConfirmDisplayTime = 5000L;
    public long toastConfirmStartAliveTime = 500L;
    public long toastConfirmEndAliveTime = 5000L;

    public static void load() {
        try {
            if (Files.notExists(path)) {
                save();
                return;
            }
            JsonElement element = JsonParser.parseString(Files.readString(path));
            JsonObject object = element.getAsJsonObject();
            if (object.has("confirmTypeInFinalQuit")) {
                config.confirmTypeInFinalQuit = ConfirmTypeEnum.valueOf(object.getAsJsonPrimitive("confirmTypeInFinalQuit").getAsString());
            }
            if (object.has("confirmTypeInSinglePlayer")) {
                config.confirmTypeInSinglePlayer = ConfirmTypeEnum.valueOf(object.getAsJsonPrimitive("confirmTypeInSinglePlayer").getAsString());
            }
            if (object.has("confirmTypeInMultiplayer")) {
                config.confirmTypeInMultiplayer = ConfirmTypeEnum.valueOf(object.getAsJsonPrimitive("confirmTypeInMultiplayer").getAsString());
            }
            if (object.has("enableScreenShortcutKey")) {
                config.enableScreenShortcutKey = object.getAsJsonPrimitive("enableScreenShortcutKey").getAsBoolean();
            }
            if (object.has("keepDarkInConfirmScreenTime")) {
                config.keepDarkInConfirmScreenTime = object.getAsJsonPrimitive("keepDarkInConfirmScreenTime").getAsLong();
            }
            if (object.has("confirmScreenDisplayType")) {
                config.confirmScreenDisplayType = ConfirmScreenDisplayTypeEnum.valueOf(object.getAsJsonPrimitive("confirmScreenDisplayType").getAsString());
            }
            if (object.has("toastConfirmDisplayTime")) {
                config.toastConfirmDisplayTime = object.getAsJsonPrimitive("toastConfirmDisplayTime").getAsLong();
            }
            if (object.has("toastConfirmStartAliveTime")) {
                config.toastConfirmStartAliveTime = object.getAsJsonPrimitive("toastConfirmStartAliveTime").getAsLong();
            }
            if (object.has("toastConfirmEndAliveTime")) {
                config.toastConfirmEndAliveTime = object.getAsJsonPrimitive("toastConfirmEndAliveTime").getAsLong();
            }
        } catch (Exception e){
            QuitConfirm.LOGGER.error("Failed to read " + path, e);
            save();
        }
    }

    public static void save() {
        try {
            Files.writeString(path, gson.toJson(config), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            QuitConfirm.LOGGER.error("Failed to save " + path, e);
        }
    }

    public <T extends Enum<T>> T nextEnum(Class<T> tClass, T value) {
        int ordinal = value.ordinal();
        T[] constants = tClass.getEnumConstants();
        return constants[(ordinal + 1) % constants.length];
    }

    public enum ConfirmScreenDisplayTypeEnum {
        CLASSIC(Text.translatable("config.quitconfirm.screentype.classic"), ClassicStyle::new),
        BEDROCK(Text.translatable("config.quitconfirm.screentype.bedrock"), BedrockStyle::new);
        public final Text displayName;
        public final Supplier<BaseStyle> baseStyleSupplier;

        ConfirmScreenDisplayTypeEnum(Text displayName, Supplier<BaseStyle> baseStyleSupplier) {
            this.displayName = displayName;
            this.baseStyleSupplier = baseStyleSupplier;
        }
    }


    public enum ConfirmTypeEnum {
        TOAST(Text.translatable("config.quitconfirm.confirmtype.toast")),
        SCREEN(Text.translatable("config.quitconfirm.confirmtype.screen")),
        NONE(Text.translatable("config.quitconfirm.confirmtype.none"));
        public final Text displayName;

        ConfirmTypeEnum(Text displayName) {
            this.displayName = displayName;
        }
    }
}
