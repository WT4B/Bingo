package de.wt4b.bingo;

import de.wt4b.bingo.commands.*;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.items.BingoItemListener;
import de.wt4b.bingo.items.BingoItemManager;
import de.wt4b.bingo.items.FindItemListener;
import de.wt4b.bingo.listener.block.BlockBreakListener;
import de.wt4b.bingo.listener.block.BlockPlaceListener;
import de.wt4b.bingo.listener.entity.EntityDamageListener;
import de.wt4b.bingo.listener.entity.EntityDropItemListener;
import de.wt4b.bingo.listener.entity.EntityPickupItemListener;
import de.wt4b.bingo.listener.player.*;
import de.wt4b.bingo.listener.world.WeatherChangeListener;
import de.wt4b.bingo.manager.ConfigManager;
import de.wt4b.bingo.manager.ResetManager;
import de.wt4b.bingo.scoreboard.sidebar.ScoreboardManager;
import de.wt4b.bingo.scoreboard.tablist.TablistManager;
import de.wt4b.bingo.settings.SettingsListener;
import de.wt4b.bingo.settings.SettingsManager;
import de.wt4b.bingo.team.BackpackListener;
import de.wt4b.bingo.team.TeamListener;
import de.wt4b.bingo.team.TeamManager;
import de.wt4b.bingo.timer.Timer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 21:07
 */
@Getter
public class Bingo extends JavaPlugin {

    private static Bingo instance;

    private Timer timer;
    private ResetManager resetManager;

    public static Bingo getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return "§8[§bBingo§8] §b";
    }

    public static String getNoPerms() {
        return "§cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.";
    }

    public static String getUsage() {
        return getPrefix() + "§7Verwende§8: §c";
    }

    @Override
    public void onEnable() {
        PluginDescriptionFile pdf = getDescription();
        Bukkit.getConsoleSender().sendMessage("---------------");
        Bukkit.getConsoleSender().sendMessage("§7[§aName§7] §a" + pdf.getName());
        Bukkit.getConsoleSender().sendMessage("§7[§aAuthor§7] §a" + pdf.getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7[§aVersion§7] §a" + pdf.getVersion());
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "§7- §a§lOnline");
        Bukkit.getConsoleSender().sendMessage("---------------");

        instance = this;

        new ConfigManager();
        new ScoreboardManager();

        this.timer = new Timer();
        this.resetManager = new ResetManager();

        new BingoItemManager();
        new SettingsManager();
        new TeamManager();
        new GameManager();
        new TablistManager();

        registerListener();
        registerCommands();

        Bukkit.getWorlds().forEach(world -> {
            world.setDifficulty(Difficulty.EASY);
            world.setTime(1000);
            world.setGameRule(GameRule.SPAWN_RADIUS, 10);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        });
    }

    @Override
    public void onDisable() {
        if (resetManager.isReset()) resetManager.reset();

        PluginDescriptionFile pdf = getDescription();
        Bukkit.getConsoleSender().sendMessage("---------------");
        Bukkit.getConsoleSender().sendMessage("§7[§aName§7] §a" + pdf.getName());
        Bukkit.getConsoleSender().sendMessage("§7[§aAuthor§7] §a" + pdf.getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7[§aVersion§7] §a" + pdf.getVersion());
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "§7- §c§lOffline");
        Bukkit.getConsoleSender().sendMessage("---------------");
    }

    private void registerListener() {
        new BlockBreakListener();
        new BlockPlaceListener();

        new EntityDamageListener();
        new EntityDropItemListener();
        new EntityPickupItemListener();

        new AsyncChatListener();
        new FoodLevelChangeListener();
        new InventoryClickListener();
        new PlayerInteractListener();
        new PlayerJoinListener();
        new PlayerLoginListener();
        new PlayerMoveListener();
        new PlayerQuitListener();

        new WeatherChangeListener();

        new BingoItemListener();
        new FindItemListener();
        new SettingsListener();
        new BackpackListener();
        new TeamListener();
    }

    private void registerCommands() {
        new BackpackCommand();
        new BingoCommand();
        new PositionCommand();
        new ResetCommand();
        new SettingsCommand();
        new TimerCommand();
        new TopCommand();
    }
}
