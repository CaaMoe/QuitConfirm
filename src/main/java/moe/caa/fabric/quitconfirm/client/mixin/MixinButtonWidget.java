package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.screen.ToMainPressAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ButtonWidget.class)
public abstract class MixinButtonWidget {

    @Mutable
    @Shadow @Final protected ButtonWidget.PressAction onPress;

    @Inject(method =
            "<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;" +
                    "Lnet/minecraft/client/gui/widget/ButtonWidget$TooltipSupplier;)V", at = @At("RETURN"))
    private void onInit(int x, int y, int width, int height, Text message, ButtonWidget.PressAction onPress,
                        ButtonWidget.TooltipSupplier tooltipSupplier, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof GameMenuScreen) {
            if (message instanceof TranslatableText translatableText && (translatableText.getKey().equals("menu.returnToMenu") || translatableText.getKey().equals("menu.disconnect"))) {
                this.onPress = new ToMainPressAction(MinecraftClient.getInstance());
            }
        }
    }
}
