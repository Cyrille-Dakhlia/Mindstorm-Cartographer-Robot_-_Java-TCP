package communication_EV3_PC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import objets_cartographie.Old_CartographieDonneesGraphiques;
import objets_cartographie.Old_DonneesGraphiques;

import robot.Constantes;

import objets_communs.PointCapture;
//METTRE CA AVEC LE PASSAGE D'UN PARAMETRE DonneesGraphiques AU LIEU de PointCapture[]
//METTRE LES CHAMPS TABLEAU DANS CarteToile3 et 


public class Old_PCClient_tmp {
	
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("10.0.1.1", Constantes.PORT);
			System.out.println("Connexion d'une socket");
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					
			Old_DonneesGraphiques data = (Old_DonneesGraphiques)in.readObject();
			
//			for(int i=0 ; i<data.captureTab.length ; i++) // VERSION TABLEAU
			for(PointCapture pc : data.capture) // VERSION ARRAYLIST
				System.out.println("(d= " + pc.getPolarDistance() + ", a= " + pc.getPolarAngle()
						+ "  <=>  (x= " + pc.getCartesianX() + ", y= " + pc.getCartesianY() + ")");		

			
			Old_CartographieDonneesGraphiques carte = new Old_CartographieDonneesGraphiques(data);
			
			socket.close();
							
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
