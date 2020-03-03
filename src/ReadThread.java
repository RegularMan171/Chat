import java.net.Socket;
import java.io.*;
//import java.net.*;

public class ReadThread extends Thread{

	private BufferedReader reader;
	private Socket socket;
	private ChatClient chatClient;
	
	public ReadThread(Socket socket, ChatClient chatClient) {
		this.socket = socket;
		this.chatClient = chatClient;
		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch(IOException ex) {
			System.out.println("Error getting input stream - " +ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				String response = reader.readLine();
				System.out.println(response);
				
				/*
				 * if(chatClient.getUsername()!= null) {
				 * System.out.println(chatClient.getUsername()+ " said: "); }
				 */			} catch(IOException ex) {
				//System.out.println("Error reading from server " +ex.getMessage());
				//ex.printStackTrace(); //Print stack trace is only for educational purposes
				//if we press bye this gets executed
				System.out.println(ex.getMessage());
				break;
			}
		}
	}
}
