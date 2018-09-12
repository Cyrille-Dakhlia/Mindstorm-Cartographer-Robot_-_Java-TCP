package calculs;

import objets_communs.File;

import java.util.ArrayList;

import calculs.Point;
import calculs.Sommet;

import java.io.Serializable;

public class Sommet implements Serializable {
	private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    boolean isVisite = false;
    boolean BFScheck = false;
    Issue issue;
    ArrayList<Sommet> voisins = new ArrayList<Sommet>();
    Sommet pred = null;
	
    public Sommet(int x, int y){
	this.x = x;
	this.y = y;
    }

    public int getX() {
	return x;
    }
    
    public Sommet getPred() {
    	return pred;
    }

    public boolean getIsVisite() {
    	return this.isVisite;
    }
    
    public boolean BFScheck() {
    	return BFScheck;
    }

    public ArrayList<Sommet> getVoisins() {
    	return this.voisins;
    }
    
    public void setPred(Sommet pred) {
    	this.pred = pred;
    }
    
    public void setVisite(boolean isVisite) {
	this.isVisite = isVisite;
    }
    
    public void setBFScheck(boolean BFScheck) {
    	this.BFScheck = BFScheck;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }
	
	public void addVoisins(ArrayList<Sommet> arrayList) {
		for(Sommet p : arrayList)
			this.voisins.add(new Sommet(p.x, p.y));
	}

	public boolean areAllVisited() {
		boolean res = true;
		Sommet next;
		File Q = new File();
		Q.push(this);
		while(!Q.isEmpty()) {
		    next = Q.pop();
		    if (!next.getIsVisite()) {
		    	res = false;
		    	break;
		    }
		    next.setBFScheck(true);
		    for (Sommet v : next.getVoisins()) {
		    	if (!v.BFScheck()) {
		    		Q.push(v);
		    		v.setBFScheck(true);
		    	}
		    }
		}
		Q.push(this);
		while(!Q.isEmpty()) {
		    next = Q.pop();
		    next.setBFScheck(false);
		    for (Sommet v : next.getVoisins()) {
		    	if (v.BFScheck()) {
		    		Q.push(v);
		    		v.setBFScheck(false);
		    	}
		    }
		}
		return res;
	}
	
	public Sommet closestUnvisitedOrSelf() {
		Sommet next;
		Sommet destination = null;
		File Q = new File();
		Q.push(this);
		while(!Q.isEmpty()) {
			next = Q.pop();
		    next.setBFScheck(true);
		    for (Sommet v : next.getVoisins()) {
		    	if (!v.BFScheck()) {
		    		v.pred = next;
		    		if (!v.getIsVisite()) { destination = v; break;}
		    		Q.push(v);
		    		v.setBFScheck(true);
		    	}
		    }
		}
		Q.push(this);
		while(!Q.isEmpty()) {
		    next = Q.pop();
		    next.setBFScheck(false);
		    for (Sommet v : next.getVoisins()) {
		    	if (v.BFScheck()) {
		    		Q.push(v);
		    		v.setBFScheck(false);
		    	}
		    }
		}
		return destination;
	}
	
	
    public int distance(Sommet s) {
    	return (int)Math.sqrt( (this.x - s.x)*(this.x - s.x) + (this.y - s.y)*(this.y - s.y) );
    }

}