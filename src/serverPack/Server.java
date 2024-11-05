package serverPack;

import messagePack.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		
		try (ServerSocket serverListener = new ServerSocket(7777)) {
			System.out.println("Server is listening on port 7777");
			while (true) {
				// accept client connections
				Socket client = serverListener.accept(); // blocks
				System.out.println("New client connected " + client);
				
				// create new server handler for client x
				ServerHandler serverHandler = new ServerHandler(client);
				(new Thread(serverHandler)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
