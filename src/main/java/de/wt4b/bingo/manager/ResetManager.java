package de.wt4b.bingo.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:36
 */
@Getter
public class ResetManager {

    private static ResetManager instance;

    private boolean isReset;

    public ResetManager() {
        instance = this;
    }

    public static ResetManager getInstance() {
        return instance;
    }

    public void prepareReset() {
        isReset = true;
        Bukkit.getOnlinePlayers().forEach(player -> player.kick(Component.text("§cDer Server wird resetet.")));
        Bukkit.shutdown();
    }

    public void reset() {
        String worldName = getWorldName();

        try {
            FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer(), worldName));
            FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer(), worldName + "_nether"));
            FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer(), worldName + "_the_end"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWorldName() {
        File serverPropertiesFile = new File(Bukkit.getWorldContainer(), "server.properties");
        String worldName = "world";
        try {
            FileInputStream fileInputStream = new FileInputStream(serverPropertiesFile);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            worldName = properties.getProperty("level-name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return worldName;
    }
}
