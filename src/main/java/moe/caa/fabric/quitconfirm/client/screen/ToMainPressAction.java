package moe.caa.fabric.quitconfirm.client.screen;

import moe.caa.fabric.quitconfirm.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.text.TranslatableText;

public class ToMainPressAction implements ButtonWidget.PressAction {

    private final MinecraftClient CLIENT;

    public ToMainPressAction(MinecraftClient client) {
        CLIENT = client;
    }

    @Override
    public void onPress(ButtonWidget button) {
        boolean bl = this.CLIENT.isInSingleplayer();
        boolean bl2 = this.CLIENT.isConnectedToRealms();
        button.active = false;
        if (bl) {
            if (Config.CONFIG.integratedE) {
                this.CLIENT.openScreen(new ConfirmScreen(this.CLIENT.currentScreen, new TranslatableText("gui.quitconfirm.integrate.text"), new TranslatableText("menu.returnToMenu"), () -> confirm(0)));
            } else {
                confirm(0);
            }
        } else if (bl2) {
            if (Config.CONFIG.reamsE) {
                this.CLIENT.openScreen(new ConfirmScreen(this.CLIENT.currentScreen, new TranslatableText("gui.quitconfirm.reams.text"), new TranslatableText("menu.disconnect"), () -> confirm(1)));
            } else {
                confirm(1);
            }
        } else {
            if (Config.CONFIG.dedicatedE) {
                this.CLIENT.openScreen(new ConfirmScreen(this.CLIENT.currentScreen, new TranslatableText("gui.quitconfirm.dedicated.text"), new TranslatableText("menu.disconnect"), () -> confirm(2)));
            } else {
                confirm(2);
            }
        }
    }

    private void confirm(int i) {
        this.CLIENT.world.disconnect();
        if (i == 0) {
            this.CLIENT.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
            this.CLIENT.openScreen(new TitleScreen());
        } else {
            this.CLIENT.disconnect();
            if (i == 1) {
                RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
                realmsBridgeScreen.switchToRealms(new TitleScreen());
            } else {
                this.CLIENT.openScreen(new MultiplayerScreen(new TitleScreen()));
            }
        }
    }
}
