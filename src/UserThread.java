import java.net.Socket;
import java.io.*;
//import java.net.*;
//import java.util.*;

public class UserThread extends Thread {
	
	private Socket socket;
	private ChatServer chatServer;
	private PrintWriter writer;
	

	public UserThread(Socket socket, ChatServer chatServer) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.chatServer = chatServer;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			printUsers();
			String username = reader.readLine();
			chatServer.addUserName(username);
			String serverMessage = username + " connected." ;
			chatServer.broadcast(serverMessage, this);
			String clientMessage;
			do {
				clientMessage = reader.readLine();
				if(!clientMessage.equals("")) {
					serverMessage = "" + username + " said: " + clientMessage; 
					chatServer.broadcast(serverMessage, this);
				}
			} while(!clientMessage.equals("bye"));
			chatServer.removeUser(username, this);
			socket.close();			
			serverMessage = username + " quitted.\n"+ "We have " +chatServer.getUsernames();
			chatServer.broadcast(serverMessage, this);
		} catch(IOException io) {
			System.out.println("Error in userThread - " +io.getMessage());
			io.printStackTrace();
		}
	}
	private void printUsers() {
		// TODO Auto-generated method stub
		if(chatServer.hasUsers()) {
			writer.println("Connected users: " +chatServer.getUsernames());
		} else {
			writer.println("No other connected users.");
		}
	}
	
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		writer.println(message);
	}

}
