package moe.caa.fabric.quitconfirm.client.screen.confirm;

import moe.caa.fabric.quitconfirm.client.config.Config;
import moe.caa.fabric.quitconfirm.client.screen.confirm.styles.BaseStyle;
import moe.caa.fabric.quitconfirm.client.screen.confirm.styles.BedrockStyle;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class StyleFactory {
    private static final AtomicInteger CURRENT_ID;
    private static final List<Supplier<BaseStyle>> STYLES;

    static {
        STYLES = Arrays.asList(
                BedrockStyle::new
        );
        CURRENT_ID = new AtomicInteger(Config.config.get().confirmScreenStyle);
        int size = STYLES.size();
        if (CURRENT_ID.get() >= size || CURRENT_ID.get() < 0) {
            CURRENT_ID.set(new Random().nextInt(size));
        }
    }

    public static BaseStyle createStyleFromConfig() {
        return STYLES.get(CURRENT_ID.get()).get();
    }

    public static void nextStyleFromConfig() {
        CURRENT_ID.getAndIncrement();
        int size = STYLES.size();
        if (CURRENT_ID.get() >= size || CURRENT_ID.get() < 0) {
            CURRENT_ID.set(0);
        }
        Config.config.get().confirmScreenStyle = CURRENT_ID.get();
    }
}
