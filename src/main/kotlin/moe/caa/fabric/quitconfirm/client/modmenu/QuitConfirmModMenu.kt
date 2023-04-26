package moe.caa.fabric.quitconfirm.client.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import moe.caa.fabric.quitconfirm.client.generateConfigScreen

class QuitConfirmModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory {
        return@ConfigScreenFactory generateConfigScreen(it)
    }
}