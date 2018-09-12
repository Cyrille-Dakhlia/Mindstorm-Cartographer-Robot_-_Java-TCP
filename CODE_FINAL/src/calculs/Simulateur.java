package calculs;

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

public class Simulateur extends Application {
    public static final int H = 600;
    public static final int W = 800;

    public static final int PORTEE = 150;

    public static int xR = 400;
    public static int yR = 300;

    int[][] carte = new int[W][H];
    int[][] matriceTmp = new int[W][H];
    GraphicsContext gc;
    Canvas canvas;
    WritableImage wi;
    ArrayList<Coor> list = new ArrayList<Coor>();
    ArrayList<Obstacle> comps = new ArrayList<Obstacle>();
    ArrayList<Obstacle> oldComps = new ArrayList<Obstacle>();
    ArrayList<Obstacle> compsTmp = new ArrayList<Obstacle>();
    ArrayList<Issue> issues = new ArrayList<Issue>();
    ArrayList<Issue> issuesTmp = new ArrayList<Issue>();
    Graphe graphe = new Graphe();
    Point robot = new Point(xR,yR);
    Sommet current = new Sommet(robot.x,robot.y);
    int i = 0;
    
    @Override
    public void start(Stage stage){
	BorderPane root = new BorderPane();
	Scene scene = new Scene(root);
	canvas = new Canvas(W,H);
	Button scan = new Button("scan");
	ToggleButton changeRobot = new ToggleButton("change Robot");
	Button showExtrems = new Button("show extrems");
	Button close = new Button("close");
	ToolBar toolBar = new ToolBar(scan,changeRobot,showExtrems,close);
	graphe.add(current);
	root.setTop(toolBar);
	root.setCenter(canvas);
	gc = canvas.getGraphicsContext2D();

	gc.setStroke(Color.GREEN);
	
	gc.strokeRect(robot.x,robot.y,2,2);
	
	gc.setStroke(Color.RED);


	EventHandler<MouseEvent> changePosRobot = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
		    xR = (int)e.getX();
		    yR = (int)e.getY();
		    robot.x = xR;
		    robot.y = yR;
		    current = new Sommet(robot.x,robot.y);
		    graphe.add(current);
		    //current.setX(robot.x);
		    //current.setY(robot.y);
		    gc.setStroke(Color.GREEN);
		    gc.strokeRect(robot.x,robot.y,2,2);
		}
	    };

	EventHandler<MouseEvent> buildObstacle = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e){
		    gc.setStroke(Color.RED);
		    gc.strokeRect(e.getX(),e.getY(),1,1);
		    list.add(new Coor((int)e.getX(),(int)e.getY(),i++));
		    matriceTmp[(int)e.getX()][(int)e.getY()] = 2;
		}
	    };

	showExtrems.setOnAction(e -> {
		gc.setStroke(Color.BLUE);
		/*for (Obstacle o : comps) {
		    gc.strokeRect(o.extrem1.x,o.extrem1.y,2,2);
		    gc.strokeRect(o.extrem2.x,o.extrem2.y,2,2);
		    }*/
		/*for (Issue i : issuesTmp) {
		    /*gc.setStroke(Color.GREEN);
		    gc.strokeRect(i.extrem1.x,i.extrem1.y,2,2);
		    gc.setStroke(Color.YELLOW);
		    gc.strokeRect(i.extrem2.x,i.extrem2.y,2,2);
		    gc.setStroke(Color.BLUE);*/
		    /*for (Point p : i.exits) {
			System.out.println("exits.size = " + i.exits.size());
			gc.strokeRect(p.x,p.y,2,2);
			}*/
		    /*for (Sommet s : current.voisins) {
		      gc.strokeRect(s.x,s.y,2,2);
		      }*/
		    //}
		    //gc.strokeRect(i.oe1.x,i.oe1.y,2,2);
		    //gc.strokeRect(i.oe2.x,i.oe2.y,2,2);
		    //}
		for (Sommet s : graphe) {
		    if (s.issue != null) {
			gc.strokeRect(s.issue.oe1.x,s.issue.oe1.y,2,2);
			gc.strokeRect(s.issue.oe2.x,s.issue.oe2.y,2,2);
		    }
		}
	    });

	changeRobot.selectedProperty().addListener((observer,oldValue,newValue) -> {
		if (newValue) {
		    canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, buildObstacle);
		    canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, changePosRobot);
		} else {
		    canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, buildObstacle);
		    canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, changePosRobot);
		}
	    });

	close.setOnAction(e -> {

		createObstacle(carte);
		
		for (Obstacle obs : oldComps) {
		    compsTmp.get(0).union(obs,carte,oldComps);
		}
		
		fillZone(compsTmp.get(0),carte,false);

		unify(carte);

		fusion();
		
		refresh();

		System.out.println("comps.size() = " + comps.size());
	    });	
		
	
	canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, buildObstacle);

	scan.setOnAction(e -> {
		issuesTmp.removeAll(issuesTmp);		
		createObstacle(matriceTmp);
		
		for (Obstacle o1 : compsTmp) {
		    for (Obstacle o2 : oldComps) {
			o1.union(o2,matriceTmp,oldComps);
		    }
		}
		
		for (Obstacle obs : compsTmp) {
		    fillZone(obs,matriceTmp,true);
		}
		
		createIssues();
		
		fusion();

		Remplissage.remplir(matriceTmp,robot.x,robot.y,1,true);
		
		unify(matriceTmp);
		
		for (int i=0; i<W; i++) {
		    for (int j=0; j<H; j++) {
			carte[i][j] = Math.max(matriceTmp[i][j],carte[i][j]);
		    }
		}

		
		ArrayList<Coor> coordsToRemove = new ArrayList<Coor>();
		ArrayList<Issue> issuesToRemove = new ArrayList<Issue>();
		for (Issue iss : issues) {
		    for (Coor c : iss) {
			if (Math.max(matriceTmp[c.x][c.y],carte[c.x][c.y]) == 1) {
			    coordsToRemove.add(c);
			}
		    }
		    iss.removeAll(coordsToRemove);
		    if (iss.size()==0) issuesToRemove.add(iss);
		    coordsToRemove.removeAll(coordsToRemove);
		}
		issues.removeAll(issuesToRemove);

		for (Obstacle obs : comps) {
		    int xb = 0, yb = 0;
		    if (obs.closed) {
			System.out.println("closed");
			for (Coor c : obs) {
			    xb += c.x;
			    yb += c.y;
			}
			xb = (int)(xb/(double)obs.size());
			yb = (int)(yb/(double)obs.size());
			System.out.println("ici");
			Remplissage.remplir(carte,xb,yb,2,true);
			gc.setStroke(Color.BLUE);
			gc.strokeRect(xb,yb,2,2);
		    }
		}
		for (Issue iss : issuesTmp) {
		    for (Coor c : iss) {
			for (Obstacle obs : comps) {
			    Point ext1 = Bresenham.projete(carte,robot.x,robot.y,obs.extrem1.x,obs.extrem1.y,W,H,1);
			    Point ext2 = Bresenham.projete(carte,robot.x,robot.y,obs.extrem2.x,obs.extrem2.y,W,H,1);
			    if (iss.extrem1 == null) {
				if (c.isNVoisin(ext1,5)) {
				    iss.extrem1 = c;
				    iss.oe1 = obs.extrem1;
				} else if (c.isNVoisin(ext2,5)) {
				    iss.extrem1 = c;
				    iss.oe1 = obs.extrem2;
				}
			    } else {
				if (c.isNVoisin(ext1,5)) {
				    iss.extrem2 = c;
				    iss.oe2 = obs.extrem1;
				} else if (c.isNVoisin(ext2,5)) {
				    iss.extrem2 = c;
				    iss.oe2 = obs.extrem2;
				}
			    }
			}
		    }
		}

		for (Issue iss : issuesTmp) {
		    editIssue(iss);
		}
		

		for (Issue iss : issuesTmp) {
		    for (Point p : iss.exits) {
		        Point next = Bresenham.projete(matriceTmp,robot.x,robot.y,p.x,p.y,W,H,2);
			Sommet s = new Sommet(next.x,next.y);
			s.issue = iss;
			current.voisins.add(s);
			s.voisins.add(current);
			graphe.add(s);
		    }
		}
		
		refresh();
		
		for (int i=0; i<W; i++) {
		    for (int j=0; j<H; j++) {
			matriceTmp[i][j] = 0;
		    }
		}
		
		compsTmp.removeAll(compsTmp);
		oldComps.removeAll(oldComps);
		
		list.removeAll(list);
		//System.out.println("comps.size() = " + comps.size());
		System.out.println("issues.size() = " + issues.size());
		
		for (Issue issue : issues) {
		    System.out.println("issue size = " + issue.size());
		}
	    });
	    
    
	stage.setTitle("Simulateur");
	stage.setScene(scene);
	stage.show();
    }

    private void fusion() {
	for (Obstacle obs : compsTmp) {
	    if (obs.mixWith1 != null) {
		Obstacle o = new Obstacle();
		o.addAll(obs);
		o.addAll(obs.mixWith1);
		if (obs.mixWith2 != null) {
		    o.addAll(obs.mixWith2);
		    comps.remove(obs.mixWith2);
		}
		o.extrem1 = obs.newExtrem1;
		o.extrem2 = obs.newExtrem2;
		o.closed = obs.closed;
		comps.remove(obs);
		comps.remove(obs.mixWith1);
		comps.add(o);
	    }
	}
    }

    private void unify(int[][] m) {
	for (int i=0; i<W; i++) {
	    for (int j=0; j<H; j++) {
		if (m[i][j] == -1 || m[i][j] == -2 || m[i][j] == -3) {
		    m[i][j] = 0;
		}
	    }
	}
    }
	
    private void createObstacle(int[][] m) {
    
	/* -list : mesure courante
	   -on créé les composantes connexes
	*/
		
	for (Coor c1 : list) {
	    for (Coor c2 : list) {
		if (!c1.equals(c2)) {
		    if (c1.isNVoisin(c2,20)) {
			c1.union(c2);
		    }
		}
	    }
	}
		
	/* on créé donc les obstacles (liste comps) */
		
	oldComps.addAll(comps);
	for (Coor c : list) {
	    if (c.isRacine() && c.taille > 5) {
		Obstacle obstacle = new Obstacle();
		for (Coor ci : list) {
		    if (ci.pere == c) {
			obstacle.add(ci);
		    }
		}
		obstacle.init();
		comps.add(obstacle);
		compsTmp.add(obstacle);
	    }
	}

	/* on relie les points des obstacles */
		
	for (Obstacle obs : compsTmp) {
	    for (int i=0; i<obs.size()-1; i++) {
		Bresenham.tracerSegment(m,obs.get(i).x,obs.get(i).y,obs.get(i+1).x,obs.get(i+1).y,2);
	    }
	}
    }

    private void createIssues() {
	ArrayList<Coor> cercle = Bresenham.drawCircle(matriceTmp,robot.x,robot.y,PORTEE,W,H);

	for (int i=0; i<2; i++) {
	    for (Coor c1 : cercle) {
		for (Coor c2 : cercle) {
		    if (!c1.equals(c2)) {
			if (c1.isNVoisin(c2,1)) {
			    c1.union(c2);
			}
		    }
		}
	    }
	}
		
	for (Coor c : cercle) {
	    if (c.isRacine() && c.taille > 5) {
		Issue issue = new Issue();
		for (Coor ci : cercle) {
		    if (ci.pere == c) {
			issue.add(ci);
		    }
		}
		issues.add(issue);
		issuesTmp.add(issue);
	    }
	}
    }

    private void editIssue(Issue issue) {
	issue.init(robot);
	int s = issue.size();
	double e = issue.ecart;
	if (s < 400) {
	    createExits(issue,2,e);
	} else if (s >= 400 && s < 600) {
	    createExits(issue,3,e);
	} else if (s >= 600 && s < 848) {
	    createExits(issue,4,e);
	} else {
	    issue.exits.add(issue.extrem1);
	    createExits(issue,5,e);
	}

    }

    private void createExits(Issue iss, int div, double e) {
	double pas = e/div;
	double d = pas;
	double eps = 2;
	int nb = 0;
        while (Math.abs(d) < e) {
	    for (Coor coor : iss) {
		double ang = robot.angle(iss.extrem1,coor)*180.0/Math.PI;
		if (ang >= d-eps && ang <= d+eps) {
		    iss.exits.add(coor);
		    nb++;
		    break;
		}
	    }
	    d += pas;
	}
	ArrayList<Point> toRemove = new ArrayList<Point>();
	for (Point p : iss.exits) {
	    if (p.isNVoisin(iss.extrem1,30) || p.isNVoisin(iss.extrem2,30)) {
		toRemove.add(p);
	    }
	}
	iss.exits.removeAll(toRemove);
    }
    
    private boolean closeToExtrem(Issue iss) {
	for (Point p : iss.exits) 
	    if (p.isNVoisin(iss.extrem1,30) || p.isNVoisin(iss.extrem2,30))
		return true;
	return false;
    }
    
    private void fillZone(Obstacle obs, int[][] m, boolean b) {
	Coor extrem1, extrem2;
	Point barycentre;
	Point proj1, proj2;
	ZoneInc zone;
	zone = new ZoneInc();
	extrem1 = obs.getExtrem1();
	extrem2 = obs.getExtrem2();
	
	zone.add(extrem1);
	proj1 = Bresenham.projete(m,robot.x,robot.y,extrem1.x,extrem1.y,W,H,0);
	Bresenham.tracerSegment(m,extrem1.x,extrem1.y,proj1.x,proj1.y,-3);
	zone.add(proj1);
	
	proj2 = Bresenham.projete(m,robot.x,robot.y,extrem2.x,extrem2.y,W,H,0);
	zone.add(proj2);
	zone.add(extrem2);
	Bresenham.tracerSegment(m,extrem2.x,extrem2.y,proj2.x,proj2.y,-3);
	
	barycentre = zone.barycentre();
	
	Remplissage.remplir(m,barycentre.x,barycentre.y,-1,b);
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
	for (Sommet s : graphe) {
	    drawSommet(s);
	    for (Sommet v : s.voisins) {
		drawSommet(v);
		drawLine(s,v);
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
	   
 
    public static void main(String[]args){
	launch(args);
    }
}
