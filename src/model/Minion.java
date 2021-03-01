package model;

public class Minion {
    private final Player owner;
    private final Card summoningCard;
    private int life;
    private boolean canAttack;
    private final boolean canBeAttacked;

    public Minion(Player owner, Card summoningCard) {
        this.owner = owner;
        this.summoningCard = summoningCard;
        this.life = summoningCard.getToughness();
        this.canAttack = !summoningCard.hasDefenderAbility();
        this.canBeAttacked = !summoningCard.hasUntouchableAbility();
    }

    public void receiveDamage(int numberOfDamage) {
        this.life -= numberOfDamage;
    }

    public Card getSummoningCard() {
        return this.summoningCard;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean getCanBeAttacked() {
        return this.canBeAttacked;
    }

    public boolean getCanAttack() {
        return this.canAttack;
    }

    public int getLife() {
        return this.life;
    }

    public void changeCanAttack(boolean newValue) {
        this.canAttack = newValue;
    }
}

