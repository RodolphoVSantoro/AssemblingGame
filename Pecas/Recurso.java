package Pecas;

import GUI.Desenhavel;

public class Recurso extends Desenhavel{
	private double valor;
	private int id;
	public Recurso(String nomeImagem, double valor, int id) {
		super(nomeImagem);
		this.valor = valor;
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public Recurso copia() {
		return new Recurso(nomeImagem, valor, id);
	}
}
