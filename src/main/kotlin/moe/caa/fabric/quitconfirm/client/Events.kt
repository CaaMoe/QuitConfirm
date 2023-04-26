package moe.caa.fabric.quitconfirm.client

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.client.gui.widget.ButtonWidget

@JvmField
val RESOURCE_FIRST_RELOADED: Event<Runnable> = EventFactory.createArrayBacked(
    Runnable::class.java
) { Runnable { it.forEach { it.run() } } }


@JvmField
val CLIENT_SCHEDULE_STOP: Event<ClientScheduleStop> = EventFactory.createArrayBacked(
    ClientScheduleStop::class.java
) {  callbacks: Array<ClientScheduleStop> ->
    ClientScheduleStop {
        callbacks.forEach {
            if(it.onScheduleStop() == EventResult.CANCEL){
                return@ClientScheduleStop EventResult.CANCEL
            }
        }
        return@ClientScheduleStop EventResult.PASS
    }
}

@JvmField
val BUTTON_PRESS: Event<ButtonWidgetPress> = EventFactory.createArrayBacked(
    ButtonWidgetPress::class.java
) {  callbacks: Array<ButtonWidgetPress> ->
    ButtonWidgetPress { button ->
        callbacks.forEach {
            if(it.onPress(button) == EventResult.CANCEL){
                return@ButtonWidgetPress EventResult.CANCEL
            }
        }
        return@ButtonWidgetPress EventResult.PASS
    }
}

fun interface ButtonWidgetPress {
    fun onPress(buttonWidget: ButtonWidget): EventResult
}

fun interface ClientScheduleStop {
    fun onScheduleStop(): EventResult
}

enum class EventResult  {
    CANCEL,
    PASS
}