package network;

import java.net.ServerSocket;
import java.net.Socket;


public class MultithreadedTcpServer {

	static final int MAX_CONN = 999;
	static final int SRV_PORT = 8698;

	public static void main(String[] args) throws Exception {
	   
		int count = 0;                  // conta il numero di client
			
		// Creazione del socket
		ServerSocket server = new ServerSocket(SRV_PORT);
	
		while(count<MAX_CONN) {
			count++;
			// Attendiamo le richieste di connessione dei client
			System.out.println("Server: in ascolto sulla porta " + SRV_PORT );
			Socket serverClientSocket = server.accept();  // bloccante

			System.out.println("Serving Client " + count);
			// Handle the client communication
			TcpServer sa = new TcpServer(serverClientSocket, count);
			sa.start();
			sa.setName("Questo-e-il-mio-ServerThread-Numero-" +count);  
		}
		
		server.close();
	}
}