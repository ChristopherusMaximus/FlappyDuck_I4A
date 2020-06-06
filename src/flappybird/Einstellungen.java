/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

/**
 *
 * @author max.kessler
 */
public interface Einstellungen {

    // Einstellungen Vogel
    long WAIT = (long) 1e8;          // Animationtimer Vogel
    double VOGEL_X = 200;
    double VOGEL_Y_START = 200;

    double VOGEL_RAUF = 100;
    double VOGEL_FALL_0 = 10;       // Anfangsgeschwindigkeit beim Fallen
    double FALLBESCHLEUNIGUNG = 5;  // Zunahme der Fallgeschwindigkeit

    // Einstellungen Rohre
    long WAIT_ROHR = (long) 1e7;    // Geschwindigkeit der Animation der Rohre und der Wiese
    double SCHRITTWEITE_WIESE = 2;

    double SCHRITTWEITE_ROHR = 4;       // Anzahl der Pixel um die die Rohr-Objekte in jedem Schritt verschoben werden

    double ROHRABSTAND_Y = 300;       // vertikaler Abstand zweier Rohre
    double ROHRE_WARTESCHRITTE = 200;       // Anzahl der Animationschritte zwischen der Erzeugung zweier Rohrpaare

    double ROHRABSTAND_OBEN = 50;  // minmaler Abstand der Unterkante des oberen Rohres vom oberen Bildschirmrand
    double ROHRABSTAND_UNTEN = 100; //minimaler Abstand der Oberkante des unteren Rohres vom unteren Bildschirmrand

    // Highscore
    String HIGHSCORE_FILENAME = "flappyHighscore";
    int HIGHSCORE_SIZE = 5;           // maximale Anzahl von Einträgen im highscore
    String HIGHSCORE_EINGABEAUFFORDERUNG = "Gratulation, Du hast es in den Highscore geschafft, bitte gib Deinen Namen ein:";
    int HIGHSCORE_TEXTLENGTH = 15;
}
