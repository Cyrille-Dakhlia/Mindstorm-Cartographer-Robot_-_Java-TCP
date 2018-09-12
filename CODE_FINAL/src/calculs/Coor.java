package calculs;

import java.io.Serializable;
import java.util.List;

public class Coor extends Point implements Serializable {
	private static final long serialVersionUID = 1L;

	int taille;
    int rg;
    Coor pere;

    public Coor(int x, int y){
	super(x,y);
	this.taille = 1;
	this.pere = this;
    }

    public Coor(int x, int y, Coor pere){
	this(x,y);
	this.pere = pere;
    }
    
    public Coor(int x, int y, int rg){
	this(x,y);
	this.rg = rg;
    }
    
    public Coor(int x, int y, int rg, Coor pere) {
	this(x,y,rg);
	this.pere = pere;
    }

    public boolean isRacine(){
	return this.pere == this;
    }

    public boolean isNVoisin(Coor c, int N){
	return this.distance(c) <= N;
    }

    public Coor racine(){
	if (this.pere != this)
	    this.pere = this.pere.racine();
	return this.pere;
    }

    public boolean find(Coor c){
	return this.racine() == c.racine();
    }
    
    public void union(Coor c){
	if(!this.find(c)){
	    if(this.pere.taille < c.pere.taille){
		c.pere.taille += this.pere.taille;
		this.pere.pere = c.pere;
	    } else{
		this.pere.taille += c.pere.taille;
		c.pere.pere = this.pere;
	    }
	}
    }
}
