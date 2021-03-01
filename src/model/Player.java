package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Deck deck;
    private int life;
    private int numberOfManaCrystals;
    private int manaCrystalsRegenerationRate = 1;
    private final String name;
    private final List<Minion> minionList = new ArrayList<>();
    private final List<Card> cardsInHand = new ArrayList<>();
    private static final int MAX_NUMBER_OF_CARDS_IN_HAND = 6;
    private static final int MAX_NUMBER_OF_MINION_IN_BATTLEFIELD = 7;
    private static final int NUMBER_OF_CARD_IN_START_HAND = 3;
    private static final int MAX_NUMBER_OF_MANA_CRYSTALS = 10;
    private static final int STARTING_LIFE = 20;
    private static final int STARTING_NUMBER_OF_MANA_CRYSTAL = 0;

    public Player(Deck deck, String name) {
        this.deck = deck;
        this.life = STARTING_LIFE;
        this.numberOfManaCrystals = STARTING_NUMBER_OF_MANA_CRYSTAL;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addCrystalMana() {
        numberOfManaCrystals = manaCrystalsRegenerationRate;
        manaCrystalsRegenerationRate = Math.min(manaCrystalsRegenerationRate + 1, MAX_NUMBER_OF_MANA_CRYSTALS);
    }

    public void removeManaCrystals(int manaCost) {
        this.numberOfManaCrystals -= manaCost;
    }

    public int getLife() {
        return this.life;
    }

    public int getNumberOfManaCrystals() {
        return this.numberOfManaCrystals;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void startHand() {
        for (int i = 0; i < NUMBER_OF_CARD_IN_START_HAND; i++) {
            this.draw();
        }
    }

    public Card draw() {
        Card card = this.deck.getAndDeleteFirstCard();
        cardsInHand.add(card);
        return card;
    }

    public boolean canDraw() {
        return this.cardsInHand.size() < MAX_NUMBER_OF_CARDS_IN_HAND;
    }

    public Minion playCard(Card cardToPlay) {
        this.cardsInHand.remove(cardToPlay);
        Minion minionToPlay = new Minion(this, cardToPlay);
        this.minionList.add(minionToPlay);
        this.removeManaCrystals(cardToPlay.getManaCost());
        return minionToPlay;
    }

    public boolean canPlayCard(Card cardToPlay) {
        return this.numberOfManaCrystals >= cardToPlay.getManaCost()
                && this.minionList.size() < MAX_NUMBER_OF_MINION_IN_BATTLEFIELD;
    }

    public void attackPlayer(Minion myMinion, Player attackedPlayer) {
        int attackingMinionAttack = myMinion.getSummoningCard().getAttack();
        attackedPlayer.receiveDamage(attackingMinionAttack);
        if (myMinion.getSummoningCard().hasLifelinkAbility()) this.gainLife(attackingMinionAttack);
        myMinion.changeCanAttack(false);
    }

    public void attackMinion(Minion myMinion, Minion attackedMinion) {
        int myMinionAttack = myMinion.getSummoningCard().getAttack();
        int attackedMinionAttack = attackedMinion.getSummoningCard().getAttack();
        int myMinionLife = myMinion.getLife();
        int attackedMinionLife = attackedMinion.getLife();
        Card myMinionCard = myMinion.getSummoningCard();
        Card attackedMinionCard = attackedMinion.getSummoningCard();
        if (myMinionCard.hasDeathtouchAbility()) attackedMinion.receiveDamage(attackedMinionLife);
        else attackedMinion.receiveDamage(myMinionAttack);
        if (attackedMinionCard.hasDeathtouchAbility()) myMinion.receiveDamage(myMinionLife);
        else myMinion.receiveDamage(attackedMinionAttack);
        if (myMinionCard.hasLifelinkAbility())
            myMinion.getOwner().gainLife(myMinionAttack);
        if (attackedMinionCard.hasLifelinkAbility())
            attackedMinion.getOwner().gainLife(attackedMinionAttack);
        if (attackedMinion.getLife() <= 0) attackedMinion.getOwner().deleteMinionFromBattlefield(attackedMinion);
        myMinion.changeCanAttack(false);
        if (myMinion.getLife() <= 0) this.deleteMinionFromBattlefield(myMinion);
    }

    private void receiveDamage(int damage) {
        this.life -= damage;
    }

    private void gainLife(int numberOfGainedLife) {
        this.life += numberOfGainedLife;
    }

    public boolean canAttackPlayer(Minion myMinion, Player attackedPlayer) {
        if (!myMinion.getCanAttack() || this.equals(attackedPlayer)) return false;
        else {
            return !isControlMinionWithTaunt(attackedPlayer);
        }
    }

    public boolean canAttackMinion(Minion myMinion, Minion attackedMinion) {
        if (!myMinion.getCanAttack() || !attackedMinion.getCanBeAttacked()
                || this.equals(attackedMinion.getOwner())) return false;
        else {
            return attackedMinion.getSummoningCard().hasTauntAbility() ||
                    !isControlMinionWithTaunt(attackedMinion.getOwner());
        }
    }

    public boolean isControlMinionWithTaunt(Player player) {
        for (int i = 0; i < player.minionList.size(); i++) {
            if (player.minionList.get(i).getSummoningCard().hasTauntAbility()) return true;
        }
        return false;
    }

    private void deleteMinionFromBattlefield(Minion minion) {
        this.minionList.remove(minion);
    }

}
