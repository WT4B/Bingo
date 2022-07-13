package de.wt4b.bingo.commands;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.timer.Timer;
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
 * Erstellt - 28.06.2022 23:25
 */
public class TimerCommand implements CommandExecutor {

    public TimerCommand() {
        Bukkit.getPluginCommand("timer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Bingo.getPrefix() + "§cDu musst ein Spieler sein");
            return false;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("bingo.timer")) {
            player.sendMessage(Component.text(Bingo.getNoPerms()));
            return false;
        }
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() == gameManager.getSettingsPhase()) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cDu kannst diesen Befehl nur während des Spiels verwenden."));
            return false;
        }
        if (args.length == 0 || args.length > 2) {
            player.sendMessage(Component.text(Bingo.getUsage() + "/timer [pause,resume,set] <Zeit>"));
            return false;
        }
        Timer timer = Bingo.getInstance().getTimer();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("pause")) {
                timer.pause();
                return true;
            }
            if (args[0].equalsIgnoreCase("resume")) {
                timer.resume();
                return true;
            }
            player.sendMessage(Component.text(Bingo.getUsage() + "/timer [pause,resume,set] <Zeit>"));
            return false;
        }
        if (!args[0].equalsIgnoreCase("set")) {
            player.sendMessage(Component.text(Bingo.getUsage() + "/timer [pause,resume,set] <Zeit>"));
            return false;
        }
        try {
            timer.setTime(Integer.parseInt(args[1]));
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text(Bingo.getUsage() + "/timer [pause,resume,set] <Zeit>"));
        }
        return true;
    }
}