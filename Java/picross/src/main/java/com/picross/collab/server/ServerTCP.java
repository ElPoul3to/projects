package com.picross.collab.server;

import com.picross.collab.network.common.Log;
import com.picross.collab.shared.ServerResponse;
import com.picross.collab.shared.protocol.Message;
import com.picross.collab.network.tcp.TcpServerAsync;
import com.picross.collab.network.tcp.TcpSocketAsync;
import org.apache.logging.log4j.Level;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.picross.collab.network.common.Log.COMM;
import static com.picross.collab.network.common.Log.GEN;


/**
 * Serveur TCP asynchrone
 * @author Thomas Fargues et Raphaël Fabre
 *
 */
public class ServerTCP {

	//Numéro de port du serveur
	private final String serverPort;

	// Map de tous les clients connectés
	private static Map<SelectionKey, TcpSocketAsync> clients;


	private static final int header_int = -1;
	private final Message msg;
	private final ServerController serverController;
	public static final int taille = 7;
	public ServerTCP(String serverPort, ServerController serverController) {
		this.serverPort = serverPort;
		this.clients = new HashMap<SelectionKey, TcpSocketAsync>();
		this.msg = new Message();
		this.serverController = serverController;
	}

	/**
	 * Affiche le contenu du buffer contenant les entiers.
	 *
	 * @param buffer le buffer contenant les entiers.
	 */
	static void displayBuff(final ByteBuffer buffer) {
		//buffer.flip();
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get() + " ");
		}
		System.out.println();
	}

	/**
	 * Lance le serveur.
	 * - Écoute et accepte les connexions des clients.
	 * - Lorsqu'un client se connecte, envoie le picross.
	 * - Gère la réception des messages des clients et les déconnexions éventuelles.
	 * - Réalise la réception et le traitement des messages en boucle.
	 *
	 * @throws IOException En cas d'erreur d'entrée/sortie.
	 */
	public void launchServer() {
		try (Selector selector = Selector.open();
			 TcpServerAsync serveur = new TcpServerAsync(Integer.parseInt(serverPort))) {

			serveur.getListenChannel().register(selector, SelectionKey.OP_ACCEPT);

			Log.setLevel(COMM, Level.TRACE);
			Log.setLevel(GEN, Level.INFO);

			COMM.info("Le serveur est prêt, attente de réception du niveau");
			while (true) {
				selector.select();
				Set<SelectionKey> readyKeys = selector.selectedKeys();

				COMM.info("Nombre de clés prêtes : {}", readyKeys.size());

				Iterator<SelectionKey> it = readyKeys.iterator();
				while (it.hasNext()) {
					SelectionKey curKey = it.next();
					it.remove();

					if (curKey.isAcceptable()) {
						TcpSocketAsync client = serveur.acceptClientAsync();
						SelectionKey newKey = client.getRwChan().register(selector, SelectionKey.OP_READ);
						clients.put(newKey, client);



						client.startNewReception(taille); // Start receiving the header
					} else if (curKey.isReadable()) {
						TcpSocketAsync client = clients.get(curKey);
						ByteBuffer buffer;

						if (client.getExpectedMessageSize() == header_int) {
							// We're expecting a header
							buffer = ByteBuffer.allocate(taille);
							int res = client.receiveBufferAsync(buffer);
							if (res != -1) {
								String header = bytesToString(buffer);
								System.out.println("En-tête reçu : " + header);
								msg.readMsg(header);

								client.setExpectedMessageSize(msg.getSize());
								client.startNewReception(msg.getSize()); // Start receiving the message
							} else {
								client.close();
								clients.remove(curKey);
								curKey.cancel();
							}
						} else {
							// We're expecting a message
							buffer = ByteBuffer.allocate(client.getExpectedMessageSize());
							int res = client.receiveBufferAsync(buffer);
							if (res != -1) {
								String receivedMessage = bytesToString(buffer);

								msg.reset();
								msg.readMsg(receivedMessage);

								int typeReception = msg.getType();


								//Traitement du message reçu
								ServerResponse response = serverController.processMessage(typeReception, msg.getData());


								//Envoie un message à tous les clients (type de données , message)
								sendMessageClients(response.getType(), response.getMessage());

								client.setExpectedMessageSize(header_int); // Reset expected message size
								client.startNewReception(taille); // Start receiving the next header
							} else {
								client.close();
								clients.remove(curKey);
								curKey.cancel();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converts the contents of a ByteBuffer into a String.
	 *
	 * This method first flips the buffer to prepare it for reading. It then reads the remaining bytes from the buffer,
	 * converts these bytes into a String using the UTF-8 charset, and returns the resulting String.
	 *
	 * @param buffer the ByteBuffer to convert
	 * @return the String representation of the ByteBuffer's contents
	 */
	private String bytesToString(ByteBuffer buffer) {
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}


	/**
	 * Envoie à tous les clients le message en argument
	 * @param message
	 */
	/**
	 * Envoie un message à tous les clients connectés.
	 * - Construit l'en-tête : type$taille$
	 * - Convertit l'en-tête et le message en bytes
	 * - Alloue des buffers
	 * - Met les bytes dans les buffers
	 * - Crée un itérateur pour gérer les déconnexions éventuelles
	 * - Parcourt les clients et envoie le message à chacun
	 * - Vérifie si le client est déconnecté et le supprime de la liste des clients le cas échéant
	 *
	 * @param type    Type de message
	 * @param message Contenu du message
	 * @throws IOException En cas d'erreur d'entrée/sortie
	 */
	static void sendMessageClients(int type, String message) throws IOException {
		if (clients.isEmpty()) {
			System.out.println("Erreur : aucune connexion établie.");
			return;
		}

		// Construction de l'en-tête : type$taille$
		String header;
		if (message.getBytes(StandardCharsets.UTF_8).length < 1000) {
			header = type + "$" + String.format("%04d", message.getBytes(StandardCharsets.UTF_8).length) + "$";
		} else {
			header = type + "$" + message.getBytes(StandardCharsets.UTF_8).length + "$";
		}
		byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
		byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
		// Allocation des buffers
		ByteBuffer headerBuffer = ByteBuffer.allocate(headerBytes.length);
		ByteBuffer messageBuffer = ByteBuffer.allocate(messageBytes.length);

		// Mise des bytes dans les buffers
		headerBuffer.put(headerBytes);
		messageBuffer.put(messageBytes);

		// Flip des buffers pour la lecture
		headerBuffer.flip();
		messageBuffer.flip();

		// Création d'un itérateur pour gérer les déconnexions potentielles
		Iterator<Map.Entry<SelectionKey, TcpSocketAsync>> iterator = clients.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<SelectionKey, TcpSocketAsync> entry = iterator.next();
			TcpSocketAsync client = entry.getValue();

			// Clear the buffers before use
			headerBuffer.clear();
			messageBuffer.clear();

			// Create a new ByteBuffer for each client to avoid sharing buffers
			ByteBuffer clientBuffer = ByteBuffer.allocate(headerBytes.length + messageBytes.length);
			clientBuffer.put(headerBuffer);
			clientBuffer.put(messageBuffer);
			clientBuffer.flip(); // Flip the buffer for reading

			// Send the buffer to the client
			client.sendBuffer(clientBuffer);

			System.out.println("Sending message: " + header + message);

			// Check if the client is disconnected
			if (!client.isConnected()) {
				System.out.println("Client disconnected. Removing from client list.");
				iterator.remove(); // Remove the disconnected client from the iterator
			}

			// Rewind headerBuffer for the next iteration
			headerBuffer.rewind();
		}
	}

	private void sendMessageOneCLient(int type, String message, TcpSocketAsync client) throws IOException {
		if (client == null) {
			System.out.println("Erreur : connexion non établie.");
			return;
		}
		//Envoyer type et taille sous forme d'entier
		String messageString = type + "$" + message.getBytes().length + "$" + message;

		System.out.println(messageString);
		//Conversion des characters ascii en bytes
		byte[] messageBytes = messageString.getBytes(StandardCharsets.UTF_8);

		ByteBuffer outBuffer = ByteBuffer.allocate(messageBytes.length);

		outBuffer.put(messageBytes);

		outBuffer.flip();

		client.sendBuffer(outBuffer);
	}
}


