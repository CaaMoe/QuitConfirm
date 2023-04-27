package moe.caa.fabric.quitconfirm.client.handle;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.event.EventResult;
import moe.caa.fabric.quitconfirm.client.toast.QuitToast;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ToastQuitHandler {
    private final Text message;
    private State state = State.INACTIVE;
    private long startTime = 0;

    public ToastQuitHandler(Text message) {
        this.message = message;
    }

    public EventResult trigger() {
        long millis = System.currentTimeMillis();
        if (state == State.ACTIVE) {
            if (startTime + Config.config.toastConfirmDisplayTime < millis) {
                state = State.INACTIVE;
            }
        }

        if (state == State.INACTIVE) {
            startTime = millis;
            state = State.ACTIVE;
            MinecraftClient.getInstance().getToastManager().add(
                    new QuitToast(message, Config.config.toastConfirmDisplayTime)
            );
            return EventResult.CANCEL;
        }
        if (Config.config.toastConfirmStartAliveTime + startTime < millis) {
            if (Config.config.toastConfirmEndAliveTime + startTime > millis) {
                return EventResult.PASS;
            }
        }
        return EventResult.CANCEL;
    }

    private enum State {
        ACTIVE,
        INACTIVE
    }
}
