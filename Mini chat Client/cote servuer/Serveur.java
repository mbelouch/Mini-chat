/* serveur.java
 *dans cette classe qui joue le r�le du serveur 
 *on declare une ServerSocket avec un numero de port 
 *d'�coute.
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
			System.out.println("Le serveur est � l'�coute du port "+serverSocket.getLocalPort());
			t = new Thread(new ClientServeur(serverSocket));
			t.start();
		} catch (IOException e) {
			System.err.println("Le port "+serverSocket.getLocalPort()+" est d�j� utilis� !");
		}
	}
	public static void ajoutPrintWriter(PrintWriter sortie,String adress){
		//un nouveau client vient de se connect� on garde son adresse 
		//et le flux de sotire qui est lui associ�		
		out[nbr]=sortie;
		nomClient[nbr] = adress;
		//on informe les autre qu'un nouveau client s'est connect� 
		for(int i=0;i<nbr;i++){
			Thread e = new Thread(new Emission(out[i],adress+" vient de se connect� "));
			e.start();
		}
		nbr++;
	}
	public static void redirectMessage(String adrDestin,String msg){
		//on cherche le flux de sortie associ� a cette adresse pour qu'on puise 
		//l'envoy� le message	
		for(int i=0;i<nbr;i++){
			if(nomClient[i].equalsIgnoreCase("/"+adrDestin)){
				Thread t1 = new Thread(new Emission(out[i],msg));
				System.out.println("dddddd "+adrDestin);
				t1.start();
			}
		}

	}


}
