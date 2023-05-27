package logic;

import data.Parser;
import org.xml.sax.SAXException;
import parameters.MusicBand;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *A class containing methods for loading a collection from a file and initializing it.
 */

public class Loader {

    public static ArrayList<MusicBand> collection = new ArrayList<>();
    public static String dataPath = new String();
    public Loader(String path){
        dataPath = path;
    }

    public ArrayList<MusicBand> loadCollectionFromFile(){
        try {
            collection = Parser.deserialize(dataPath);
            System.out.println("Загружена коллекция: " + collection.getClass().getName() + ". Количество элементов: " + collection.size() + ".");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.print("Ошибка парсера. Проерьте исходный файл: " + e.getMessage() + "\n");
        }
        return collection;
    }
}
