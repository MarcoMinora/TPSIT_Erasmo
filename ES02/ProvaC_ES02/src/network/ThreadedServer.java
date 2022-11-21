package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer {
	
	ReservationManager reservations;
	
	public static void main(String[] args) throws Exception {
	   
		int severPort=8698;
		int clienCount = 0;       // conta il numero di client
		int reservationsSize=5;  // numero di posti
		ReservationManager reservations= new ReservationManager(reservationsSize);
		
		
		//porta in ascolto del server
		ServerSocket ssock = new ServerSocket(8698);
		System.out.println("Server: in ascolto sula porta " + severPort );
		
		//ciclo while infinito perchè il server deve essere sempre raggiungibile
		while (true) {		
			//il server resta in ascolto e aspetta il client, quando arriva gli "assegna" il numero
			Socket serverClientSocket = ssock.accept();  // bloccante
			clienCount++;
			System.out.println("Server: Serving Client " + clienCount);
			
			//mantenimento della connessione con il client tramite thread
			WorkerThread sa = new WorkerThread( "Thread-Numero-" +clienCount ,  serverClientSocket, clienCount , reservations); 
			sa.start();  // non è bloccante
		}		
	}
}
