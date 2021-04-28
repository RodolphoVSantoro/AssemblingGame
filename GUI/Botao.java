package GUI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Botao extends Desenhavel {
	private int x, y, sx, sy;

	public Botao(String imgName, int x, int y) {
		super(imgName);
		this.x=x;
		this.y=y;
		sx = imagem.getWidth();
		sy = imagem.getHeight();
	}
	
	public Botao(BufferedImage img, int x, int y) {
		super();
		imagem = img;
		this.x = x;
		this.y = y;
		sx = imagem.getWidth();
		sy = imagem.getHeight();
	}

	public boolean checkClick(int cx, int cy) {
		return cx >= x && cx <= x + sx && cy >= y && cy <= y + sy;
	}
	public void Desenha(Graphics2D g2d) {
		super.Desenha(g2d, x, y);
	}
}
