package Pecas;

import java.util.ArrayList;

import GUI.Desenhavel;

public class Receptor extends Desenhavel implements Tile {
	private int rotacao;
	private ArrayList<Recurso> armazenamento; 
	public Receptor() {
		super("venda.png");
		armazenamento = new ArrayList<Recurso>();
	}
	public void rotaciona() {
		rotacao = (rotacao + 1) % 4;
		this.rotateImageByDegrees(90);
	}

	public int getRotacao() {
		return rotacao;
	}

	public Recurso output() {
		Recurso r = null;
		if(!armazenamento.isEmpty()) {
			r = armazenamento.get(0);
			armazenamento.remove(0);
		}
		return r;
	}

	public boolean input(Recurso r) {
		if(r!=null) {
			armazenamento.add(r);
			return true;
		}
		else {
			return false;
		}
	}
	public void reset() {
		armazenamento = new ArrayList<Recurso>(); 
	}
}
