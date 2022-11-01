package com.javafx2dengine.javafx2dengine;

import MatrixTranspose.TransponeMatrix;
import UnityMath.Vector2;
import UnityMath.Vector3;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**Abstract class for Shapes on scene*/
public abstract class AbstractShape extends EngineObject implements Painting {
    public ArrayList<Vector2> vertices;//set of shape's vertices
    public Color color;//shape's color
    public boolean CENTER;//flag center show
    public boolean colored;//flag fill shape color
    public int width;//shape's width
    public int height;//shape's height
    public boolean repaint = false;
    public ShapeObject parent;//reference on parent ShapeObject
    /**Inner constructor for ini vertices and color members.*/
    protected AbstractShape(Color c){
        this.vertices = new ArrayList<>();
        if(c != null) {this.color = c; this.colored = true;}
        else this.colored = false;
    }
    /**Get vertices in screen dimension in camera projection after transformation.*/
    public Vector2 getVertices(Vector2 vertices) {
        Vector2 tmpPos = getParentRotateCenter();//get ShapeObject rotate center coord
        Vector2 screen_coord = new Vector2((int) (vertices.x), (int) (vertices.y));//get vertices in new variable
        Scene.toSceneDimension(screen_coord);//get vertices coord in scene dimension
        Vector3 newPoint = new Vector3(screen_coord, 0);//get vertices in new variable type of Vector3
        Vector2 sceneCenter = new Vector2(0,0);//get 0,0 point
        Scene.toSceneDimension(sceneCenter);//get 0,0 point in screen dimension

        TransponeMatrix.Offset(-(int)sceneCenter.x, -(int)sceneCenter.y, 0, newPoint);//move point in rotate's center
        TransponeMatrix.RotationX(angX + parent.angX, newPoint, 0, 0, 0);//rotate in X axis
        TransponeMatrix.RotationY(angY + parent.angY, newPoint, 0, 0, 0);//rotate in Y axis
        TransponeMatrix.RotationZ(angZ + parent.angZ, newPoint, 0, 0, 0);//rotate in Z axis
        TransponeMatrix.Offset((int)sceneCenter.x, (int)sceneCenter.y, 0, newPoint);//move point from rotate's center
        //TransponeMatrix.Offset((int) this.position.x, (int) -(this.position.y), 0, newPoint);
        TransponeMatrix.Offset((int) tmpPos.x, (int) -(tmpPos.y), 0, newPoint);//move point in shape's position

        newPoint = new Vector3(Scene.camera.projection(new Vector2(newPoint.x, newPoint.y)), 0);//get point coord in camera projection
        return new Vector2(newPoint.x, newPoint.y);//return vertices point after transform
    }
    /**Get list of vertices in screen dimension in camera projection after transformation.*/
    public ArrayList<Vector2> getVertices(ArrayList<Vector2> vertices) {
        Vector2 tmpPos = getParentRotateCenter();//get ShapeObject rotate center coord
        ArrayList<Vector2> dots = new ArrayList<>();//ini set for return
        for (var i : vertices){
            Vector2 screen_coord = new Vector2((int) (i.x), (int) (i.y));//get vertices in new variable
            Scene.toSceneDimension(screen_coord);//get vertices coord in scene dimension
            Vector3 newPoint = new Vector3(screen_coord, 0);//get vertices in new variable type of Vector3
            Vector2 sceneCenter = new Vector2(0,0);//get 0,0 point
            Scene.toSceneDimension(sceneCenter);//get 0,0 point in screen dimension

            TransponeMatrix.Offset(-(int)sceneCenter.x, -(int)sceneCenter.y, 0, newPoint);//move point in rotate's center
            TransponeMatrix.RotationX(angX+parent.angX, newPoint, 0, 0, 0);//rotate in X axis
            TransponeMatrix.RotationY(angY+parent.angY, newPoint, 0, 0, 0);//rotate in Y axis
            TransponeMatrix.RotationZ(angZ+parent.angZ, newPoint, 0, 0, 0);//rotate in Z axis
            TransponeMatrix.Offset((int)sceneCenter.x, (int)sceneCenter.y, 0, newPoint);//move point from rotate's center
            //TransponeMatrix.Offset((int) this.position.x, (int) -(this.position.y), 0, newPoint);
            TransponeMatrix.Offset((int) tmpPos.x, (int) -(tmpPos.y), 0, newPoint);//move point in shape's position

            newPoint = new Vector3(Scene.camera.projection(new Vector2(newPoint.x, newPoint.y)), 0);//get point coord in camera projection

            if(newPoint.x < 0 || newPoint.x >= Scene.WIDTH || newPoint.y < 0 || newPoint.y >= Scene.HEIGHT) return null;//if point out of sceen return null
            else dots.add(new Vector2(newPoint.x, newPoint.y));//else return vertices point after transform

        }
        return dots;
    }
    /**Get parent's ShapeObject for rotate.*/
    private Vector2 getParentRotateCenter(){
        Vector2 screen_coord = new Vector2((int) (this.position.x), (int) (this.position.y));//get vertices in new variable
        Scene.toSceneDimension(screen_coord);//get vertices coord in scene dimension
        Vector3 newPoint = new Vector3(screen_coord, 0);//get vertices in new variable type of Vector3
        Vector2 parentCenter = new Vector2(parent.center);//get parent center in new variable
        Scene.toSceneDimension(parentCenter);//get parent's center in screen dimension

        TransponeMatrix.Offset(-(int)parentCenter.x, -(int)parentCenter.y, 0, newPoint);//move point in rotate's center
        TransponeMatrix.RotationX(parent.angX, newPoint, 0, 0, 0);//rotate in X axis
        TransponeMatrix.RotationY(parent.angY, newPoint, 0, 0, 0);//rotate in Y axis
        TransponeMatrix.RotationZ(parent.angZ, newPoint, 0, 0, 0);//rotate in Z axis
        TransponeMatrix.Offset((int)parentCenter.x, (int)parentCenter.y, 0, newPoint);//move point from rotate's center

        var tmp = new Vector2(newPoint.x, newPoint.y);//get Vector2 from Vector3 newPoint
        Scene.toScreenDimension(tmp);//get parent rotate center in Scene dimension
        newPoint.x = tmp.x;
        newPoint.y = tmp.y;

        //newPoint = new Vector3(Scene.camera.Projection(new Vector2(newPoint.x, newPoint.y)), 0);

        return new Vector2(newPoint.x, newPoint.y);//return parent rotate center
    }
    /**Interpolete to points*///todo explore this function
    public ArrayList<Integer> Interpolate (float i0, float d0, float i1, float d1) {
        var values = new ArrayList<Integer>();
        if (i0 == i1) {
            values.add((int)d0);
            return values;
        }
        float a = (d1 - d0) / (i1 - i0);
        float d = d0;
        for (int i = (int)i0; i <= (int)i1; ++i) {
            values.add((int)d);
            d = d + a;
        }
        return values;
    }
    /**Draw filled triangle using interpolate*///todo exlpore this function
    public void DrawFilledTriangle (Vector2 v0, Vector2 v1, Vector2 v2, PixelWriter g, ShapeObject o) {
        // Сортировка точек так, что y0 <= y1 <= y2
        if (v0.y > v1.y) {
            var tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        if (v0.y > v2.y) {
            var tmp = v0;
            v0 = v2;
            v2 = tmp;
        }
        if (v1.y > v2.y) {
            var tmp = v1;
            v1 = v2;
            v2 = tmp;
        }

        // Вычисление координат x рёбер треугольника
        var x01 = Interpolate(v0.y, v0.x, v1.y, v1.x);
        var x12 = Interpolate(v1.y, v1.x, v2.y, v2.x);
        var x02 = Interpolate(v0.y, v0.x, v2.y, v2.x);

        //# Конкатенация коротких сторон
        x01.remove(x01.size()-1);

        x01.addAll(x12);
        var x012 = x01;

        // Определяем, какая из сторон левая и правая
        ArrayList<Integer> x_left;
        ArrayList<Integer> x_right;
        var m = x012.size() / 2;
        if (x02.get(m) < x012.get(m)) {
            x_left = x02;
            x_right = x012;
        } else {
            x_left = x012;
            x_right = x02;
        }

        //# Отрисовка горизонтальных отрезков
        for (int y = (int)v0.y; y <= v2.y; ++y){
            for (int x = x_left.get(y - (int)v0.y); x <= x_right.get(y - (int)v0.y); ++x){
                g.setColor(x, y, color);
                O_BUFFER(x, y, o);
            }
        }
    }
    /**Draw line using intorpolation*///todo exlpore this function
    public void Brezenheim(Vector2 v1, Vector2 v2, PixelWriter g) {
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (v1.x > v2.x) {
                var tmp = v1;
                v1 = v2;
                v2 = tmp;
            }
            var ys = Interpolate(v1.x, v1.y, v2.x, v2.y);
            /*float invslope1 = (v2.y - v1.y) / (v2.x - v1.x);

            float curx1 = v1.y;*/

            for (int scanlineY = (int) v1.x; scanlineY <= v2.x; scanlineY++) {
                g.setColor(scanlineY, ys.get(scanlineY - (int)v1.x), color);
                //curx1 += invslope1;
            }
        } else /*if (Math.abs(dx) > Math.abs(dy))*/{
            if (v1.y > v2.y) {
                var tmp = v1;
                v1 = v2;
                v2 = tmp;
            }
            /*float invslope1 = (v2.x - v1.x) / (v2.y - v1.y);

            float curx1 = v1.x;*/
            var xs = Interpolate(v1.y, v1.x, v2.y, v2.x);

            for (int scanlineY = (int) v1.y; scanlineY <= v2.y; scanlineY++) {
                g.setColor(xs.get(scanlineY - (int)v1.y), scanlineY, color);
                //curx1 += invslope1;
            }
        } /*else{
            g.drawRect((int)v1.x, (int)v2.y, 1, 1);
        }*/
    }
    /**Fill all screen points references on according ShapeObjects*/
    public static void O_BUFFER(int x, int y, ShapeObject o){//TODO
        if(x >= 0 & x < Scene.WIDTH && y >= 0 & y < Scene.HEIGHT)
            Scene.O_BUFFER[x][y] = o;
    }
    /**Set color*/
    public void setColor(Color c){
        if(c != null) {this.color = c; this.colored = true;}
        else this.colored = false;
    }
}
