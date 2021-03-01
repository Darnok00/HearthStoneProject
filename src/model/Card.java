package model;

public class Card {
    private final String nameCard;
    private final int attack;
    private final int toughness;
    private final int manaCost;
    private final boolean abilityDeathtouch;
    private final boolean abilityDefender;
    private final boolean abilityLifelink;
    private final boolean abilityTaunt;
    private final boolean abilityUntouchable;

    public Card(String nameCard, int attack, int toughness, int manaCost, boolean abilityDeathTouch,
                boolean abilityDefender, boolean abilityLifelink, boolean abilityTaunt,
                boolean abilityUntouchable) {
        this.nameCard = nameCard;
        this.attack = attack;
        this.toughness = toughness;
        this.manaCost = manaCost;
        this.abilityDeathtouch = abilityDeathTouch;
        this.abilityDefender = abilityDefender;
        this.abilityLifelink = abilityLifelink;
        this.abilityTaunt = abilityTaunt;
        this.abilityUntouchable = abilityUntouchable;
    }

    public String getNameCard() {
        return this.nameCard;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getToughness() {
        return this.toughness;
    }

    public boolean hasDeathtouchAbility() {
        return this.abilityDeathtouch;
    }

    public boolean hasDefenderAbility() {
        return this.abilityDefender;
    }

    public boolean hasLifelinkAbility() {
        return this.abilityLifelink;
    }

    public boolean hasTauntAbility() {
        return this.abilityTaunt;
    }

    public boolean hasUntouchableAbility() {
        return this.abilityUntouchable;
    }

    public int getManaCost() {
        return this.manaCost;
    }
}
