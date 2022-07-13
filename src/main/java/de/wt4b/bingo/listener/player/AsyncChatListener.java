package de.wt4b.bingo.listener.player;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 22:56
 */
public class AsyncChatListener implements Listener {

    public AsyncChatListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        GameManager gameManager = GameManager.getInstance();
        TeamManager teamManager = TeamManager.getInstance();
        Team team = teamManager.getPlayerTeam(player);
        event.setCancelled(true);
        if (gameManager.getCurrentPhase() == gameManager.getInGamePhase()) {
            if (!message.startsWith("@a") && !message.startsWith("@all")) {
                teamManager.getPlayersInTeam(team).forEach(teamPlayers -> teamPlayers.sendMessage(Component.text("§8[" +
                        team.getColorCode() + "#" + team.getID() + "§8] " + team.getColorCode() + player.getName() + "§7: §f" +
                        message)));
                return;
            }
        }
        Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(Component.text(team.getColorCode() + player.getName() + "§7: §f" + message
                .replace("@all ", "").replace("@all", "")
                .replace("@a ", "").replace("@a", ""))));
    }
}