package programmes_fonctionnels;

import robot.Constantes;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;

public class TestCalibrageEpsilon {

	public static void main(String[] args) {
		RegulatedMotor roueDroite = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor roueGauche = new EV3LargeRegulatedMotor(MotorPort.B);
		NXTUltrasonicSensor sonar = new NXTUltrasonicSensor(SensorPort.S1);
		Robot robot = new Robot(roueGauche, roueDroite, sonar, Constantes.PORT);

		System.out.println(robot.getOpp().getPose());
		
		
		final int ANGLE = 360;
		final int VITESSE = 40;

		/*
		for(int i=0 ; i<2 ; i++) {
			robot.pilot.travel(5);
			robot.pilot.setAngularSpeed(VITESSE);
	
			robot.pilot.rotate(ANGLE);
			System.out.println(robot.opp.getPose());
			
			robot.pilot.travel(5);
			
			robot.pilot.rotate(ANGLE);
			System.out.println(robot.opp.getPose());

			robot.pilot.travel(5);
			robot.pilot.rotate(90);
		}
		*/

		
		robot.pilot.setAngularSpeed(VITESSE);
		robot.pilot.rotate(ANGLE);
/*
		Point p1 = new Point(5, 0); //0
		Point p2 = new Point(5, 5); //45
		Point p3 = new Point(0, 5); //90
		Point p4 = new Point(5, 6); //50-60
		System.out.println("angle = " + robot.opp.getPose().angleTo(p1));
		System.out.println("angle = " + robot.opp.getPose().angleTo(p2));
		System.out.println("angle = " + robot.opp.getPose().angleTo(p3));
		System.out.println("angle = " + robot.opp.getPose().angleTo(p4));

		robot.nav.rotateTo(robot.opp.getPose().angleTo(p1));
		robot.nav.rotateTo(robot.opp.getPose().angleTo(p2));
		robot.nav.rotateTo(robot.opp.getPose().angleTo(p3));
		robot.nav.rotateTo(robot.opp.getPose().angleTo(p4));
		robot.nav.rotateTo(0);
*/
	}

}
