package engine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerControl implements Initializable {
    @FXML
    private Pane player;
    @FXML
    private Label life;
    @FXML
    private Label playerName;
    @FXML
    private Label manaCristal;
    @FXML
    private Button endTurnButton;
    @FXML
    private Button targetButton;
    private Player playerField;
    private TargetPlayerObserver targetPlayerObserver;
    private EndTurnObserver endTurnObserver;

    public Player getPlayerField() {
        return this.playerField;
    }

    public void setPlayer(Player player) {
        this.playerField = player;
        updatePlayer();
    }
    public void updatePlayer() {
        this.life.setText(Integer.toString(playerField.getLife()));
        this.manaCristal.setText(Integer.toString(playerField.getNumberOfManaCrystals()));
        this.playerName.setText(playerField.getName());
    }

    public void setTargetPlayerObserver(TargetPlayerObserver targetPlayerObserver) {
        this.targetPlayerObserver = targetPlayerObserver;
    }

    public void endTurn() {
        this.endTurnObserver.endedTurn(this);
    }

    public void setEndTurn(EndTurnObserver endTurnObserver) {
        this.endTurnObserver = endTurnObserver;
    }

    public void targetPlayer() {
        this.targetPlayerObserver.playerTargeted(this);
    }

    public void setMyTurn() {
        this.endTurnButton.setVisible(true);
        this.targetButton.setVisible(false);
    }

    public void setMyOpponentTurn() {
        this.endTurnButton.setVisible(false);
        this.targetButton.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.player.setVisible(true);
        this.endTurnButton.setVisible(false);
        this.targetButton.setVisible(false);
    }
}
