import java.io.*;
import java.io.IOException;
import java.net.Socket;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null;
	private Socket socket;

	public Reception(Socket socket){

		this.socket = socket;
	}

	public void run() {
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while(true){
	        try {

			message = in.readLine();
			Client.receptionMessage("serveur",message);
			System.out.println("Le serveur vous dit :" +message);

		    } catch (IOException e) {

				e.printStackTrace();
			}
		}
		}catch(IOException ex){
			System.err.println("erreur");
		}
	}

}
