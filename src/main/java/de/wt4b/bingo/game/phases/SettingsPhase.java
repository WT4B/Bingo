package de.wt4b.bingo.game.phases;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GamePhase;
import de.wt4b.bingo.items.BingoItemManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:21
 */
public class SettingsPhase extends GamePhase {

    private final TeamManager teamManager;
    private final BingoItemManager bingoItemManager;

    public SettingsPhase() {
        this.teamManager = TeamManager.getInstance();
        this.bingoItemManager = BingoItemManager.getInstance();
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team team = teamManager.getPlayerTeam(player);
            if (team.equals(Team.SPEC)) {
                for (int id = 1; id < 18; id++) {
                    if (teamManager.getPlayersInTeam(teamManager.getTeamByID(id)).size() == 0) {
                        teamManager.joinTeam(player, id);
                        team = teamManager.getPlayerTeam(player);
                        break;
                    }
                }
                if (team.equals(Team.SPEC)) {
                    teamManager.joinTeam(player, new Random().nextInt(18) + 1);
                    team = teamManager.getPlayerTeam(player);
                }
            }
            player.sendMessage(Component.text(Bingo.getPrefix() + "§7Du bist in Team " + team.getColorCode() + "#" + team.getID()));
        }
        this.bingoItemManager.setupItems();
    }
}
