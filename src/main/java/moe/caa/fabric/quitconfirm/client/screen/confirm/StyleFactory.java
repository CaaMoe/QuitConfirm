package moe.caa.fabric.quitconfirm.client.screen.confirm;

import moe.caa.fabric.quitconfirm.client.screen.confirm.styles.BaseStyle;
import moe.caa.fabric.quitconfirm.client.screen.confirm.styles.BedrockStyle;

public class StyleFactory {

    public static BaseStyle createStyleFromConfig() {
        return new BedrockStyle();
    }
}
