package programmes_fonctionnels;

import objets_cartographie.Old_DonneesGraphiques;
import robot.Constantes;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import objets_communs.PointCapture;
import robot.Robot;

// EV3 SERVEUR + PC CLIENT
public class TestCommunicationSimple_reversed_tmp {

	public static void main(String[] args) {

		RegulatedMotor roueDroite = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor roueGauche = new EV3LargeRegulatedMotor(MotorPort.B);
		NXTUltrasonicSensor sonar = new NXTUltrasonicSensor(SensorPort.S1);
		Robot robot = new Robot(roueGauche, roueDroite, sonar, Constantes.PORT);
		
		System.out.println("port:" + robot.getServeur().getPort());
	
		
		/*** TEST FONCTIONNEL (rotation sans mouvement) ***/
//		System.out.println("\nReady for PointCapture transmission...");
//		PointCapture[] capture = robot.captureAngle_StructurePointCapture(360, 10);
//		DonneesGraphiques data = new DonneesGraphiques(capture);
//		System.out.println("data xMin = " + data.xMin + "\ndata yMin = " + data.yMin);		
//		robot.server.transmission_donnees(data);

		/*** TEST FONCTIONNEL : avec mouvement ***/
//		System.out.println("\nReady for PointCapture transmission...");	
//		PointCapture[] capture = robot.captureAngle_StructurePointCapture(360, 10);
//		robot.pilot.travel(80);
//		PointCapture[] capture2 = robot.captureAngle_StructurePointCapture(360, 10);
//		
//		PointCapture[] captureTotale = new PointCapture[capture.length + capture2.length];
//		for(int i=0 ; i<captureTotale.length ; i++) {
//			if(i < capture.length)
//				captureTotale[i] = capture[i];
//			else
//				captureTotale[i] = capture2[i - capture.length];
//		}
//		
//		DonneesGraphiques data = new DonneesGraphiques(captureTotale);
//		System.out.println("data xMin = " + data.xMin + "\ndata yMin = " + data.yMin);
//		robot.server.transmission_donnees(data);

		/*** TEST FONCTIONNEL : Reconnaissance d'un objet en en faisant le tour ***/
//		System.out.println("\nReady for PointCapture transmission...");	
//		final int ANGLE = 360;
//		final int VITESSE = 20;
//		PointCapture[] capture1 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//		
//		PointCapture[] captureAngle1 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.rotate(90);
//		robot.pilot.travel(35);
//		PointCapture[] capture2 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(35);
//		
//		PointCapture[] captureAngle2 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.rotate(90);
//		robot.pilot.travel(40);
//		PointCapture[] capture3 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//
//		PointCapture[] captureAngle3 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.rotate(90);
//		robot.pilot.travel(35);
//		PointCapture[] capture4 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(35);
//
//		PointCapture[] captureAngle4 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//
//		PointCapture[] captureTotale = fusion(capture1, captureAngle1, capture2, captureAngle2, capture3, captureAngle3, capture4, captureAngle4);
//		
//		DonneesGraphiques data = new DonneesGraphiques(captureTotale);
//		System.out.println("data xMin = " + data.xMin + "\ndata yMin = " + data.yMin);
//		robot.server.transmission_donnees(data);

		/*** TEST : EN ATTENTE : Reconnaissance d'un environnement "ferme" ***/
		System.out.println("\nReady for PointCapture transmission...");	
		final int ANGLE = 360;
		final int VITESSE = 40;

//		PointCapture[] capture1 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//		PointCapture[] capture2 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//		PointCapture[] capture3 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//		PointCapture[] capture4 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//		PointCapture[] capture5 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.rotate(90);
//		robot.pilot.travel(40);		
//		PointCapture[] capture6 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.rotate(90);
//		robot.pilot.travel(40);				
//		PointCapture[] capture7 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.rotate(-90);
//		robot.pilot.travel(40);
//		PointCapture[] capture8 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//		robot.pilot.travel(40);
//		PointCapture[] capture9 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
//
//		PointCapture[] captureTotale = fusion(capture1, capture2, capture3, capture4, capture5, capture6, capture7, capture8, capture9);

		PointCapture[] capture1 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
		robot.pilot.travel(50);
		PointCapture[] capture2 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);
		robot.pilot.rotate(-90);
		robot.pilot.travel(40);		
		PointCapture[] capture3 = robot.captureAngle_StructurePointCapture(ANGLE, VITESSE);

//		PointCapture[] captureTotale = fusion(capture1, capture2, capture3, capture4);
		PointCapture[] captureTotale = fusion(capture1, capture2, capture3);
		
		
		Old_DonneesGraphiques data = new Old_DonneesGraphiques(captureTotale);
		System.out.println("data xMin = " + data.xMin + "\ndata yMin = " + data.yMin);
		robot.server.transmission_donnees(data);

	}

	public static PointCapture[] fusion(PointCapture[]... captures) {
		int tailleTotale = 0;
		for(PointCapture[] pc : captures) {
			tailleTotale += pc.length;
		}
		PointCapture[] captureTotale = new PointCapture[tailleTotale];
		
		int cpt = 0;
		for(PointCapture[] pc : captures) {
			for(int i=0 ; i<pc.length ; i++) {
				captureTotale[cpt++] = pc[i];
			}
		}
		return captureTotale;
	}

	
}
