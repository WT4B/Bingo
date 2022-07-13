package de.wt4b.bingo.items;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 29.06.2022 21:03
 */
public class FindItemListener implements Listener {

    private final BingoItemManager bingoItemManager = BingoItemManager.getInstance();
    private final TeamManager teamManager = TeamManager.getInstance();

    public FindItemListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        checkItem(player, event.getItem().getItemStack().getType());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        checkItem(event.getPlayer(), event.getMaterial());
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        checkItem(event.getPlayer(), event.getBucket());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;
            checkItem(player, itemStack.getType());
        }
    }

    private void checkItem(Player player, Material material) {
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() != gameManager.getInGamePhase()) return;
        Team team = teamManager.getPlayerTeam(player);
        if (team.equals(Team.SPEC)) return;
        if (!bingoItemManager.getNeededItems().contains(material)) return;
        if (!bingoItemManager.getItemsOfTeam(team).contains(material)) return;
        bingoItemManager.foundItem(player, material);
    }
}