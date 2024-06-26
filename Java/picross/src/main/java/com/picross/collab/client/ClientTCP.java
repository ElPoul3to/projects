package com.picross.collab.client;

import com.picross.collab.client.controller.ClientController;
import com.picross.collab.shared.Level;
import com.picross.collab.shared.ServerResponse;
import com.picross.collab.shared.protocol.Message;
import com.picross.collab.network.tcp.TcpSocket;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Client TCP qui envoie des entiers en boucle.
 * @author Raphaël Fabre et Thomas Fargues.
 */
public class ClientTCP {

	private final TcpSocket client;
	private final String serverPort;
	private final String serverIP;
	private final ClientController clientController;
	private final Message msg;

	/**
	 * Constructor for the ClientTCP class.
	 *
	 * This constructor initializes a new instance of the ClientTCP class with the specified server port, server IP, and client controller.
	 * It also creates a new TcpSocket with the specified server IP and port, and prints a message indicating that the client is connected.
	 *
	 * @param serverPort The port number of the server as a string. This is used to establish the TcpSocket connection.
	 * @param serverIP The IP address of the server as a string. This is used to establish the TcpSocket connection.
	 * @param clientController The client controller that handles the client-side logic of the application.
	 * @throws IOException If an I/O error occurs when creating the TcpSocket.
	 */
	public ClientTCP(String serverPort, String serverIP, ClientController clientController) throws IOException {
		this.serverPort = serverPort;
		this.serverIP = serverIP;
		this.clientController = clientController;
		this.msg = new Message();

		client = new TcpSocket(serverIP, Integer.parseInt(serverPort));
		System.out.println("Client connecté à l'IP " + serverIP + " sur le port " + serverPort);


	}


	/**
	 * Sends a message to the server.
	 *
	 * This method constructs a header containing the type of the message and its length,
	 * and then sends this header along with the actual message to the server.
	 * If the client is not connected, an error message is printed and the method returns.
	 *
	 * Message format : data$length$type
	 *
	 * @param type The type of the message to be sent. This is an integer value that represents the type of the message.
	 * @param message The actual message to be sent. This is a string value.
	 * @throws IOException If an I/O error occurs when sending the message.
	 */
	public void sendMessage(int type, String message) throws IOException {
		if (client == null) {
			System.out.println("Erreur : connexion non établie.");
			return;
		}
		String header;

		//Envoyer type et taille sous forme d'entier
		if (message.getBytes(StandardCharsets.UTF_8).length < 1000) {
			header = type + "$" + String.format("%04d", message.getBytes(StandardCharsets.UTF_8).length) + "$";
		} else {
			header = type + "$" + message.getBytes(StandardCharsets.UTF_8).length + "$";
		}
		//Conversion des characters ascii en bytes
		byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
		byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);

		ByteBuffer messageBuffer = ByteBuffer.allocate(messageBytes.length);
		ByteBuffer headerBuffer = ByteBuffer.allocate(7);

		messageBuffer.put(messageBytes);
		headerBuffer.put(headerBytes);
		while (headerBuffer.hasRemaining()) {
			headerBuffer.put((byte) 'h');
		}
		messageBuffer.flip();
		headerBuffer.flip();

		client.sendBuffer(headerBuffer);
		client.sendBuffer(messageBuffer);

		System.out.println("Envoi du message : " + header+message);
	}

	/**
	 * Receives messages from the server.
	 *
	 * This method reads the header of the incoming message, which contains the type of the message and its length.
	 * It then reads the actual message from the server. Depending on the type of the message, different actions are performed.
	 * If the type of the message is LEVEL_DATA_TYPE, a new Level object is created and passed to the client controller.
	 * If the type of the message is CLICK_FEEDBACK_TYPE, a new ServerResponse object is created and passed to the client controller.
	 *
	 * @return An array of strings containing the type of the received message and the message itself.
	 * @throws IOException If an I/O error occurs when receiving the message.
	 * @throws ParseException If an error occurs when parsing the received message.
	 */

	public String[] reception() throws IOException, ParseException {
		ByteBuffer headerBuffer = ByteBuffer.allocate(7);
	
		while (headerBuffer.hasRemaining()) {
			int res = client.receiveBufferAsync(headerBuffer);
			if (res == -1) {
				//System.out.println("Erreur");
				return new String[0];
			}
		}
	
		String header = bytesToString(headerBuffer);
		System.out.println("Header reçu: " + header);

		msg.readMsg(header);

		client.setExpectedMessageSize(msg.getSize());
	
		ByteBuffer messageBuffer = ByteBuffer.allocate(msg.getSize());
	
		while (messageBuffer.hasRemaining()) {
			int res = client.receiveBufferAsync(messageBuffer);
			if (res == -1) {
				//System.out.println("Erreur");
				return new String[0];
			}
		}
	
		String receivedMessage = bytesToString(messageBuffer);
		System.out.println("Message reçu: " + receivedMessage);
		msg.reset();
		msg.readMsg(receivedMessage);
		int typeReception = msg.getType();
		String data = msg.getData();

		switch (typeReception) {
			// case 0: Not Handled here
			case Message.LEVEL_DATA_TYPE: // réception du Level JSON (Type 1)
				// ici on recoit le Level JSON du serveur
				System.out.println("Level JSON reçu :"+data);

                Level level = new Level(data, true);
				clientController.levelReceived(level);

				break;
			// case 2: Not handled here
			case Message.CLICK_FEEDBACK_TYPE: // réception de (tileUpdate (x,y,trueState), lives(int), gameEvent (String)) (Type 3)
				// ici on recoit la réponse du serveur à un clic

				System.out.println("Réponse du serveur à un clic reçue");
				ServerResponse serverResponse = new ServerResponse(typeReception, data) ;

				clientController.processServerFeedback(serverResponse);

				break;
			case Message.GAME_NOT_CREATED_ERROR:
				System.out.println("ERROR: GAME NOT CREATED, CREATE A GAME FIRST, THEN JOIN");



				clientController.processError("Partie inexistante","La partie n'a pas encore été créée. Veuillez créer une partie avant de la rejoindre.");
		}

		return new String[]{String.valueOf(typeReception), receivedMessage};
	}

	/**
	 * Converts a ByteBuffer to a string.
	 *
	 * This method flips the given ByteBuffer, reads all remaining bytes from it, and converts these bytes to a string using UTF-8 encoding.
	 *
	 * @param buffer The ByteBuffer to be converted to a string.
	 * @return The string representation of the ByteBuffer's contents.
	 */
	private String bytesToString(ByteBuffer buffer) {
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}

}
