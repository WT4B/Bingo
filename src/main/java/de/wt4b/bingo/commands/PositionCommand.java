package de.wt4b.bingo.commands;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.settings.Setting;
import de.wt4b.bingo.settings.SettingsManager;
import de.wt4b.bingo.team.Position;
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
 * Erstellt - 28.06.2022 23:32
 */
public class PositionCommand implements CommandExecutor {

    public PositionCommand() {
        Bukkit.getPluginCommand("position").setExecutor(this);
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
        if (!settingsManager.isActivated(Setting.POSITIONS)) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cDiese Funktion ist nicht aktiviert."));
            return false;
        }
        if (args.length > 1) {
            player.sendMessage(Component.text(Bingo.getUsage() + "/pos <Name>"));
            return false;
        }
        TeamManager teamManager = TeamManager.getInstance();
        if (args.length == 0) {
            if (teamManager.getPositions(player).size() == 0) {
                player.sendMessage(Component.text(Bingo.getPrefix() + "§7Es gibt keine §bPositions§8."));
                return false;
            }
            player.sendMessage(Component.text(Bingo.getPrefix() + "§7Es gibt folgende Positions§8:"));
            teamManager.getPositions(player).forEach(position -> player.sendMessage(Component.text("§8» §b" + position.getName())));
            return true;
        }
        if (teamManager.existsPosition(player, args[0])) {
            Position position = teamManager.getPosition(player, args[0]);
            int x = position.getLocation().getBlockX();
            int y = position.getLocation().getBlockY();
            int z = position.getLocation().getBlockZ();
            player.sendMessage(Component.text(Bingo.getPrefix() + "§b" + position.getName() + "§8 - §7X§8: §b" +
                    x + "§7 Y§8: §b" + y + "§7 Z§8: §b" + z));
            return true;
        }
        teamManager.addPosition(player, args[0]);
        return true;
    }
}