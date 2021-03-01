package engine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Card;
import model.Minion;

import java.net.URL;
import java.util.ResourceBundle;

public class MinionControl implements Initializable {

    @FXML
    private Pane emptyField;
    @FXML
    private Pane minion;
    @FXML
    private Label nameMinion;
    @FXML
    private Label attack;
    @FXML
    private Label life;
    @FXML
    private Label deathtouch;
    @FXML
    private Label defender;
    @FXML
    private Label lifelink;
    @FXML
    private Label taunt;
    @FXML
    private Label untouchable;
    @FXML
    private Button attackButton;
    @FXML
    private Button targetButton;
    private Minion minionOnBattlefield;
    private TargetMinionObserver targetMinionObserver;
    private AttackObserver attackObserver;


    public void setMinion(Minion minion) {
        this.minionOnBattlefield = minion;
        updateMinion();
    }

    public void updateMinion() {
        Card summoningCard = minionOnBattlefield.getSummoningCard();
        this.nameMinion.setText(summoningCard.getNameCard());
        this.attack.setText(Integer.toString(summoningCard.getAttack()));
        this.life.setText(Integer.toString(minionOnBattlefield.getLife()));
        this.deathtouch.setVisible(summoningCard.hasDeathtouchAbility());
        this.defender.setVisible(summoningCard.hasDefenderAbility());
        this.lifelink.setVisible(summoningCard.hasLifelinkAbility());
        this.taunt.setVisible(summoningCard.hasTauntAbility());
        this.untouchable.setVisible(summoningCard.hasUntouchableAbility());
        this.emptyField.setVisible(false);
        this.minion.setVisible(true);
    }

    public Minion getMinion() {
        return minionOnBattlefield;
    }

    public Pane getEmptyField() {
        return this.emptyField;
    }

    public Pane getMinionField() {
        return this.minion;
    }

    public void setTargetMinionObserver(TargetMinionObserver targetMinionObserver) {
        this.targetMinionObserver = targetMinionObserver;
    }

    public void setAttackObserver(AttackObserver attackObserver) {
        this.attackObserver = attackObserver;
    }

    public void deleteMinion() {
        this.minion.setVisible(false);
        this.emptyField.setVisible(true);
    }

    public void setMyMinion() {
        this.targetButton.setVisible(false);
        this.attackButton.setVisible(true);
    }

    public void setOpponentMinion() {
        this.attackButton.setVisible(false);
        this.targetButton.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.emptyField.setVisible(true);
        this.minion.setVisible(false);
        this.targetButton.setVisible(false);
        this.targetButton.setVisible(false);
    }

    @FXML
    public void attackMinion() {
        attackObserver.minionAttacked(this);
    }

    public void chooseTargetMinion() {
        targetMinionObserver.minionTargeted(this);
    }
}