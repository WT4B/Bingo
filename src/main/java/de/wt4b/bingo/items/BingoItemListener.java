package de.wt4b.bingo.items;

import de.wt4b.bingo.Bingo;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 30.06.2022 22:42
 */
public class BingoItemListener implements Listener {

    public BingoItemListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory().equals(player.getInventory())) return;
        if (event.getView().title().equals(Component.text("§bRezept"))) {
            event.setCancelled(true);
            return;
        }
        if (!event.getView().title().equals(Component.text("§bItems"))) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().containsEnchantment(Enchantment.DURABILITY)) return;

        /* Coming Soon
        List<Recipe> recipes = Bukkit.getRecipesFor(event.getCurrentItem());

        if (recipes.isEmpty()) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cEs konnten keine Rezepte gefunden werden."));
            return;
        }

        */
    }
}