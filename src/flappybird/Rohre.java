/*
 * Verwaltung eines Rohrpaares
 */
package flappybird;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Rohre implements Einstellungen {

    private final Pane pane;
    private final ImageView blockUnten, blockOben;
    private final double blockHoehe, blockBreite;
    private double xPos, yPosUnten;

    private final Vogel vogel;
    private boolean punktVergeben;

    public Rohre(Pane pane, Vogel vogel) {
        this.pane = pane;
        this.vogel = vogel;
        punktVergeben = false;
        Image imgOben = new Image("/bilder/BlockOben.png");
        blockHoehe = imgOben.getHeight();
        blockBreite = imgOben.getWidth();

        blockOben = new ImageView(new Image("/bilder/BlockOben.png"));
        blockUnten = new ImageView(new Image("/bilder/BlockUnten.png"));
        xPos = pane.getPrefWidth();

        // Auslosen der y-Position des unteren Blocks
        // kleinstmöglicher Wert für yPosUnten : ROHRABSTAND_OBEN + ROHRABSTAND_Y 
        // größtmöglicher Wert für yPosUnten : hoehe der Pane -  ROHRABSTAND_UNTEN 
        Random zufall = new Random();
        double yMin = ROHRABSTAND_OBEN + ROHRABSTAND_Y;
        double yMax = pane.getPrefHeight() - ROHRABSTAND_UNTEN;
        yPosUnten = zufall.nextDouble() * (yMax - yMin) + yMin;

        blockUnten.setTranslateX(xPos);
        blockUnten.setTranslateY(yPosUnten);
        pane.getChildren().add(blockUnten);

        blockOben.setTranslateX(xPos);
        blockOben.setTranslateY(yPosUnten - ROHRABSTAND_Y - blockHoehe);      // !!!
        pane.getChildren().add(blockOben);
    }

    public void verschieben() {
        xPos -= SCHRITTWEITE_ROHR;
        blockOben.setTranslateX(xPos);
        blockUnten.setTranslateX(xPos);
    }

    /**
     * Falls nicht mehr sichtbar, ntferne Blöcke und gib true zurück
     *
     * @return
     */
    public boolean entfernen() {
        if (xPos < -blockBreite) {
            pane.getChildren().remove(blockOben);
            pane.getChildren().remove(blockUnten);
            return true;

        }
        return false;
    }

    public boolean kollidiert() {
        Bounds bu = blockUnten.getBoundsInParent();
        Bounds bo = blockOben.getBoundsInParent();
        Bounds bv = vogel.gibBounds();
        return bu.intersects(bv) || bo.intersects(bv);
    }

    public boolean rohrPassiert() {
        if (xPos + blockBreite < VOGEL_X && !punktVergeben) {
            punktVergeben = true;
            return true;
        }
        return false;
    }
}
