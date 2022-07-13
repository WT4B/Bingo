package de.wt4b.bingo.game.phases;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GamePhase;
import de.wt4b.bingo.manager.ResetManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import de.wt4b.bingo.timer.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:21
 */
public class FinishPhase extends GamePhase {

    private int time = 60;

    @Override
    public void onEnable() {
        Team team = TeamManager.getInstance().getWinnerTeam();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(Title.title(Component.text("§7Team " + team.getColorCode() + "#" + team.getID()),
                    Component.text("§7hat §bBingo §7gewonnen§8!")));
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            player.sendMessage("§8§m------------------------------");
            player.sendMessage("§7Team " + team.getColorCode() + "#" + team.getID() + " §7hat die §bRunde Bingo §7gewonnen§8.");
            Timer timer = Bingo.getInstance().getTimer();
            player.sendMessage("§7Dauer§8: §7" + timer.getTimeDisplay(timer.getTime()));
            player.sendMessage("§8§m------------------------------");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if ((time % 15 == 0 && time != 0) || (time <= 10 && time > 1))
                    Bukkit.broadcast(Component.text(Bingo.getPrefix() + "§cDer Server stoppt in " + time + " Sekunde" + (time == 1 ? "" : "n") + "."));
                else if (time == 0) onDisable();
                time--;
            }
        }.runTaskTimer(Bingo.getInstance(), 0, 20);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kick(Component.text("§cDer Server stoppt.")));
        ResetManager.getInstance().prepareReset();
    }
}
