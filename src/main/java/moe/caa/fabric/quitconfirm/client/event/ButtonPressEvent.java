package moe.caa.fabric.quitconfirm.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ButtonPressEvent {
    public static final Event<ButtonPress> BUTTON_PRESS = EventFactory.createArrayBacked(ButtonPress.class,
            (callbacks) -> (button) -> {
                for (ButtonPress callback : callbacks) {
                    if (callback.onPress(button) == EventResult.CANCEL) {
                        return EventResult.CANCEL;
                    }
                }
                return EventResult.PASS;
            });

    @FunctionalInterface
    public interface ButtonPress {
        EventResult onPress(ButtonWidget widget);
    }
}
