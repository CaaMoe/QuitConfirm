package moe.caa.fabric.quitconfirm.client.main;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.event.ButtonPressEvent;
import moe.caa.fabric.quitconfirm.client.event.ClientScheduleStopEvent;
import moe.caa.fabric.quitconfirm.client.event.EventResult;
import moe.caa.fabric.quitconfirm.client.handle.ToastQuitHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.text.TranslatableTextContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class QuitConfirm implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("QuitConfirm");
    private final ToastQuitHandler toastInFinalQuitHandler = new ToastQuitHandler("退出这个游戏，请再次操作");
    private final ToastQuitHandler toastInSinglePlayerQuitHandle = new ToastQuitHandler("退出单人游戏，请再次操作");
    private final ToastQuitHandler toastInMultiplayerQuitHandle = new ToastQuitHandler("退出多人游戏，请再次操作");

    @Override
    public void onInitializeClient() {
        Config.load();
        ClientScheduleStopEvent.CLIENT_SCHEDULE_STOP.register(() -> {
            if (Config.config.confirmTypeInFinalQuit == Config.ConfirmTypeEnum.TOAST) {
                return toastInFinalQuitHandler.trigger();
            }
            if (Config.config.confirmTypeInFinalQuit == Config.ConfirmTypeEnum.SCREEN) {
                return toastInFinalQuitHandler.trigger();
            }
            return EventResult.PASS;
        });

        ButtonPressEvent.BUTTON_PRESS.register((button) -> {
            if (!(MinecraftClient.getInstance().currentScreen instanceof GameMenuScreen)) {
                return EventResult.PASS;
            }

            String key = null;
            if (button.getMessage() instanceof TranslatableTextContent) {
                key = ((TranslatableTextContent) button.getMessage()).getKey();
            } else if (button.getMessage().getContent() instanceof TranslatableTextContent) {
                key = ((TranslatableTextContent) button.getMessage().getContent()).getKey();
            }
            if ("menu.returnToMenu".equals(key)) {
                return toastInSinglePlayerQuitHandle.trigger();
            }
            if ("menu.disconnect".equals(key)) {
                return toastInMultiplayerQuitHandle.trigger();
            }
            return EventResult.PASS;
        });
    }
}
