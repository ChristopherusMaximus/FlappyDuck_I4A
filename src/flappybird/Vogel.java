/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author max.kessler
 */
public class Vogel extends AnimationTimer implements Einstellungen {

    private final Pane pane;
    private Button button;

    private ImageView[] bilder;
    private int bildnummer;

    private long last;

    private double x, y;
    private double spielfeldhoehe;
    private double fallgeschwindigkeit;

    private FXMLDocumentController controller;

    String soundFile = this.getClass().getResource("/sounds/quack.wav").toString();
    private final Media sound = new Media(soundFile);
    private MediaPlayer mediaplayer;

    public void soundAbspielen() {
        mediaplayer = new MediaPlayer(sound);	// für jedes Abspielen wird ein neues 
        // mediaplayer-Objekt benötigt
        mediaplayer.play();
    }

    public Vogel(Pane pane, FXMLDocumentController controller) {
        this.pane = pane;
        this.controller = controller;
        bilderArrayErzeugen();
        bildnummer = 0;
        spielfeldhoehe = pane.getPrefHeight();

    }

    @Override
    public void start() {
        x = VOGEL_X;
        y = VOGEL_Y_START;
        fallgeschwindigkeit = VOGEL_FALL_0;
        super.start();
    }

    private void bilderArrayErzeugen() {
        bilder = new ImageView[5];
        for (int i = 0; i < bilder.length; ++i) {
            Image image = new Image("/bilder/bird_" + i + ".png");
            bilder[i] = new ImageView(image);
        }
    }

    public void vogelNachObenBewegen() {
        y -= VOGEL_RAUF;
        fallgeschwindigkeit = VOGEL_FALL_0;
        if (y < 0) {
            y = 0;
        }
    }

    @Override
    public void handle(long now) {
        if (now - last > WAIT) {
            last = now;
            pane.getChildren().remove(bilder[bildnummer++]);
            bildnummer %= 5;        // 5%5 = 0 !
            bilder[bildnummer].setTranslateX(x);
            bilder[bildnummer].setTranslateY(y);
            pane.getChildren().add(bilder[bildnummer]);

            y += fallgeschwindigkeit;
            fallgeschwindigkeit += FALLBESCHLEUNIGUNG;

            if (y > spielfeldhoehe - 50) {
                controller.rundeBeenden();
            }
        }
    }

    public Bounds gibBounds() {
        return bilder[bildnummer].getBoundsInParent();
    }

}
