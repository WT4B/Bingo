package de.wt4b.bingo.team;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 27.06.2022 00:40
 */
public class TeamListener implements Listener {

    public TeamListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() != gameManager.getSettingsPhase()) return;
        Player player = event.getPlayer();
        if (event.getMaterial().equals(Material.RED_BED))
            player.openInventory(TeamManager.getInstance().getTeamSelectionInventory());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory().equals(player.getInventory())) return;
        if (!event.getView().title().equals(Component.text(Bingo.getPrefix() + "§8- §a§lTeamauswahl"))) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        TeamManager teamManager = TeamManager.getInstance();
        teamManager.joinTeam(player, event.getSlot() + 1);
        player.closeInventory();
    }
}