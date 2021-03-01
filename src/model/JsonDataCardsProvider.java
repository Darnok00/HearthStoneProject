package model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class JsonDataCardsProvider implements DataCardsProvider {
    private List<Card> cardsList;

    public List<Card> loadDataToList() {
        if (cardsList == null) {
            Gson gson = new Gson();
            JsonReader reader = null;
            try {
                reader = new JsonReader(new FileReader("src/model/parameters.json"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("Parameters file doesn't exist.");
                System.exit(-1);
            }
            Card[] cardsArray = gson.fromJson(reader, Card[].class);
            cardsList = Arrays.asList(cardsArray);
        }
        return cardsList;

    }

}
