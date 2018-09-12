package communication_EV3_PC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import objets_cartographie.DonneesGraphiques;
import objets_cartographie.DonneesResultantes;


public class EV3Serveur_tmp {
	private int port;
	private ServerSocket server;
	
	
	public EV3Serveur_tmp(int port) {
		this.port = port;
	}
		
	public int getPort() { return this.port; }
	public ServerSocket getServerSocket() { return this.server; }
	
	
	public void transmission_donnees(DonneesGraphiques p) { 
		System.out.println("Let's start server to send data...");

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

	
	
	public DonneesResultantes reception_donnees() { 
		System.out.println("Let's start server to recv data...");
		DonneesResultantes data = null;
		
		try {
			this.server = new ServerSocket(this.port);
			Socket socket= server.accept(); 
			System.out.println("Connexion d'une socket");
		
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			data = (DonneesResultantes)in.readObject();

			in.close();
						
			socket.close();
			this.server.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
}
