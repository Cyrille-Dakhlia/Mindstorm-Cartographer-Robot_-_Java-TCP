package communication_EV3_PC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import objets_communs.PointCapture;

public class EV3Client {
	public String adressePC;
	public int portPC;
	public Socket socket;
	
	
	public EV3Client(String adresse, int port) {
		this.adressePC = adresse;
		this.portPC = port;
	}
	
	
	public void transmission_donnees(PointCapture p) {
		try {
			
			this.socket = new Socket(this.adressePC, this.portPC);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			pw.println(p);
			pw.flush();
			
			pw.close();		
			
			this.socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void transmission_donnees(String p) {
		try {
			
			this.socket = new Socket(this.adressePC, this.portPC);
			System.out.println("Connecté");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			System.out.println("on va print");
			pw.println(p);
			pw.flush();
			System.out.println("on a printé");
			
			pw.close();		
			
			this.socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/*** Ancienne architecture où les étapes de manipulations de la socket étaient séparées ***/
//	public void ouverture_connexion_PC() {
//		try {
//			this.socket = new Socket(this.adressePC, this.portPC);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void fermeture_connexion_PC() {
//		try {
//			this.socket.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	
}
