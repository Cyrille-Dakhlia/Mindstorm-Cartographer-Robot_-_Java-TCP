package calculs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class CC extends ArrayList<Coor> {
    Coor extrem1, extrem2, newExtrem1, newExtrem2;
    CC mixWith1, mixWith2;
    boolean closed = false;
    
    public void trier() {
	Collections.sort(this, new Comparator<Coor>() {
		@Override
		public int compare(Coor c1, Coor c2){
		    return ((Integer)c1.rg).compareTo(c2.rg);
		}
	    });
    }

    public boolean isClosed() {
	return this.extrem1.isNVoisin(this.extrem2,20);
    }

    public Coor getExtrem1() {
	return extrem1;
    }

    public Coor getExtrem2() {
	return extrem2;
    }

    public boolean find(CC cc) {
	return extrem1.isNVoisin(cc.extrem1,20) || extrem1.isNVoisin(cc.extrem2,20) || extrem2.isNVoisin(cc.extrem1,20) || extrem2.isNVoisin(cc.extrem2,20);
    }

    public void unionPlus(ArrayList<Obstacle> oldComps, Coor coor, CC cc, int[][] m) {
        for (Obstacle o3 : oldComps) {
		if (coor.isNVoisin(o3.extrem1,20)) {
		    Bresenham.tracerSegment(m,coor.x,coor.y,o3.extrem1.x,o3.extrem1.y,2);
		    coor = o3.extrem1;
		    if (o3==cc) this.closed = true;
		    this.mixWith2 = o3;
		    this.newExtrem1 = o3.extrem2;
		} else if(coor.isNVoisin(o3.extrem2,20)) {
		    Bresenham.tracerSegment(m,coor.x,coor.y,o3.extrem2.x,o3.extrem2.y,2);
		    coor = o3.extrem2;
		    if (o3==cc) this.closed = true;
		    this.mixWith2 = o3;
		    this.newExtrem1 = o3.extrem1;
		}
	    }
    }
	    

    public void union(CC cc, int[][] matriceTmp, ArrayList<Obstacle> oldComps) {
	Coor oldExtrem;
	if (this.extrem1.isNVoisin(cc.extrem1,20)) {
	    oldExtrem = this.extrem1;
	    this.extrem1 = cc.extrem1;
	    Bresenham.tracerSegment(matriceTmp,oldExtrem.x,oldExtrem.y,this.extrem1.x,this.extrem1.y,2);
	    this.newExtrem1 = this.extrem2;
	    this.newExtrem2 = cc.extrem2;
	    this.mixWith1 = cc;
	    unionPlus(oldComps,this.extrem2,cc,matriceTmp);
	}else if(this.extrem1.isNVoisin(cc.extrem2,20)) {
	    oldExtrem = this.extrem1;
	    this.extrem1 = cc.extrem2;
	    Bresenham.tracerSegment(matriceTmp,oldExtrem.x,oldExtrem.y,this.extrem1.x,this.extrem1.y,2);
	    this.newExtrem1 = this.extrem2;
	    this.newExtrem2 = cc.extrem1;
	    this.mixWith1 = cc;
	    unionPlus(oldComps,this.extrem2,cc,matriceTmp);
	}else if(this.extrem2.isNVoisin(cc.extrem1,20)) {
	    oldExtrem = this.extrem2;
	    this.extrem2 = cc.extrem1;
	    Bresenham.tracerSegment(matriceTmp,oldExtrem.x,oldExtrem.y,this.extrem2.x,this.extrem2.y,2);
	    this.newExtrem1 = this.extrem1;
	    this.newExtrem2 = cc.extrem2;
	    this.mixWith1 = cc;
	    unionPlus(oldComps,this.extrem1,cc,matriceTmp);
	}else if(this.extrem2.isNVoisin(cc.extrem2,20)) {
	    oldExtrem = this.extrem2;
	    this.extrem2 = cc.extrem2;
	    Bresenham.tracerSegment(matriceTmp,oldExtrem.x,oldExtrem.y,this.extrem2.x,this.extrem2.y,2);
	    this.newExtrem1 = this.extrem1;
	    this.newExtrem2 = cc.extrem1;
	    this.mixWith1 = cc;
	    unionPlus(oldComps,this.extrem1,cc,matriceTmp);
	}
    }

    public Coor getByCoord(int x, int y) {
	Coor c = null;
	for (int i=0; i<size(); i++) {
	    if (get(i).x == x && get(i).y == y) {
		c = get(i);
	    }
	}
	return c;
    }
	
    /*public int closestExtremity(Coor c) {
	int toExtrem1 = c.distance(this.extrem1);
	int toExtrem2 = c.distance(this.extrem2);
        if (toExtrem1 < toExtrem2) return 0;
	else return size()-1;
	}*/

    /*public Coor getCoorByRg(int rg) {
	for (Coor c : this) {
	    if (c.rg == rg) {
		return c;
	    }
	}
	return null;
    }*/

    /* Supprimer les points entre le point d'indice ind 
       et l'extrémité la plus proche */
    
    /*public Coor supprimerPoints(int ind, int[][] matrice) {
	int closest = closestExtremity(get(ind));
	if (closest == 0) {
	    for (int i = ind; i > 0; i--) {
		Bresenham.tracerSegment(matrice,get(i).x,get(i).y,get(i-1).x,get(i-1).y,0);
	    }
	    removeRange(0,ind);
	    this.extrem1 = get(ind);
	    return this.extrem1;
	} else {
	    for (int i = ind; i < size() - 1; i++) {
		Bresenham.tracerSegment(matrice,get(i).x,get(i).y,get(i+1).x,get(i+1).y,0);
	    }
	    removeRange(ind+1,size());
	    this.extrem2 = get(ind);
	    return this.extrem2;
	}
	}*/

    public Coor oppositeExtremity(Coor c) {
	if (c.equals(this.extrem1)) return this.extrem2;
	else return this.extrem1;
    }
    
    /*public void setExtrems() {
	int distMax = 0;
	for (Coor c1 : this) {
	    for (Coor c2 : this) {
		if (c1.distance(c2) > distMax) {
		    distMax = c1.distance(c2);
		    this.extrem1 = c1;
		    this.extrem2 = c2;
		}
		
	    }
	}
	}*/
}
