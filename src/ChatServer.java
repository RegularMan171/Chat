
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	
	private int port;
	private Set<String> usernames = new HashSet<>();
	private Set<UserThread> userThreads =  new HashSet<>();
	
	public ChatServer(int port) {
		this.port = port;
	}
	
	public void execute() {
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("chat server is listening on port " +port);
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected.");
				
				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();
			}
			
		}	catch(IOException ie) {
			System.out.println("Error in the server - " +ie.getMessage());
			ie.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1) {
			System.out.println("Syntax: java ChatServer <port-number>");
			System.exit(0);
		}
		
		int port = Integer.parseInt(args[0]);
		
		ChatServer server = new ChatServer(port);
		server.execute();
		
	}
	
	void broadcast(String message, UserThread excludeUser) {
		for(UserThread aUser: userThreads) {
			if(aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}
	void addUserName(String username) {
		usernames.add(username);
	}
	
	void removeUser(String username, UserThread aUser) {
		boolean removed = usernames.remove(username);
		if(removed) {
			userThreads.remove(aUser);
			System.out.println(username+ " quitted.");
		}
	}
	
	Set<String> getUsernames() {
		return this.usernames;
	}
	
	boolean hasUsers() {
		return !this.usernames.isEmpty();
	}
}