package de.wt4b.bingo.scoreboard.sidebar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:13
 */
public abstract class AbstractScoreboard {

    protected final Scoreboard scoreboard;
    protected final Objective objective;
    protected final Player player;

    public AbstractScoreboard(Player player, String displayName) {
        this.player = player;
        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        this.scoreboard = player.getScoreboard();
        if (this.scoreboard.getObjective("display") != null)
            Objects.requireNonNull(this.scoreboard.getObjective("display")).unregister();

        this.objective = this.scoreboard.registerNewObjective("display", "dummy", Component.text(displayName));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public abstract void createScoreboard();

    public abstract void updateScoreboard();

    public abstract void updateDisplayName(String time);

    public void setDisplayName(String displayName) {
        this.objective.displayName(Component.text(displayName));
    }

    public void setScore(int score, String content) {
        Team team = getTeamByScore(score);
        if (team == null) return;
        team.prefix(Component.text(content));
        showScore(score);
    }

    public void removeScore(int score) {
        hideScore(score);
    }

    private EntryName getEntryNameByScore(int score) {
        for (EntryName name : EntryName.values()) {
            if (score == name.getEntry()) return name;
        }
        return null;
    }

    private Team getTeamByScore(int score) {
        EntryName name = getEntryNameByScore(score);
        if (name == null) return null;

        Team team = scoreboard.getEntryTeam(name.getEntryName());
        if (team != null) return team;
        team = scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());
        return team;
    }

    private void showScore(int score) {
        EntryName name = getEntryNameByScore(score);
        if (name == null) return;
        if (objective.getScore(name.getEntryName()).isScoreSet()) return;
        objective.getScore(name.getEntryName()).setScore(score);
    }

    private void hideScore(int score) {
        EntryName name = getEntryNameByScore(score);
        if (name == null) return;
        if (!objective.getScore(name.getEntryName()).isScoreSet()) return;
        scoreboard.resetScores(name.getEntryName());
    }

    @AllArgsConstructor
    @Getter
    public enum EntryName {

        ENTRY_0(0, "§a"),
        ENTRY_1(1, "§b"),
        ENTRY_2(2, "§c"),
        ENTRY_3(3, "§d"),
        ENTRY_4(4, "§e"),
        ENTRY_5(5, "§f"),
        ENTRY_6(6, "§1"),
        ENTRY_7(7, "§2"),
        ENTRY_8(8, "§3"),
        ENTRY_9(9, "§4"),
        ENTRY_10(10, "§5"),
        ENTRY_11(11, "§6"),
        ENTRY_12(12, "§7"),
        ENTRY_13(13, "§8"),
        ENTRY_14(14, "§9"),
        ENTRY_15(15, "§0");

        private final int entry;
        private final String entryName;
    }
}
