package de.wt4b.bingo.manager;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:33
 */
@Getter
public class ConfigManager {

    private static ConfigManager instance;

    private final File directory;
    private final File file;
    private final YamlConfiguration configuration;

    public ConfigManager() {
        instance = this;

        this.directory = new File("./plugins/Bingo/");
        if (Files.notExists(directory.toPath())) directory.mkdirs();

        this.file = new File(directory, "config.yml");
        if (Files.notExists(this.file.toPath()))
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public void set(String path, Object value) {
        this.configuration.set(path, value);
        saveConfig();
    }

    public boolean exists(String path) {
        return this.configuration.contains(path);
    }

    public void saveConfig() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
