package com.picross.collab.client.view;

import com.picross.collab.play.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;

public class LevelsView {

    private static final int itemsToLoad = 2000;

    public Scene createScene(Player player) {
        TilePane circlePane = new TilePane();
        for (int i = 0; i < itemsToLoad; i++) {
            Circle circle = new Circle(5, randomColor());
            circlePane.getChildren().add(circle);
        }

        int numberOfButtons = 10;
        Button[] buttons = new Button[numberOfButtons];
        for (int i = 0; i < numberOfButtons; i++) {
            buttons[i] = new Button("" + (i + 1));
            buttons[i].setOnMouseClicked(e -> {
                System.out.println("Bouton " + ((Button) e.getSource()).getText() + " cliqué !");

                try {
                    player.connectToServer(((Button) e.getSource()).getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
        }

        Text title = new Text("Choix des niveaux");
        title.setFont(Font.font("Minecraftia", 100));
        title.setFill(Color.WHITE);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        title.setEffect(dropShadow);

        for (Button button : buttons) {
            button.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black; -fx-font-family: 'Minecraftia'; -fx-font-size: 16px; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");
        }

        FlowPane buttonFlowPane = new FlowPane();
        buttonFlowPane.getChildren().addAll(buttons);
        buttonFlowPane.setAlignment(Pos.CENTER);
        buttonFlowPane.setHgap(10);
        buttonFlowPane.setVgap(10);
        buttonFlowPane.setPrefWrapLength(400);

        for (Button button : buttons) {
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);
        }

        StackPane root = new StackPane();
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(circlePane, titleBox, buttonFlowPane);

        Scene scene = new Scene(root, 400, 400);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Button button : buttons) {
                button.setPrefWidth(newVal.doubleValue() / 4);
            }
        });

        title.setFont(Font.font("Minecraftia", 45));
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newSize = newVal.doubleValue() / 10;
            title.setFont(Font.font("Minecraftia", newSize));
        });

        Font.loadFont(getClass().getResourceAsStream("Minecraftia.ttf"), 14);

        return scene;
    }

    private Color randomColor() {
        return Color.rgb(getRandomColor(125, 180), getRandomColor(180, 220), getRandomColor(180, 255));
    }

    public int getRandomColor(int minValue, int maxValue) {
        Random rand = new Random();
        int color = rand.nextInt(maxValue + 1 - minValue) + minValue;
        return color;
    }
}
