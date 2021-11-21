package moe.caa.fabric.quitconfirm.client.screen.settle;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class SettleScreen extends Screen {
    private final Screen parentScreen;

    public SettleScreen(Screen parentScreen) {
        super(new TranslatableText("gui.quitconfirm.settle.title"));
        this.parentScreen = parentScreen;
    }
}
