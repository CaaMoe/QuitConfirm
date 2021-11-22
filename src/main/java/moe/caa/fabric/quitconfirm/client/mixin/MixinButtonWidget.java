package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.screen.confirm.ConfirmScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ButtonWidget.class)
public abstract class MixinButtonWidget extends PressableWidget {

    @Shadow
    @Final
    protected ButtonWidget.PressAction onPress;

    public MixinButtonWidget(int i, int j, int k, int l, Text text) {
        super(i, j, k, l, text);
    }

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void onOnPress(CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof GameMenuScreen) {
            if (getMessage() instanceof TranslatableText translatableText && (translatableText.getKey().equals("menu.returnToMenu")
                    || translatableText.getKey().equals("menu.disconnect"))) {
                quitconfirm_handler(onPress);
                ci.cancel();
            }
        }
    }

    private void quitconfirm_handler(ButtonWidget.PressAction onPress) {
        this.active = false;
        MinecraftClient client = MinecraftClient.getInstance();
        boolean inSinglePlayer = client.isInSingleplayer();
        boolean connectedToRealms = client.isConnectedToRealms();
        if (inSinglePlayer) {
            if (Config.config.get().inIntegrated) {
                client.setScreen(new ConfirmScreen(client.currentScreen, new TranslatableText("gui.quitconfirm.integrate.text"), onPress));
            } else {
                onPress.onPress((ButtonWidget) (Object) this);
            }
            return;
        }
        if (connectedToRealms) {
            if (Config.config.get().inReams) {
                client.setScreen(new ConfirmScreen(client.currentScreen, new TranslatableText("gui.quitconfirm.reams.text"), onPress));
            } else {
                onPress.onPress((ButtonWidget) (Object) this);
            }
            return;
        }
        if (Config.config.get().inDedicated) {
            client.setScreen(new ConfirmScreen(client.currentScreen, new TranslatableText("gui.quitconfirm.dedicated.text"), onPress));
        } else {
            onPress.onPress((ButtonWidget) (Object) this);
        }
    }
}
