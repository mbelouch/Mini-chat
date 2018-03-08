/* serveur.java
 *dans cette classe qui joue le rôle du serveur 
 *on declare une ServerSocket avec un numero de port 
 *d'écoute.
 */
import java.io.*;
import java.net.*;
public class Serveur {
 public static ServerSocket serverSocket = null;
 public static Thread t;
 public static PrintWriter[] out = new PrintWriter[10];
 public static int nbr=0;
 public static String[] nomClient = new String[10];

	public static void main(String[] args) {
		try {
			//creation d'une socket serveur avec un numero de port 
			serverSocket = new ServerSocket(2019);
			System.out.println("Le serveur est à l'écoute du port "+serverSocket.getLocalPort());
			t = new Thread(new ClientServeur(serverSocket));
			t.start();
		} catch (IOException e) {
			System.err.println("Le port "+serverSocket.getLocalPort()+" est déjà utilisé !");
		}
	}
	public static void ajoutPrintWriter(PrintWriter sortie,String adress){
		//un nouveau client vient de se connecté on garde son adresse 
		//et le flux de sotire qui est lui associé		
		out[nbr]=sortie;
		nomClient[nbr] = adress;
		//on informe les autre qu'un nouveau client s'est connecté 
		for(int i=0;i<nbr;i++){
			Thread e = new Thread(new Emission(out[i],adress+" vient de se connecté "));
			e.start();
		}
		nbr++;
	}
	public static void redirectMessage(String adrDestin,String msg){
		//on cherche le flux de sortie associé a cette adresse pour qu'on puise 
		//l'envoyé le message	
		for(int i=0;i<nbr;i++){
			if(nomClient[i].equalsIgnoreCase("/"+adrDestin)){
				Thread t1 = new Thread(new Emission(out[i],msg));
				System.out.println("dddddd "+adrDestin);
				t1.start();
			}
		}

	}


}
