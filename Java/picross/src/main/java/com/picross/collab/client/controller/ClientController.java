package com.picross.collab.client.controller;
//vnkvkrnfklnfdvdfkjlvnfdkjnvkjdfnvjkfdnvkjdfnvkj
import com.picross.collab.client.ClientTCP;
import com.picross.collab.client.view.LoadingView;
import com.picross.collab.play.Player;
import com.picross.collab.shared.Level;
import com.picross.collab.client.model.ClientModel;
import com.picross.collab.client.view.GameView;
import com.picross.collab.shared.Position;
import com.picross.collab.shared.ServerResponse;
import com.picross.collab.shared.protocol.GameEvent;
import com.picross.collab.shared.protocol.Message;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import static com.picross.collab.client.view.GameView.*;


public class ClientController {

    public ClientModel model;
    public Level level;
    public final GameView gameView;
    public Stage stage;
    public ClientTCP clientTCP;
    public Pane rootPane;
    public Player playerGlobal;

    private static final int TILE_ERROR = 0;
    private static final int TILE_CORRECT = 1;

    public static final int UNCLICKED_STATE = 0;
    public static final int CLICKED_STATE = 1;
    public static final int MARKED_STATE = 2;


    /* Méthodes pour envoyer 2 entiers (clic) au réseau etc */
    /* Méthode pour recevoir la réponse (à l'endroit du clic est ce que c'est correct ou pas */

    /**
     * Constructs a new PicrossController.
     * <p>
     * This constructor initializes the model with the provided level data,
     * stores the level data, and creates a new view, passing itself as the controller.
     *
     *
     */
    public ClientController(Player player){
        playerGlobal = player;
        if (player == null) {
            System.out.println("Player is null in ClientController constructor");

        }
        this.stage = player.getStage();

        this.gameView = new GameView(this);
    }

  /*  public Scene createGameScene() {
        return new Scene(gameView.getLayout(), 800, 600);
    }*/

    public Scene createLoadingScene() {
        LoadingView loadingView = new LoadingView();
        return new Scene(loadingView.getLayout(), 300, 200);
    }

    /*public Scene createHomeScene() {

    }*/

    public Scene createGameScene() {
        Pane newRootPane = new Pane();
        newRootPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #ff9d9a, #fecfef);");

        newRootPane.getChildren().addAll(rootPane.getChildren());
        return new Scene(newRootPane, 800, 600);
    }

    public void levelReceived(Level level) {

        //view.removeLoading(rootPane);
        System.out.println("Level received from server: \n\""+level.getTitle()+"\"");

        this.level = level;
        this.model = new ClientModel(level);
        System.out.println("Model initialized with level data");


        // TODO : Thomas : create a function that parses currentBoard to set the tiles right
        gameView.configureBoard();

        parseAndSetTiles();


        if (playerGlobal == null) {
            System.out.println("Player is null");
        } else {
            playerGlobal.switchToGameView(level.getTitle());
        }

    }

