package communication_EV3_PC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import robot.Constantes;


public class PCServeur {
	
	public static void main(String[] args) {
		
		
		ServerSocket server  ;
		Socket socket ;

		System.out.println("Let's start");
		
		try {
			server = new ServerSocket(Constantes.PORT);
			while(true) {
				socket= server.accept(); 
				System.out.println("Connexion d'une socket");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

				String mess = br.readLine();
				System.out.println("Message reçu du client :\n" + mess);
				
			    
			    socket.close();

			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
