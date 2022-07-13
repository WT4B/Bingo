package de.wt4b.bingo.items;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.builder.InventoryBuilder;
import de.wt4b.bingo.builder.ItemBuilder;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.scoreboard.sidebar.ScoreboardManager;
import de.wt4b.bingo.settings.SettingsManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:26
 */
@Getter
public class BingoItemManager {

    private static BingoItemManager instance;

    private final List<Material> easy = Lists.newArrayList();
    private final List<Material> normal = Lists.newArrayList();
    private final List<Material> hard = Lists.newArrayList();
    private final Map<Team, List<Material>> teamItems = Maps.newHashMap();

    private List<Material> neededItems;

    public BingoItemManager() {
        instance = this;
        this.neededItems = Lists.newArrayList();
        loadItems();
    }

    public static BingoItemManager getInstance() {
        return instance;
    }

    private void loadItems() {
        // Easy
        addEasyItem("IRON");
        addEasyItem("REDSTONE");
        removeEasyItem(Material.REDSTONE_ORE);
        addEasyItem("GOLD");
        removeEasyItem(Material.ENCHANTED_GOLDEN_APPLE);
        addEasyItem("FURNACE");
        addEasyItem("RAIL");
        addEasyItem(Material.SUGAR_CANE);
        addEasyItem(Material.PUMPKIN);
        addEasyItem(Material.JUKEBOX);
        addEasyItem("EMERALD");
        addEasyItem(Material.FISHING_ROD);
        addEasyItem(Material.OBSERVER);
        addEasyItem(Material.BOW);
        addEasyItem(Material.FEATHER);
        addEasyItem(Material.COMPASS);
        addEasyItem(Material.CLOCK);
        addEasyItem(Material.SHIELD);
        addEasyItem(Material.SHEARS);
        addEasyItem(Material.WATER_BUCKET);
        addEasyItem(Material.LAVA_BUCKET);
        addEasyItem(Material.MILK_BUCKET);
        addEasyItem(Material.BUCKET);
        addEasyItem(Material.SUGAR_CANE);
        addEasyItem(Material.FLINT_AND_STEEL);
        addEasyItem("BOOK");
        addEasyItem("PAPER");
        addEasyItem(Material.LECTERN);

        // Normal
        addNormalItem(Material.NETHERRACK);
        addNormalItem("SOUL");
        addNormalItem("DIAMOND");
        removeNormalItem(Material.DIAMOND_ORE);
        removeNormalItem(Material.DIAMOND_HORSE_ARMOR);
        addNormalItem("WART");
        removeNormalItem(Material.SOUL_FIRE);
        removeNormalItem(Material.SOUL_WALL_TORCH);
        addNormalItem(Material.CRYING_OBSIDIAN);
        addNormalItem("GLOWSTONE");

        // Hard
        addHardItem(Material.NETHERITE_HOE);
        addHardItem(Material.NETHERITE_INGOT);
        addHardItem(Material.NETHERITE_SCRAP);
        addHardItem(Material.OBSIDIAN);
        addHardItem(Material.ANCIENT_DEBRIS);
        addHardItem(Material.ENDER_PEARL);
        addHardItem("BLAZE");
        removeHardItem(Material.BLAZE_SPAWN_EGG);
    }

    private void addEasyItem(Material material) {
        this.easy.add(material);
    }

    private void addEasyItem(String material) {
        for (Material value : Material.values()) {
            if (value.name().toUpperCase().contains(material)) this.easy.add(value);
        }
    }

    private void removeEasyItem(Material material) {
        this.easy.remove(material);
    }

    private void addNormalItem(Material material) {
        this.normal.add(material);
    }

    private void addNormalItem(String material) {
        for (Material value : Material.values()) {
            if (value.name().toUpperCase().contains(material)) this.normal.add(value);
        }
    }

    private void removeNormalItem(Material material) {
        this.normal.remove(material);
    }

    private void addHardItem(Material material) {
        this.hard.add(material);
    }

    private void addHardItem(String material) {
        for (Material value : Material.values()) {
            if (value.name().toUpperCase().contains(material)) this.hard.add(value);
        }
    }

    private void removeHardItem(Material material) {
        this.hard.remove(material);
    }

    public void setupItems() {
        SettingsManager settingsManager = SettingsManager.getInstance();
        List<Material> neededItems = getItems(settingsManager.getItemDifficulty(), settingsManager.getItemsToFind());

        Collections.shuffle(neededItems);

        for (Team team : Team.values()) {
            if (team.equals(Team.SPEC)) continue;
            teamItems.put(team, neededItems);
        }

        this.neededItems.addAll(neededItems);
    }

