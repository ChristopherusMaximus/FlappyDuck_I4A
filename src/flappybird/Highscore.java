package flappybird;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 *
 * @author kessler
 */
public class Highscore implements Einstellungen {

    private File file;
    private HashMap<Integer, String> highscoreMap;
    private int highscoreLevel;      // Punktestand ab dem man aktuell in den Highscore kommt

    public Highscore() {
        file = new File(HIGHSCORE_FILENAME);
        try {
            readMap();
            ausgabeHighscore();
        } catch (Exception e) {
            highscoreMap = new HashMap<>();
            highscoreLevel = 1;
        }
    }

    private void readMap() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream is;
        is = new ObjectInputStream(new FileInputStream(file));
        highscoreMap = (HashMap<Integer, String>) is.readObject();
        is.close();
    }

    private void writeMap() throws FileNotFoundException, IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
        os.writeObject(highscoreMap);
        os.close();
    }

    public boolean gibHighscoreLevel(int punkte) {
        return punkte >= highscoreLevel;
    }

    public void mapEintragHinzufuegen(int punkte, String name) {
        if (highscoreMap.containsKey(punkte)) {
            name = highscoreMap.get(punkte) + "\n" + name;      // neuen Namenseintrag anhängen
        }
        highscoreMap.put(punkte, name);
        try {
            writeMap();
        } catch (IOException ex) {
            Logger.getLogger(Highscore.class.getName()).log(Level.SEVERE, null, ex);     // !!!
        }
    }

    public void zeigeMap(TextArea textArea) {
        textArea.setText(ausgabeHighscore());
    }

    // Formattiere den Text in dem der Highscore ausgegeben wird
    private String ausgabeHighscore() {
        String ausgabe = "   Highscore:\n";
        Integer[] punkteSortiert = highscoreMap.keySet().toArray(new Integer[highscoreMap.size()]);
        Arrays.sort(punkteSortiert, Comparator.reverseOrder()); // rückwärts sortieren
        int zaehler = 0;                // zählt die auszugebenden Zeilen
        for (int key : punkteSortiert) {
            String[] namen = highscoreMap.get(key).split("\n");
            for (int i = 0; i < namen.length && zaehler < HIGHSCORE_SIZE; ++i) {
                ausgabe += "" + (zaehler + 1) + ") " + namen[i] + " : " + key + "\n";
                ++zaehler;
                highscoreLevel = 1;                           //key: geringster im highscore angezeiger Punktestand
                if (zaehler >= HIGHSCORE_SIZE) {              // bei diesem Punktestand ist der Highscore schon voll
                    highscoreLevel = key+1;
                }
            }
        }
        return ausgabe;
    }
}
