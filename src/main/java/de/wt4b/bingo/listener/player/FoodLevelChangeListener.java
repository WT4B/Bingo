package de.wt4b.bingo.listener.player;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.settings.Setting;
import de.wt4b.bingo.settings.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 23:42
 */
public class FoodLevelChangeListener implements Listener {

    public FoodLevelChangeListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onChange(FoodLevelChangeEvent event) {
        if (!Bingo.getInstance().getTimer().isRunning()) {
            event.setCancelled(true);
            return;
        }
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (!settingsManager.isActivated(Setting.SATURATION)) event.setCancelled(true);
    }
}