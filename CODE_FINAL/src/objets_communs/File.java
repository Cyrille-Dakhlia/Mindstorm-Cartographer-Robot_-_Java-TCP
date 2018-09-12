package objets_communs;

import java.util.LinkedList;

import calculs.Sommet;

public class File{
    LinkedList<Sommet> F;

    public File(){
	this.F = new LinkedList<Sommet>();
    }

    public boolean isEmpty(){
	return this.F.size()==0;
    }

    public void push(Sommet s){
	this.F.addLast(s);
    }

    public Sommet pop(){
	return this.F.removeFirst();
    }
}