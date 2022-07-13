package de.wt4b.bingo.listener.player;

import de.wt4b.bingo.Bingo;
import de.wt4b.bingo.game.GameManager;
import de.wt4b.bingo.settings.Setting;
import de.wt4b.bingo.settings.SettingsManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 28.06.2022 23:05
 */
public class PlayerLoginListener implements Listener {

    public PlayerLoginListener() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getCurrentPhase() == gameManager.getSettingsPhase()) return;
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (settingsManager.isActivated(Setting.SPECTATOR)) return;
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§cDu kannst nicht zuschauen."));
    }
}