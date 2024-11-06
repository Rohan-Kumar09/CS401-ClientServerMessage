package serverPack;

import messagePack.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			// ObjectInputStream is used for reading the object
			// sent by the connected socket.
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			// ObjectOutputStream is used for writing to the object
			// and sending it to the connected socket.
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

			while (true) {				
				Message message = (Message) in.readObject();
				
				// exit loop if logout message is received
				if (message.getType().equals(new MessageType().logout())) {
					System.out.println("logging out " + clientSocket);
					break;
				}
				
				// send success message if login message is received
				if (message.getType().equals(new MessageType().login())) {				
					System.out.println("Sucess message sent to client");
					out.writeObject(new Message("", "", new MessageStatus().success()));
				}
				
				// send text in upper case if text message is received
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
