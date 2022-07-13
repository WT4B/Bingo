package de.wt4b.bingo.game.phases;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GamePhase;
import de.wt4b.bingo.scoreboard.sidebar.ScoreboardManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:20
 */
public class InGamePhase extends GamePhase {

    @Override
    public void onEnable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.getInstance().initScoreboard(player);
            player.getInventory().clear();
        }
        Bukkit.broadcast(Component.text(Bingo.getPrefix() + "§7Das §bSpiel §7startet§8. §7Finde alle §bItems§8."));
        Bingo.getInstance().getTimer().resume();
    }

    @Override
    public void onDisable() {
        Bingo.getInstance().getTimer().stop();
    }
}
