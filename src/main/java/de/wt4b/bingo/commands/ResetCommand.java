package de.wt4b.bingo.commands;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.manager.ResetManager;
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
public class ResetCommand implements CommandExecutor {

    public ResetCommand() {
        Bukkit.getPluginCommand("reset").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Bingo.getPrefix() + "§cDu musst ein Spieler sein");
            return false;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("bingo.reset")) {
            player.sendMessage(Component.text(Bingo.getNoPerms()));
            return false;
        }
        if (args.length > 1) {
            player.sendMessage(Bingo.getUsage() + "/reset");
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§7Bestätige den Reset mit §b/reset confirm"));
            return false;
        } else {
            if (!args[0].equalsIgnoreCase("confirm")) {
                player.sendMessage(Component.text(Bingo.getUsage() + "/reset confirm"));
                return false;
            }
            ResetManager.getInstance().prepareReset();
        }
        return true;
    }
}