package moe.caa.fabric.quitconfirm.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class ClientScheduleStopEvent {
    public static final Event<ClientScheduleStop> CLIENT_SCHEDULE_STOP = EventFactory.createArrayBacked(ClientScheduleStop.class,
            (callbacks) -> () -> {
                for (ClientScheduleStop callback : callbacks) {
                    if (callback.onScheduleStop() == EventResult.CANCEL) {
                        return EventResult.CANCEL;
                    }
                }
                return EventResult.PASS;
            });

    @FunctionalInterface
    public interface ClientScheduleStop {
        EventResult onScheduleStop();
    }
}