    public void foundItem(Player player, Material material) {
        TeamManager teamManager = TeamManager.getInstance();
        Team team = teamManager.getPlayerTeam(player);
        for (Player teamPlayers : teamManager.getPlayersInTeam(team)) {
            teamPlayers.showTitle(Title.title(Component.text("§b" + getItemName(material)), Component.text("§7wurde gefunden!")));
            teamPlayers.playSound(teamPlayers.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
        int itemsToFind = SettingsManager.getInstance().getItemsToFind();
        Bukkit.broadcast(Component.text(Bingo.getPrefix() + "§7Der Spieler " + team.getColorCode() + player.getName() +
                "§7 aus dem Team " + team.getColorCode() + "#" + team.getID() + "§7 hat §b" + getItemName(material) + "§7 gefunden§8. " +
                "§8[§a" + (itemsToFind - getItemsOfTeam(team).size() + 1) + "§7/§f" + itemsToFind + "§8]"));
        getItemsOfTeam(team).remove(material);
        ScoreboardManager.getInstance().updateScoreboard();
        GameManager gameManager = GameManager.getInstance();
        if (getItemsOfTeam(team).size() == 0) {
            teamManager.setWinnerTeam(team);
            gameManager.setPhase(gameManager.getFinishPhase());
        }
    }

    public Team getPosition1() {
        Team team = Team.TEAM_1;
        for (Team teams : Team.values()) {
            if (team.equals(Team.SPEC)) continue;
            if (getItemsOfTeam(teams).size() < getItemsOfTeam(team).size()) team = teams;
        }
        return team;
    }

    public int getPosition(Team team) {
        int itemsToFind = SettingsManager.getInstance().getItemsToFind();
        int items = itemsToFind - getItemsOfTeam(team).size();

        int place = 1;
        for (Map.Entry<Team, List<Material>> teamListEntry : teamItems.entrySet()) {
            int otherTeamItems = itemsToFind - getItemsOfTeam(teamListEntry.getKey()).size();

            if (otherTeamItems > items) place++;
        }
        return place;
    }

    public Inventory getBingoInventory(Player player) {
        Inventory inventory = new InventoryBuilder("§bItems", 9 * 2).build();
        Team team = TeamManager.getInstance().getPlayerTeam(player);
        int slot = 0;
        for (Material material : neededItems) {
            if (!getItemsOfTeam(team).contains(material)) inventory.setItem(slot, new ItemBuilder(material)
                    .addEnchantment(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
            else inventory.setItem(slot, new ItemBuilder(material).build());

            slot++;
        }
        return inventory;
    }

    public List<Material> getItemsOfTeam(Team team) {
        return this.teamItems.get(team);
    }

    private List<Material> getItems(ItemDifficulty itemDifficulty, int itemsToFind) {
        List<Material> items = Lists.newArrayList();
        if (itemDifficulty.equals(ItemDifficulty.EASY)) {
            for (int i = 0; i < itemsToFind; i++) {
                Material material = easy.get(getRandomNumber(easy));
                while (items.contains(material) || !material.isItem()) material = easy.get(getRandomNumber(easy));
                items.add(material);
            }
        } else if (itemDifficulty.equals(ItemDifficulty.NORMAL)) {
            int easyItems = (itemsToFind / 3) * 2;
            int normalItems = itemsToFind - easyItems;
            for (int i = 0; i < easyItems; i++) {
                Material material = easy.get(getRandomNumber(easy));
                while (items.contains(material) || !material.isItem()) material = easy.get(getRandomNumber(easy));
                items.add(material);
            }
            for (int i = 0; i < normalItems; i++) {
                Material material = normal.get(getRandomNumber(normal));
                while (items.contains(material) || !material.isItem()) material = normal.get(getRandomNumber(normal));
                items.add(material);
            }
        } else if (itemDifficulty.equals(ItemDifficulty.HARD)) {
            int itemSize = itemsToFind / 3;

            for (int i = 0; i < itemSize; i++) {
                Material material = easy.get(getRandomNumber(easy));
                while (items.contains(material) || !material.isItem()) material = easy.get(getRandomNumber(easy));
                items.add(material);
            }
            for (int i = 0; i < itemSize; i++) {
                Material material = normal.get(getRandomNumber(normal));
                while (items.contains(material) || !material.isItem()) material = normal.get(getRandomNumber(normal));
                items.add(material);
            }
            for (int i = 0; i < itemSize; i++) {
                Material material = hard.get(getRandomNumber(hard));
                while (items.contains(material) || !material.isItem()) material = hard.get(getRandomNumber(hard));
                items.add(material);
            }
        }
        return items;
    }

    public String getItemName(Material material) {
        return WordUtils.capitalize(material.name().replace("_", " ").toLowerCase());
    }

    private int getRandomNumber(List<Material> itemDifficulty) {
        return new Random().nextInt(itemDifficulty.size());
    }
}
