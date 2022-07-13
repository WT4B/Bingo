package de.wt4b.bingo.commands;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.settings.Setting;
import de.wt4b.bingo.settings.SettingsManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:19
 */
public class BackpackCommand implements CommandExecutor {

    public BackpackCommand() {
        Bukkit.getPluginCommand("backpack").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Bingo.getPrefix() + "§cDu musst ein Spieler sein");
            return false;
        }
        Player player = (Player) commandSender;
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() != gameManager.getInGamePhase()) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cDu kannst diesen Befehl nur während des Spiels verwenden."));
            return false;
        }
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (!settingsManager.isActivated(Setting.BACKPACK)) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cDiese Funktion ist nicht aktiviert."));
            return false;
        }
        TeamManager teamManager = TeamManager.getInstance();
        if (teamManager.getPlayerTeam(player).equals(Team.SPEC)) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cDu kannst als Spectator kein Backpack öffnen."));
            return false;
        }
        player.openInventory(teamManager.getTeamBackPack(player));
        return true;
    }
}