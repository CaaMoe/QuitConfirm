package moe.caa.fabric.quitconfirm.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;

public class QuitConfirm {
    public static final Timer TIMER = new Timer("QuitConfirm");
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Logger LOGGER = LogManager.getLogger("QuitConfirm");
}
