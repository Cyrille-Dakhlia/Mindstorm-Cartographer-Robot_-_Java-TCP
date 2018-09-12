package objets_cartographie;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.event.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.beans.property.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tmp.Graphe;
import tmp.Sommet;
import objets_communs.PointCapture;


public class CartographieDonneesGraphiques extends JFrame {
	private static final long serialVersionUID = 1L;

	public CartographieDonneesGraphiques(DonneesGraphiques data) {
		
        JFrame frame = new JFrame("Dessinons mon ami");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Toile toile = new Toile(1250, 760, data);
        frame.setMinimumSize(new Dimension(toile.width, toile.height));
        this.setSize((int)getToolkit().getScreenSize().getWidth(), ((int)getToolkit().getScreenSize().getHeight() - 40));

        JPanel mainLayout = new JPanel(new BorderLayout());
        mainLayout.add(toile);
        frame.add(mainLayout);
        frame.pack();
        frame.setVisible(true);
	}

	public CartographieDonneesGraphiques(int width, int height, Old_DonneesGraphiques data) {
        JFrame frame = new JFrame("Dessinons mon ami");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toile toile = new Toile(width, height, data);
        frame.setMinimumSize(new Dimension(toile.width, toile.height));
        this.setSize((int)getToolkit().getScreenSize().getWidth(), ((int)getToolkit().getScreenSize().getHeight() - 40));
        
        JPanel mainLayout = new JPanel(new BorderLayout());
        mainLayout.add(toile);
        frame.add(mainLayout);
        frame.pack();
        frame.setVisible(true);
	}

	
	class Toile extends JPanel implements MouseMotionListener {
		private static final long serialVersionUID = 1L;
		public int width;
	    public int height;
	    Old_DonneesGraphiques data;

	    private final int TAILLE_POINT = 3; //1;
		private final int FACTEUR_AGRANDISSEMENT = 2; //3;  // Correspond a l'echelle du dessin
		private final int PORTEE_SONAR = 100*2;
		private final int MARGE_X = 300; //this.width/2;  // Correspond au vecteur de translation en abscisse du dessin
		private final int MARGE_Y = 100; //this.height/2;  // Correspond au vecteur de translation en ordonnee du dessin

	    ArrayList<Point> liste;

	    Toile(int w, int h, Old_DonneesGraphiques data) {
	        this.width = (w < 800)? 800 : w;
	        this.height = (h < 500)? 500 : h;
	        this.data = data;
	        this.liste = new ArrayList<Point>();
	        this.setBackground(Color.red);
	    }
	
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
	        Graphics2D g2d = (Graphics2D) g;
	        
	        System.out.println("YOYOYO");

	        
	        g2d.setColor(Color.white);
	        g2d.drawOval((int)(Math.abs(this.data.xMin) - (PORTEE_SONAR / 2)) * FACTEUR_AGRANDISSEMENT + MARGE_X,
	        			(int)(Math.abs(this.data.yMin) - (PORTEE_SONAR / 2)) * FACTEUR_AGRANDISSEMENT + MARGE_Y,
	        			PORTEE_SONAR * FACTEUR_AGRANDISSEMENT, PORTEE_SONAR * FACTEUR_AGRANDISSEMENT);

	        g2d.setColor(Color.yellow);

	        for(int i=0 ; i<this.data.capture.length ; i++) {
	        	PointCapture p = this.data.capture[i];

	        	if(p.getEstCentreDuRobot())
	        		g2d.setColor(Color.BLUE);
	        	else
	        		g2d.setColor(Color.YELLOW);  // RAJOUTER UN CHAMP "boolean centre" qui permet de savoir si un point est un centre pour la couleur
	        	
				System.out.println("x = " + (int)p.getCartesianX() + ", y = " + (int)p.getCartesianY());
				g2d.drawRect((int)(Math.abs(this.data.xMin) + p.getCartesianX()) * FACTEUR_AGRANDISSEMENT + MARGE_X,
							(int)(Math.abs(this.data.yMin) + p.getCartesianY()) * FACTEUR_AGRANDISSEMENT + MARGE_Y,
							TAILLE_POINT, TAILLE_POINT);
	        }
	        
//	        g2d.setColor(Color.white);
//	        g2d.drawRect((int)Math.abs(this.data.xMin) * FACTEUR_AGRANDISSEMENT + MARGE_X,
//        				(int)Math.abs(this.data.yMin) * FACTEUR_AGRANDISSEMENT + MARGE_Y,
//        				TAILLE_POINT, TAILLE_POINT);
//	        g2d.drawOval((int)(Math.abs(this.data.xMin) - (PORTEE_SONAR / 2)) * FACTEUR_AGRANDISSEMENT + MARGE_X,
//	        			(int)(Math.abs(this.data.yMin) - (PORTEE_SONAR / 2)) * FACTEUR_AGRANDISSEMENT + MARGE_Y,
//	        			PORTEE_SONAR * FACTEUR_AGRANDISSEMENT, PORTEE_SONAR * FACTEUR_AGRANDISSEMENT);
//
//	        g2d.setColor(Color.yellow);
//
//	        for(PointCapture p : this.data.capture) {
//				System.out.println("x = " + (int)p.getCartesianX() + ", y = " + (int)p.getCartesianY());
//				g2d.drawRect((int)(Math.abs(this.data.xMin) + p.getCartesianX()) * FACTEUR_AGRANDISSEMENT + MARGE_X,
//							(int)(Math.abs(this.data.yMin) + p.getCartesianY()) * FACTEUR_AGRANDISSEMENT + MARGE_Y,
//							TAILLE_POINT, TAILLE_POINT);
//	        }
	
	        g2d.setColor(Color.blue);
	        
	        for(Point p : this.liste) {
	        	g2d.drawRect(p.x, p.y, 10, 10);                // Beau
//	        	g2d.drawLine(p.x, p.y, p.x+10, p.y+10);        // ok
//	        	g2d.drawRoundRect(p.x, p.y, p.x, p.y, 50, 50); // Magnifique
	        }

	        
	    }

	    
	    @Override
	    public void mouseDragged(MouseEvent e) {
	        System.out.println("you dragged me");
	        this.liste.add(new Point(e.getX(), e.getY()));
	        this.repaint();
	    }

		@Override
		public void mouseMoved(MouseEvent arg0) { }
	    
	}








	/* DANS CE FICHIER : 
	   ======> RECEVOIR LA CARTE (int[][]) ET LE GRAPHE (ArrayList<Sommet>);
	*/

	/* APPELLER refresh(carte,graphe); */




    private void refresh(GraphicsContext gc, int[][] carte, Graphe graphe){
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
		/*for (Sommet s : graphe) {
		    drawSommet(s);
		    for (Sommet v : s.voisins) {
			drawSommet(v);
			drawLine(s,v);
		    }
		    }*/
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
}

