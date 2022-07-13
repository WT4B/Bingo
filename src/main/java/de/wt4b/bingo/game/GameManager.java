package de.wt4b.bingo.game;

import de.wt4b.bingo.game.phases.FinishPhase;
import de.wt4b.bingo.game.phases.InGamePhase;
import de.wt4b.bingo.game.phases.SettingsPhase;
import lombok.Getter;

/**
 * @author - WT4B
 * @GitHub - https://github.com/WT4B
 * @Twitter - https://twitter.com/WT4B_
 * Erstellt - 26.06.2022 22:20
 */
@Getter
public class GameManager {

    private static GameManager instance;
    private final SettingsPhase settingsPhase;
    private final InGamePhase inGamePhase;
    private final FinishPhase finishPhase;
    private GamePhase currentPhase;

    public GameManager() {
        instance = this;
        this.settingsPhase = new SettingsPhase();
        this.inGamePhase = new InGamePhase();
        this.finishPhase = new FinishPhase();

        setPhase(settingsPhase);
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void setPhase(GamePhase gamePhase) {
        if (this.currentPhase != null) this.currentPhase.onDisable();
        this.currentPhase = gamePhase;
        this.currentPhase.onEnable();
    }
}
