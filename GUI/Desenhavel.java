package GUI;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

public abstract class Desenhavel {
	protected String nomeImagem;
	protected BufferedImage imagem;

	protected Desenhavel(String nomeImagem) {
		this.nomeImagem = nomeImagem;
		try {
			imagem = ImageIO.read(new File("src/Assembler/img/" + nomeImagem));
		} catch (Exception e) {
			imagem = null;
			System.out.println("Erro ao abrir a imagem:" + nomeImagem);
		}
	}

	protected Desenhavel() {
		imagem = null;
	}

	public void Desenha(Graphics2D g2d, int x, int y) {
		g2d.drawImage(imagem, x, y, null);
	}
	
	public void rotateImageByDegrees(double angle) {
	    double rads = Math.toRadians(angle);
	    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
	    int w = imagem.getWidth();
	    int h = imagem.getHeight();
	    int newWidth = (int) Math.floor(w * cos + h * sin);
	    int newHeight = (int) Math.floor(h * cos + w * sin);

	    Graphics2D g2d = imagem.createGraphics();
	    AffineTransform at = new AffineTransform();
	    at.translate((newWidth - w) / 2, (newHeight - h) / 2);
	    
	    BufferedImage tmp = this.copiaImagem();
	    
	    int x = w / 2;
	    int y = h / 2;

	    g2d.clearRect(0, 0, w, h);
	    at.rotate(rads, x, y);
	    g2d.setTransform(at);
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	}

	public BufferedImage copiaImagem() {
		ColorModel cm = imagem.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = imagem.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public BufferedImage getImagem() {
		return imagem;
	}
}
