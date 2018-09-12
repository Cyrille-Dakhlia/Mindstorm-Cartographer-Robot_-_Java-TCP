package calculs;

import java.util.LinkedList;

public class Remplissage {

    public static void remplir(int[][] matrice, int x, int y, int code,boolean b){
	boolean bb = true;
	LinkedList<Point> list = new LinkedList<Point>();
	list.add(new Point(x,y));
	while(!list.isEmpty()) {
	    Point p = list.get(0);
	    if (p.x > 0 && p.x < Cartographe.W && p.y > 0 && p.y < Cartographe.H) {
		if (b) bb = matrice[p.x][p.y] == 0;
		else bb = (matrice[p.x][p.y] == 1 || matrice[p.x][p.y] == 0);
	    }
	    list.remove(0);
	    if (p.x > 0 && p.x < Cartographe.W && p.y > 0 && p.y < Cartographe.H && bb) {
		matrice[p.x][p.y] = code;
		list.add(new Point(p.x,p.y-1));
		list.add(new Point(p.x,p.y+1));
		list.add(new Point(p.x-1,p.y));
		list.add(new Point(p.x+1,p.y));
	    }
	}
    }
}
