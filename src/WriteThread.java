import java.net.Socket;
import java.io.*;
import java.net.*;

public class WriteThread extends Thread{

	private PrintWriter writer;
	private Socket socket;
	private ChatClient chatClient;

	public WriteThread(Socket socket, ChatClient chatClient) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.chatClient = chatClient;

		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch(IOException ex) {
			System.out.println("Error getting output stream - " +ex.getMessage());
			ex.printStackTrace();
		}
	}
	public void run() {
		Console console = System.console();

		String username = console.readLine("Name: ");
		chatClient.setUsername(username);
		writer.println(username);

		String text;

		do {
			text = console.readLine();
			writer.println(text);
		} while(!text.equals("bye"));

		try {
			socket.close();
		} catch(IOException ex) {
			System.out.println("Error writing to server - " +ex.getMessage());
		}
	}
}