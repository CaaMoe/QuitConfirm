package moe.caa.fabric.quitconfirm.client;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.io.*;
import java.util.TimerTask;

@Environment(EnvType.CLIENT)
public final class Config {
    private static final File CONFIG_FOLDER = new File("config");
    private static final File CONFIG_FILE = new File(CONFIG_FOLDER, "quitconfirm.json");
    public static final Config CONFIG = new Config();
    public boolean finalE = true;
    public boolean integratedE = false;
    public boolean dedicatedE = true;
    public boolean reamsE = true;
    public boolean shortcutKet = false;

    static {
        CONFIG.readNew();
        QuitConfirm.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                CONFIG.pushSave();
            }
        },0 , 1000 * 3600);
    }

    public void pushSave(){
        try {
            CONFIG.save();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to save configuration file");
        }
    }

    private void readNew(){
        if(!CONFIG_FILE.exists()){
            return;
        }
        try {
            JsonObject root = (JsonObject) new JsonParser().parse(new FileReader(CONFIG_FILE));
            finalE = root.get("finalE").getAsBoolean();
            integratedE = root.get("integratedE").getAsBoolean();
            dedicatedE = root.get("dedicatedE").getAsBoolean();
            reamsE = root.get("reamsE").getAsBoolean();
            shortcutKet = root.get("shortcutKet").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Some data is corrupted！");
        }
    }

    private synchronized void save() throws IOException {
        if(!CONFIG_FOLDER.exists() && !CONFIG_FOLDER.mkdirs()){
            throw new IOException("Unable to create data folder.");
        }
        if(!CONFIG_FILE.exists() && !CONFIG_FILE.createNewFile()) {
            throw new IOException("Unable to create data file.");
        }

        JsonWriter jw = new JsonWriter(new OutputStreamWriter(new FileOutputStream(CONFIG_FILE), Charsets.UTF_8));
        jw.setIndent("  ");
        JsonObject root = new JsonObject();
        root.addProperty("finalE", finalE);
        root.addProperty("integratedE", integratedE);
        root.addProperty("dedicatedE", dedicatedE);
        root.addProperty("reamsE", reamsE);
        root.addProperty("shortcutKet", shortcutKet);
        QuitConfirm.GSON.toJson(root, jw);
        jw.flush();
        jw.close();
    }
}
