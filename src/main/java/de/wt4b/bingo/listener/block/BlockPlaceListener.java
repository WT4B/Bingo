package de.wt4b.bingo.listener.block;

import de.wt4b.bingo.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 22:49
 */
public class BlockPlaceListener implements Listener {

    public BlockPlaceListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!Bingo.getInstance().getTimer().isRunning())
            event.setCancelled(true);
    }
}