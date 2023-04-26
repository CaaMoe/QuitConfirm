package moe.caa.fabric.quitconfirm.client

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory

@JvmField
val RESOURCE_FIRST_RELOADED: Event<Runnable> = EventFactory.createArrayBacked(
    Runnable::class.java
) { Runnable { it.forEach { it.run() } } }