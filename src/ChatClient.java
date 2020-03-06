import java.io.*;
import java.net.*;

public class ChatClient {

	private String hostName;
	private int port;
	private String username;

	public ChatClient(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	public void execute() {
		try {
			Socket socket = new Socket(hostName, port);
			System.out.println("Connected to the chat server (;");
			new ReadThread(socket, this).start();
			new WriteThread(socket, this).start();
		} catch(UnknownHostException ex) {
			System.out.println("Server not found - " +ex.getMessage());
		} catch(IOException ex) {
			System.out.println("IO Error - " +ex.getMessage());
		}
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static void main(String[] args) {
		if (args.length <2) return;

		String hostName = args[0];
		int port = Integer.parseInt(args[1]);

		ChatClient client = new ChatClient(hostName, port);
		client.execute();

	}
}