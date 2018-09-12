package programmes_fonctionnels;

import java.util.ArrayList;

import objets_cartographie.DonneesGraphiques;
import robot.Constantes;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;
import objets_communs.PointCapture;
import robot.Robot;
import tmp.Cartographe;
import tmp.Coor;

// EV3 SERVEUR + PC CLIENT
public class TestSonarScanArrayList {

	public static void main(String[] args) {
		
		long debut = System.currentTimeMillis();
		
		RegulatedMotor roueDroite = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor roueGauche = new EV3LargeRegulatedMotor(MotorPort.B);
		NXTUltrasonicSensor sonar = new NXTUltrasonicSensor(SensorPort.S1);
		Robot robot = new Robot(roueGauche, roueDroite, sonar, Constantes.PORT);
		
		System.out.println("port:" + robot.server.port);
	
		System.out.println("Press when ready...");
		Button.waitForAnyPress();

		/*** TEST : EN ATTENTE : Reconnaissance d'un environnement "ferme" ***/
		System.out.println("\nReady for PointCapture transmission...");	
		final int ANGLE = 360;
		final int VITESSE = 40;

		
		
		ArrayList<PointCapture> capture1 = robot.sonarScan(ANGLE, VITESSE);
//		robot.goTo(new Point(0, 40));
////		robot.pilot.travel(50);
//		ArrayList<PointCapture> capture2 = robot.sonarScan(ANGLE, VITESSE);
//		robot.goTo(new Point(30, 40));
////		robot.pilot.rotate(-90);
////		robot.pilot.travel(40);		
//		ArrayList<PointCapture> capture3 = robot.sonarScan(ANGLE, VITESSE);

		System.out.println(" --- CAPTURES TERMINEES");
		
//		ArrayList<PointCapture> captureTotale = fusion(capture1, capture2, capture3);

		ArrayList<Coor> listeCoor = convertToCoor(capture1);
//		ArrayList<Coor> listeCoor = convertToCoor(captureTotale);

		System.out.println(" --- FUSION TERMINEE");

		Cartographe cartographe = new Cartographe();
		
		System.out.println(" --- CARTOGRAPHE CREE");
		
		long debutScan = System.currentTimeMillis();
		cartographe.scan(listeCoor);
		System.out.println("Durée scan = " + (System.currentTimeMillis() - debutScan));
		
		System.out.println(" --- TRAITEMENT TERMINE");
		
		DonneesGraphiques data = new DonneesGraphiques(cartographe.getCarte(), cartographe.getGraphe());
		System.out.println(" --- PREPARATION TRANSMISSION DONNEES");
		robot.server.transmission_donnees(data);
		
/***		
		DonneesGraphiques data = new DonneesGraphiques(captureTotale);		
		System.out.println("data xMin = " + data.xMin + "\ndata yMin = " + data.yMin);
		robot.server.transmission_donnees(data);
***/
		System.out.println("Durée programme TestSonar = " + (System.currentTimeMillis() - debut));
		
	}

	public static ArrayList<PointCapture> fusion(ArrayList<PointCapture>... captures) {
		
		ArrayList<PointCapture> res = new ArrayList<PointCapture>();

		for(ArrayList<PointCapture> pc : captures)
			res.addAll(pc);
		
		return res;
	}

	public static ArrayList<Coor> convertToCoor(ArrayList<PointCapture> liste) {
		
		ArrayList<Coor> listeRes = new ArrayList<Coor>();
		
		for(PointCapture p : liste)
			listeRes.add(new Coor((int)p.getCartesianX(), (int)p.getCartesianY(), p.getId()));
	
		return listeRes;
	}
	
}
