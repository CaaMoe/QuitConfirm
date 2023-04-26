package moe.caa.fabric.quitconfirm.client.main

import moe.caa.fabric.quitconfirm.client.RESOURCE_FIRST_RELOADED
import moe.caa.fabric.quitconfirm.client.config.Config
import moe.caa.fabric.quitconfirm.client.toast.StartToast
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import java.lang.management.ManagementFactory

class QuitConfirm : ClientModInitializer {


    override fun onInitializeClient() {
        RESOURCE_FIRST_RELOADED.register {
            if (Config.gsonConfigInstance.config.enableStartTimeConsumeToast) {
                val time = (System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().startTime) / 1000.0
                MinecraftClient.getInstance().toastManager.add(
                    StartToast(time, Config.gsonConfigInstance.config.startTimToastKeepTime)
                )
            }
        }
    }
}