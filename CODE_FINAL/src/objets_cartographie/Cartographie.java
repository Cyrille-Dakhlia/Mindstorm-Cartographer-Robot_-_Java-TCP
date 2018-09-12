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
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import calculs.Coor;
import calculs.Graphe;
import calculs.Issue;
import calculs.Obstacle;
import calculs.Sommet;
import calculs.Bresenham;




public class Cartographie extends Application {
    public static final int H = 600;
    public static final int W = 800;

    public static final int PORTEE = 150;

    public static int xR = 400;
    public static int yR = 300;

//    int[][] carte = new int[W][H];
//    int[][] matriceTmp = new int[W][H];
    GraphicsContext gc;
    Canvas canvas;
    WritableImage wi;
    
//    ArrayList<Coor> list = new ArrayList<Coor>();
//    ArrayList<Obstacle> comps = new ArrayList<Obstacle>();
//    ArrayList<Obstacle> oldComps = new ArrayList<Obstacle>();
//    ArrayList<Obstacle> compsTmp = new ArrayList<Obstacle>();
//    ArrayList<Issue> issues = new ArrayList<Issue>();
//    ArrayList<Issue> issuesTmp = new ArrayList<Issue>();
//    Graphe graphe = new Graphe();
//    Point robot = new Point(xR,yR);
//    Sommet current = new Sommet(robot.x,robot.y);
    int i = 0;

    int[][] carte;
    Graphe graphe;
    
    
    public Cartographie(int[][] carte, Graphe graphe) {
    	this.carte = carte;
    	this.graphe = graphe;
    }
    
    
    @Override
    public void start(Stage stage){
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		canvas = new Canvas(W,H);
		

//		graphe.add(current);

		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.GREEN);
		gc.strokeRect(robot.x,robot.y,2,2);
		gc.setStroke(Color.RED);
	
		
		refresh();
		
		    	    
		stage.setTitle("Projection");
		stage.setScene(scene);
		stage.show();
    }

    
    
    
    private void refresh(){
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
	   
 
    public static void main(String[]args){
    	launch(args);
    }

}
