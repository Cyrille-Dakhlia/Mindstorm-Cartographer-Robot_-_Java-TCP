package objets_cartographie;

import java.io.Serializable;
import java.util.ArrayList;

import objets_communs.PointCapture;

public class Old_DonneesGraphiques implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final double MAX_VALUE = 255;
	public double xMin;
	public double yMin;
	public PointCapture[] captureTab;
	public ArrayList<PointCapture> capture;

	
	
	
	/*** INPUT : tableau ***/
	public Old_DonneesGraphiques(PointCapture[] p) {
		this.captureTab = p;
		this.xMin = 0;
		this.yMin = 0;
		for(PointCapture point : p) {
			this.xMin = (point.getCartesianX() < this.xMin && Math.abs(point.getCartesianX()) <= MAX_VALUE)?
							point.getCartesianX() : this.xMin;
			this.yMin = (point.getCartesianY() < this.yMin && Math.abs(point.getCartesianX()) <= MAX_VALUE)?
							point.getCartesianY() : this.yMin;
		}
	}

	
	/*** INPUT : arrayList ***/
	public Old_DonneesGraphiques(ArrayList<PointCapture> p) {
		this.capture = p;
		this.xMin = 0;
		this.yMin = 0;
		for(PointCapture point : p) {
			this.xMin = (point.getCartesianX() < this.xMin && Math.abs(point.getCartesianX()) <= MAX_VALUE)?
							point.getCartesianX() : this.xMin;
			this.yMin = (point.getCartesianY() < this.yMin && Math.abs(point.getCartesianX()) <= MAX_VALUE)?
							point.getCartesianY() : this.yMin;
		}
	}
	
	
}
