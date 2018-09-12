package calculs;

import java.util.ArrayList;

public class Obstacle extends CC {

    public void init() {
	trier();
	extrem1 = get(0);
	extrem2 = get(size()-1);
    }
    
    /*public Obstacle(Obstacle o1, Obstacle o2, Coor e1, Coor e2) {
	addAll(o1);
	addAll(o2);
	this.extrem1 = e1;
	this.extrem2 = e2;
	}*/
    
    /*public boolean find(Obstacle o) {
	for (Coor c1 : this)
	    for (Coor c2 : o)
		if (c1.isNVoisin(c2,20))
		    return true;
	return false;
	}*/

    /*public Obstacle union(Obstacle o, int[][] matrice) {
	int newExtrem1 = 0, newExtrem2 = 0;
	Coor closest1, closest2;
	Coor otherExtremity1, otherExtremity2;
	Point finDroite;
	Point barycentre;
	int distMin = 10000;
	ZoneInc zone = new ZoneInc();
	if (find(o)) {
	    for (int i = 0; i < this.size() - 1; i++) {
		for (int j = 0; j < o.size() - 1; j++) {
		    if (this.get(i).distance(o.get(j)) < distMin) {
			distMin = this.get(i).distance(o.get(j));
			newExtrem1 = i;
			newExtrem2 = j;
			break;
		    }
		}
	    }
	    closest1 = this.supprimerPoints(newExtrem1,matrice);
	    closest2 = o.supprimerPoints(newExtrem2,matrice);
	    //Bresenham.tracerSegment(matrice,closest1.x,closest1.y,closest2.x,closest2.y,2);
	    otherExtremity1 = this.oppositeExtremity(closest1);
	    otherExtremity2 = o.oppositeExtremity(closest2);
	    finDroite = Bresenham.projete(matrice,Projection.xR,Projection.yR,otherExtremity2.x,otherExtremity2.y,Projection.W,Projection.H);
	    Bresenham.tracerSegment(matrice,otherExtremity2.x,otherExtremity2.y,finDroite.x,finDroite.y,-3);
	    zone.add(closest2);
	    zone.add(otherExtremity2);
	    zone.add(finDroite);
	    barycentre = zone.barycentre();
	    Remplissage.remplir(matrice,barycentre.x,barycentre.y,-1);
	    
	    return new Obstacle(this,o,otherExtremity1,otherExtremity2);
	} else {
	    return null;
	}
	
	}*/
	    
}
