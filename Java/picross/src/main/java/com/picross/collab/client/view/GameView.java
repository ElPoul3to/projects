package com.picross.collab.client.view;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import com.picross.collab.client.controller.ClientController;
import com.picross.collab.shared.Position;
import javafx.animation.FillTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

import static com.picross.collab.client.controller.ClientController.*;

public class GameView {

    private final Pane layout;


    private Pane boardPane;

    public final double OFFSET = 100;
    public final double WIDTH = 35;

    private int boardWidth;
    private int boardHeight;
    private final ClientController controller;

    public static final Color TILE_CLICKED_COLOR = Color.BLUE;
    public static final Color TILE_UNCLICKED_COLOR = Color.WHITE;
    public static final Color TILE_MARKED_COLOR = Color.RED;
    /**
     * Constructeur public de PicrossView
     * Initialisation de la grille,
     * Initialisation des labels (indices des lignes et des colonnes)
     * Ajoute la grille à la vue
     *
     * @param controller
     */
    public GameView(ClientController controller) {
        this.controller = controller;
        this.layout = new Pane();
    }

    private void initLives() {
        initHeartsDisplay(controller.model.getLivesLeft());
    }



    public void configureBoard() {

        boardWidth = (int) controller.getBoardWidth(); // get the value from DB
        boardHeight = (int) controller.getBoardHeight(); // get the value from DB


        initBoard(boardWidth, boardHeight);


        initLabels(controller.getRowsIndices(), controller.getColumnsIndices(), boardWidth, boardHeight);
        initLives();


        getLayout().getChildren().add(boardPane);
        System.out.println("Board added to rootPane");
    }

    /**
     * Affiche un message de réussite du niveau et bloque les clics
     */
    public void displayLevelFinished() {
        Text successText = new Text("Niveau réussi !");
        successText.setX(0);
        successText.setY(WIDTH * boardHeight + 60);

        // Add a drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));
        successText.setEffect(dropShadow);

        // Add a gradient fill
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.web("#f8bd55")),
                new Stop(0.14, Color.web("#c0fe56")),
                new Stop(0.28, Color.web("#5dfbc1")),
                new Stop(0.43, Color.web("#64c2f8")),
                new Stop(0.57, Color.web("#be4af7")),
                new Stop(0.71, Color.web("#ed5fc2")),
                new Stop(0.85, Color.web("#ef504c")),
                new Stop(1, Color.web("#f2660f")),});
        successText.setFill(gradient);

        // Set the font and other styles
        successText.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
        successText.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");

        try {
            Platform.runLater(() -> boardPane.getChildren().add(successText));
            System.out.println("Tu as réussi, trop fort(e) !");
            Platform.runLater(() -> boardPane.getChildren().forEach(node -> node.setMouseTransparent(true)));
            // Pour bloquer les clics : (on a perdu le jeu est fini
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logBoard(int[][] board) {
        for (int[] ints : board) {
            StringBuilder lineString = new StringBuilder();
            lineString.append("[ ");
            for (int column = 0; column < board[0].length; column++) {
                lineString.append(ints[column]);
                lineString.append(" ");
            }
            lineString.append("]");
            System.out.println(lineString);
        }
        System.out.println();
    }

    /**
     * Affiche un message de fin de jeu et bloque les clics
     */
    public void displayGameOver() {

        Text gameOverText = new Text("Game Over :/");
        gameOverText.setX(60);
        gameOverText.setY(WIDTH * boardHeight + 60);

// Add a drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));
        gameOverText.setEffect(dropShadow);

// Add a gradient fill
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.web("#ff0000")), // Bright red
                new Stop(0.14, Color.web("#ff3300")), // Slightly darker red
        });
        gameOverText.setFill(gradient);

