package objets_cartographie;

import java.io.Serializable;
import java.util.ArrayList;

import calculs.Coor;
import calculs.Sommet;


public class DonneesGraphiques implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Coor> liste;
	private int[][] carte;
	private Sommet sommetCourant;
	
	public DonneesGraphiques(ArrayList<Coor> liste) {
		this.liste = liste;
	}
	
	
	public ArrayList<Coor> getListe() { return this.liste; }
	public int[][] getCarte() { return this.carte; }
	public Sommet getSommetCourant() { return this.sommetCourant; }
	
	public void setCarte(int[][] c) { this.carte = c; }
	public void setSommetCourant(Sommet s) { this.sommetCourant = s; }
		
}
