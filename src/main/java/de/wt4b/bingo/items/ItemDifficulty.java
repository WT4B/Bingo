package de.wt4b.bingo.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Difficulty;
import org.bukkit.Material;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 27.06.2022 00:06
 */
@AllArgsConstructor
@Getter
public enum ItemDifficulty {

    EASY("§aEasy", Material.GREEN_WOOL, Difficulty.EASY),
    NORMAL("§6Normal", Material.ORANGE_WOOL, Difficulty.NORMAL),
    HARD("§cHard", Material.RED_WOOL, Difficulty.HARD);

    private final String name;
    private final Material material;
    private final Difficulty difficulty;
}
