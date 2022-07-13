package de.wt4b.bingo.listener.block;

import de.wt4b.bingo.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:32
 */
public class BlockBreakListener implements Listener {

    public BlockBreakListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!Bingo.getInstance().getTimer().isRunning())
            event.setCancelled(true);
    }
}