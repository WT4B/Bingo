package de.wt4b.bingo.builder;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 21:55
 */
@AllArgsConstructor
public class InventoryBuilder {

    private final Inventory inventory;

    public InventoryBuilder(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, Component.text(title));
    }

    public InventoryBuilder(String title, InventoryType inventoryType) {
        this.inventory = Bukkit.createInventory(null, inventoryType, Component.text(title));
    }

    public InventoryBuilder fill(Material material) {
        fill(new ItemBuilder(material).setNoName().build());
        return this;
    }

    public InventoryBuilder fill(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            this.inventory.setItem(i, itemStack);
        }
        return this;
    }

    public InventoryBuilder fillEmpty(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null)
                this.inventory.setItem(i, itemStack);
        }
        return this;
    }

    public InventoryBuilder add(ItemStack... itemStacks) {
        this.inventory.addItem(itemStacks);
        return this;
    }

    public InventoryBuilder set(ItemStack itemStack, int... slots) {
        for (int i : slots) this.inventory.setItem(i, itemStack);
        return this;
    }

    public Inventory build() {
        return this.inventory;
    }

}
