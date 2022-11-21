package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class WorkerThread extends Thread{
	
	Socket sock; //definizione della porta sulla quale comunicare
	int clientNo; //conterrà il numero del client
	ReservationManager reservations;
	
	WorkerThread( String threadName , Socket inSocket, int ClientNo ,  ReservationManager res) {
		super(threadName);
		sock = inSocket;
		clientNo = ClientNo;
		reservations=res;
	}

	
	//ritorna true se la conversione da stringa a numero ha avuto successo
	public boolean isNumeric( String stringa_numerica) {
	  boolean result=true;
	  
	  try{
		  int num = Integer.parseInt(stringa_numerica);
	        
	  }catch (NumberFormatException e) {
	      result = false;
	        
	  }
	  return result;
	}
	
	//riunisce tutto l'array di stringhe che era stato frammentato dal metodo .split()
	public String joinArray( String a[] , int from , int to ) {
		  String s="";
		  for (int i=from ; i<=to ; i++) {
			  s=s+ " " + a[i];
		  }
		  return s;
		}
	
	//esecuzione della classe
	public void run() {
		try {

			InetSocketAddress clienAddr = (InetSocketAddress) sock.getRemoteSocketAddress();
	        System.out.println("ServerTread: " + this.getName() + " New connection from port=" + clienAddr.getPort() + " host=" + clienAddr.getHostName());
	        
			// Streams to read and write the data to socket streams
			DataInputStream inStream   = new DataInputStream(sock.getInputStream());
			DataOutputStream outStream = new DataOutputStream(sock.getOutputStream());

			String clientCommand = ""; //stringa che conterrà il comando del client ricevuto in input
			String serverResponse = ""; //stringa che conterrà la risposta di questo server alla richiesta del client

			//si sta nel ciclo finchè il cliente non digita 'end'
			while (!clientCommand.equals("end")) {
				clientCommand = inStream.readUTF();  // bloccante
				
				
				// elaboro il comando del client
				clientCommand.trim();	//tolgo gli spazi all'inizio e alla fine della stringa
				String clientCommandArr[]=clientCommand.split("\\s+"); //segmento la stringa ad ogni spazio in una sezione dell'array differente
				
				System.out.println("ServerTread: " + this.getName() + " Ricevuto Comando " + clientCommand );
				
				//analizzo a prima casella dell'array nel quale è contenuta l'istruzione da andare ad eseguire
				switch (clientCommandArr[0]) {
					                         
				  case "info" :  serverResponse=reservations.getReservations();
                                 break;
                            
				  case "reserve": 
							     System.out.println("ServerTread: " + this.getName() +" (reserve) num="+  clientCommandArr[1] + " name=" +  clientCommandArr[2] );
					             //controllo se il secondo spazio dell'array è costituito da un numero: posto da prenotare
							     if (isNumeric(clientCommandArr[1]) == true) {
							    	 
							    	 //se è un numero lo converto da stringa ad intero
					            	 int num=Integer.parseInt(clientCommandArr[1]);
					            	
					            	 //ricompongo la stringa completa da restituire al clien
					            	 String name=joinArray(clientCommandArr , 2 , clientCommandArr.length-1 );
								     serverResponse=reservations.setReservation(num, name);
					             
							     } else {
					            	
							    	 //se non è un intero restituirò un messaggio contenente ''errore di sintassi'
					            	 serverResponse="comand reserve: syntax error";
					             }
                                 break;
		            
				  case "help" :  serverResponse="Help: comandi disponibili help, info, reserve <num> <nome>, end";
				                 break;
				                 
				  case "end" :  serverResponse="Bye from server";
	                            break;
		                		
				  default:      serverResponse="command " + clientCommand + " not found";
		                        break;
				}
				
				//stampo su console e invio della risposta del server al client
				System.out.println("ServerTread: " + this.getName() + " invio risposta " + serverResponse );
				outStream.writeUTF(serverResponse);
				outStream.flush();
			}
			
			//quando il client digiterà end verrà eseguita questa parte dove si saluta il client
			serverResponse="arrivederci";
			System.out.println("Server.Thread " + clientNo + " Invio messaggio " + clientCommand );
			outStream.writeUTF(serverResponse);
			outStream.flush();
			
			//chiusura dei flussi e delle socket aperte
			inStream.close();
			outStream.close();
			sock.close();

		//eccezioni se qualcosa dovesse andare storto: gestione degli errori
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			System.out.println("Client -" + clientNo + " exit!! ");
		}
	}

	

}
