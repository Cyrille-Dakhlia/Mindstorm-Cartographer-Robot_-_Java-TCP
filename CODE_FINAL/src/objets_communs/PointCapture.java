package objets_communs;

import java.io.Serializable;

public class PointCapture implements Serializable {
	
	static int idClassement = 0;
	
	private static final long serialVersionUID = 1L;
	private float cartesian_x;
	private float cartesian_y;
	private float polar_distance;
	private float polar_angle;
	private int id;
	
	private float xRobot;
	private float yRobot;
	private boolean estCentreDuRobot;
	
	/****** DEBUT : CONSTRUCTEURS  ******/
//	public PointCapture(float x, float y) {
//		this.cartesian_x = x;
//		this.cartesian_y = y;
//		this.id = idClassement++;
//	}
	
	public PointCapture(float distance, float angle) {
		this.polar_distance = distance; // en cm
		this.polar_angle = angle;
		this.id = idClassement++;

		this.xRobot = 0;
		this.yRobot = 0;
		this.estCentreDuRobot = false;
	}

	public PointCapture(float distance, float angle, float xRobot, float yRobot) {
		this.polar_distance = distance; // en cm
		this.polar_angle = angle;
		this.id = idClassement++;

		this.xRobot = xRobot;
		this.yRobot = yRobot;
		this.estCentreDuRobot = false;
	}
	
	public PointCapture(float distance, float angle, float xRobot, float yRobot, boolean estCentre) {
		this.polar_distance = distance; // en cm
		this.polar_angle = angle;
		this.id = idClassement++;

		this.xRobot = xRobot;
		this.yRobot = yRobot;
		this.estCentreDuRobot = estCentre;
	}

	/****** FIN : CONSTRUCTEURS  ******/
	
	
	
	/****** DEBUT : ACCESSEURS  ******/
	public float getCartesianX() { return this.cartesian_x; }
	public float getCartesianY() { return this.cartesian_y; }
	public float getPolarDistance() { return this.polar_distance; }
	public float getPolarAngle() { return this.polar_angle; }
	public int getId() { return this.id; }
	public float getXRobot() { return this.xRobot; }
	public float getYRobot() { return this.yRobot; }
	public boolean getEstCentreDuRobot() { return this.estCentreDuRobot; }
	
	public void setCartesianX(float x) { this.cartesian_x = x; }
	public void setCartesianY(float y) { this.cartesian_y = y; }
	public void setPolarDistance(float d) { this.polar_distance = d; }
	public void setPolarAngle(float a) { this.polar_angle = a; }
	public void setId(int i) { this.id = i; }
	public void setXRobot(float x) { this.xRobot = x; }
	public void setYRobot(float y) { this.yRobot = y; }
	public void setEstCentreDuRobot(boolean bool) { this.estCentreDuRobot = bool; }
	/****** FIN : ACCESSEURS  ******/


	/****** DEBUT : FONCTIONS SUPPLEMENTAIRES  ******/
	/*
	 * RETURN : Retourne le point avec ses coordonnées cartésiennes complétées
	 */
	public void completeCartesianCoordinates() {
		// Conversion en radian par : rad = deg * pi / 180
		this.cartesian_x = (float)(this.xRobot + this.polar_distance * Math.cos(this.polar_angle * Math.PI / 180)); // On n'a pas besoin de corriger le signe car cos(theta) = cos(-theta)  (bien vu momo)
		this.cartesian_y = (float)(this.yRobot + this.polar_distance * Math.sin(this.polar_angle * Math.PI / 180)); // * -1); // On corrige le signe car le robot relève son orientation (heading) dans le sens anti-trigonométrique
	}


	
	/*
	 * FONCTION : Convertit un tableau de points en coordonnées polaires en un tableau de points en coordonnées cartésiennes
	 * ARGS : Tableau de float bidimensionnel contenant les coordonnées polaires des points avec les distances dans la 1ère ligne et les angles dans la 2nde
	 * RETURN : tableau de double bidimensionnel contenant dans la 1ère ligne les coordonnées en abscisse (cm) et dans la 2nde les coordonnées en ordonnée (cm)
	 */
	public static void completeCartesianCoordinatesTab(PointCapture[] capturePolaire) {
		for(int i=0 ; i<capturePolaire.length ; i++)
			capturePolaire[i].completeCartesianCoordinates();	
	}
	
	
	/*
	 * FONCTION : Convertit un tableau de points en coordonnées polaires en un tableau de points en coordonnées cartésiennes
	 * ARGS : Tableau de float bidimensionnel contenant les coordonnées polaires des points avec les distances dans la 1ère ligne et les angles dans la 2nde
	 * RETURN : tableau de double bidimensionnel contenant dans la 1ère ligne les coordonnées en abscisse (cm) et dans la 2nde les coordonnées en ordonnée (cm)
	 */
	public static double[][] getCartesianFromPolarCoordinates(float[][] capturePolaire) {
		double[][] captureCartesienne = new double[capturePolaire.length][capturePolaire[0].length];
		for(int i=0 ; i<capturePolaire[0].length ; i++) {
			captureCartesienne[0][i] = capturePolaire[0][i] * Math.cos(capturePolaire[1][i]); // Calcul de la coordonnée en x
			captureCartesienne[1][i] = capturePolaire[0][i] * Math.sin(capturePolaire[1][i]); // Calcul de la coordonnée en y
		}
		return captureCartesienne;
	}
	/****** DEBUT : FONCTIONS SUPPLEMENTAIRES  ******/

}
