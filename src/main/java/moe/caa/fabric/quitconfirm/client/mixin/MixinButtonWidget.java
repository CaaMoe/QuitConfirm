package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.EventResult;
import moe.caa.fabric.quitconfirm.client.EventsKt;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ButtonWidget.class)
public class MixinButtonWidget {
    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void onOnPress(CallbackInfo ci) {
        EventResult result = EventsKt.BUTTON_PRESS.invoker().onPress((ButtonWidget)(Object)this);
        if(result == EventResult.CANCEL){
            ci.cancel();
        }
    }
}
