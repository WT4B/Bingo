package de.wt4b.bingo.settings;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 27.06.2022 00:39
 */
public class SettingsListener implements Listener {

    public SettingsListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() != gameManager.getSettingsPhase()) return;
        Player player = event.getPlayer();
        if (event.getMaterial().equals(Material.ANVIL))
            player.openInventory(SettingsManager.getInstance().getSettingsInventory());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if (event.getClickedInventory().equals(player.getInventory())) return;
        if (!event.getView().title().equals(Component.text(Bingo.getPrefix() + "§8- §c§lSettings"))) return;
        event.setCancelled(true);
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (event.getCurrentItem() == null) return;
        switch (event.getCurrentItem().getType()) {
            case GREEN_WOOL, ORANGE_WOOL, RED_WOOL -> settingsManager.changeDifficulty(event.getClickedInventory());
            case OAK_SIGN -> {
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (settingsManager.getItemsToFind() > 3)
                        settingsManager.changeItemsToFind(event.getClickedInventory(), settingsManager.getItemsToFind() - 3);
                } else if (event.getClick().equals(ClickType.RIGHT)) {
                    if (settingsManager.getItemsToFind() < 18)
                        settingsManager.changeItemsToFind(event.getClickedInventory(), settingsManager.getItemsToFind() + 3);
                }
            }
            default -> settingsManager.change(settingsManager.getSettingBySlot(event.getSlot()), event.getClickedInventory(), event.getSlot());
        }
    }
}