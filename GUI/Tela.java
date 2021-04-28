package GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.Mouse;
import util.Pair;

import Assembler.*;

@SuppressWarnings("serial")
public class Tela extends JPanel {
	static final int LARGURATELA = 1200;
	static final int ALTURATELA = 700;
	Estagio estagio = null;

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(estagio!=null) {
			int x=-100, y=-100;
			Point mousePos = MouseInfo.getPointerInfo().getLocation();     
	        if(mousePos!=null) {
	        	x = mousePos.x - this.getLocationOnScreen().x;
	        	y =  mousePos.y - this.getLocationOnScreen().y;
	        }
			estagio.desenha(g2d, x, y);
		}
	}

	public static void main(String[] args) {

		boolean jogoRodando = true;
		Tela tela = new Tela();
		Mouse mouse = new Mouse(LARGURATELA, ALTURATELA);
		tela.addMouseListener(mouse);
		JFrame janela = new JFrame();
		janela.setLocation(360, 70);
		janela.add(tela);
		janela.setSize(LARGURATELA, ALTURATELA);
		janela.setVisible(true);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.estagio = CriadorEstagio.criaEstagio("estagio1.txt");
		boolean started=false;
		while (jogoRodando) {
			if(tela.estagio.checkStart()) {
				started=true;
				tela.estagio.simulaLinha();
			}
			else {
				if(started) {
					tela.estagio.resetLinha();
					started=false;
				}
				if (mouse.validClick()) {
					Pair<Integer, Integer> coord = mouse.getLastClick();
					int mouseButton = mouse.getButton();
					tela.estagio.mouseClick(coord, mouseButton);
				}
			}
			tela.repaint();
			if(tela.estagio.checkStart()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
