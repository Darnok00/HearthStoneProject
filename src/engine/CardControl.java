package engine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Card;
import model.Minion;
import model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class CardControl extends StackPane implements Initializable {
    @FXML
    private ImageView backCard;
    @FXML
    private ImageView placeOnCard;
    @FXML
    private Pane card;
    @FXML
    private Label nameCard;
    @FXML
    private Label attack;
    @FXML
    private Label toughness;
    @FXML
    private Label manaCost;
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
    private Card cardInHand;
    private Player owner;
    private SummonedMinionObserver summonedMinionObserver;
    private SummonedMinionErrorObserver summonedMinionErrorObserver;

    public void setSummonedMinionErrorObserver(SummonedMinionErrorObserver errorObserver) {
        this.summonedMinionErrorObserver = errorObserver;
    }

    public void setSummonedMinionObserver(SummonedMinionObserver summonedMinionObserver) {
        this.summonedMinionObserver = summonedMinionObserver;
    }

    public void setCard(Card card, Player owner) {
        this.cardInHand = card;
        this.owner = owner;
        this.nameCard.setText(card.getNameCard());
        this.attack.setText(Integer.toString(card.getAttack()));
        this.toughness.setText(Integer.toString(card.getToughness()));
        this.manaCost.setText(Integer.toString(card.getManaCost()));
        this.deathtouch.setVisible(card.hasDeathtouchAbility());
        this.defender.setVisible(card.hasDefenderAbility());
        this.lifelink.setVisible(card.hasLifelinkAbility());
        this.taunt.setVisible(card.hasTauntAbility());
        this.untouchable.setVisible(card.hasUntouchableAbility());
        this.backCard.setVisible(false);
        this.placeOnCard.setVisible(false);
        this.card.setVisible(true);
    }

    public void hideCard() {
        this.backCard.setVisible(true);
        this.card.setVisible(false);
        this.placeOnCard.setVisible(false);
    }

    public void deleteCard() {
        this.backCard.setVisible(false);
        this.card.setVisible(false);
        this.placeOnCard.setVisible(true);
    }

    public void viewCard() {
        this.backCard.setVisible(false);
        this.card.setVisible(true);
        this.placeOnCard.setVisible(false);
    }

    public void summon() {
        if (owner.canPlayCard(cardInHand)) {
            Minion minion = owner.playCard(cardInHand);
            deleteCard();
            summonedMinionObserver.minionSummoned(minion);
        } else {
            summonedMinionErrorObserver.summonMinionErrorOccured();
        }
    }

    public ImageView getPlaceOnCard() {
        return this.placeOnCard;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.placeOnCard.setVisible(true);
        this.backCard.setVisible(false);
        this.card.setVisible(false);
    }
}
