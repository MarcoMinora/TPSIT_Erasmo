/**
 * from network/..
 * javac network/Client.java; c
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TcpClient {
	public static void main(String[] args) throws Exception {
		try {

			String severAddress="127.0.0.1";  // localhost
			int severPort=8698;
			String serverMessage = "";
			
			// Create connection to server socket
			System.out.print("Client: Tentativo di connessione server=" + severAddress + ":" + severPort + " ... ");
			Socket socket = new Socket(severAddress, severPort); //
			System.out.println("Connected");

			// Create streams to read/write data from socket
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		
			//il client riceve il numero dal server
			serverMessage = inStream.readUTF();
			System.out.println("Numero Client: " + serverMessage);

			//tempo di durata del thread in quanto non ci sono particolari operazioni da eseguire una volta che il numero è stato assegnato correttamente
			Thread.sleep(3000);
			
			System.out.println("Numero Client: " + serverMessage + "exit");
			// Close resources
			inStream.close();
			outStream.close();
			socket.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}