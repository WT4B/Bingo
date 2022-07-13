package de.wt4b.bingo.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:41
 */
@AllArgsConstructor
@Getter
public enum Setting {

    DAMAGE(2, Material.DIAMOND_SWORD, "Schaden", Arrays.asList(Component.text("§7Stelle ein, ob man Schaden bekommen kann"))),
    KEEPINVENTORY(3, Material.CHEST, "Keep Inventory", Arrays.asList(Component.text("§7Stelle ein, ob man nach einem"), Component.text("§7Tod das Inventar behält"))),
    POSITIONS(4, Material.MAP, "Positions", Arrays.asList(Component.text("§7Stelle ein, ob man Positions speichern kann"))),
    SATURATION(5, Material.COOKED_BEEF, "Kein Hunger", Arrays.asList(Component.text("§7Stelle ein, ob man Hunger verliert"))),
    TOPCOMMAND(6, Material.MAGENTA_GLAZED_TERRACOTTA, "Top Teleport", Arrays.asList(Component.text("§7Stelle ein, ob man sich nach oben teleportieren kann"))),
    BACKPACK(7, Material.ENDER_CHEST, "Backpack", Arrays.asList(Component.text("§7Stelle ein, ob man einen Rucksack hat"))),
    SPECTATOR(8, Material.COMPASS, "Spectator", Arrays.asList(Component.text("§7Stelle ein, ob man zuschauen kann")));

    private final int slot;
    private final Material item;
    private final String name;
    private final List<Component> lore;
}
