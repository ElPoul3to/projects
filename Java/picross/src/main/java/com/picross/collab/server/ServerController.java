package com.picross.collab.server;

import com.picross.collab.model.ServerModel;
import com.picross.collab.network.common.Log;
import com.picross.collab.shared.Level;
import com.picross.collab.shared.Position;
import com.picross.collab.shared.ServerResponse;
import com.picross.collab.shared.protocol.GameEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;

import static com.picross.collab.shared.protocol.GameEvent.*;
import static com.picross.collab.shared.protocol.Message.*;

public class ServerController {
    private final ServerModel serverModel;
    private ServerTCP serverTCP;

    private static final int TILE_FILLED_STATE = 1;

    private static final int DEFAULT_TYPE = -1;

    public ServerController() {
        serverModel = new ServerModel();
    }


    public ServerResponse clickTileReceived(int x, int y, int state) {

        boolean isError = false;
        GameEvent gameEvent = CONTINUE;
        int trueState;

        if (state == TILE_FILLED_STATE) {
            // Check if the tile is correct
            boolean isGoodMove = serverModel.checkTile(x, y);
            trueState = serverModel.getSolvedBoard()[x][y];
            if (!isGoodMove) {
                Log.COMM.info("Wrong move at x: {} y: {}", x, y);
                isError = true;
                serverModel.addPositionOfError(new Position(x, y));
                serverModel.lostLife();
            } else {
                Log.COMM.info("[CORRECT] Correct move at x:{}, y:{}", x, y);
                serverModel.updateBoard(x, y, state);
            }
        } else {
            serverModel.updateBoard(x, y, state);
            trueState = state;
        }

        if (serverModel.getLivesLeft() <= 0) {
            Log.COMM.info("[GAME_OVER] No life left");
            gameEvent = GAME_OVER;
        } else {
            Log.COMM.info("Lives left: {}", serverModel.getLivesLeft());
        }


        if (serverModel.isSolved()) {
            System.out.println("[VICTORY] Level Solved");
            gameEvent = VICTORY;
        };

        logging();

        serverModel.updateJSON();

        return new ServerResponse(CLICK_FEEDBACK_TYPE, x+","+y+","+trueState+","+isError+"|"+ serverModel.getLivesLeft()+"|"+gameEvent);
    }


    public void logging() {
        System.out.println("Server board: ");
        for (int[] row : serverModel.getServerBoard()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }


    /**
     * Processes the received message from the client.
     *
     * This method handles different types of messages received from the client.
     * If the type of the message is LEVEL_DIGIT_TYPE, it initializes a new Level object, updates the server model,
     * and sends back a Level JSON to the client.
     * If the type of the message is CLICK_SENT_TYPE, it processes the click received from the client and sends back a ServerResponse.
     *
     * @param typeReception The type of the received message.
     * @param message The received message.
     * @return A ServerResponse object containing the response to be sent to the client.
     * @throws IOException If an I/O error occurs when processing the message.
     * @throws ParseException If an error occurs when parsing the received message.
     */
    public ServerResponse processMessage(int typeReception, String message) throws IOException, ParseException {
        // Process the message
        System.out.println("Process message: " + message);
        ServerResponse serverResponse = new ServerResponse(DEFAULT_TYPE, "Error");

        switch (typeReception) {
            case LEVEL_DIGIT_TYPE: //Le client envoie un n° de niveau au serveur (Type 0:LEVEL_DIGIT_TYPE)
                System.out.println("\n[0] Level number received: " + message);

                Level level = new Level(message);
                serverModel.initLevelAndCreateBoard(level);
                serverModel.initLives(5);

                serverModel.levelJSON = level.levelJSON;
                serverModel.levelJSON.put("board", serverModel.getServerBoardString());
                serverModel.levelJSON.put("lives", serverModel.getLivesLeft());

                //Envoie d'un levelJSON différent que celui du serveur (suppression de solve)
                JSONObject clientlevelJSON = serverModel.levelJSON;
                clientlevelJSON.remove("solve");

                System.out.println("\n[0] Level JSON sent: " + clientlevelJSON.get("title") + " " + message);


                String messageEnvoi = clientlevelJSON.toJSONString();

                serverResponse = new ServerResponse(LEVEL_DATA_TYPE, messageEnvoi); //le serveur renvoie à tous les joueurs le Level JSON (enlever solve, ajouter board) (Type 1)

                break;
            // case 1: Not Handled here

            case CLICK_SENT_TYPE: //Le client joue: il envoie un clic (x,y, state) (Type 2)


                //String message = "x,y,state";

                // "x,y,state", split it using the "," delimiter
                String[] infos = message.split(",");

                // Now you can access the individual parts
                int x = Integer.parseInt(infos[0]);
                int y = Integer.parseInt(infos[1]);
                int state = Integer.parseInt(infos[2]);

                serverResponse = clickTileReceived(x,y,state);

                System.out.println("[2] Click received at " + x + "x " + y + "y with state " + state);

                // Retourne message de type 3 : CLICK_FEEDBACK_TYPE
                // De la forme : x,y,trueState,isError|livesLeft|gameEvent

                break;

                //type pour les nouveaux joueurs qui se connectent autres que le premier
            case NEW_PLAYER_TYPE:
                Log.COMM.info("Nouvelle connexion");

                if (serverModel.levelJSON != null) {
                    Log.COMM.info("Le niveau existe, on l'envoie au serveur sans sa solution");


                    serverModel.levelJSON.put("board", serverModel.getServerBoardString());
                    serverModel.levelJSON.put("lives", serverModel.getLivesLeft());

                    JSONObject levelCLientJSON = serverModel.levelJSON;
                    levelCLientJSON.remove("solve");

                    String messageAEnvoyer = levelCLientJSON.toJSONString();
                    Log.COMM.info("\nENVOI DU MESSAGE pour la nouvelle connexion - {}\n", messageAEnvoyer);

                    //le serveur renvoie à tous les joueurs le Level JSON (enlever solve, ajouter board) (Type 1)
                    serverResponse = new ServerResponse(LEVEL_DATA_TYPE, messageAEnvoyer);

                } else {
                    Log.COMM.info("La partie n'a pas encore été créée");

                    serverResponse = new ServerResponse(GAME_NOT_CREATED_ERROR, "nullLevelException");
                }

                break;
        }

        return serverResponse;
    }

}