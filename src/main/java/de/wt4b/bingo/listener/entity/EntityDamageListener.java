package de.wt4b.bingo.listener.entity;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.settings.Setting;
import de.wt4b.bingo.settings.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 23:00
 */
public class EntityDamageListener implements Listener {

    public EntityDamageListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!Bingo.getInstance().getTimer().isRunning()) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getEntity() instanceof Player)) return;
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (!settingsManager.isActivated(Setting.DAMAGE)) event.setCancelled(true);
    }
}