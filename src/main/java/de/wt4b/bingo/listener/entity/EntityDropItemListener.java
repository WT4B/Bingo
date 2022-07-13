package de.wt4b.bingo.listener.entity;

import de.wt4b.bingo.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 22:56
 */
public class EntityDropItemListener implements Listener {

    public EntityDropItemListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onDrop(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!Bingo.getInstance().getTimer().isRunning())
            event.setCancelled(true);
    }
}