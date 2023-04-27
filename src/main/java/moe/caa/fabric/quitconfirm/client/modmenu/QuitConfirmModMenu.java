package moe.caa.fabric.quitconfirm.client.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import moe.caa.fabric.quitconfirm.client.screen.setting.SettingScreen;
import net.minecraft.client.gui.screen.Screen;

public class QuitConfirmModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) SettingScreen::new;
    }
}
