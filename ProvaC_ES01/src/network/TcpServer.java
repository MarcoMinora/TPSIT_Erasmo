
/**
 * from network/..
 * javac network/TcpServer.java
 * java network.TcpServer 
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	public static void main(String[] args) throws Exception {
		
		int severPort=8765;
		String clientMsg = "";
		int i=0;
		int contaVoc = 0, contaCons = 0, flag= 0;
		String uscire = "quit";
		
		try {			 
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(severPort);
			System.out.println("Server: in ascolto sulla porta " + severPort);

			// Attesa della connessione con il client
			Socket clientSocket = serverSocket.accept();
			
			// Create input and output streams to read/write data
			DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	

			// Scambio di dati tra client e server
				//Lettura dato da stream di rete
				
			do {
				clientMsg = inStream.readUTF();
				clientMsg.toLowerCase();
				//char ch = clientMsg.charAt(i);
			
				for(i=0; i<clientMsg.length(); i++) 
				{
					if ((clientMsg.charAt(i)=='a')||(clientMsg.charAt(i)=='e')||(clientMsg.charAt(i)=='i')|| (clientMsg.charAt(i)=='o')||(clientMsg.charAt(i)=='u')) {
						contaVoc++;
					} else if (clientMsg.charAt(i)>'a' && clientMsg.charAt(i)<='z') {
						contaCons++;
					}
				}
					
				//la connessione terminerà quando il numero di vocali sono la metà delle consonanti
				if(contaVoc==(contaCons/2) && contaCons%2==0)
				{
					outStream.writeUTF("echo: " + clientMsg + " --> " + uscire + "\nvocali: " + contaVoc + " \nconsonanti: " + contaCons);
					outStream.flush();
					System.out.println("echo: " + clientMsg + " --> " + uscire + "\nvocali: " + contaVoc + " \nconsonanti: " + contaCons);
					flag=1;
				} else {
					outStream.writeUTF("echo: " + clientMsg + "\nvocali: " + contaVoc + " \nconsonanti: " + contaCons);
					outStream.flush();
					System.out.println("echo: " + clientMsg + "\nvocali: " + contaVoc + " \nconsonanti: " + contaCons);
				}
				contaVoc=0;
				contaCons=0;
			} while(flag==0);
				
			
				
			// Close resources
			serverSocket.close();
			clientSocket.close();
			inStream.close();
			outStream.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
