package de.wt4b.bingo.scoreboard.sidebar;

import de.wt4b.bingo.items.BingoItemManager;
import de.wt4b.bingo.team.Team;
import org.bukkit.entity.Player;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 29.06.2022 21:34
 */
public class SpectatorScoreboard extends AbstractScoreboard {

    private final BingoItemManager bingoItemManager = BingoItemManager.getInstance();

    public SpectatorScoreboard(Player player) {
        super(player, "§b§lBingo §8- §700:00:00");
        createScoreboard();
    }

    @Override
    public void createScoreboard() {
        Team team = bingoItemManager.getPosition1();
        setScore(0, "§61. Platz§7: " + team.getColorCode() + "#" + team.getID());
    }

    @Override
    public void updateScoreboard() {
        Team team = bingoItemManager.getPosition1();
        setScore(0, "§61. Platz§7: " + team.getColorCode() + "#" + team.getID());
    }

    @Override
    public void updateDisplayName(String time) {
        setDisplayName("§b§lBingo §8- §7" + time);
    }
}
