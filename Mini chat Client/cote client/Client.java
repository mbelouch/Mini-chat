/**
 * @(#)Client.java
 * @author : Belouch Mustapha
 * @version 1.00 2010/12/14
 */
 /*on a utilisé les interfaces graphique du package awt
  *et a l'aide de l'action listner on connecte le client a un serveur avec la methode connectActionPerformed   
  *et on envoie des messages à l'aide de la methode envoyerActionPerformed
  *
  *
  */
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.SocketException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;


public class Client extends Frame{
	public static Client client;
	public static Socket socket = null;
	public static Thread t1,t2;
    public Client() {
    	initComponents();
    	setTitle("Mini-chat Client/Client" );
    	setSize(500,440);
    	client=this;
    }
    public void initComponents(){
    	//initialisation des composantes de l'interface graphiques
   
    	connect 		= new Button();
    	envoyer 		= new Button();
    	portClient 		= new TextField();
    	portServeur		= new TextField();
    	ipServeur 		= new TextField();
    	nomClient		= new TextField();
    	nomClientDes	= new TextField();
    	text			= new TextArea();
    	scroll			= new ScrollPane();
    	message 		= new TextField();
    	label1	= new Label();
    	label2	= new Label();
    	label3	= new Label();
    	label4	= new Label();
    	label5	= new Label();
    	label6	= new Label();
    	label7	= new Label();
    	label8	= new Label();

    	label1.setText("Port Client");
    	label2.setText("Nom");
		label3.setText("Port Serveur");
		label4.setText("IP Serveur");
		label5.setText("Client Destiné");
		label6.setText("Messages Reçus");
		label7.setText("votre Message");
		label8.setText("");

		text.setRows(20);
		text.setColumns(60);
		scroll.setComponentZOrder(text,0);

		envoyer.setLabel("Envoyer");
		connect.setLabel("Conneter");
		//ajout d'un action listner lié au bouton envoyer 
		envoyer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				envoyerActionPerformed(ev);
			}
		});
		//ajout d'un action listner lié au bouton connecter  		
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				connectActionPerformed(ev);
			}
		});
		//ajout d'un action pour fermer la fenetre
		Closer close = new Closer();
		addWindowListener(close);

		//on declare ici un groupeLayout pour groupé tous les zones de textes et labels
		Panel p1 	= new Panel();
		Panel p2 	= new Panel();
		Panel p3	= new Panel();
		Panel p4 	= new Panel();
		Panel p5	= new Panel();
		p1.setLayout(new FlowLayout());
		portServeur.setText("2018");
		ipServeur.setText("192.168.0.11");
		message.setText("coucou Serveur");
		nomClientDes.setText("192.168.0.2");
		// ajout des panel qui contient des composants à la fenetre
		p1.add(label1);
		p1.add(portClient);
		p1.add(label2);
		p1.add(nomClient);
		p2.add(label3);
		p2.add(portServeur);
		p2.add(label4);
		p2.add(ipServeur);
		p2.add(connect);
		p3.add(text);
		p4.add(label7);
		p4.add(message);
		p4.add(label5);
		p4.add(nomClientDes);
		p4.add(envoyer);

      	add("North",p2);
      	add("Center",p3);
      	add("South",p4);
		
		pack();
		show();




    }
    //definition du code qui vas se generer lors de l'appuit sur connect
    public void connectActionPerformed(ActionEvent ev){
    	
    	String adrServer = ipServeur.getText();
    	int portServer = Integer.parseInt(portServeur.getText());
		String localNom = nomClient.getText();
		System.out.println("port "+portServer+" est destiné a : "+adrServer);
		try {
			 System.out.println("Demande de connexion");
			 //on lance une socket client demandant une connection avec la socket serveur 
			 socket = new Socket(adrServer,portServer);
			 System.out.println("Connexion établie avec le serveur, authentification :"); // Si le message s'affiche c'est que je suis connecté
			 // on lance un thread qui s'occupe de recevoir les messages venants du serveur
			 t2 = new Thread(new Reception(socket));
			 t2.start();
		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
			} catch (IOException e) {
			System.err.println("Aucun serveur à l'écoute du port "+socket.getLocalPort());
			}
		}
		//definition du code qui vas se generer lors de l'appuit sur envoyer
		private void envoyerActionPerformed(ActionEvent ev){
			//lorsqu'on click sur on envoyer on recupère les données des zones de textes
			// et on les transmettres aux serveurs
			String nomDestine 	= nomClientDes.getText();
			String msg 			= " "+message.getText();
			String data =nomDestine+"°"+msg;
			message.setText("");
			t1 = new Thread(new Emission(socket,data));
			t1.start();


		}
		
		//la fonction principal main
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new Client().setVisible(true);
			}
		});
	}
	class Closer extends WindowAdapter {
		public void windowClosing (WindowEvent event) {
			System.exit (0);
    	}
	}
	public static void receptionMessage(String hoste,String msg){
		//on affiche le message recu dans la zone textField 
		client.text.append("\n"+msg);
	}


    //ici on declare les variables de l'interface graphique
    private Button connect;
    private TextField portServeur;
    private TextField portClient;
    private TextField ipServeur;
    private TextField nomClient;
    private TextField nomClientDes;
    private TextField message;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Label label5;
    private Label label6;
    private Label label7;
    private Label label8;
	private TextArea text;
	private Button envoyer;
	private ScrollPane scroll;

}