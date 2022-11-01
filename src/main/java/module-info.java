module com.javafx2dengine.javafx2dengine {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires MatrixTransforms;

    opens com.javafx2dengine.javafx2dengine to javafx.fxml;
    exports com.javafx2dengine.javafx2dengine;
}