package clientPack;

import messagePack.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		System.out.println("client running...");
		
		// open a socket with this IP address at port 7777
		try (Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress().trim(), 7777)){			
			System.out.println("Connected to " + InetAddress.getLocalHost().getHostAddress().trim() + " at port 7777");
			
			// output stream socket.
			// Creates a object output stream from the output stream to send an object through it
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			
			// for reading
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Login request to server");
			out.writeObject(new Message("", new MessageType().login(), ""));
			
			Message message;
			
			message = (Message) in.readObject();
			
			// if the server responds with success good, otherwise abort
			if (message.getStatus().equals(new MessageStatus().success())) {
				System.out.println("Login was a success");
			} else {
				System.out.println("Login Failed");
				System.exit(1);
			}
			
			String input = ""; // string for writing
			Scanner scanner = new Scanner(System.in); // for user input
			
			while (true) {
				System.out.println("Enter your message (type {exit} to quit): ");
				input = scanner.nextLine();
				
				// if the user wants to exit, send server logout message and abort client.
				if (input.equalsIgnoreCase("exit")) {
					out.writeObject(new Message("", new MessageType().logout(), ""));
					scanner.close();
					System.exit(0);
				} else {
					out.writeObject(new Message(input, new MessageType().text(), ""));
					// read message sent by server
					System.out.println(((Message) in.readObject()).getText());
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
