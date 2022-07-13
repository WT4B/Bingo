package de.wt4b.bingo.scoreboard.sidebar;

import com.google.common.collect.Maps;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 29.06.2022 21:30
 */
public class ScoreboardManager {

    public static ScoreboardManager instance;

    public final Map<Player, AbstractScoreboard> scoreboards;

    public ScoreboardManager() {
        instance = this;
        this.scoreboards = Maps.newHashMap();
    }

    public static ScoreboardManager getInstance() {
        return instance;
    }

    public void initScoreboard(Player player) {
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() == gameManager.getSettingsPhase()) {
            this.scoreboards.put(player, new SettingsScoreboard(player));
            return;
        }
        TeamManager teamManager = TeamManager.getInstance();
        if (teamManager.getPlayerTeam(player).equals(Team.SPEC)) {
            this.scoreboards.put(player, new SpectatorScoreboard(player));
            return;
        }
        this.scoreboards.put(player, new BingoScoreboard(player));
    }

    public void updateScoreboard() {
        Bukkit.getOnlinePlayers().forEach(player -> scoreboards.get(player).updateScoreboard());
    }

    public void updateDisplayName(String time) {
        Bukkit.getOnlinePlayers().forEach(player -> scoreboards.get(player).updateDisplayName(time));
    }

    public AbstractScoreboard getPlayerScoreboard(Player player) {
        return this.scoreboards.get(player);
    }
}
