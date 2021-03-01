package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Deck {
    private final List<Card> cardsList = new ArrayList<>();
    private final DataCardsProvider dataCardsProvider;

    public Deck(DataCardsProvider dataCardsProvider) {
        this.dataCardsProvider = dataCardsProvider;
        initDeck();
    }

    private void initDeck() {
        List<Card> cards = dataCardsProvider.loadDataToList();
        int startNumberOfCardInDeck = 30;
        for (int i = 0; i < startNumberOfCardInDeck; i++) {
            this.cardsList.add(i, cards.get((int) round(random() * (cards.size() - 1))));
        }
    }

    public Card getAndDeleteFirstCard() {
        Card card = this.cardsList.get(0);
        this.cardsList.remove(0);
        return card;
    }
}

