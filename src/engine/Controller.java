package engine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Card;
import model.Deck;
import model.JsonDataCardsProvider;
import model.Player;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import java.util.ArrayList;

public class Controller implements Initializable {
    @FXML
    private CardControl card1Player2Controller;
    @FXML
    private CardControl card2Player2Controller;
    @FXML
    private CardControl card3Player2Controller;
    @FXML
    private CardControl card4Player2Controller;
    @FXML
    private CardControl card5Player2Controller;
    @FXML
    private CardControl card6Player2Controller;
    @FXML
    private CardControl card1Player1Controller;
    @FXML
    private CardControl card2Player1Controller;
    @FXML
    private CardControl card3Player1Controller;
    @FXML
    private CardControl card4Player1Controller;
    @FXML
    private CardControl card5Player1Controller;
    @FXML
    private CardControl card6Player1Controller;
    @FXML
    private PlayerControl player2Controller;
    @FXML
    private PlayerControl player1Controller;
    @FXML
    private MinionControl minion1Player2Controller;
    @FXML
    private MinionControl minion2Player2Controller;
    @FXML
    private MinionControl minion3Player2Controller;
    @FXML
    private MinionControl minion4Player2Controller;
    @FXML
    private MinionControl minion5Player2Controller;
    @FXML
    private MinionControl minion6Player2Controller;
    @FXML
    private MinionControl minion7Player2Controller;
    @FXML
    private MinionControl minion1Player1Controller;
    @FXML
    private MinionControl minion2Player1Controller;
    @FXML
    private MinionControl minion3Player1Controller;
    @FXML
    private MinionControl minion4Player1Controller;
    @FXML
    private MinionControl minion5Player1Controller;
    @FXML
    private MinionControl minion6Player1Controller;
    @FXML
    private MinionControl minion7Player1Controller;
    @FXML
    private Pane fieldError;
    @FXML
    private Label textError;

    private MinionControl attackingMinion;
    private List<CardControl> player1CardControls;
    private List<CardControl> player2CardControls;
    private List<MinionControl> player1MinionControls;
    private List<MinionControl> player2MinionControls;

    public void setShowError() {
        this.fieldError.setVisible(true);
        this.textError.setVisible(true);
    }

    public void setHideError() {
        this.fieldError.setVisible(false);
        this.textError.setVisible(false);
    }

    public void setTextErrorWithTauntAndPlayer() {
        this.textError.setText("YOU CAN'T ATTACK YOUR OPPONENT!");
    }

    public void setTextErrorWithAttack() {
        this.textError.setText("YOU CAN'T ATTACK THIS MINION!");
    }

    public void setTextErrorWithCantPlayMinion() {
        this.textError.setText("YOU CAN'T PLAY THIS CARD!");
    }

    public void setTextErrorWithSelectTargetWithoutAttacker() {
        this.textError.setText("FIRST YOU HAVE TO CHOOSE ATTACKER MINION!");
    }

