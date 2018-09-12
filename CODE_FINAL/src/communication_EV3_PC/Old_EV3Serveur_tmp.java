package communication_EV3_PC;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import objets_cartographie.Old_DonneesGraphiques;


public class Old_EV3Serveur_tmp {
	public int port;
	public ServerSocket server;
	
	
	public Old_EV3Serveur_tmp(int port) {
		this.port = port;
	}
		
	
//	public void transmission_donnees(PointCapture[] p) {
//	public void transmission_donnees(Object p) { // ON METTRA "DonneesGraphique" EN PARAMETRE POUR ETRE PLUS SPECIFIQUE
	public void transmission_donnees(Old_DonneesGraphiques p) { // ON METTRA "DonneesGraphique" EN PARAMETRE POUR ETRE PLUS SPECIFIQUE
		System.out.println("Let's start");

		try {
			this.server = new ServerSocket(this.port);
			Socket socket= server.accept(); 
			System.out.println("Connexion d'une socket");
		
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			
//			out.writeObject((Object)p);
			out.writeObject(p);
			out.flush();
			
			out.close();
			
			socket.close();
			this.server.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

//	public void transmission_donnees(PointCapture p) {
//		System.out.println("Let's start");
//
//		try {
//			this.server = new ServerSocket(this.port);
//			Socket socket= server.accept(); 
//			System.out.println("Connexion d'une socket");
//		
////			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//			
////			pw.println(p);
////			pw.flush();
//			
////			pw.close();		
//			System.out.println("On ecrit l'objet");
//			out.writeObject((Object)p);
//			System.out.println("On flush l'objet");
//			out.flush();
//			System.out.println("On a ecrit l'objet");
//			
//			out.close();
//			
//			socket.close();
//			this.server.close();
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}


//	public void transmission_donnees(String p) {
//		System.out.println("Let's start");
//
//		try {
//			this.server = new ServerSocket(this.port);
//			Socket socket= server.accept(); 
//			System.out.println("Connexion d'une socket");
//		
//			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//			
//			pw.println(p);
//			pw.flush();
//
//			pw.close();		
//			
//			socket.close();
//			this.server.close();
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}


	
}
