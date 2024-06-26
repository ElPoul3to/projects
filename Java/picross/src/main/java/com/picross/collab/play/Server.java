package com.picross.collab.play;

import com.picross.collab.server.ServerController;
import com.picross.collab.server.ServerTCP;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerController serverController = new ServerController();
        ServerTCP server = new ServerTCP("5110", serverController);
        server.launchServer();
    }
}
