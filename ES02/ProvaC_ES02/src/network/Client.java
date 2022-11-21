package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	static Scanner scanner;
	
	public static void main(String[] args) throws Exception {
		
		try {
			String severAddress="127.0.0.1";  // localhost
			int severPort=8698;
						
			String clientCommand = ""; //comando del client
			String serverMessage = ""; //messaggio in risposta dal server
			
			//appena si esegue il programma si stampa il menu delle operazioni che si possono compiere
			printMenu();	
			
			// Create connection to server socket
			System.out.print("Client: Tentativo di connessione server=" + severAddress + ":" + severPort + " ... ");
			Socket socket = new Socket(severAddress, severPort); //

			System.out.println("Connected");
			// Create streams to read/write data
			DataInputStream inStream   = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

			//creazione di un oggetto scanner di tipo Scanner
			scanner = new Scanner(System.in);
			
			//restare nel loop finchè l'utente non digita 'end'
			while (!clientCommand.equals("end")) {

				// Prompt user to enter some number or 'end'
				clientCommand = prompt();
			    System.out.println("Client: invio il comando: " + clientCommand);
				//invio al server del messaggio del client
			    outStream.writeUTF(clientCommand);    // bloccanre
				outStream.flush();
				
				// Read data from socket input stream
				serverMessage = inStream.readUTF();      // bloccante  
				System.out.println("Client: ricevuto il messaggio: " + serverMessage);     
			}
			
			//una volta usciti dal ciclo il client può disconnettersi
			System.out.println("Client: disconnecting");
			
			// Close resources
			outStream.close();
			outStream.close();
			socket.close();
			scanner.close();
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Exit");
		}
	}
	
	
	//stampa del menu delle operazioni permesse
	public static void printMenu() {
		System.out.println("Help: comandi disponibili");
		System.out.println("  help");
		System.out.println("  info");
		System.out.println("  reserve <numero> <nome>");
		System.out.println("  end");

	}
	
	//richiesta di inserire il comando all'utente
	public static String prompt() {
		scanner = new Scanner(System.in);
		System.out.print("inserisci comando> ");
		String cmd = scanner.nextLine();
		return cmd;
	}
}
