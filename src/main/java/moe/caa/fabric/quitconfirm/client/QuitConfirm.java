package moe.caa.fabric.quitconfirm.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Timer;

public final class QuitConfirm {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Timer TIMER = new Timer("QuitConfirm");
}
