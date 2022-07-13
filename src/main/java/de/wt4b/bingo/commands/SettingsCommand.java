package de.wt4b.bingo.commands;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.settings.SettingsManager;
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
 * Erstellt - 26.06.2022 22:20
 */
public class SettingsCommand implements CommandExecutor {

    public SettingsCommand() {
        Bukkit.getPluginCommand("settings").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Bingo.getPrefix() + "§cDu musst ein Spieler sein");
            return false;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("bingo.settings")) {
            player.sendMessage(Bingo.getNoPerms());
            return false;
        }
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() != gameManager.getSettingsPhase()) {
            player.sendMessage(Component.text(Bingo.getPrefix() + "§cDu kannst diesen Befehl nur während der Lobby Phase verwenden."));
            return false;
        }
        player.openInventory(SettingsManager.getInstance().getSettingsInventory());
        return true;
    }
}