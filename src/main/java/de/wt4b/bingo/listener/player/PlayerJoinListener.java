package de.wt4b.bingo.listener.player;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.builder.ItemBuilder;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.scoreboard.sidebar.ScoreboardManager;
import de.wt4b.bingo.scoreboard.tablist.TablistManager;
import de.wt4b.bingo.team.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 23:03
 */
public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScoreboardManager.getInstance().initScoreboard(player);
        TablistManager.getInstance().setAllTeams();
        event.joinMessage(Component.text("§a§l» " + Team.SPEC.getColorCode() + player.getName()));
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() == gameManager.getSettingsPhase()) {
            player.getInventory().clear();
            if (player.hasPermission("bingo.admin")) {
                player.getInventory().setItem(4, new ItemBuilder(Material.LIME_DYE).setName(Component.text("§a§lRunde starten")).build());
                player.getInventory().setItem(8, new ItemBuilder(Material.ANVIL).setName(Component.text("§c§lSettings")).build());
            }
            player.getInventory().setItem(0, new ItemBuilder(Material.RED_BED).setName(Component.text("§a§lTeamauswahl")).build());
            return;
        }
        player.setGameMode(GameMode.SPECTATOR);
    }
}