    public void setTextWinner() {
        this.textError.setText("YOU ARE THE WINNER!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fieldError.setVisible(false);
        this.textError.setVisible(false);

        player1MinionControls = List.of(
                minion1Player1Controller, minion2Player1Controller, minion3Player1Controller,
                minion4Player1Controller, minion5Player1Controller, minion6Player1Controller,
                minion7Player1Controller
        );
        player2MinionControls = List.of(
                minion1Player2Controller, minion2Player2Controller, minion3Player2Controller,
                minion4Player2Controller, minion5Player2Controller, minion6Player2Controller,
                minion7Player2Controller);

        List<MinionControl> minionControls = new ArrayList<>(player1MinionControls);
        minionControls.addAll(player2MinionControls);

        for (MinionControl control : minionControls) {
            control.setTargetMinionObserver(this::minionTargeted);
            control.setAttackObserver(minionControl -> attackingMinion = minionControl);
        }

        player1Controller.setTargetPlayerObserver(this::playerTargeted);
        player2Controller.setTargetPlayerObserver(this::playerTargeted);
        player1Controller.setEndTurn(this::endTurn);
        player2Controller.setEndTurn(this::endTurn);

        player1CardControls = List.of(
                card1Player1Controller, card2Player1Controller, card3Player1Controller,
                card4Player1Controller, card5Player1Controller, card6Player1Controller);
        player2CardControls = List.of(
                card1Player2Controller, card2Player2Controller, card3Player2Controller,
                card4Player2Controller, card5Player2Controller, card6Player2Controller
        );

        for (CardControl cardControl : player1CardControls) {
            cardControl.setSummonedMinionObserver(minion -> {
                for (MinionControl minionControl : player1MinionControls) {
                    if (minionControl.getEmptyField().isVisible() && !minionControl.getMinionField().isVisible()) {
                        minionControl.setMinion(minion);
                        minionControl.setMyMinion();
                        break;
                    }
                }
                player1Controller.updatePlayer();
            });
            cardControl.setSummonedMinionErrorObserver(() -> {
                setTextErrorWithCantPlayMinion();
                setShowError();
            });
        }
        for (CardControl cardControl : player2CardControls) {
            cardControl.setSummonedMinionObserver(minion -> {
                for (MinionControl minionControl : player2MinionControls) {
                    if (minionControl.getEmptyField().isVisible() && !minionControl.getMinionField().isVisible()) {
                        minionControl.setMinion(minion);
                        minionControl.setMyMinion();
                        break;
                    }
                }
                player2Controller.updatePlayer();
            });
            cardControl.setSummonedMinionErrorObserver(() -> {
                setTextErrorWithCantPlayMinion();
                setShowError();
            });
        }
        initializeGame();
    }

    private void initializeGame() {
        Player player1 = new Player(new Deck(new JsonDataCardsProvider()), "PLAYER 1");
        Player player2 = new Player(new Deck(new JsonDataCardsProvider()), "PLAYER 2");

        player1Controller.setPlayer(player1);
        player2Controller.setPlayer(player2);
        player1.startHand();
        player2.startHand();
        for (int i = 0; i < player1.getCardsInHand().size(); i++) {
            CardControl cardControl = player1CardControls.get(i);
            Card drawnCard = player1.getCardsInHand().get(i);
            cardControl.setCard(drawnCard, player1);
        }
        for (int i = 0; i < player2.getCardsInHand().size(); i++) {
            CardControl cardControl = player2CardControls.get(i);
            Card drawnCard = player2.getCardsInHand().get(i);
            cardControl.setCard(drawnCard, player2);
        }
        this.firstTurn();
    }

    private void firstTurn() {
        for (CardControl cardControl : player2CardControls) {
            if (!cardControl.getPlaceOnCard().isVisible()) cardControl.hideCard();
        }
        player1Controller.getPlayerField().addCrystalMana();
        player1Controller.updatePlayer();
        player1Controller.setMyTurn();
        player2Controller.setMyOpponentTurn();
        for (MinionControl minionControl : player1MinionControls) {
            if (!minionControl.getEmptyField().isVisible() && minionControl.getMinionField().isVisible()) {
                minionControl.setMyMinion();
            }
        }
        for (MinionControl minionControl : player2MinionControls) {
            if (!minionControl.getEmptyField().isVisible() && minionControl.getMinionField().isVisible()) {
                minionControl.setOpponentMinion();
            }
        }

    }

    private void endTurn(PlayerControl playerEndingTurn) {
        attackingMinion = null;
        PlayerControl playerStartingTurn;
        List<CardControl> playerStartingTurnCards;
        List<CardControl> playerEndingTurnCards;
        List<MinionControl> playerStartingTurnMinions;
        List<MinionControl> playerEndingTurnMinions;

        if (playerEndingTurn == player2Controller) {
            playerStartingTurn = player1Controller;
            playerStartingTurnCards = player1CardControls;
            playerEndingTurnCards = player2CardControls;
            playerEndingTurnMinions = player2MinionControls;
            playerStartingTurnMinions = player1MinionControls;
        } else {
            playerStartingTurn = player2Controller;
            playerStartingTurnCards = player2CardControls;
            playerEndingTurnCards = player1CardControls;
            playerEndingTurnMinions = player1MinionControls;
            playerStartingTurnMinions = player2MinionControls;
        }
        Player player2 = playerStartingTurn.getPlayerField();
        playerStartingTurn.getPlayerField().addCrystalMana();
        playerStartingTurn.updatePlayer();
        playerEndingTurn.setMyOpponentTurn();
        playerStartingTurn.setMyTurn();


        for (CardControl cardControl : playerEndingTurnCards) {
            if (!cardControl.getPlaceOnCard().isVisible()) cardControl.hideCard();
        }

        for (MinionControl minionControl : playerStartingTurnMinions) {
            if (!minionControl.getEmptyField().isVisible() && minionControl.getMinionField().isVisible()) {
                minionControl.setMyMinion();
                if (!minionControl.getMinion().getSummoningCard().hasDefenderAbility())

                    minionControl.getMinion().changeCanAttack(true);
            }
        }
        for (MinionControl minionControl : playerEndingTurnMinions) {
            if (!minionControl.getEmptyField().isVisible() && minionControl.getMinionField().isVisible()) {
                minionControl.setOpponentMinion();
                if (!minionControl.getMinion().getSummoningCard().hasDefenderAbility())
                    minionControl.getMinion().changeCanAttack(true);
            }
        }
        if (player2.canDraw()) {
            Card drawenCard;
            for (CardControl cardControl : playerStartingTurnCards) {
                if (cardControl.getPlaceOnCard().isVisible()) {
                    drawenCard = player2.draw();
                    cardControl.setCard(drawenCard, player2);
                    break;
                }
            }
        }
        for (CardControl cardControl : playerStartingTurnCards) {
            if (!cardControl.getPlaceOnCard().isVisible()) cardControl.viewCard();
        }
    }

    private void playerTargeted(PlayerControl opponentControl) {
        if (attackingMinion == null) {
            setTextErrorWithSelectTargetWithoutAttacker();
            setShowError();
        } else {
            Player attacker = attackingMinion.getMinion().getOwner();
            Player opponent = opponentControl.getPlayerField();
            if (attacker.canAttackPlayer(attackingMinion.getMinion(), opponent)) {
                attacker.attackPlayer(attackingMinion.getMinion(), opponent);
                player1Controller.updatePlayer();
                player2Controller.updatePlayer();
                if (opponent.getLife() <= 0) {
                    setTextWinner();
                    setShowError();
                }
            } else {
                setTextErrorWithTauntAndPlayer();
                setShowError();
            }
            attackingMinion = null;
        }
    }

    private void minionTargeted(MinionControl minionControl) {
        if (attackingMinion == null) {
            setTextErrorWithSelectTargetWithoutAttacker();
            setShowError();
        } else {
            Player attacker = attackingMinion.getMinion().getOwner();
            if (attacker.canAttackMinion(attackingMinion.getMinion(), minionControl.getMinion())) {
                attacker.attackMinion(attackingMinion.getMinion(), minionControl.getMinion());
                minionControl.updateMinion();
                attackingMinion.updateMinion();
                player1Controller.updatePlayer();
                player2Controller.updatePlayer();
                if (minionControl.getMinion().getLife() <= 0)
                    minionControl.deleteMinion();
                if (attackingMinion.getMinion().getLife() <= 0)
                    attackingMinion.deleteMinion();
            } else {
                setTextErrorWithAttack();
                setShowError();
            }
        }
        attackingMinion = null;
    }
}
