package de.wt4b.bingo.scoreboard.sidebar;

import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import org.bukkit.entity.Player;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 29.06.2022 21:35
 */
public class SettingsScoreboard extends AbstractScoreboard {

    private final Player player;

    public SettingsScoreboard(Player player) {
        super(player, "§b§lBingo");
        this.player = player;
        createScoreboard();
    }

    @Override
    public void createScoreboard() {
        Team team = TeamManager.getInstance().getPlayerTeam(player);
        setScore(0, "§bDein Team: " + team.getColorCode() + "#" + team.getID());
    }

    @Override
    public void updateScoreboard() {
        Team team = TeamManager.getInstance().getPlayerTeam(player);
        setScore(0, "§bDein Team: " + team.getColorCode() + "#" + team.getID());
    }

    @Override
    public void updateDisplayName(String time) {

    }
}
