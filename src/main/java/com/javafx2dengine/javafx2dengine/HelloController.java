package com.javafx2dengine.javafx2dengine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    @FXML
    private ImageView canvas;
    private WritableImage graphics;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Ini");
        graphics = new WritableImage(WIDTH, HEIGHT);
        PixelWriter pw = graphics.getPixelWriter();

        for (int i = 0; i < graphics.getWidth(); i++)
            for (int j = 0; j < graphics.getHeight(); j++)
                pw.setColor(i,j, Color.RED);

        canvas.setImage(graphics);
    }
}