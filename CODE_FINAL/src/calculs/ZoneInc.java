package calculs;

import java.util.ArrayList;

public class ZoneInc extends ArrayList<Point> {

    public Point barycentre(){
        int[] b = new int[2];
	for(int i=0; i<size(); i++){
	    b[0] = b[0] + get(i).x;
	    b[1] = b[1] + get(i).y;
	}
	b[0] = (int)(((double)b[0])/size());
	b[1] = (int)(((double)b[1])/size());
	return new Point(b[0],b[1]);
    } 
}
