package com.picross.collab.play;

import com.picross.collab.client.ClientTCP;
import com.picross.collab.client.controller.ClientController;
import com.picross.collab.client.view.GameView;
import com.picross.collab.client.view.HomeView;
import com.picross.collab.client.view.LevelsView;
import com.picross.collab.shared.UnsolvablePicrossException;
import com.picross.collab.shared.protocol.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Player extends Application {

    public ClientController clientController;
    public GameView gameView;
    public Scene scene;

    private Stage stage;
    private String mode = "join";

    private String[] args;
    private String levelDigit ="0";


    @Override
    public void init() throws Exception {
        super.init();
        args = getParameters().getRaw().toArray(new String[0]);
    }

    public Stage getStage() {
        return stage;
    }


    public void switchToGameView(String gameTitle) {
        System.out.println("Creating game scene");
        try {
            Scene gameWindow = clientController.createGameScene();
            //logNodes(gameWindow);
            System.out.println("Setting game scene");
            Platform.runLater(() -> {
                // existing code to switch to game window
                stage.setScene(gameWindow);
                stage.setTitle("Niveau \""+gameTitle+"\"");
                stage.sizeToScene();
                stage.show();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectToServer(String levelDigit) throws IOException {
        this.levelDigit = levelDigit;

        System.out.println("MODE : "+mode);

        sendFirstMessage(clientController.clientTCP);

        switchToLoadingView();
    }

    public void switchToLoadingView() {
        System.out.println("Creating Loading scene");
        Scene loadingWindow = clientController.createLoadingScene();
        stage.setScene(loadingWindow);
        stage.setTitle("Attente de la réception du niveau");
        stage.sizeToScene();
        stage.show();
    }

    public void switchToHomeView() {
        Platform.runLater(() -> {
            System.out.println("Creating Home scene");
            HomeView homeView = new HomeView(Player.this);
            Scene homeWindow = homeView.createScene(stage);
            stage.setScene(homeWindow);
            stage.setTitle("Picross");
            stage.sizeToScene();
            stage.show();
        });

    }

    public void switchToLevelChoiceView(String mode) {

        if (mode.equals("create")) {
            this.mode = "create";
        }

        System.out.println("Creating Level scene");
        LevelsView levelsView = new LevelsView();

        stage.setScene( levelsView.createScene(Player.this));
        stage.setTitle("Choix du niveau");
        stage.sizeToScene();
        stage.show();
    }


    /**
     * Service launching the reception task.
     */
    private void initServiceReception(ClientTCP client) {
        // Reception service
        Service<String[]> receptionService = new Service<String[]>() {
            @Override
            protected Task<String[]> createTask() {
                return new Task<String[]>() {
                    @Override
                    protected String[] call() throws Exception {
                        return client.reception();
                    }
                };
            }
        };

        // Event handler for successful reception
        EventHandler<WorkerStateEvent> succeedEvent = event -> {
            // Update client grid attribute
            String[] receptionMessage = receptionService.getValue();

            // TODO: Redisplay the game page to take into account the new grid

            // Restart the service for the next reception.
            receptionService.restart();
        };

        // Event handler for failed reception
        EventHandler<WorkerStateEvent> failedEvent = event -> {
            Throwable error = receptionService.getException();
            error.printStackTrace();

            receptionService.restart();
        };

        receptionService.setOnSucceeded(succeedEvent);
        receptionService.setOnFailed(failedEvent);
        receptionService.start();
    }

    public void setupClientTCP() throws IOException {
        ClientTCP clientTCP = new ClientTCP("5110", "localhost", clientController);
        clientController.setClientTCP(clientTCP);

        initServiceReception(clientTCP);
    }


    @Override
    public void start(Stage stageCreated) throws IOException {
        stage = stageCreated;

        clientController = new ClientController(this);
        gameView = clientController.gameView;


        clientController.rootPane = gameView.getLayout();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gameView.getLayout());

        scene = new Scene(stackPane, gameView.getBoardWidth() * gameView.WIDTH + gameView.OFFSET*2 , gameView.getBoardHeight()* gameView.WIDTH + gameView.OFFSET*2);


        clientController.stage = stage;



        switchToHomeView();
        // ---> Then switch to LevelView
        // ---> Then switch to LoadingView

        System.out.println("Showing stage");

    }

    public void sendFirstMessage(ClientTCP clientTCP) throws IOException {
        if (mode.equals("join")) {
            System.out.println("No arguments provided");
            System.out.println("Joining level");
            /// TODO : soit enovyer un msg pour dire au server renvoie le niv
            // TODO : normalmt le serveur envoie le niv au client automatic (pas le cas actuellement)
            clientTCP.sendMessage(Message.NEW_PLAYER_TYPE,"123");
            return;
        } else if (mode.equals("create")) {
            System.out.println("Creating level");
            if (levelDigit == "0") {
                System.out.println("ERROR : No level digit provided.");
                return;
            }
            clientTCP.sendMessage(Message.LEVEL_DIGIT_TYPE, levelDigit);
        } else {
            System.out.println("Joining level");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
