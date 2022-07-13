package de.wt4b.bingo.listener.player;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 29.06.2022 22:49
 */
public class InventoryClickListener implements Listener {

    public InventoryClickListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() != gameManager.getSettingsPhase()) return;
        event.setCancelled(true);
    }
}