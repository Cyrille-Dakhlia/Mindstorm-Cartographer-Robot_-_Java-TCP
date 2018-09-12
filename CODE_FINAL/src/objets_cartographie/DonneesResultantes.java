package objets_cartographie;

import java.io.Serializable;
import java.util.ArrayList;

import calculs.Sommet;


public class DonneesResultantes implements Serializable {

	private static final long serialVersionUID = 1L;

	private int[][] nouvelleCarte;
	private ArrayList<Sommet> nouveauxVoisins;

	
	public DonneesResultantes(int[][] carte, ArrayList<Sommet> voisins) {
		this.nouvelleCarte = carte;
		this.nouveauxVoisins = voisins;
	}
	
	public int[][] getNouvelleCarte() { return this.nouvelleCarte; }
	public ArrayList<Sommet> getNouveauxVoisins() { return this.nouveauxVoisins; }
		
}
