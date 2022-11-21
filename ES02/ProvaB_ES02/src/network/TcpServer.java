package network;

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

			serverClientSocket.close();

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}