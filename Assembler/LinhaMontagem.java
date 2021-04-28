package Assembler;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Pecas.Esteira;
import Pecas.Maquina;
import Pecas.Receptor;
import Pecas.Recurso;
import Pecas.Tile;
import util.Pair;

public class LinhaMontagem {
	private Tile[][] linhaMontagem;
	private int tamanhoHorizontal, tamanhoVertical;
	private int iteracao;
	ArrayList<Objetivo> listaObjetivo;

	public LinhaMontagem(int tamanhoHorizontal, int tamanhoVertical, ArrayList<Objetivo> listaObjetivo) {
		this.listaObjetivo = listaObjetivo;
		this.linhaMontagem = new Tile[tamanhoHorizontal][tamanhoVertical];
		for (int i = 0; i < tamanhoHorizontal; i++)
			for (int j = 0; j < tamanhoVertical; j++)
				this.linhaMontagem[i][j] = null;
		this.tamanhoHorizontal = tamanhoHorizontal;
		this.tamanhoVertical = tamanhoVertical;
		this.iteracao = 0;
	}

	public Pair<Integer, Integer> getTamanho() {
		return new Pair<Integer, Integer>(tamanhoHorizontal, tamanhoVertical);
	}

	public Tile getTile(int x, int y) {
		return linhaMontagem[x][y];
	}

	public void setTile(Tile t, int x, int y) {
		linhaMontagem[x][y] = t;
	}

	private Pair<Integer, Integer> getCoordenadaDirecao(int x, int y, int direcao) {
		switch (direcao) {
		case 0: {
			x++;
		}
			break;
		case 1: {
			y++;
		}
			break;
		case 2: {
			x--;
		}
			break;
		case 3: {
			y--;
		}
			break;
		}
		return new Pair<Integer, Integer>(x, y);
	}

	// retorna false se deu erro
	private boolean simulaTile(int i, int j) {
		Tile t = this.linhaMontagem[i][j];
		Recurso r = t.output();
		if (r != null) {
			Pair<Integer, Integer> coordenada = getCoordenadaDirecao(i, j, t.getRotacao());
			int x = coordenada.getElement0(), y = coordenada.getElement1();
			if (x >= 0 && x < tamanhoHorizontal && y >= 0 && y < tamanhoVertical) {
				Tile t2 = this.linhaMontagem[x][y];
				if (t2 == null) {
					// System.out.println("Output para vazio");
					JOptionPane.showMessageDialog(null, "Output para vazio");
					return false;
				}
				if (!t2.input(r)) {
					// System.out.println("Erro ao dar input");
					JOptionPane.showMessageDialog(null, "Erro ao dar input");
					return false;
				} else {
					return true;
				}
			} else {
				// System.out.println("Output para fora do campo");
				JOptionPane.showMessageDialog(null, "Output para fora do campo");
				return false;
			}
		} else {
			return true;
		}
	}

	// retorna false se deu erro
	private boolean simulaTipo(Class<?> classe) {
		for (int i = 0; i < this.tamanhoHorizontal; i++) {
			for (int j = 0; j < this.tamanhoVertical; j++) {
				if (this.linhaMontagem[i][j] != null) {
					if (this.linhaMontagem[i][j].getClass() == classe) {
						if (!simulaTile(i, j)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	// retorna se terminou
	public boolean simula() {
		// mover existentes
		if(!simulaTipo(Esteira.class)) {
			return true;
		}
		// gerar recursos
		if(!simulaTipo(Maquina.class)) {
			return true;
		}
		for (int i = 0; i < this.tamanhoHorizontal; i++) {
			for (int j = 0; j < this.tamanhoVertical; j++) {
				if (this.linhaMontagem[i][j] != null) {
					if (this.linhaMontagem[i][j] instanceof Esteira) {
						((Esteira) this.linhaMontagem[i][j]).fimCiclo();
					}
					if(this.linhaMontagem[i][j] instanceof Receptor) {
						Recurso r;
						do {
							 r = this.linhaMontagem[i][j].output();
							 if(r!=null) {
								 for (Objetivo obj : this.listaObjetivo)
									 obj.adicionaUm(r);
							 }
							
						}while(r!=null);
					}
				}
			}
		}
		this.iteracao++;
		boolean terminouObjetivos = true;
		for (Objetivo obj : this.listaObjetivo) {
			if (!obj.finalizado()) {
				terminouObjetivos = false;
				break;
			}
		}
		if (terminouObjetivos) {
			//System.out.println("Terminou Objetivos");
			JOptionPane.showMessageDialog(null, "Terminou Objetivos");
			return true;
		}

		if (this.iteracao > 100) {
			return true;
		}

		return false;
	}

	public void reset() {
		for (int i = 0; i < tamanhoHorizontal; i++)
			for (int j = 0; j < tamanhoVertical; j++)
				if (this.linhaMontagem[i][j] != null)
					this.linhaMontagem[i][j].reset();
		for(Objetivo obj : listaObjetivo)
			obj.reset();
	}
}
