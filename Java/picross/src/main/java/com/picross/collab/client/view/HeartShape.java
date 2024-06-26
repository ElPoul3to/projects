package com.picross.collab.client.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class HeartShape extends Group {

    /**
     * Constructs a HeartShape object which represents a life in the game.
     * The heart shape is created using two circles and a polygon, all filled with red color.
     *
     * @param centerHeartX The x-coordinate of the center of the heart shape.
     * @param centerHeartY The y-coordinate of the center of the heart shape.
     */
    public HeartShape(double centerHeartX, double centerHeartY) {
        super();
        Circle circle1 = new Circle(centerHeartX - 8, centerHeartY - 8, 12);
        Circle circle2 = new Circle(centerHeartX + 8, centerHeartY - 8, 12);
        circle1.setFill(Color.RED);
        circle2.setFill(Color.RED);

        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(centerHeartX - 17, centerHeartY,
                centerHeartX + 17, centerHeartY,
                centerHeartX, centerHeartY + 16);

        polygon.setFill(Color.RED);

        getChildren().addAll(circle1, circle2, polygon);
    }
}
