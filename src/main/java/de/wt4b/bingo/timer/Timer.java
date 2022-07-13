package de.wt4b.bingo.timer;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.items.BingoItemManager;
import de.wt4b.bingo.manager.ConfigManager;
import de.wt4b.bingo.scoreboard.sidebar.ScoreboardManager;
import de.wt4b.bingo.settings.SettingsManager;
import de.wt4b.bingo.team.Team;
import de.wt4b.bingo.team.TeamManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:53
 */
@Getter
public class Timer {

    private static Timer instance;

    private final ConfigManager configManager;
    private final ScoreboardManager scoreboardManager = ScoreboardManager.getInstance();

    private BukkitTask bukkitTask;
    private boolean isRunning;
    private int time;

    public Timer() {
        instance = this;
        this.isRunning = false;
        this.configManager = ConfigManager.getInstance();
        if (!this.configManager.exists("timer.time"))
            this.configManager.getConfiguration().set("timer.time", 0);
        else
            this.time = this.configManager.getConfiguration().getInt("timer.time");

        start();
    }

    public String getTimeDisplay(int seconds) {
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;
        hours %= 24;
        return "" + ((hours > 9) ? hours : "0" + hours) + ":" + ((minutes > 9) ? minutes : "0" + minutes) + ":" + ((seconds > 9) ? seconds : "0" + seconds);
    }

    private void start() {
        if (this.bukkitTask != null) return;

        this.bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                sendInformation();
                if (!isRunning) return;
                time++;
                scoreboardManager.updateDisplayName(getTimeDisplay(time));
            }
        }.runTaskTimer(Bingo.getInstance(), 0, 20);
    }

    public void resume() {
        if (this.bukkitTask == null) return;
        this.isRunning = true;
    }

    public void pause() {
        this.isRunning = false;
    }

    public void stop() {
        this.isRunning = false;
        this.configManager.set("timer.time", 0);
    }

    public void sendInformation() {
        int itemsToFind = SettingsManager.getInstance().getItemsToFind();
        if (isRunning) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Team team = TeamManager.getInstance().getPlayerTeam(player);
                if (team.equals(Team.SPEC)) {
                    player.sendActionBar(Component.text("§b§lBingo §8- §b" +
                            Bingo.getInstance().getDescription().getVersion() + "§7 by §4WT4B"));
                    continue;
                }
                int itemsOfTeam = BingoItemManager.getInstance().getItemsOfTeam(team).size();
                player.sendActionBar(Component.text("§7Team " + team.getColorCode() + "#" + team.getID() +
                        "§8 - §7Aufgaben§7: §a" + (itemsToFind - itemsOfTeam) + "§7/§f" + itemsToFind));
            }
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(Component.text("§b§lBingo §8- §b" +
                Bingo.getInstance().getDescription().getVersion() + "§7 by §4WT4B")));
    }

    public void setTime(int time) {
        this.time = time;
    }
}
