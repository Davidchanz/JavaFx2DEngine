package com.javafx2dengine.javafx2dengine;

import UnityMath.Vector2;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**Class 2D Circle*/
public class Circle extends AbstractShape implements Resizing{
    /**Circle constructor for oval
     * ini height, width, position, color
     * compute and add vertices for circle*/
    public Circle(int height, int width, Vector2 pos, Color c){
        super(c);//ini AbstractShape constructor
        this.height = height;//ini height
        this.width = width;//ini width
        this.position = new Vector2(pos);//ini position
        this.center = new Vector2(0,0);//ini center 0,0
        for(double angle = 0.0; angle <= Math.PI * 2; angle += 1.0/Math.min(this.width, this.height)) {
            double x = this.width * Math.sin(angle);
            double y = this.height * Math.cos(angle);
            this.vertices.add(new Vector2((float) x,(float) y));//add point in vertices
        }
    }
    /**Circle constructor for circle
     * ini height, width, position, color
     * compute and add vertices for circle
     * radius = width = height*/
    public Circle(int radius, Vector2 pos, Color c){
        super(c);//ini AbstractShape constructor
        this.position = new Vector2(pos);//ini position
        this.center = new Vector2(0,0);//ini center 0,0
        this.height = radius;//ini height
        this.width = radius;//ini width
        for(double angle = 0.0; angle <= Math.PI * 2; angle += 1.0/ this.width) {
            double x = this.width * Math.sin(angle);
            double y = this.height * Math.cos(angle);
            this.vertices.add(new Vector2((float) x,(float) y));//add point in vertices
        }
    }
    /**Method for paint on scene*/
    @Override
    public void paint(PixelWriter g, ShapeObject o) {
        ArrayList<Vector2> dots = getVertices(this.vertices);//get vertices for paint in screen dimension
        Vector2 zero = getVertices(this.center);//get object center in screen dimension
        if(dots == null) return;//if no points for paint return
        if(colored) {//if color flag true fill object
            //g.setColor(this.color);//set color object color
            int i = 0;//ini count
            do{//get first vertices point
                Vector2 p2 = dots.get(i);//get i vertices point
                Brezenheim(zero, p2, g);//paint line from first point to i point
                i++;//inc i
            }while(i < (dots.size()));//when paint all points break
        }
        //g.setColor(Color.BLACK);//set color BLACK
        for(int i = 0; i < dots.size()-1; ++i){
            Brezenheim(dots.get(i), dots.get(i+1), g);//paint line form i to i+1 point
        }
        Brezenheim(dots.get(dots.size()-1), dots.get(0), g);//paint line from last to first point
        if(this.CENTER) {//if center flag true paint center point
            //g.setColor(Color.RED);//set color for center point RED
            g.setColor((int) zero.x, (int) zero.y, color);//paint center point
        }
    }
    /**Method for resize object*/
    @Override
    public void resize() {
        this.vertices.clear();//clear old vertices
        this.center = new Vector2(this.position);//ini new center TODO
        for(double angle = 0.0; angle <= Math.PI * 2; angle += 1.0/Math.min(this.width, this.height)) {
            double x = this.width * Math.sin(angle);
            double y = this.height * Math.cos(angle);
            this.vertices.add(new Vector2((float) x,(float) y));//add new vertices
        }
    }
}
