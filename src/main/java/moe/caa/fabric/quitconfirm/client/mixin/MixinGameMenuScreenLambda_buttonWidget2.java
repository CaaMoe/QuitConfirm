package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.Config;
import moe.caa.fabric.quitconfirm.client.ConfirmScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("all")
@Environment(EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreenLambda_buttonWidget2 extends Screen {

    protected MixinGameMenuScreenLambda_buttonWidget2(Text title) {
        super(title);
    }

    @Inject(method = "net/minecraft/client/gui/screen/GameMenuScreen.method_19836(Lnet/minecraft/client/gui/widget/ButtonWidget;)V", at = @At(value = "HEAD"), cancellable = true)
    private void on(ButtonWidget button, CallbackInfo ci){
        boolean bl = this.client.isInSingleplayer();
        boolean bl2 = this.client.isConnectedToRealms();
        button.active = false;
        if(bl){
            if(Config.CONFIG.integratedE){
                client.openScreen(new ConfirmScreen(client.currentScreen, new TranslatableText("gui.quitconfirm.integrate.text"), new TranslatableText("menu.returnToMenu"), ()-> confirm(0)));
            } else {
                confirm(0);
            }
        } else if(bl2){
            if(Config.CONFIG.reamsE){
                client.openScreen(new ConfirmScreen(client.currentScreen, new TranslatableText("gui.quitconfirm.reams.text"), new TranslatableText("menu.disconnect"), ()-> confirm(1)));
            } else {
                confirm(1);
            }
        } else {
            if(Config.CONFIG.dedicatedE){
                client.openScreen(new ConfirmScreen(client.currentScreen, new TranslatableText("gui.quitconfirm.dedicated.text"), new TranslatableText("menu.disconnect"), ()-> confirm(2)));
            } else {
                confirm(2);
            }
        }
        // break
        ci.cancel();
    }

    /**
     * 0 integrated
     * 1 reams
     * 2 dedicated
     */
    private void confirm(int i){
        this.client.world.disconnect();
        if (i == 0) {
            this.client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
            this.client.openScreen(new TitleScreen());
        } else {
            this.client.disconnect();
            if (i == 1) {
                RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
                realmsBridgeScreen.switchToRealms(new TitleScreen());
            } else {
                this.client.openScreen(new MultiplayerScreen(new TitleScreen()));
            }
        }
    }
}
