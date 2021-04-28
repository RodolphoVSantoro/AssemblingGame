package GUI;

import java.awt.Graphics2D;
import java.util.ArrayList;
import Assembler.LinhaMontagem;
import Assembler.Objetivo;
import Pecas.Esteira;
import Pecas.Maquina;
import Pecas.Receptor;
import Pecas.Tile;
import util.ModoCursor;
import util.Pair;

public class Estagio {
	private LinhaMontagem assembly;
	private ArrayList<Maquina> poolMaquina;
	private ArrayList<Botao> botoes;
	private ModoCursor modo;
	private int maquinaSelecionada;

	public Estagio(int tamanhoHorizontal, int tamanhoVertical, ArrayList<Objetivo> listaObjetivo,
			ArrayList<Maquina> poolMaquina) {
		this.assembly = new LinhaMontagem(tamanhoHorizontal, tamanhoVertical, listaObjetivo);
		this.poolMaquina = poolMaquina;
		this.botoes = new ArrayList<Botao>();
		botoes.add(new Botao("start.png", 0, 0));
		botoes.add(new Botao("remove.png", 30, 0));
		botoes.add(new Botao("rotaciona.png", 60, 0));
		botoes.add(new Botao("esteira.png", 90, 0));
		botoes.add(new Botao("venda.png", 120, 0));
		int k = 0;
		for (Maquina m : poolMaquina) {
			botoes.add(new Botao(m.getImagem(), 150 + k, 0));
			k += 30;
		}
		int ix = 100, iy = 100;
		for (int i = 0; i < tamanhoHorizontal; i++) {
			for (int j = 0; j < tamanhoVertical; j++) {
				botoes.add(new Botao("vazio.png", ix, iy));
				iy += 35;
			}
			iy = 100;
			ix += 35;
		}
		modo = ModoCursor.NADA;
	}

	public void adicionaTile(Tile t, int x, int y) {
		assembly.setTile(t, x, y);
	}

	public void retiraTile(int x, int y) {
		assembly.setTile(null, x, y);
	}

	public boolean checkStart() {
		if (modo == ModoCursor.START)
			return true;
		else
			return false;
	}

	public void simulaLinha() {
		boolean terminou = assembly.simula();
		if (terminou)
			modo = ModoCursor.NADA;
	}

	public void desenha(Graphics2D g2d, int mx, int my) {
		int i=0;
		int ultimaMaquina = 5 + poolMaquina.size();
		Pair<Integer, Integer> tamanho = assembly.getTamanho();
		int tamanhoVertical = tamanho.getElement1();
		for(Botao b : botoes) {
			b.Desenha(g2d);
			if(i>=ultimaMaquina) {
				int k = i - ultimaMaquina;
				int xTile = k / tamanhoVertical, yTile = k % tamanhoVertical;
				Tile t = assembly.getTile(xTile, yTile);
				if(t instanceof Esteira) {
					((Esteira) t).desenhaRecurso(g2d, 100 + xTile*35, 100 + yTile*35);
				}
			}
			i++;
		}
		
		if(modo == ModoCursor.MAQUINA) {
			poolMaquina.get(maquinaSelecionada).Desenha(g2d, mx, my);
		}
		else {
			switch(modo) {
				case REMOVE:{
					botoes.get(1).Desenha(g2d, mx, my);
				}break;
				case RODA:{
					botoes.get(2).Desenha(g2d, mx, my);
				}break;
				case ESTEIRA:{
					botoes.get(3).Desenha(g2d, mx, my);
				}break;
				case SELL:{
					botoes.get(4).Desenha(g2d, mx, my);
				}break;
				default:
					break;
			}
		}
	}

	public void resetLinha() {
		assembly.reset();
	}

	public void mouseClick(Pair<Integer, Integer> coord, int mouseButton) {
		// reseta cursor
		if (mouseButton == 2) {
			modo = ModoCursor.NADA;
		} else if (mouseButton == 1) {
			int i = 0;
			for (Botao b : botoes) {
				if (b.checkClick(coord.getElement0(), coord.getElement1()))
					break;
				i++;
			}
			int ultimaMaquina = 5 + poolMaquina.size();
			Pair<Integer, Integer> tamanho = assembly.getTamanho();
			int tamanhoHorizontal = tamanho.getElement0(), tamanhoVertical = tamanho.getElement1();
			int ultimaTile = ultimaMaquina + tamanhoHorizontal * tamanhoVertical;
			// start simulacao
			if (i == 0) {
				modo = ModoCursor.START;
			}
			// remove maquinas
			else if (i == 1) {
				modo = ModoCursor.REMOVE;
			}
			// gira tiles
			else if (i == 2) {
				modo = ModoCursor.RODA;
			}
			// coloca esteira
			else if (i == 3) {
				modo = ModoCursor.ESTEIRA;
			}
			// coloca receptor
			else if (i == 4) {
				modo = ModoCursor.SELL;
			}
			// pool de maquinas
			else if (i >= 5 && i < ultimaMaquina) {
				modo = ModoCursor.MAQUINA;
				maquinaSelecionada = i - 5;
			}
			// clica em um tile
			else if (i >= ultimaMaquina && i < ultimaTile) {
				int k = i - ultimaMaquina;
				int xTile = k / tamanhoVertical, yTile = k % tamanhoVertical;
				// coloca maquina na tile
				if (modo == ModoCursor.MAQUINA) {
					Maquina m = poolMaquina.get(maquinaSelecionada).copiaMaquina();
					adicionaTile(m, xTile, yTile);
					botoes.set(i, new Botao(m.getImagem(), 100 + xTile * 35, 100 + yTile * 35));
				}
				// remove maquina
				else if (modo == ModoCursor.REMOVE) {
					retiraTile(xTile, yTile);
					botoes.set(i, new Botao("vazio.png", 100 + xTile * 35, 100 + yTile * 35));
				}
				// gira tile
				else if (modo == ModoCursor.RODA) {
					if (assembly.getTile(xTile, yTile) != null)
						assembly.getTile(xTile, yTile).rotaciona();
				}
				// coloca esteira
				else if (modo == ModoCursor.ESTEIRA) {
					Esteira est = new Esteira();
					adicionaTile(est, xTile, yTile);
					botoes.set(i, new Botao(est.getImagem(), 100 + xTile * 35, 100 + yTile * 35));
				} else if (modo == ModoCursor.SELL) {
					Receptor r = new Receptor();
					adicionaTile(r, xTile, yTile);
					botoes.set(i, new Botao(r.getImagem(), 100 + xTile * 35, 100 + yTile * 35));
				}
			}
		}
	}
}
