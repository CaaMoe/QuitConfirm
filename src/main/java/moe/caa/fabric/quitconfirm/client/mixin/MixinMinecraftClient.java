package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.event.ClientScheduleStopEvent;
import moe.caa.fabric.quitconfirm.client.event.EventResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow @Final private Window window;

    @Inject(method = "scheduleStop", at = @At("HEAD"), cancellable = true)
    private void onScheduleStop(CallbackInfo ci){
        EventResult result = ClientScheduleStopEvent.CLIENT_SCHEDULE_STOP.invoker().onScheduleStop();
        if(result == EventResult.CANCEL){
            GLFW.glfwSetWindowShouldClose(this.window.getHandle(), false);
            ci.cancel();
        }
    }
}
