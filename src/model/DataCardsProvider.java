package model;

import java.io.FileNotFoundException;
import java.util.List;

public interface DataCardsProvider {
    List<Card> loadDataToList() ;
}
