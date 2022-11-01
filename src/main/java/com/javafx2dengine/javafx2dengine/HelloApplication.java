package com.javafx2dengine.javafx2dengine;

import UnityMath.Vector2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), HelloController.WIDTH+100, HelloController.HEIGHT+100);

        ShapeObject obj = new ShapeObject("test", 2);
        obj.add(new Triangle(10, new Vector2(0,0), Color.CYAN));
        scene.add(obj);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), e ->run(HelloController.graphics)));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public static void main(String[] args) {
        launch();
    }
    private void run(WritableImage graphics){
        System.out.println("1111111");

        PixelWriter pw = graphics.getPixelWriter();
        scene.repaint(pw);

        /*
        for (int i = 0; i < graphics.getWidth(); i++)
            for (int j = 0; j < graphics.getHeight(); j++)
                pw.setColor(i,j, Color.GREEN);*/
    }
}