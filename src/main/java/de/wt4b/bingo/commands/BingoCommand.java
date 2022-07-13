package de.wt4b.bingo.commands;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.items.BingoItemManager;
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
public class BingoCommand implements CommandExecutor {

    public BingoCommand() {
        Bukkit.getPluginCommand("bingo").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Bingo.getPrefix() + "§cDu musst ein Spieler sein");
            return false;
        }
        Player player = (Player) commandSender;
        GameManager gameManager = GameManager.getInstance();
        if (args.length == 0) {
            if (gameManager.getCurrentPhase() != gameManager.getInGamePhase()) {
                player.sendMessage(Component.text(Bingo.getPrefix() + "§cDu kannst diesen Befehl nur während des Spiels verwenden."));
                return false;
            }
            player.openInventory(BingoItemManager.getInstance().getBingoInventory(player));
            return false;
        }
        if (args.length == 1) {
            if (gameManager.getCurrentPhase() != gameManager.getSettingsPhase()) {
                player.sendMessage(Component.text(Bingo.getPrefix() + "§7Das §bSpiel §7wurde bereits gestartet."));
                return false;
            }
            if (args[0].equalsIgnoreCase("start")) gameManager.setPhase(gameManager.getInGamePhase());
        }
        return true;
    }
}