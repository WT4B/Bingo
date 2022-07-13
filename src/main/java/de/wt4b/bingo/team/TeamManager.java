package de.wt4b.bingo.team;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.builder.InventoryBuilder;
import de.wt4b.bingo.builder.ItemBuilder;
import de.wt4b.bingo.scoreboard.sidebar.ScoreboardManager;
import de.wt4b.bingo.scoreboard.tablist.TablistManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:49
 */
public class TeamManager {

    private static TeamManager instance;

    private final Map<Player, Team> teams;
    private final Map<Team, Inventory> backPacks;
    private final Map<Team, List<Position>> positions;

    private Team winnerTeam;

    public TeamManager() {
        instance = this;
        this.teams = Maps.newHashMap();
        this.backPacks = Maps.newHashMap();
        this.positions = Maps.newHashMap();
        initTeamBackPacks();
    }

    public static TeamManager getInstance() {
        return instance;
    }

    public void initTeamBackPacks() {
        for (Team team : Team.values())
            backPacks.put(team, new InventoryBuilder("§bTeam§8: #" + team.getColorCode() + team.getID(), 9 * 3).build());
    }

    public void updateBackPack(Player player, Inventory inventory) {
        backPacks.put(getPlayerTeam(player), inventory);
    }

    public Inventory getTeamBackPack(Player player) {
        return this.backPacks.get(getPlayerTeam(player));
    }

    public void addPosition(Player player, String name) {
        Team team = getPlayerTeam(player);
        List<Position> teamPositions = getPositions(player);
        teamPositions.add(new Position(name, player.getLocation()));
        this.positions.put(team, teamPositions);
        getPlayersInTeam(team).forEach(teamPlayers -> teamPlayers.sendMessage(Component.text(Bingo.getPrefix() +
                "§7Der Spieler " + team.getColorCode() + player.getName() + "§7 hat die Position §b" + name + "§7 erstellt§8.")));
    }

    public boolean existsPosition(Player player, String name) {
        List<Position> teamPositions = this.positions.getOrDefault(getPlayerTeam(player), Lists.newArrayList());
        return teamPositions.stream().anyMatch(position -> position.getName().equalsIgnoreCase(name));
    }

    public List<Position> getPositions(Player player) {
        return this.positions.getOrDefault(getPlayerTeam(player), Lists.newArrayList());
    }

    public Position getPosition(Player player, String name) {
        List<Position> teamPositions = this.positions.getOrDefault(getPlayerTeam(player), Lists.newArrayList());
        return teamPositions.stream().filter(position -> position.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void joinTeam(Player player, int id) {
        Team team = getTeamByID(id);
        this.teams.put(player, team);
        ScoreboardManager.getInstance().getPlayerScoreboard(player).updateScoreboard();
        TablistManager.getInstance().setAllTeams();
        player.sendMessage(Component.text(Bingo.getPrefix() + "§7Du bist nun in Team " + team.getColorCode() + "#" + id));
    }

    public Inventory getTeamSelectionInventory() {
        Inventory inventory = new InventoryBuilder(Bingo.getPrefix() + "§8- §a§lTeamauswahl", 9 * 2).build();
        int slot = 0;
        for (Team team : Team.values()) {
            if (team.equals(Team.SPEC)) continue;
            inventory.setItem(slot, new ItemBuilder(team.getMaterial()).setName(Component.text("§8#" + team.getColorCode() + team.getID())).build());
            slot++;
        }
        return inventory;
    }

    public Team getTeamByID(int id) {
        for (Team team : Team.values()) {
            if (team.getId() == id) return team;
        }
        return Team.SPEC;
    }

    public Team getPlayerTeam(Player player) {
        return this.teams.getOrDefault(player, Team.SPEC);
    }

    public List<Player> getPlayersInTeam(Team team) {
        List<Player> players = Lists.newArrayList();
        for (Map.Entry<Player, Team> playerTeamEntry : this.teams.entrySet()) {
            if (playerTeamEntry.getValue().getId() == team.getId())
                players.add(playerTeamEntry.getKey());
        }
        return players;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }
}
