package moe.caa.fabric.quitconfirm.client.main

import moe.caa.fabric.quitconfirm.client.BUTTON_PRESS
import moe.caa.fabric.quitconfirm.client.CLIENT_SCHEDULE_STOP
import moe.caa.fabric.quitconfirm.client.EventResult
import moe.caa.fabric.quitconfirm.client.RESOURCE_FIRST_RELOADED
import moe.caa.fabric.quitconfirm.client.config.Config
import moe.caa.fabric.quitconfirm.client.handle.ToastQuitHandler
import moe.caa.fabric.quitconfirm.client.toast.StartToast
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.GameMenuScreen
import net.minecraft.text.MutableText
import net.minecraft.text.TranslatableTextContent
import java.lang.management.ManagementFactory

class QuitConfirm : ClientModInitializer {
    private val toastInFinalQuitHandler: ToastQuitHandler = ToastQuitHandler("退出这个游戏，请再次操作")
    private val toastInSinglePlayerQuitHandle: ToastQuitHandler = ToastQuitHandler("退出单人游戏，请再次操作")
    private val toastInMultiplayerQuitHandle: ToastQuitHandler = ToastQuitHandler("退出多人游戏，请再次操作")

    override fun onInitializeClient() {

        RESOURCE_FIRST_RELOADED.register {
            if (Config.gsonConfigInstance.config.enableStartTimeConsumeToast) {
                val time = (System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().startTime) / 1000.0
                MinecraftClient.getInstance().toastManager.add(
                    StartToast(time, Config.gsonConfigInstance.config.startTimToastKeepTime)
                )
            }
        }

        CLIENT_SCHEDULE_STOP.register {
            if (Config.gsonConfigInstance.config.confirmTypeInFinalQuit == Config.ConfirmType.TOAST) {
                return@register toastInFinalQuitHandler.trigger()
            }
            if (Config.gsonConfigInstance.config.confirmTypeInFinalQuit == Config.ConfirmType.SCREEN) {
                return@register toastInFinalQuitHandler.trigger()
            }
            return@register EventResult.PASS
        }

        BUTTON_PRESS.register {
            if (MinecraftClient.getInstance().currentScreen !is GameMenuScreen) {
                return@register EventResult.PASS
            }

            var key: String? = null
            if (it.message is TranslatableTextContent) {
                key = (it.message as TranslatableTextContent).key
            } else if (it.message is MutableText && it.message.content is TranslatableTextContent) {
                key = (it.message.content as TranslatableTextContent).key
            }

            if (key.equals("menu.returnToMenu")) {
                return@register toastInSinglePlayerQuitHandle.trigger()
            }
            if (key.equals("menu.disconnect")) {
                return@register toastInMultiplayerQuitHandle.trigger()
            }
            EventResult.PASS
        }
    }
}