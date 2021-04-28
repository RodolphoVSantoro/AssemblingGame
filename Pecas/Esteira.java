package Pecas;

import java.awt.Graphics2D;
import GUI.Desenhavel;

public class Esteira extends Desenhavel implements Tile {
	private Recurso armazenamento;
	private Recurso temporario;
	protected final double custo;
	protected int rotacao;

	public Esteira() {
		super("esteira.png");
		armazenamento = temporario = null;
		rotacao = 0;
		custo = 10;
	}

	public int getRotacao() {
		return rotacao;
	}

	public void rotaciona() {
		rotacao = (rotacao + 1) % 4;
		this.rotateImageByDegrees(90);
	}

	public void desenhaRecurso(Graphics2D g2d, int x, int y) {
		if(armazenamento != null) {
			armazenamento.Desenha(g2d, x, y);
		}
	}
	
	public Recurso output() {
		Recurso tmp = armazenamento;
		armazenamento = null;
		return tmp;
	}

	public boolean input(Recurso r) {
		if (temporario == null) {
			temporario = r;
			return true;
		} else {
			return false;
		}
	}

	public void fimCiclo() {
		armazenamento = temporario;
		temporario = null;
	}

	@Override
	public void Desenha(Graphics2D g2d, int x, int y) {
		super.Desenha(g2d, x, y);
		if (armazenamento != null)
			armazenamento.Desenha(g2d, x + 4, y + 4);
	}
	
	public void reset() {
		this.armazenamento = this.temporario = null; 
	}
}
