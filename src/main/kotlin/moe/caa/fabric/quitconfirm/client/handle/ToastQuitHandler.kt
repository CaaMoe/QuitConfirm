package moe.caa.fabric.quitconfirm.client.handle

import moe.caa.fabric.quitconfirm.client.EventResult
import moe.caa.fabric.quitconfirm.client.config.Config
import moe.caa.fabric.quitconfirm.client.toast.QuitToast
import net.minecraft.client.MinecraftClient

class ToastQuitHandler(
    private val message: String
) {
    private var flag: State = State.INACTIVE
    private var startTime: Long = 0;

    fun trigger(): EventResult {
        val millis = System.currentTimeMillis()
        if (flag == State.ACTIVE) {
            if (startTime + Config.gsonConfigInstance.config.toastConfirmDisplayTime < millis) {
                flag = State.INACTIVE
            }
        }

        if (flag == State.INACTIVE) {
            startTime = millis
            flag = State.ACTIVE
            MinecraftClient.getInstance().toastManager.add(
                QuitToast(
                    message,
                    Config.gsonConfigInstance.config.toastConfirmDisplayTime
                )
            )
            return EventResult.CANCEL
        }

        if (Config.gsonConfigInstance.config.toastConfirmStartAliveTime + startTime < millis) {
            if (Config.gsonConfigInstance.config.toastConfirmEndAliveTime + startTime > millis) {
                return EventResult.PASS
            }
        }
        return EventResult.CANCEL
    }

    private enum class State {
        ACTIVE,
        INACTIVE
    }
}