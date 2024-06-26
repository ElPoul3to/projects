package com.picross.collab.client.view;

import com.picross.collab.play.Player;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class HomeView {

    private final Player player;

    public HomeView(Player player) {
        this.player = player;
    }

    private static final int itemsToLoad = 2000;

    public Scene createScene(Stage primaryStage) {
        TilePane circlePane = createCircles();
        Button button1 = createButton("Créer");
        Button button2 = createButton("Rejoindre");
        Text title = createTitle();

        HBox buttonBox = createButtonBox(button1, button2);
        VBox vbox = createVBox(buttonBox);
        VBox titleBox = createTitleBox(title);

        button1.setOnAction(e -> {
            try {
                System.out.println("\"Créer\" clicked");

                player.setupClientTCP();
                player.switchToLevelChoiceView("create");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        button2.setOnMouseClicked(e -> {
            try {
                System.out.println("\"Rejoindre\" clicked");
                player.setupClientTCP();

                player.connectToServer("-1");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(circlePane, titleBox, vbox);

        Scene scene = new Scene(root, 400, 400);
        Button[] buttons = {button1, button2};
        setupSceneListeners(scene, buttons, title);

        return scene;
    }


    /**
     * Creates a TilePane with circles.
     * @return a TilePane with circles.
     */
    private TilePane createCircles() {
        TilePane circlePane = new TilePane();
        for (int i = 0; i < itemsToLoad; i++) {
            Circle circle = new Circle(5, randomColor());
            circlePane.getChildren().add(circle);
        }
        return circlePane;
    }

    /**
     * Creates a button with a specific text and style.
     * @param text the text for the button.
     * @return a styled button.
     */
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black; -fx-font-family: 'Minecraftia'; -fx-font-size: 16px; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");
        HBox.setHgrow(button, Priority.ALWAYS);
        return button;
    }

    /**
     * Creates a title with a specific font, color, and shadow effect.
     * @return a styled title.
     */
    private Text createTitle() {
        Text title = new Text("Picross");
        title.setFont(Font.font("Minecraftia", 100));
        title.setFill(Color.WHITE);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        title.setEffect(dropShadow);
        return title;
    }

    /**
     * Creates a HBox with buttons.
     * @param buttons the buttons to add to the HBox.
     * @return a HBox with buttons.
     */
    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(buttons);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    /**
     * Creates a VBox with a specific alignment.
     * @param nodes the nodes to add to the VBox.
     * @return a VBox with nodes.
     */
    private VBox createVBox(Node... nodes) {
        VBox vbox = new VBox(nodes);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    /**
     * Creates a VBox with a title.
     * @param title the title to add to the VBox.
     * @return a VBox with a title.
     */
    private VBox createTitleBox(Text title) {
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        return titleBox;
    }

    /**
     * Sets up listeners for the scene.
     * @param scene the scene to set up listeners for.
     * @param buttons the buttons to resize.
     * @param title the title to resize.
     */
    private void setupSceneListeners(Scene scene, Button[] buttons, Text title) {
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Button button : buttons) {
                button.setPrefWidth(newVal.doubleValue() / 4);
            }
            double newSize = newVal.doubleValue() / 5;
            title.setFont(Font.font("Minecraftia", newSize));
        });
        Font.loadFont(getClass().getResourceAsStream("Minecraftia.ttf"), 14);
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