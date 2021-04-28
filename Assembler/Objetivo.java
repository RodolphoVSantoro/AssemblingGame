package Assembler;

import Pecas.Recurso;

public class Objetivo {
	private int guardados;
	private int quantidade;
	private int id;

	public Objetivo(int i, int qtd) {
		id = i;
		quantidade = qtd;
		guardados = 0;
	}

	public void adicionaUm(Recurso r) {
		if (r.getId() == id)
			guardados++;
	}

	public int getId() {
		return id;
	}

	public int getQtd() {
		return quantidade;
	}

	public boolean finalizado() {
		if (guardados >= quantidade)
			return true;
		else
			return false;
	}

	public void reset() {
		guardados = 0;
	}
}
