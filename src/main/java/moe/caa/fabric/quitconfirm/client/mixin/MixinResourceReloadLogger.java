package moe.caa.fabric.quitconfirm.client.mixin;

import net.minecraft.client.resource.ResourceReloadLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static moe.caa.fabric.quitconfirm.client.EventsKt.RESOURCE_FIRST_RELOADED;

@Mixin(ResourceReloadLogger.class)
public abstract class MixinResourceReloadLogger {
    private boolean firstInit = false;

    @Inject(method = "finish", at = @At("RETURN"))
    private void onFinish(CallbackInfo ci) {
        if (!firstInit) {
            RESOURCE_FIRST_RELOADED.invoker().run();
        }
        firstInit = true;
    }
}