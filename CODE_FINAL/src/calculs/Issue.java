package calculs;

import java.util.ArrayList;

public class Issue extends CC {
    ArrayList<Point> exits = new ArrayList<Point>();
    double ecart = 0;
    Coor oe1, oe2;
    
    public void init(Point p){
	for (Coor c1 : this) {
	    for (Coor c2 : this) {
		double e = p.angle(c1,c2)*180.0/Math.PI;
		if (ecart < e) {
		    ecart = e;
		    extrem1 = c1;
		    extrem2 = c2;
		}
	    }
	}
	ecart = size()*360.0/848.0;
    }
	    
	    /*public boolean getPoint(double angle, Point p1, Point p2) {
	double start = Math.atan((double)(p1.y - p2.y)/(double)(p1.x - p2.x));
	
	}*/
}