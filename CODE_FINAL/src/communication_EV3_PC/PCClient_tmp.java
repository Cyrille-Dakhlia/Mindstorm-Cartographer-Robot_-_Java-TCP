package communication_EV3_PC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import objets_cartographie.Cartographie;
import objets_cartographie.CartographieDonneesGraphiques;
import objets_cartographie.DonneesGraphiques;
import robot.Constantes;

//METTRE CA AVEC LE PASSAGE D'UN PARAMETRE DonneesGraphiques AU LIEU de PointCapture[]
//METTRE LES CHAMPS TABLEAU DANS CarteToile3 et 


public class PCClient_tmp {

    
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("10.0.1.1", Constantes.PORT);
			System.out.println("Connexion d'une socket");
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					
			DonneesGraphiques data = (DonneesGraphiques)in.readObject();

//			for(int i=0 ; i<data.capture.length ; i++) 
//				System.out.println("(d= " + data.capture[i].getPolarDistance() + ", a= " + data.capture[i].getPolarAngle()
//						+ "  <=>  (x= " + data.capture[i].getCartesianX() + ", y= " + data.capture[i].getCartesianY() + ")");		

			
//			CartographieDonneesGraphiques carte = new CartographieDonneesGraphiques(data);
			Cartographie cartographie = new Cartographie(data.getCarte(), data.getGraphe());
			
			in.close();
			
			socket.close();
							
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
