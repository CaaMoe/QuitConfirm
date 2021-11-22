package moe.caa.fabric.quitconfirm.client.config;

import moe.caa.fabric.quitconfirm.client.main.QuitConfirm;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class Config {
    public static final AtomicReference<Config> config = new AtomicReference<>(new Config());
    private static final File CONFIG_FOLDER = new File("config");
    private static final File CONFIG_FILE = new File(CONFIG_FOLDER, "quitconfirm.json");

    static {
        readNew();
        QuitConfirm.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                saveConfig();
            }
        }, 0, 1000 * 3600);
    }

    // 在退出游戏时显示确认界面
    public boolean inFinal = true;
    // 在退出存档时显示确认界面
    public boolean inIntegrated = false;
    // 在退出多人时显示确认界面
    public boolean inDedicated = true;
    // 在退出领域时显示确认界面
    public boolean inReams = true;
    // 响应快捷键
    public boolean shortcutKet = false;
    // 按钮灰度时间
    public long keepInAction = 1000;
    // 确认退出屏幕样式
    public int confirmScreenStyle = 0;

    private static synchronized void readNew() {
        if (!CONFIG_FILE.exists()) {
            return;
        }
        Config value = null;
        try {
            value = QuitConfirm.GSON.fromJson(new FileReader(CONFIG_FILE), Config.class);
        } catch (Exception ignored) {
        }
        if (value != null) config.set(value);
    }

    public static synchronized void save() throws IOException {
        if (!CONFIG_FOLDER.exists() && !CONFIG_FOLDER.mkdirs()) {
            throw new IOException("Unable to create data folder: " + CONFIG_FOLDER.getAbsolutePath());
        }
        if (!CONFIG_FILE.exists() && !CONFIG_FILE.createNewFile()) {
            throw new IOException("Unable to create data file: " + CONFIG_FILE.getAbsolutePath());
        }
        try (FileWriter fw = new FileWriter(CONFIG_FILE)) {
            fw.write(QuitConfirm.GSON.toJson(config.get()));
        }
    }

    public static void saveConfig() {
        try {
            Config.save();
        } catch (Throwable e) {
            QuitConfirm.LOGGER.log(Level.ERROR, "Unable to save configuration.", e);
        }
    }
}
