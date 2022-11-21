package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class TcpServer extends Thread {
	Socket serverClientSocket;
	int clientNo;
	
	TcpServer(Socket inSocket, int ClientNo) {
		serverClientSocket = inSocket;
		clientNo = ClientNo;
	}

	public void run() {
		try {
			// Streams to read and write the data to socket streams
			DataInputStream inStream = new DataInputStream(serverClientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(serverClientSocket.getOutputStream());

			String cMsg = "";
			String sMsg = "";

			while (!cMsg.equals("end")) {

				//Leggiamo il messaggio proveniente dal client, e lo stampiamo a schermo
				cMsg = inStream.readUTF();
				System.out.println("Server.Thread " + clientNo + " Ricevuto messaggio " + cMsg );

				//Echo
				sMsg=cMsg;
				System.out.println("Server.Thread " + clientNo + " Invio messaggio " + sMsg );
				outStream.writeUTF(sMsg);
				outStream.flush();
			}
			
			//Prima di chiudere la connessione viene inviato un ultimo messaggio
			sMsg="Bye";
			System.out.println("Server.Thread " + clientNo + " Invio messaggio " + cMsg );
			outStream.writeUTF(sMsg);
			outStream.flush();

			//Chiusura delle risorse utilizzate
			inStream.close();
			outStream.close();
			serverClientSocket.close();

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			System.out.println("Client -" + clientNo + " exit!! ");
		}
	}
}