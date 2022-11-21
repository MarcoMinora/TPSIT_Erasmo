package network;


public class ReservationManager {
   
    private String[] reservations;

    //Costruttore
    ReservationManager(int reservationsSize ) {
    	reservations=new String[reservationsSize];
    	for (int i=0 ; i<reservationsSize ; i++ ) {
    		reservations[i]="Free_Seat";
    	}
    }
    
    //restituisce le prenotazioni, sinchronized perchè un solo thread potrà entrarci alla volta, in quanto queste risorse sono condivise
    public synchronized String getReservations() {
    	String res="";
    	for (int i=0 ; i<reservations.length ; i++ ) {
    		res= res + "\n" +  i + " " + reservations[i];
    	}
        return res;
    }
    
    //modifica lo stato della prenotazione, sinchronized perchè un solo thread potrà entrarci alla volta, in quanto queste risorse sono condivise
    public synchronized String setReservation( int seatNum, String name ) {
    	
    	//pongo di default tutte le prenotazioni con refused
    	String result = "Reservation for " + name + " Refused";
    	
    	if ( reservations[seatNum].equals("Free_Seat")) {
    		//se il posto che si vuole prenotare è disponibile cambio il suo stato con prenotato e restituisco il valore dell'operazionie
    		 reservations[seatNum]=name;
    		 result="Reservation for " + name + " Accepted";
    	} 
    	//altrimenti ritorno refused
        return result;
    } 
}
