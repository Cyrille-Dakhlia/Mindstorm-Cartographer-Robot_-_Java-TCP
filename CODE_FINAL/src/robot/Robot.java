package robot;

//import lejos.robotics.navigation.DifferentialPilot;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

import objets_cartographie.DonneesGraphiques;
import objets_cartographie.DonneesResultantes;
import objets_communs.PointCapture;
import objets_communs.File;
import calculs.Coor;
import calculs.Sommet;


import communication_EV3_PC.EV3Client;
import communication_EV3_PC.EV3Serveur_tmp;

import lejos.hardware.Button;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.geometry.Point;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;


public class Robot {
	private MovePilot pilot;
	private Chassis chassis;
	private OdometryPoseProvider opp;
	private Navigator nav;
	
	private NXTUltrasonicSensor sonar;
	private SampleProvider distanceProvider;
	
//	private EV3Client client;
	private EV3Serveur_tmp server;
	
	private int[][] carte;
	private Sommet sommetCourant;

    public static final int H = 600;
    public static final int W = 800;

    private static final String UNITE = "cm";
	private static final double EPSILON = 0.85; //0.8; //0.7;//0.8; //0.55; //1.1; //0.8; //0.55; //0; //0.8; //0.7; //0.6; //0.59;
	private static final double ECART_ROUES = 11.20 - EPSILON; //10.61; //10.62; //11.2; //10.7; //10.75; //10.8; //10.6; //20; //11.2;
	private static final double DIAMETRE_ROUE = 4.2; //10;
	
	
	
	/******************** DEBUT : CONSTRUCTEURS  ********************/
	// Constructeur avec roues, avec sonar et avec connexion PC (EV3 Serveur, PC Client)
	public Robot(RegulatedMotor roueGauche, RegulatedMotor roueDroite, NXTUltrasonicSensor _sonar, int port) {
		Wheel leftWheel = WheeledChassis.modelWheel(roueGauche, DIAMETRE_ROUE).offset( -(ECART_ROUES/2) );
		Wheel rightWheel = WheeledChassis.modelWheel(roueDroite, DIAMETRE_ROUE).offset(ECART_ROUES/2);
		chassis = new WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);		
		pilot = new MovePilot(chassis);
		opp = new OdometryPoseProvider(pilot);
		nav = new Navigator(pilot, opp);
		sonar = _sonar;
		distanceProvider = sonar.getDistanceMode();
		
		server = new EV3Serveur_tmp(port);
		
		carte = new int[W][H];
		sommetCourant = new Sommet((int)this.opp.getPose().getX(), (int)this.opp.getPose().getY());
	}

	/******************** FIN : CONSTRUCTEURS  ********************/

	
	
	
	/******************** DEBUT : ACCESSEURS  ********************/
	public EV3Serveur_tmp getServeur() { return this.server; }
	public OdometryPoseProvider getOpp() { return this.opp; }
	public Navigator getNav() { return this.nav; }
	public int[][] getCarte() { return this.carte; }
	public Sommet getSommetCourant() { return this.sommetCourant; }
	
	public void setCarte(int[][] c) { this.carte = c; }
	public void setSommetCourant(Sommet s) { this.sommetCourant = s; }
	/******************** FIN : ACCESSEURS  ********************/

	
	
	
	/******************** DEBUT : FONCTIONS  ********************/

	public void algoCartographie() {
		final int ANGLE = 360;
		final int VITESSE_ANGULAIRE = 40;
		ArrayList<PointCapture> capture;
		DonneesGraphiques data;
		DonneesResultantes dataRes;
		Sommet prochaineDestination;
		
		while( ! this.sommetCourant.areAllVisited() ) {
			
			capture = this.sonarScan(ANGLE, VITESSE_ANGULAIRE); //on capture
			data = new DonneesGraphiques(convertToCoor(capture)); //on encapsule
			data.setCarte(this.carte);
			data.setSommetCourant(this.sommetCourant);
			this.server.transmission_donnees(data); //on envoie
			
			dataRes = this.server.reception_donnees(); //on receptionne les donnees traitees
			this.carte = dataRes.getNouvelleCarte(); //on met a jour la carte
			this.sommetCourant.addVoisins(dataRes.getNouveauxVoisins()); //on ajoute les nouveaux voisins du sommet courant
			this.sommetCourant.setVisite(true); //on marque le sommet courant comme visite
			
			prochaineDestination = this.sommetCourant.closestUnvisitedOrSelf();
			this.goTo(prochaineDestination);
			
		}
		System.out.println("[Robot.java]:void algoCartographie(): Fin de l'algorithme.");
	}

	
		
	public void goTo(Sommet s) {
		File Q = new File();
		Sommet next;
		LinkedList<Sommet> chemin = new LinkedList<Sommet>();
		Sommet step = s;
		while (step.getPred() != null) {
			chemin.addFirst(step);
			step = step.getPred();
		}
		for (int i=0; i<chemin.size()-1; i++) {
			Point p = new Point(chemin.get(i).getX(), chemin.get(i).getY());
			this.nav.rotateTo(this.opp.getPose().angleTo(p));
			this.pilot.travel(this.opp.getPose().distanceTo(p));

			while(this.nav.isMoving()) {}	
		}
		sommetCourant = s;
	}
	
	
		
	public ArrayList<PointCapture> sonarScan(int angle, int vitesse) {
		
		System.out.println("     DEBUT fonction : Robot.sonarScan(int angle, int vitesse)");
		
		this.pilot.setAngularSpeed(vitesse);
		float[] sample = new float[this.distanceProvider.sampleSize()]; // taille = 1 : permet de récupérer les valeurs du sonar
		ArrayList<PointCapture> pointCaptureList = new ArrayList<PointCapture>();

		//COM: On ajoute la position du robot
		pointCaptureList.add(
				new PointCapture(0,
								this.opp.getPose().getHeading(),
								this.opp.getPose().getX(),
								this.opp.getPose().getY(),
								true));
		
		//COM: On procede a la rotation du robot et a l'enregistrement des donnees
		this.pilot.rotate(angle, true); // immediate return = true
		while(this.pilot.isMoving()) {
			this.distanceProvider.fetchSample(sample, 0);
			//COM: on ajoute dans la liste un nouveau PointCapture avec (distance en cm (= "*100"), angle)

//			pointCaptureList.add(new PointCapture(sample[0]*100, this.opp.getPose().getHeading()));
			pointCaptureList.add(
					new PointCapture(sample[0]*100,
									this.opp.getPose().getHeading(),
									this.opp.getPose().getX(),
									this.opp.getPose().getY()));
		}
		
//		System.out.println("PointCaptureSize = " + pointCaptureList.size());
		Iterator it = pointCaptureList.iterator();
		while(it.hasNext())
			((PointCapture)it.next()).completeCartesianCoordinates();
		
		System.out.println("     FIN fonction : Robot.sonarScan(int angle, int vitesse)");
		
		return pointCaptureList;		
	}
	
	
	public static ArrayList<Coor> convertToCoor(ArrayList<PointCapture> liste) {
		
		ArrayList<Coor> listeRes = new ArrayList<Coor>();
		
		for(PointCapture p : liste)
			listeRes.add(new Coor((int)p.getCartesianX(), (int)p.getCartesianY(), p.getId()));
	
		return listeRes;
	}

	
	/******************** FIN : FONCTIONS  ********************/



}
