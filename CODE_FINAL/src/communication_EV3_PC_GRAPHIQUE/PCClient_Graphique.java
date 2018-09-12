package communication_EV3_PC_GRAPHIQUE;

import calculs.Graphe;
import calculs.Sommet;
import calculs.Coor;
import calculs.Cartographe;
import objets_communs.File;

import javafx.application.*;
import javafx.stage.Stage;
import objets_cartographie.DonneesGraphiques;
import objets_cartographie.DonneesResultantes;
import objets_communs.File;
import robot.Constantes;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.event.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.beans.property.*;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;



public class PCClient_Graphique extends Application {
    public static final int H = 600;
    public static final int W = 800;

    public static final int PORTEE = 150;

    public static int xR = 400;
    public static int yR = 300;

    GraphicsContext gc;
    Canvas canvas;
    WritableImage wi;

    int i = 0;
    
    Cartographe cartographe = new Cartographe();

    static ArrayList<Coor> list;
    static int[][] carte;
    static Sommet current;
    
    
    
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage stage) {
		
		
	  	 /*********************************************************/
		/*** DEBUT : Partie recuperation des donnees de l'EV3  ***/
       /*********************************************************/

		try {
			Socket socket = new Socket("10.0.1.1", Constantes.PORT);
			System.out.println("Connexion d'une socket");
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					
			DonneesGraphiques data = (DonneesGraphiques)in.readObject();
			
			list = data.getListe();
			carte = data.getCarte();
			current = data.getSommetCourant();
			socket.close();
							
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 	  	 /*******************************************************/
		/*** FIN : Partie recuperation des donnees de l'EV3  ***/
       /*******************************************************/
		
		
		
		
		 /*********************************************************/
		/*** DEBUT : Scan et envoi des voisins                 ***/
	   /*********************************************************/
		
		cartographe.scan(list, current, carte);
		DonneesResultantes data = new DonneesResultantes(carte, cartographe.getSommetsAEnvoyer());
		
		try {
			Socket socket = new Socket("10.0.1.1", Constantes.PORT);
			System.out.println("Connexion d'une socket");
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
											
//			out.writeObject((Object)p);
			out.writeObject(data);
			out.flush();
			
			out.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		
		 /*********************************************************/
		/*** FIN : Scan et envoi des voisins                 ***/
	   /*********************************************************/
		
		
	  	 /*********************************************************/
		/*** DEBUT : Partie creation de l'interface graphique  ***/
       /*********************************************************/
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		canvas = new Canvas(W,H);
		
//		graphe.add(current);

		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.GREEN);
//		gc.strokeRect(robot.x,robot.y,2,2);
		gc.setStroke(Color.RED);
		
		refresh(gc,carte,current,cartographe.getSommetsAEnvoyer());	
		    	    
		stage.setTitle("Projection");
		stage.setScene(scene);
		stage.show();

	  	 /*******************************************************/
		/*** FIN : Partie creation de l'interface graphique  ***/
       /*******************************************************/

	}
	
	
	

	
    private void refresh(GraphicsContext gc, int[][] carte, Sommet d, ArrayList<Sommet> nouveauxVoisins){
		PixelWriter pw = gc.getPixelWriter();
		for (int i=0; i<W; i++) {
		    for (int j=0; j<H; j++) {
			if (carte[i][j] == -1) {
			    pw.setColor(i,j,Color.MAGENTA);
			}
			if (carte[i][j] == 0) {
			    pw.setColor(i,j,Color.BLACK);
			}
			if (carte[i][j] == 1) {
			    pw.setColor(i,j,Color.WHITE);
			}
			if (carte[i][j] == 2) {
			    pw.setColor(i,j,Color.RED);
			}
			if (carte[i][j] == -3) {
			    pw.setColor(i,j,Color.PURPLE);
			}
			if (carte[i][j] == -2) {
			    pw.setColor(i,j,Color.YELLOW);
			}
		    }
		}
		drawGraphe(d);
		for (Sommet v : nouveauxVoisins) {
		    drawSommet(v);
		    drawLine(d,v);
		}
    }

    public void drawGraphe(Sommet d) {
		Sommet next;
		File Q = new File();
		Q.push(d);
		while(!Q.isEmpty()) {
		    next = Q.pop();
		    drawSommet(next);
		    if (next.getIsVisite()) drawCross(next);
		    next.setBFScheck(true);
		    for (Sommet v : next.getVoisins()) {
			if (!v.BFScheck()) {
			    Q.push(v);
			    drawSommet(v);
			    drawLine(next,v);
			    v.setBFScheck(true);
			}
		    }
		}
		Q.push(d);
		while(!Q.isEmpty()) {
		    next = Q.pop();
		    next.setBFScheck(false);
		    for (Sommet v : next.getVoisins()) {
			if (v.BFScheck()) {
			    Q.push(v);
			    v.setBFScheck(false);
			}
		    }
		}
    }

    public void drawSommet(Sommet s) {
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(2.0);
		gc.strokeOval((double)s.getX()-10,(double)s.getY()-10,20,20);
		gc.setLineWidth(1.0);
    }

    public void drawLine(Sommet s1, Sommet s2) {
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(2.0);
		gc.strokeLine((double)s1.getX(),(double)s1.getY(),(double)s2.getX(),(double)s2.getY());
		gc.setLineWidth(1.0);
    }

    public void drawCross(Sommet s) {
		double x1, x2, y1, y2, x, y;
		x = s.getX();
		y = s.getY();
		gc.setStroke(Color.ORANGE);
		x1 = x + 10*Math.cos(Math.PI*(3.0/4));
		x2 = x + 10*Math.cos(-Math.PI/4);
		y1 = y - 10*Math.sin(Math.PI*(3.0/4));
		y2 = y - 10*Math.sin(-Math.PI/4);
		gc.strokeLine(x1,y1,x2,y2);
		x1 = x + 10*Math.cos(Math.PI/4);
		x2 = x + 10*Math.cos(-Math.PI*(3.0/4));
		y1 = y - 10*Math.sin(Math.PI/4);
		y2 = y - 10*Math.sin(-Math.PI*(3.0/4));
		gc.strokeLine(x1,y1,x2,y2);
    }
}

	

