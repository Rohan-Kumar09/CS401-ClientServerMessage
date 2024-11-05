package serverPack;

import messagePack.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ServerHandler implements Runnable{
	
	private final Socket clientSocket;
	
	ServerHandler(Socket socket){
		this.clientSocket = socket;
		System.out.println("Server Handler has " + clientSocket);
	}

	@Override
	public void run() {
		System.out.println("ServerHandler's run method is running...");
		
		try {	
			// get the input stream from the connected socket			
			// create a ObjectInputStream so we can read data from it.
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			// for writing back
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

			while (true) {				
				Message message = (Message) in.readObject();
				
				// if the client wants to log out exit the while loop
				if (message.getType().equals(new MessageType().logout())) {
					System.out.println("logging out " + clientSocket);
					break;
				}
				
				// if client sent a login request, send a success message
				if (message.getType().equals(new MessageType().login())) {				
					System.out.println("Sucess message sent to client");
					out.writeObject(new Message("", "", new MessageStatus().success()));
				}
				
				// if client sent a text message capitalize it and send it back
				if (message.getType().equals(new MessageType().text())) {
					System.out.println("Text message sent to client " + clientSocket);
					out.writeObject(new Message(message.getText().toUpperCase(), new MessageType().text(), ""));
				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
