import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.io.PrintWriter;

public class Reception implements Runnable {

	private BufferedReader in;
	private PrintWriter out;
	private String message = null, login = null;
	private Socket socket;

	public Reception(Socket socket){

		this.socket = socket;
	}

	public void run() {
		try{
			//on enregistre le flux de sortie li� et on l'ajoute � la table 
			//pour le r�cup�rer lorsqu'un autre client l'envoie un message 
			out = new PrintWriter(socket.getOutputStream());
			InetAddress adr = socket.getInetAddress();
			String adress = adr.toString();
			Serveur.ajoutPrintWriter(out,adress);
	
			while(true){
	        	try {
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					message = in.readLine();
					//on lit le message du client et on prend l'adresse du destinataire 
					//car le message contient l'adresse du destinataire suivi d'un delimiteursuivi du message � envoyer.
					String[] data= message.split("�");
					String source= (socket.getInetAddress()).toString();
					//une fonction de la classe Serveur se charge de l'�mission du message � sa destination
					Serveur.redirectMessage(data[0],"from <"+source+"> : "+data[1]);


		    	} catch (IOException e) {

					e.printStackTrace();
				}
			}

		} catch(IOException ex){
			ex.printStackTrace();
		}

	}

}