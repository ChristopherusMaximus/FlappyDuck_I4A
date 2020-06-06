/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import static flappybird.Einstellungen.ROHRE_WARTESCHRITTE;
import java.util.Iterator;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;     



/**
 *
 * @author max.kessler
 */
public class FXMLDocumentController implements Initializable, Einstellungen {

    @FXML
    private Button button;
    @FXML
    private Pane pane;
    @FXML
    private Label labelPunkte;
    @FXML
    private TextArea ta_Highscore_Ausgabe;
    @FXML
    private TextField tf_Highscore_Eingabe;

    private Vogel vogel;

    private ArrayList<Rohre> rohre;
    private int rohreWarteschritteZaehler;     // 

    // Animation der Hindernisse
    private AnimationTimer timerRohre;
    private long last;

    private int punkte;

    private Highscore highscore;
    private Vordergrund vordergrund;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vordergrund = new Vordergrund(pane);
        rohre = new ArrayList<>();
        //  System.out.println("pane: " + pane.getChildren().size() + " - AL: " + rohre.size());  // Test

        timerRohre = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - last > WAIT_ROHR) {
                    last = now;
                    // Steuerung der Wiese
                    vordergrund.verschieben();
                    // Steuerung der Rohre
                    rohreWarteschritteZaehler++;
                    if (rohreWarteschritteZaehler >= ROHRE_WARTESCHRITTE) { // neues Rohrobjekt erzeugen
                        rohre.add(new Rohre(pane, vogel));
                        rohreWarteschritteZaehler = 0;
                    }
                    // for (Rohre rohr : rohre) {
                    Iterator<Rohre> it = rohre.iterator();
                    while (it.hasNext()) {
                        Rohre rohr = it.next();
                        rohr.verschieben();
                        if (rohr.kollidiert()) {
                            rundeBeenden(); //  stop();
                        }
                        if (rohr.entfernen()) {
                            it.remove();
                        }
                        if (rohr.rohrPassiert()) {
                            punkte++;
                            labelPunkte.setText("" + punkte);
                        }
                    }
                }
            }
        };
        vogel = new Vogel(pane, this);
        highscore = new Highscore();
        highscore_Eingabe_ausblenden();
    }

    private void rundeStarten() {        // wird bei Buttonbetätigung aufgerufen 
        highscore_Eingabe_ausblenden();
        // gesamten pane-Inhalt leeren (Rohre, Vogel, Button)
        pane.getChildren().clear();
        // AL leeren
        rohre.clear();

        vordergrund.vordergrundAnzeigen(); // Vordergrund (wieder) anzeigen
        //Neuen Vogel erzeugen (Vogel sitzt wieder am Startpunkt) 
        vogel = new Vogel(pane, this);
        vogel.start();
        //Animationtimer von rohre starten 
        rohre.add(new Rohre(pane, vogel));
        rohreWarteschritteZaehler = 0;
        timerRohre.start();

        pane.requestFocus();
        //Punkte zurücksetzen (falls vorhanden) 
        pane.getChildren().add(labelPunkte);
        punkte = 0;
        labelPunkte.setText("" + punkte);
        //    vordergrund = new Vordergrund(pane);
        vogel.soundAbspielen();
    }

    public void rundeBeenden() {    // wird nach Absturz oder Kollision des Vogels aufgerufen 
        vogel.soundAbspielen();
        //Alle Animationtimer stoppen 
        timerRohre.stop(); // AnimationsTimer für Rohre anhalten
        vogel.stop();
        //Start-Button anzeigen
        pane.getChildren().add(button);
        //Ergebnis einblenden (evtl. auch Highscore) 
        labelPunkte.toFront();
        aktualisiereHighscore();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
//        vogel.start();
//        pane.getChildren().remove(button);
//        pane.requestFocus();
//        timerRohre.start();
        rundeStarten();
    }

    @FXML
    private void handleKeyTyped(KeyEvent event) {
        if (event.getCharacter().equals(" ")) {
            vogel.vogelNachObenBewegen();
        }
    }

    @FXML
    private void tf_Highscore_OnAction(ActionEvent event) {
        String eingabe = tf_Highscore_Eingabe.getText();
        String name = eingabe.substring(0, Math.min(HIGHSCORE_TEXTLENGTH, eingabe.length()));
        highscore.mapEintragHinzufuegen(punkte, name);
        pane.getChildren().remove(tf_Highscore_Eingabe);
        // Highscore anzeigen:
        highscore.zeigeMap(ta_Highscore_Ausgabe);
        button.toFront();
    }

    private void aktualisiereHighscore() {
        if (highscore.gibHighscoreLevel(punkte)) {
            highscoreEingabe();
        } else {
            highscoreAusgabe();
        }
    }

    // Ermöglicht es dem Benutzer, seinen Naemen einzugeben
    private void highscoreEingabe() {
        ta_Highscore_Ausgabe.setText(HIGHSCORE_EINGABEAUFFORDERUNG);
        pane.getChildren().add(ta_Highscore_Ausgabe);
        pane.getChildren().add(tf_Highscore_Eingabe);
        tf_Highscore_Eingabe.requestFocus();
    }

    private void highscoreAusgabe() {
        pane.getChildren().add(ta_Highscore_Ausgabe);
        highscore.zeigeMap(ta_Highscore_Ausgabe);
        button.toFront();
    }

    private void highscore_Eingabe_ausblenden() {
        pane.getChildren().remove(ta_Highscore_Ausgabe);
        pane.getChildren().remove(tf_Highscore_Eingabe);
    }
}
