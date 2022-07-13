package de.wt4b.bingo.team;

import de.wt4b.bingo.Bingo;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 27.06.2022 00:54
 */
public class BackpackListener implements Listener {

    public BackpackListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!PlainTextComponentSerializer.plainText().serialize(event.getView().title()).contains("§7Team§8: #"))
            return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;
        TeamManager.getInstance().updateBackPack(player, event.getInventory());
    }
}