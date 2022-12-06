/**
 * Implementazione di un server web utilizzando la comunicazione tramite socket.
 * Lettura dati multi riga provenienti dal client
 * 
 * from folder network/..
 * javac network/TcpServer.java; java network.TcpServer 
 */
 
package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWeb {
	public static void main(String[] args) throws Exception {
		
		final int SERVER_PORT=8765;
		String clientMsg = "";
		String serverMsg = "";
		
		try {			 
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server: in ascolto sulla porta " + SERVER_PORT);

			boolean endConn=false;
			while(!endConn) {
				// Attesa della connessione con il client
				System.out.println("Attesa ricezione dati dal client ....................... \n");
				Socket clientSocket = serverSocket.accept();
				
				// Create output stream to write data and input stream to read data from socket
				DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	
				BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
				// ---------------------------------------------------------
				//Lettura dati dal client un righa alla volta   
				while ((clientMsg=inStream.readLine()).length() != 0) {
					System.out.println(clientMsg);	
				}  
				// Elaborare qui i dati ricevuti dal client 
				// ---------------------------------------------------------

				//Invio dei dati su stream di rete al client
				serverMsg = "HTTP/1.1 200 OK\r\n";
				//serverMsg += "Connection: close\r\n";
				//serverMsg += "Content-Type: text/plain\r\n";
				serverMsg += "\r\n";
				serverMsg += "Saluti dal web server del sergente Marco Minora";
				outStream.write(serverMsg.getBytes());
				outStream.flush();

				System.out.println("\n....................... Fine ricezione dati\n");
				// Close resources
				clientSocket.close();
				inStream.close();
				outStream.close();
			}

			// Close resources
			serverSocket.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
