import java.io.IOException;
import java.io.PrintWriter;



public class Emission implements Runnable {

	private PrintWriter out;
	private String message = null;


	//le constucteur prend en charge le flux de sortie lié au client destinataire 
	//et le message 
	public Emission(PrintWriter out,String message) {
		this.out = out;
		this.message = message;
	}
	
	public void run() {
		//envoi du message au client 
		out.println(message);
			    out.flush();

	}
}
