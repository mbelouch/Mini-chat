
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;




public class ClientServeur implements Runnable {

	private Socket socket = null;
	private ServerSocket socketServer;
	private PrintWriter out = null;
	private Thread t1, t2;


	public ClientServeur(ServerSocket socketServer){

		this.socketServer = socketServer;

	}
	public void run() {
		try {

			while(true){
				//on accepte la demande de conexion d'un client
				
				socket = socketServer.accept();
				// puis on lance un thread pour recevoir les messages de ce client
				t1 = new Thread(new Reception(socket));
				t1.start();
				// et un autre pour envoyer des messages a ce client
				out = new PrintWriter(socket.getOutputStream());
				t2 = new Thread(new Emission(out,"vous etes connecté au serveur"));
				t2.start();

			}
		}catch (IOException e) {
			System.err.println("le client est déconnecté ");
		}
}
}
