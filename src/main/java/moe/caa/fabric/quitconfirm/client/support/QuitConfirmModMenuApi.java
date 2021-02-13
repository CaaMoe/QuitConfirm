package moe.caa.fabric.quitconfirm.client.support;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import moe.caa.fabric.quitconfirm.client.screen.SettleScreen;

public final class QuitConfirmModMenuApi implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SettleScreen::new;
    }
}
