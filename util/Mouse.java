package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
	private int x, y;
	private int limx, limy;
	private int pressedButton;

	public Mouse(int largura, int altura) {
		limx = largura;
		limy = altura;
		x = y = -1;
	}

	public void mousePressed(MouseEvent me) {
		// esquerdo
		if (me.getButton() == MouseEvent.BUTTON1) {
			pressedButton = 1;
		}
		// direito
		else if (me.getButton() == MouseEvent.BUTTON3) {
			pressedButton = 2;
		}
		// meio
		else {
			pressedButton = 3;
		}
		x = me.getX();
		y = me.getY();
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	public void mouseClicked(MouseEvent me) {
	}

	public Pair<Integer, Integer> getLastClick() {
		int tx = x, ty = y;
		x = y = -1;
		return new Pair<Integer, Integer>(tx, ty);
	}

	public boolean validClick() {
		return x >= 0 && x < limx && y >= 0 && y < limy;
	}

	public int getButton() {
		return pressedButton;
	}
}
