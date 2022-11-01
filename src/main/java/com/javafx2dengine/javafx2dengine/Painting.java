package com.javafx2dengine.javafx2dengine;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**Interface for print Shapes on scene*/
public interface Painting {
    /**pint object on scene*/
    void paint(PixelWriter g, ShapeObject o);
}
