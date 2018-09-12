package calculs;

public class Point{
    int x;
    int y;

    public Point(){}
    
    public Point(int x, int y){
	this.x = x;
	this.y = y;
    }

    public int distance(Point c) {
	return (int)Math.sqrt( (this.x - c.x)*(this.x - c.x) + (this.y - c.y)*(this.y - c.y) );
    }

    public boolean isNVoisin(Point c, int N){
	return this.distance(c) <= N;
    }

    public double angle(Point p1, Point p2) {
	/*int d1 = distance(p1);
	int d2 = distance(p2);
	int d3 = p1.distance(p2);
	return Math.acos((d1*d1 + d2*d2 - d3*d3)/(2.0*d1*d2));*/
	int xO1 = p1.x - this.x;
	int yO1 = p1.y - this.y;
	int xO2 = p2.x - this.x;
	int yO2 = p2.y - this.y;
	return Math.atan2(yO2,xO2) - Math.atan2(yO1,xO1);
	
    }

    public String toString(){
	return "("+x+","+y+")";
    }
    
}
