import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;

public class Emission implements Runnable {

	private PrintWriter out;
	private String login = null, message = null;
	private Scanner sc = null;
	private Socket socket;
	public Emission(Socket socket,String message) {
		this.socket = socket;
		this.message = message;

	}


	public void run() {
		try{
			out = new PrintWriter(socket.getOutputStream());
			System.out.println("Votre message :");
	//			message = sc.nextLine();
				out.println(message);
			    out.flush();
		}catch(IOException ex){
			System.err.println("Le serveur distant s'est déconnecté !");
		}

	//	  sc = new Scanner(System.in);

	//	  while(true){

	//		  }
	}
}
