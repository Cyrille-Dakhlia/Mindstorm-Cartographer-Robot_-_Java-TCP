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
import calculs.Cartographe;
import calculs.Coor;

public class main {

	public static void main(String[] args) {
		
		long debut = System.currentTimeMillis();
		
		RegulatedMotor roueDroite = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor roueGauche = new EV3LargeRegulatedMotor(MotorPort.B);
		NXTUltrasonicSensor sonar = new NXTUltrasonicSensor(SensorPort.S1);
		Robot robot = new Robot(roueGauche, roueDroite, sonar, Constantes.PORT);
		
		System.out.println("port:" + robot.getServeur().getPort());
	
		System.out.println("Press when ready...");
		Button.waitForAnyPress();

		robot.algoCartographie();
				
	}
	
}
