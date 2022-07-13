package de.wt4b.bingo.scoreboard.tablist;

import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 01.07.2022 14:20
 */
public class TablistManager {

    private static TablistManager instance;

    public TablistManager() {
        instance = this;
    }

    public static TablistManager getInstance() {
        return instance;
    }

    public void setAllTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setTeam);
    }

    public void setTeam(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        for (Team team : Team.values()) {
            org.bukkit.scoreboard.Team scoreboardTeam = scoreboard.getTeam(team.name());
            if (scoreboardTeam == null) scoreboardTeam = scoreboard.registerNewTeam(team.name());
            scoreboardTeam.prefix(Component.text(team.getColorCode() + team.getID() + "§8 | " + team.getColorCode()));
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            Team team = TeamManager.getInstance().getPlayerTeam(all);
            org.bukkit.scoreboard.Team scoreboardTeam = scoreboard.getTeam(team.name());
            if (scoreboardTeam == null) return;
            scoreboardTeam.addEntry(all.getName());
        }
    }
}
