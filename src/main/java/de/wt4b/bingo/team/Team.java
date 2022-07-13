package de.wt4b.bingo.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:44
 */
@AllArgsConstructor
@Getter
public enum Team {

    TEAM_1(1, Material.RED_WOOL, "§c"),
    TEAM_2(2, Material.LIME_WOOL, "§a"),
    TEAM_3(3, Material.CYAN_WOOL, "§b"),
    TEAM_4(4, Material.PINK_WOOL, "§d"),
    TEAM_5(5, Material.YELLOW_WOOL, "§e"),
    TEAM_6(6, Material.GREEN_WOOL, "§2"),
    TEAM_7(7, Material.LIGHT_BLUE_WOOL, "§9"),
    TEAM_8(8, Material.PURPLE_WOOL, "§5"),
    TEAM_9(9, Material.ORANGE_WOOL, "§6"),
    TEAM_10(10, Material.RED_WOOL, "§c§n"),
    TEAM_11(11, Material.LIME_WOOL, "§a§n"),
    TEAM_12(12, Material.CYAN_WOOL, "§b§n"),
    TEAM_13(13, Material.PINK_WOOL, "§d§n"),
    TEAM_14(14, Material.YELLOW_WOOL, "§e§n"),
    TEAM_15(15, Material.GREEN_WOOL, "§2§n"),
    TEAM_16(16, Material.LIGHT_BLUE_WOOL, "§9§n"),
    TEAM_17(17, Material.PURPLE_WOOL, "§5§n"),
    TEAM_18(18, Material.ORANGE_WOOL, "§6§n"),
    SPEC(99, null, "§7");

    private final int id;
    private final Material material;
    private final String colorCode;

    public int getId() {
        return id;
    }

    public String getID() {
        if (id == 99) return "Spec";
        return "" + id;
    }
}