// Set the font and other styles
        gameOverText.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
        gameOverText.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");

        try {
            Platform.runLater(() -> boardPane.getChildren().add(gameOverText));
            System.out.println("Game Over :/");
            Platform.runLater(() -> boardPane.getChildren().forEach(node -> node.setMouseTransparent(true)));
            // Pour bloquer les clics : (on a perdu le jeu est fini
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Rectangle getRectangle(Position position) {
        System.out.println("Getting rectangle at position " + position.x() + " " + position.y());
        System.out.println(boardPane.getChildren());

        Pane foundPane = (Pane) boardPane.lookup("#pane" + position.x() + position.y());

        if (foundPane == null) {
            System.out.println("No pane found with id: " + "#pane" + position.x() + position.y());
            return null;
        }
        Node rectangle = foundPane.getChildren().get(0);
        if (rectangle instanceof Rectangle) System.out.println("Found rectangle");
        else System.out.println("Not a rectangle");

        return (Rectangle) rectangle;
    }

    public void modifyRectangle(Position position, Color newColor) {
        System.out.println("Modifying rectangle at position " + position.x() + " " + position.y());
        Platform.runLater(() -> getRectangle(position).setFill(newColor));
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Affiche le résultat du clic (remplissage bleu si bon, dessin d'une croix rouge si mauvais)
     *
     * @param isGoodMove
     * @param position
     */
    public void displayMove(boolean isGoodMove, Position position, int state) {
        if (isGoodMove) {

            try {
                switch (state) {
                    case UNCLICKED_STATE:
                        modifyRectangle(position, TILE_UNCLICKED_COLOR);
                        break;
                    case CLICKED_STATE:
                        modifyRectangle(position, TILE_CLICKED_COLOR);
                        break;
                    case MARKED_STATE:
                        modifyRectangle(position, TILE_MARKED_COLOR);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            logBadMove();
            animateBadMove(getRectangle(position));
            lifeLost();
            drawCrossOnTile(position);
        }
    }

    private void logBadMove() {
        System.out.println("Not a good move");
    }

    private void animateBadMove(Rectangle rect) {

        FillTransition ft = new FillTransition(Duration.seconds(0.2), rect, TILE_UNCLICKED_COLOR, TILE_MARKED_COLOR);
        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void drawCrossOnTile(Position position) {
        int margin = 8;
        int coordX1 = (int) (position.y() * WIDTH + margin);
        int coordY1 = (int) (position.x() * WIDTH + margin);
        int coordX2 = (int) (position.y() * WIDTH + WIDTH - margin);
        int coordY2 = (int) (position.x() * WIDTH + WIDTH - margin);

        Line line1 = createLine(coordX1, coordY1, coordX2, coordY2);
        Line line2 = createLine(coordX1, coordY2, coordX2, coordY1);

        Platform.runLater(() -> boardPane.getChildren().addAll(line1, line2));

    }

    private Line createLine(int startX, int startY, int endX, int endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(TILE_MARKED_COLOR);
        line.setStrokeWidth(4.0);
        return line;
    }


    /**
     * Initialisation des coeurs de vies sur l'affichage et ajout des vies dans la liste heartsLives
     * @param numberLives
     */
    public void initHeartsDisplay(int numberLives) {
        double centerHeartY = 30;
        double centerHeartX = WIDTH * boardWidth + 50;//OFFSET+

        for (int i = 0; i < numberLives; i++) {

            HeartShape heartShape = new HeartShape(centerHeartX, centerHeartY);
            heartShape.setId("heart" + i);

            boardPane.getChildren().add(heartShape);


            centerHeartY += 60;
        }
    }


    /**
     * On a perdu une vie, on enlève un coeur s'il reste des vies.
     */

    public void removeHeartView(int i) {
        Platform.runLater(() -> {
            Node heart = boardPane.lookup("#heart" + i);

            if (heart != null) {
                boardPane.getChildren().remove(heart);
            } else {
                System.out.println("No HeartShape found with id: " + "#heart" + i);
            }
        });

    }


    /**
     * Removes all heart shapes from the game view.
     */
    public void removeAllHearts() {
        for (int i = 0; i < 5; i++) {
            try {
                removeHeartView(i);
            } catch (Exception e) {
                System.out.println("No more hearts to remove");
            }
        }
    }


    /**
     * Handles the event when a life is lost in the game.
     * If there are lives left, it removes a heart view.
     * If there are no lives left, it removes all hearts and displays game over.
     */
    public void lifeLost() {

        int livesLeft = controller.model.getLivesLeft();
        if (livesLeft > 0) {
            removeHeartView(controller.model.getLivesLeft());
        } else {
            removeAllHearts();
            displayGameOver();
        }

        System.out.println("Life lost, hearts left: " + controller.model.getLivesLeft());

    }


    /**
     * Initialisation de la grille en fonction de la largeur et hauteur (en nombre de cases)
     * Définition des Listeners pour le glissement de la souris (colorer plusieurs cases d'un coup)
     * Dessin des cases, Listener pour le clic,
     * Dessin du contour de la grille
     *
     * @param width width of the board
     * @param height height of the board
     */
    private void initBoard(int width, int height) {

        System.out.println("Initializing board\nBoard width : " + boardWidth + " Board height : " + boardHeight);

        boardPane = new Pane();
        boardPane.setLayoutX(OFFSET);
        boardPane.setLayoutY(OFFSET);

        /* board.setOnMouseDragged(event ->


                {
                    event.consume();
                    Node node = event.getPickResult().getIntersectedNode();

                    if (node instanceof Rectangle rectangle) {

                        int coordY = (int) Math.round((event.getX() - OFFSET) / WIDTH + 3) - 1;
                        int coordX = (int) Math.round((event.getY() - OFFSET) / WIDTH + 3) - 1;
                        // OUI CEST INVERSE

                        System.out.println("X : " + coordX + " Y : " + coordY);

                        // On récupère la case sur laquelle on est
                        // On update son état dans la grille Model

                        controller.processMove(rectangle, coordY, coordX);
                    }

                }
        );*/

        /*Ce code dessine les petits carrés de la grille et les rend cliquables */
        Pane[][] tiles = new Pane[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                Pane newPane = new Pane();
                newPane.setId("pane" + i + j);
                tiles[i][j] = newPane;
                Position position = new Position(i, j);
                Rectangle r = createRect(position);


                tiles[i][j].getChildren().add(r);
                tiles[i][j].setLayoutX(j * WIDTH);
                tiles[i][j].setLayoutY(i * WIDTH);
                boardPane.getChildren().add(tiles[i][j]);
            }
        }

        Rectangle r = new Rectangle(0, 0, WIDTH * width, WIDTH * height);

        r.setStroke(Color.DARKBLUE);
        r.setStrokeWidth(3);
        r.setFill(Color.TRANSPARENT);
        r.setMouseTransparent(true); // cet attribut rend cliquables les éléments du dessous (les cases)
        boardPane.getChildren().add(r);

        System.out.println("Board initialized");
    }

    /**
     * Crée une case de la grille et ajoute un Listener pour le clic
     * @param position
     * @return la case créée (Rectangle)
     */
    @NotNull
    private Rectangle createRect(Position position) {
        Rectangle r = new Rectangle(WIDTH, WIDTH);
        r.setStroke(Color.LIGHTGRAY);
        r.setStrokeWidth(1);
        r.setFill(Color.WHITE);

        /*Définit les actions lors d'un clic : si clic gauche on remplit la case, si droit on met la case en rouge pour se rappeler qu'il faut pas la choisir (case verrouillée ?)*/

        r.setOnMouseClicked(t -> {
            try {
                controller.rectangleClicked(t, r, position);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        return r;
    }

    /**
     * Initialisation des labels des lignes et des colonnes
     * On récupère les indices des lignes et des colonnes et on les met dans un tableau String
     * On les affiche dans les labels
     *
     * @param rowsIndices
     * @param columnsIndices
     * @param width
     * @param height
     */
    private void initLabels(String[] rowsIndices, String[] columnsIndices, int width, int height){
        System.out.println("Rows indices : " + rowsIndices.length + " Columns indices : " + columnsIndices.length);
        VBox[] topLabels = new VBox[width];
        HBox[] leftLabels = new HBox[height];
        for (int i = 0; i < width; i++) {
            String[] subLabelsC = columnsIndices[i].split(",");

            topLabels[i] = new VBox();
            topLabels[i].setLayoutX(OFFSET + i * WIDTH+12);
            topLabels[i].setLayoutY(OFFSET - WIDTH -( 25 *(subLabelsC.length-1)));

            for (String subLabel : subLabelsC) {
                Label t_l = new Label(subLabel);
                t_l.setFont(new Font(20));
                topLabels[i].getChildren().add(t_l);
            }

            getLayout().getChildren().add(topLabels[i]);

        }

        for (int i = 0; i < height; i++) {

            String[] subLabelsR = rowsIndices[i].split(",");

            leftLabels[i] = new HBox();
            leftLabels[i].setLayoutY(OFFSET + i * WIDTH + 5);
            leftLabels[i].setLayoutX(OFFSET - WIDTH + 10 -( 15 *(subLabelsR.length-1)));

            for (String subLabel : subLabelsR) {
                Label t_l = new Label(subLabel+" ");
                t_l.setFont(new Font(20));
                leftLabels[i].getChildren().add(t_l);
            }


            getLayout().getChildren().add(leftLabels[i]);
        }
    }

    public Pane getLayout() {
        return this.layout;
    }



    public void showError(String title, String message) {
        Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(message);

                    alert.showAndWait();
                }
                );

    }
}