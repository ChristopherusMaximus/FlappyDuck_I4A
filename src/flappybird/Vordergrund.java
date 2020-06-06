/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import static flappybird.Einstellungen.SCHRITTWEITE_ROHR;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author kessler
 */
public class Vordergrund implements Einstellungen {

    private Pane pane;
    private ImageView vordergrund;

    double xPos = 0;

    public Vordergrund(Pane pane) {
        this.pane = pane;
        Image image = new Image("/bilder/Vordergrund.png");
        vordergrund = new ImageView(image);

        double spielfeldhoehe = pane.getPrefHeight();
        double vordergrundhoehe = image.getHeight();
        vordergrund.setTranslateY(spielfeldhoehe - vordergrundhoehe);

        vordergrundAnzeigen();
    }

    public void vordergrundAnzeigen() {
        pane.getChildren().add(vordergrund);
    }

    public void verschieben() {
        xPos -= SCHRITTWEITE_WIESE;
        vordergrund.setTranslateX(xPos);
    }
}
