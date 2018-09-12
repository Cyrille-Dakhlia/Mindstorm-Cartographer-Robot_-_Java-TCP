package programmes_fonctionnels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import calculs.Sommet;
import robot.Constantes;
import robot.Robot;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;
import lejos.robotics.navigation.Pose;


/*
 * TEST : Navigation du robot tout au long d'un chemin parsemé de Waypoint fixés
 * OUTIL(S) : Classes Navigator, MovePilot, OdometryPoseProvider
 * RESULTAT : En attente...
 */
public class TestFonctionsNavigation {


	public static void main(String[] args) {
	
		int cptPose = 1, cptTest = 1;
		
		RegulatedMotor moteurDroite = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.B);
		NXTUltrasonicSensor sonar = new NXTUltrasonicSensor(SensorPort.S1);
		Robot robot = new Robot(moteurGauche, moteurDroite, sonar, Constantes.PORT);
		
		Pose pose;
		
		ArrayList<Point> liste = new ArrayList<Point>();
		liste.add(new Point(40, 20));
		liste.add(new Point(60, -30));
		liste.add(new Point(0, 0));
		liste.add(new Point(60, 20));
		liste.add(new Point(20, 0));
		liste.add(new Point(30, 0));
		liste.add(new Point(0, 0));

		Iterator it = liste.iterator();

		Point pointDestination = new Point(50, 0); 		// POINT DESTINATION

		System.out.println("\n\n\n\n\n");

		
		// Code originel = utiliser addWayPoint(float float) et followPath() pour chaque point
		// LEFT : Test 1 = utiliser goTo(float, float, -heading-) plutot que followPath()
		// UP : Test 2 = ajouter tous les points et followPath() d'un coup
		// RIGHT : Test 3 = ajouter tous les points, faire singleStep(true), et followPath()
		

		System.out.println("Veuillez choisir le test desire :\n" +
				"   Bouton GAUCHE : Test 1 = utiliser goTo(float, float, -heading-) plutot que followPath()\n" +
				"   Bouton HAUT : Test 2 = ajouter tous les points et followPath() d'un coup\n" +
				"   Bouton DROIT : Test 3 = ajouter tous les points, faire singleStep(true), et followPath()\n" +
				"   Bouton BAS : Test 4 = deplacements manuels avec pilot.travel et nav.rotateTo" +
				"   default : Code originel = utiliser addWayPoint(float float) et followPath() pour chaque point");
		
		
		int codeButton = Button.waitForAnyPress();

		int EPSILON = 10;
		
		switch(codeButton) {
		
		case Button.ID_LEFT:
			/*** TEST 1 ***/
				
			System.out.println("   -- TEST 1 --");
				
			while(it.hasNext()) {
				Point p = (Point) it.next();
				System.out.println("     --- POSE " + cptPose + " ---");
				
				System.out.println("Ready to : ---> goTo("+p.getX()+", "+p.getY()+")");
				//Button.waitForAnyPress();
				while( !closeTo(robot.getOpp().getPose().getX(), robot.getOpp().getPose().getY(),
						(float)p.getX(), (float)p.getY(), EPSILON)) {
//				while(robot.getOpp().getPose().getX() != p.getX()
//						|| robot.getOpp().getPose().getY() != p.getY()) {
					robot.getNav().goTo( (float) p.getX(), (float) p.getY());
					while(robot.getNav().isMoving()) {}
				}
				pose = robot.getOpp().getPose();		
				System.out.println("POSE " + cptPose++ + ":\n" + pose);
				System.out.println();
			}
	
			break;
				
			
			
		case Button.ID_UP:
			/*** TEST 2 ***/
			
			System.out.println("   -- TEST 2 --");
			
			while(it.hasNext()) {
				Point p = (Point) it.next();
				robot.getNav().addWaypoint((float)p.getX(), (float)p.getY());			
			}
			
			robot.getNav().followPath();			
			while(robot.getNav().isMoving()) {}
				
			pose = robot.getOpp().getPose();		
			System.out.println("POSE " + cptPose++ + ":\n" + pose);
			System.out.println();
				
			break;
			
			
				
		case Button.ID_RIGHT:
			/*** TEST 3 ***/
				
			System.out.println("   -- TEST 3 --");
	
			robot.getNav().singleStep(true);

			while(it.hasNext()) {
				Point p = (Point) it.next();
				robot.getNav().addWaypoint((float)p.getX(), (float)p.getY());			
			}
						
			robot.getNav().followPath();			
			while(robot.getNav().isMoving()) {}
				
			pose = robot.getOpp().getPose();		
			System.out.println("POSE " + cptPose++ + ":\n" + pose);
			System.out.println();
	
			break;
				
		
			
		case Button.ID_DOWN:
			/*** TEST 4 ***/
			System.out.println("   -- TEST 4 --");

			while(it.hasNext()) {
				Point p = (Point) it.next();				

				robot.goTo(new Sommet((int)p.getX(), (int)p.getY()));
//				robot.getNav().rotateTo(robot.getOpp().getPose().angleTo(p));
//				//float distance = pose.distanceTo(p);
//				//float angle = pose.angleTo(p);
//				robot.pilot.travel(robot.getOpp().getPose().distanceTo(p));
//				
//				//robot.getNav().rotateTo(angle + pose.getHeading());
//				//robot.pilot.travel(distance);
//
//				while(robot.getNav().isMoving()) {}
				
				pose = robot.getOpp().getPose();
				System.out.println("POSE " + cptPose++ + ":\n" + pose);
				System.out.println();				
			}
			robot.getNav().rotateTo(0);
			
//			robot.pilot.travel(30); //	liste.add(new Point(30, 0));
//			robot.pilot.rotate(40); //	liste.add(new Point(60, 20));
//			robot.pilot.travel(30);
//			robot.pilot.rotate(130); //  liste.add(new Point(40, 20));
//			robot.pilot.travel(20);
//			robot.pilot.rotate(45); //  liste.add(new Point(20, 0));
//			robot.pilot.travel(20);
//			robot.pilot.rotate(-45); //  liste.add(new Point(0, 0));
//			robot.pilot.travel(20);
//			robot.pilot.rotate(160); //  liste.add(new Point(60, -30));
//			robot.pilot.travel(60);
//			robot.pilot.rotate(-160); //  liste.add(new Point(0, 0));
//			robot.pilot.travel(60);

			break;
			
			
						
		default:
			/*** CODE ORIGINEL ***/
	
			System.out.println("   -- CODE ORIGINEL --");
			
			while(it.hasNext()) {
				Point p = (Point) it.next();
				System.out.println("     --- POSE " + cptPose + " ---");
				System.out.println("Ready to : ---> addWayPoint("+p.getX()+", "+p.getY()+")");
				Button.waitForAnyPress();
				robot.getNav().addWaypoint((float)p.getX(), (float)p.getY());
				System.out.println("Ready to : ---> followPath");
				Button.waitForAnyPress();
				robot.getNav().followPath();
				while(robot.getNav().isMoving()) {}
				pose = robot.getOpp().getPose();		
				System.out.println("POSE " + cptPose++ + ":\n" + pose);
				System.out.println();
			}
			
			break;
			
		}
		
	robot.pilot.stop();
	}
	
	
	public static boolean closeTo(float xRobot, float yRobot, float d, float e, int epsilon) {
		return (xRobot >= (d - epsilon) && xRobot <= (d + epsilon))
					&& (yRobot >= (e - epsilon) && yRobot <= (e + epsilon));
	}
}			