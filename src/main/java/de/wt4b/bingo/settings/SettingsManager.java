package de.wt4b.bingo.settings;

import com.google.common.collect.Maps;
import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.builder.InventoryBuilder;
import de.wt4b.bingo.builder.ItemBuilder;
import de.wt4b.bingo.items.ItemDifficulty;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.Map;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:42
 */
@Getter
public class SettingsManager {

    private static SettingsManager instance;

    private final Map<Setting, Boolean> settings;

    private ItemDifficulty itemDifficulty;
    private int itemsToFind;

    public SettingsManager() {
        instance = this;
        this.settings = Maps.newHashMap();
        this.itemDifficulty = ItemDifficulty.EASY;
        this.itemsToFind = 9;
    }

    public static SettingsManager getInstance() {
        return instance;
    }

    public boolean isActivated(Setting setting) {
        return settings.getOrDefault(setting, false);
    }

    public void change(Setting setting, Inventory inventory, int slot) {
        this.settings.put(setting, !isActivated(setting));
        inventory.setItem(slot, new ItemBuilder(setting.getItem()).setName((isActivated(setting) ? "§a" : "§c") + setting.getName()).setLore(setting.getLore()).build());
        if (setting.equals(Setting.KEEPINVENTORY))
            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.KEEP_INVENTORY, isActivated(Setting.KEEPINVENTORY)));
    }

    public void changeDifficulty(Inventory inventory) {
        switch (itemDifficulty) {
            case EASY -> itemDifficulty = ItemDifficulty.NORMAL;
            case NORMAL -> itemDifficulty = ItemDifficulty.HARD;
            case HARD -> itemDifficulty = ItemDifficulty.EASY;
        }
        inventory.setItem(0, new ItemBuilder(itemDifficulty.getMaterial()).setName(Component.text("§7Difficulty §8- " + itemDifficulty.getName())).build());
    }

    public void changeItemsToFind(Inventory inventory, int newItemsToFind) {
        itemsToFind = newItemsToFind;
        inventory.setItem(1, new ItemBuilder(Material.OAK_SIGN).setName(Component.text("§bItems §7(§b" + itemsToFind + "§7)")).build());
    }

    public Inventory getSettingsInventory() {
        Inventory inventory = new InventoryBuilder(Bingo.getPrefix() + "§8- §c§lSettings", 9).build();
        inventory.setItem(0, new ItemBuilder(itemDifficulty.getMaterial()).setName(Component.text("§7Difficulty §8- " + itemDifficulty.getName())).build());
        inventory.setItem(1, new ItemBuilder(Material.OAK_SIGN).setName(Component.text("§bItems §7(§b" + itemsToFind + "§7)")).build());
        for (Setting setting : Setting.values())
            inventory.setItem(setting.getSlot(), new ItemBuilder(setting.getItem()).setName((isActivated(setting) ? "§a" : "§c") + setting.getName()).setLore(setting.getLore()).build());

        return inventory;
    }

    public Setting getSettingBySlot(int slot) {
        for (Setting setting : Setting.values()) {
            if (setting.getSlot() == slot) return setting;
        }
        return null;
    }
}
