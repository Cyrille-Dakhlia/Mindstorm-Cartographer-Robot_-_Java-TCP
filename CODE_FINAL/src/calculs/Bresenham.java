package calculs;

import java.util.ArrayList;
/*
      \      |      /
       \     |     /
        \ 6e | 7e /
	 \   |   /
	  \  |  /  
      5e   \ | /  8e
     _______\|/_______
            /|\
      4e   / | \   1er
	  /  |  \  octant
	 /   |   \
	/    |    \
       / 3e  | 2nd \
      /      |      \
      
    */

public class Bresenham{

    private static void setBit(int[][] matrice, int x, int y, ArrayList<Coor> l, int w, int h) {
	if (x > 0 && x < w && y > 0 && y < h && matrice[x][y] == 0) {
	    matrice[x][y] = -2;
	    l.add(new Coor(x,y));
	}
    }    
    
    public static ArrayList<Coor> drawCircle(int[][] matrice, int x0, int y0, int r, int w, int h) {
	ArrayList<Coor> list = new ArrayList<Coor>();
	int x = r;
	int y = 0;
	int decisionOver2 = 1 - x;
	
	while (y <= x) {
	    setBit(matrice, x + x0,  y + y0, list, w, h); // Octant 1
	    setBit(matrice, y + x0,  x + y0, list, w, h); // Octant 2
	    setBit(matrice, -x + x0,  y + y0, list, w, h); // Octant 4
	    setBit(matrice, -y + x0,  x + y0, list, w, h); // Octant 3
	    setBit(matrice, -x + x0, -y + y0, list, w, h); // Octant 5
	    setBit(matrice, -y + x0, -x + y0, list, w, h); // Octant 6
	    setBit(matrice, x + x0, -y + y0, list, w, h); // Octant 7
	    setBit(matrice, y + x0, -x + y0, list, w, h); // Octant 8
	    y++;
	    if (decisionOver2<=0) {
		decisionOver2 += 2 * y + 1;   // Change in decision criterion for y -> y+1
	    } else {
		x--;
		decisionOver2 += 2 * (y - x) + 1;   // Change for y -> y+1, x -> x-1
	    }
	    
	}
	return list;
    }

    
    public static void tracerSegment(int[][] matrice, int x1, int y1, int x2, int y2, int code) {
	int dx, dy;
	try{
	if ((dx = x2 - x1) != 0) {
	    if (dx > 0) {
		if ((dy = y2 - y1) != 0) {
		    if (dy > 0){
			// Vecteur oblique dans le 1er quadran
			if (dx >= dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 1er octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
			        matrice[x1][y1] = code;
				if (++x1 == x2) break;
				if ((e = e - dy) < 0) {
				    y1++;
				    e = e + dx;
				}
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 2nd octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
			        matrice[x1][y1] = code;
				if (++y1 == y2) break;
				if ((e = e - dx) < 0) {
				    x1++;
				    e = e + dy;
				}
			    }
			}
		    } else { // dy < 0 (et dx > 0)
			// Vecteur oblique dans le 4e cadran
			if (dx >= -dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 8e octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
				matrice[x1][y1] = code;
				if (++x1 == x2) break;
				if ((e = e + dy) < 0) {
				    y1--;
				    e = e + dx;
				}
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 7e octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
				matrice[x1][y1] = code;
				if (--y1 == y2) break;
				if ((e = e - dx) < 0) { // il y avait une erreur : > 0
				    x1++;
				    e = e - dy;
				}
			    }
			}
		    }
		} else { // dy = 0 et dx > 0
		    // Vecteur horizontal vers la droite
		    do {
		        matrice[x1][y1] = code;
		    } while (++x1 != x2);
		}
	    } else { // dx < 0
		if ((dy = y2 - y1) != 0) {
		    if (dy > 0) {
			// Vecteur oblique dans le 2nd quadran
			if (-dx >= dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 4e octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
			        matrice[x1][y1] = code;
				if (--x1 == x2) break;
				if ((e = e + dy) >= 0) {
				    y1++;
				    e = e + dx;
				}
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 3e octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
			        matrice[x1][y1] = code;
				if (++y1 == y2) break;
				if ((e = e + dx) <= 0) {
				    x1--;
				    e = e + dy;
				}
			    }
			}
		    } else { // dy < 0 et dx < 0
			// Vecteur oblique dans le 3e cadran
			if (dx <= dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 5 octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
			        matrice[x1][y1] = code;
				if (--x1 == x2) break;
				if ((e = e - dy) >= 0) {
				    y1--;
				    e = e + dx;
				}
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 6e octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
			        matrice[x1][y1] = code;
				if (--y1 == y2) break;
				if ((e = e - dx) >= 0) {
				    x1--;
				    e = e + dy;
				}
			    }
			}
			
		    }
		} else { // dy = 0 et dx < 0
		    // Vecteur horizontal vers la gauche
		    do {
		        matrice[x1][y1] = code;
		    } while (--x1 != x2);
		}
	    }
	} else { // dx = 0
	    
	    if ((dy = y2 - y1) != 0) {
		if (dy > 0) {
		    // Vecteur vertical croissant
		    do {
		        matrice[x1][y1] = code;
		    } while (++y1 != y2);
		} else { // dy < 0 et dx = 0
		    // Vecteur vertical decroissant
		    do {
			matrice[x1][y1] = code;
		    } while (--y1 != y2);
		}
	    }
	}
	}catch(Exception e){
	    System.err.println("x1="+x1+", y1="+y1);
	}
    }
















































































    






    






    
    public static Point projete(int[][] matrice, int x1, int y1, int x2, int y2, int w, int h, int opt){
	int dx, dy;
	int xdep = x1;
	int ydep = y1;
	if ((dx = x2 - x1) != 0) {
	    if (dx > 0) {
		if ((dy = y2 - y1) != 0) {
		    if (dy > 0){
			// Vecteur oblique dans le 1er quadran
			if (dx >= dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 1er octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
				if (++x1 == w || y1 == h) break;
				if ((e = e - dy) < 0) {
				    y1++;
				    e = e + dx;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1--; y1--;
				    break;
				}
        			if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 2nd octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
				if (++y1 == h || x1 == w) break;
				if ((e = e - dx) < 0) {
				    x1++;
				    e = e + dy;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1--;
				    y1--;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			}
		    } else { // dy < 0 (et dx > 0)
			// Vecteur oblique dans le 4e cadran
			if (dx >= -dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 8e octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
				if (++x1 == w || y1 == 0) break;
				if ((e = e + dy) < 0) {
				    y1--;
				    e = e + dx;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1--;
				    y1++;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 7e octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
				if (--y1 == 0 || x1 == w) break;
				if ((e = e - dx) < 0) { // il y avait une erreur : > 0
				    x1++;
				    e = e - dy;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1--;
				    y1++;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			}
		    }
		} else { // dy = 0 et dx > 0
		    // Vecteur horizontal vers la droite
		    do {
		        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
			    break;
			}
			if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
		    } while (++x1 != w);
		}
	    } else { // dx < 0
		if ((dy = y2 - y1) != 0) {
		    if (dy > 0) {
			// Vecteur oblique dans le 2nd quadran
			if (-dx >= dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 4e octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
				if (--x1 == 0 || y1 == h) break;
				if ((e = e + dy) >= 0) {
				    y1++;
				    e = e + dx;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1++;
				    y1--;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 3e octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
				if (++y1 == h || x1 == 0) break;
				if ((e = e + dx) <= 0) {
				    x1--;
				    e = e + dy;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    y1--;
				    x1++;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			}
		    } else { // dy < 0 et dx < 0
			// Vecteur oblique dans le 3e cadran
			if (dx <= dy) {
			    // Vecteur diagonal ou oblique proche de l'horizontale, dans le 5 octant
			    int e;
			    e = dx;
			    dx = e * 2;
			    dy = dy * 2;
			    while (true) {
				if (--x1 == 0 || y1 == 0) break;
				if ((e = e - dy) >= 0) {
				    y1--;
				    e = e + dx;
				}
				if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1++;
				    y1++;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			} else {
			    // Vecteur oblique proche de la verticale, dans le 6e octant
			    int e;
			    e = dy;
			    dy = e * 2;
			    dx = dx * 2;
			    while (true) {
				if (--y1 == 0 || x1 == 0) break;
				if ((e = e - dx) >= 0) {
				    x1--;
				    e = e + dy;
				}
			        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) {
				    x1++;
				    y1++;
				    break;
				}
				if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
			    }
			}
			
		    }
		} else { // dy = 0 et dx < 0
		    // Vecteur horizontal vers la gauche
		    do {
		        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) break;
			if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
		    } while (--x1 != 0);
		}
	    }
	} else { // dx = 0
	    
	    if ((dy = y2 - y1) != 0) {
		if (dy > 0) {
		    // Vecteur vertical croissant
		    do {
			if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) break;
			if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
		    } while (++y1 != h);
		} else { // dy < 0 et dx = 0
		    // Vecteur vertical decroissant
		    do {
		        if (opt==1 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) <= 151 && (new Point(xdep,ydep)).distance(new Point(x1,y1)) >= 149) break;
			if (opt==2 && (new Point(x1,y1)).distance(new Point(x2,y2)) >= 22 && (new Point(x1,y1)).distance(new Point(x2,y2)) <= 28) break;
		    } while (--y1 != 0);
		}
	    }
	}
	//System.out.println("x1="+x1+", y1="+y1);
	return new Point(x1,y1);
    }
}
