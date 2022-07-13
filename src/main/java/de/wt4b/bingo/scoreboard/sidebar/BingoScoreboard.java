package de.wt4b.bingo.scoreboard.sidebar;

import de.wt4b.bingo.items.BingoItemManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 29.06.2022 21:34
 */
public class BingoScoreboard extends AbstractScoreboard {

    private final BingoItemManager bingoItemManager = BingoItemManager.getInstance();
    private final Team team;

    public BingoScoreboard(Player player) {
        super(player, "§b§lBingo §8- §700:00:00");
        this.team = TeamManager.getInstance().getPlayerTeam(player);
        createScoreboard();
    }

    @Override
    public void createScoreboard() {
        setScore(15, " ");
        setScore(14, "§61. Platz§7: " + team.getColorCode() + "#" + team.getID());
        setScore(13, "§bDein Team: §7" + bingoItemManager.getPosition(team) + ". Platz");
        setScore(12, " ");
        List<Material> items = bingoItemManager.getItemsOfTeam(team);
        setScore(11, "§bAufgaben: §8(§7noch §c" + items.size() + "§8)");
        setScore(10, " ");
        setScore(9, " ");
        setScore(8, " ");
        setScore(7, " ");
        setScore(6, " ");
        setScore(5, " ");
        setScore(4, " ");
        setScore(3, " ");
        setScore(2, " ");
        setScore(1, " ");

        if (items.size() >= 10) {
            int score = 10;
            for (Material material : items) {
                setScore(score, "§8- §7" + bingoItemManager.getItemName(material));
                score--;
                if (score <= 1) break;
                setScore(1, "§8... und §7" + (items.size() - 9) + "§8 weitere");
            }
        } else {
            int score = 10;
            for (Material material : items) {
                setScore(score, "§8- §7" + bingoItemManager.getItemName(material));
                score--;
            }
        }
    }

    @Override
    public void updateScoreboard() {
        setScore(14, "§61. Platz§7: " + team.getColorCode() + "#" + team.getID());
        setScore(13, "§bDein Team: §7" + bingoItemManager.getPosition(team) + ". Platz");
        List<Material> items = bingoItemManager.getItemsOfTeam(team);
        setScore(11, "§bAufgaben: §8(§7noch §c" + items.size() + "§8)");
        setScore(10, " ");
        setScore(9, " ");
        setScore(8, " ");
        setScore(7, " ");
        setScore(6, " ");
        setScore(5, " ");
        setScore(4, " ");
        setScore(3, " ");
        setScore(2, " ");
        setScore(1, " ");

        if (items.size() >= 10) {
            int score = 10;
            for (Material material : items) {
                setScore(score, "§8- §7" + bingoItemManager.getItemName(material));
                score--;
                if (score <= 1) break;
                setScore(1, "§8... und §7" + (items.size() - 9) + "§8 weitere");
            }
        } else {
            int score = 10;
            for (Material material : items) {
                setScore(score, "§8- §7" + bingoItemManager.getItemName(material));
                score--;
            }
        }
    }

    @Override
    public void updateDisplayName(String time) {
        setDisplayName("§b§lBingo §8- §7" + time);
    }
}