    /**
     * Parses the current board and updates the tiles in the view.
     * This method is used when the game has already begun and the board needs to be updated to reflect the current state.
     */
    public void parseAndSetTiles() {
        int[][] board = level.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Position position = new Position(i, j);
                int state = board[i][j];

                if (state == 1) {
                    tileUpdateReceived(position, state, false);

                }
            }
        }
    }

    /**
     * This method is called when a tile update is received from the server.
     * It updates the state of the tile in the model and then updates the view to reflect this change.
     *
     * @param position The position of the tile that needs to be updated.
     * @param state The new state of the tile. This can be 0 (unclicked), 1 (clicked), or 2 (marked).
     */
    void tileUpdateReceived(Position position, int state, Boolean isError) {

        model.updatePosition(position, state);
        gameView.displayMove(!isError, position, state);
    }

    void processGameEvent(GameEvent gameEvent) {
        // TODO : Recevoir un événement du réseau
        // TODO : Mettre à jour l'interface graphique

        // Exemple d'événement : "Game over"
        // Exemple d'événement : "Victory"
        // Exemple d'événement : "Player 1 has left the game"
        // Exemple d'événement : "Player 2 has joined the game"

        /*
         * à quel point on assiste le client ? On lui dit Game Over, Victory.. ou il le déduit ?
         * */

        switch (gameEvent) {
            case VICTORY:
                System.out.println("Victory: displaying event");
                gameView.displayLevelFinished();
                break;
            case GAME_OVER:
                System.out.println("Game over: displaying event");
                gameView.displayGameOver();
                break;
            case CONTINUE:

                break;
            default:
                System.out.println("GAME_EVENT : Unknown game event: " + gameEvent);
                break;
        }

    }


    public void updateLives(int livesLeftServer) {
        // TODO : Recevoir le nombre de vies restantes du réseau
        // TODO : Mettre à jour l'interface graphique

        model.setLivesLocal(livesLeftServer);

        if (livesLeftServer < model.getLivesLeft()) {
            System.out.println("Life in server is less than local model. Updating lives."+livesLeftServer+" "+model.getLivesLeft());
            for (int i = livesLeftServer; i < model.getLivesLeft(); i++) {
                gameView.lifeLost();

            }
        }
    }


    private boolean isPositionInErrors(Position position) {
        // Assuming model.getPositionOfErrors() returns a Set
        return model.getPositionOfErrorsLocal().contains(position);
    }

    public void sendClickToServer(Position position, int state) throws IOException {
        int type = Message.CLICK_SENT_TYPE; // Type 2 : Le client joue: il envoie un clic (x,y, state)
        String message = position.x() + "," + position.y() + "," + state;
        clientTCP.sendMessage(type, message);
        System.out.println("Sent click to server: " + message);
    }


    /**
     * Processes the feedback received from the server.
     * The server response contains information about the game state, including the position of the clicked tile,
     * its true state, whether the click resulted in an error, the number of lives left, and the current game event.
     *
     * @param serverResponse The ServerResponse object containing the server's feedback.
     */
    public void processServerFeedback(ServerResponse serverResponse){

        // Recu : x,y,trueState,isError|livesLeft|gameEvent

        // Split the message into two parts using the "|" delimiter
        String[] parts = serverResponse.getMessage().split("\\|");
        System.out.println("Parts :"+parts[0]+" "+parts[1]+" "+parts[2]);

        // The first part is "x,y,trueState,isError", split it using the "," delimiter
        String[] clickResult = parts[0].split(",");

        int x = Integer.parseInt(clickResult[0]);
        int y = Integer.parseInt(clickResult[1]);
        int trueState = Integer.parseInt(clickResult[2]);
        boolean isError = Boolean.parseBoolean(clickResult[3]);

        // The second part is "livesLeft|gameEvent", split it using the "|" delimiter

        int livesLeft = Integer.parseInt(parts[1]);
        String gameEventStr =parts[2];



        Position position = new Position(x, y);

        if (isError) model.addPositionOfError(position);

        updateLives(livesLeft);

        GameEvent gameEvent = switch (gameEventStr) {
            case "VICTORY" -> GameEvent.VICTORY;
            case "GAME_OVER" -> GameEvent.GAME_OVER;
            default -> GameEvent.CONTINUE;
        };
        System.out.println("RECEIVED Game event : "+gameEvent);

        processGameEvent(gameEvent);

        tileUpdateReceived(position, trueState, isError);
    }

    private void processMove(Position position) throws IOException {

        sendClickToServer(position, CLICKED_STATE);

        // TODO: DISPLAY A MESSAGE IF DISCONNECTED (too long)
    }





    /**
     * Déclenche l'affichage des grilles de jeu et de solution dans la console sous forme de tableau
     */
    public void logging() {
        logPlayerBoard();
    }

    public void rectangleClicked(MouseEvent t, Rectangle r, Position position) throws IOException {
        switch (t.getButton()) {
            case PRIMARY:
                handleLeftClick(r, position);
                break;
            case SECONDARY:
                handleRightClick(r, position);
                break;
            default:
                System.out.println("Unexpected mouse event: " + t + " on item: " + position);
        }
    }

    /**
     * This method is called when the user clicks on a tile in the game.
     * It checks if the clicked tile is already corrected (previous error).
     * If not, it checks if the move is valid and updates the tile state accordingly.
     * If the move is not valid, it adds the tile to the list of errors.
     * It then updates the view to reflect the result of the move.
     * If the game is solved after this move, it displays the level finished message.
     *
     * @param r The Rectangle object representing the clicked tile.
     * @param position Couple of integers representing the position of the clicked tile.
     */
    public void handleLeftClick(Rectangle r, Position position) throws IOException {
        System.out.println("Left clicked tile: " + position);
        if (r.getFill() == TILE_UNCLICKED_COLOR) {
            if (!isPositionInErrors(position)) {
                processMove(position);
            };
        } else {
            System.out.println("Tile already clicked");
        }
    }

    /**
     * Handles the scenario when a tile is right-clicked in the game.
     * This method calls the toggleTileMark method to change the tile's color.
     *
     * @param r The Rectangle object representing the right-clicked tile.
     * @param position The Position object representing the position of the right-clicked tile.
     */
    private void handleRightClick(Rectangle r, Position position) {
        System.out.println("Right clicked tile: " + position);
        toggleTileMark(r);
    }

    // TODO : Do that in the view

    /**
     * Toggles the color of a tile between unclicked and marked.
     * If the tile is currently unclicked, it is set to marked.
     * If the tile is currently marked, it is set to unclicked.
     *
     * @param r The Rectangle object representing the tile to be toggled.
     */
    private void toggleTileMark(Rectangle r) {
        if (r.getFill() == TILE_UNCLICKED_COLOR) r.setFill(TILE_MARKED_COLOR);
        else if (r.getFill() == TILE_MARKED_COLOR) r.setFill(TILE_UNCLICKED_COLOR);
    }

    public long getBoardWidth() {
        return level.getWidth();
    }

    //Affiche la board actuelle du joueur
    public void logPlayerBoard() {
        gameView.logBoard(model.getPlayerBoardLocal());
    }

    public long getBoardHeight() {
        return level.getHeight();
    }

    public String[] getRowsIndices() {
        return level.getRowsIndices();
    }

    public String[] getColumnsIndices() {
        return level.getColumnsIndices();
    }

    public void setClientTCP(ClientTCP clientTCP) {
        this.clientTCP  = clientTCP;
    }




    public void processError(String title, String message) {
        playerGlobal.switchToHomeView();
        gameView.showError(title, message);
    }
}
