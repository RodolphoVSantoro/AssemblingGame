package Pecas;

import GUI.Desenhavel;

public class Maquina extends Desenhavel implements Tile {
	private int[] armazenamento;
	private final int[] idInput;
	private final int[] receita;
	private final int quantidadeInput;
	private final Recurso outputResource;
	protected final double custo;
	protected int rotacao;

	public Maquina(String nomeImagem, int qtd, int[] idInput, int[] recipe, Recurso output, double custo) {
		super(nomeImagem);
		this.custo = custo;
		this.outputResource = output.copia();
		this.quantidadeInput = qtd;
		this.rotacao = 0;
		this.idInput = new int[qtd];
		this.armazenamento = new int[qtd];
		this.receita = new int[qtd];
		for (int i = 0; i < qtd; i++) {
			this.receita[i] = recipe[i];
			this.idInput[i] = idInput[i];
			this.armazenamento[i] = 0;
		}
	}
	
	public Maquina copiaMaquina() {
		return new Maquina(nomeImagem, idInput.length, idInput, receita, outputResource, custo);
	}

	public Recurso output() {
		boolean completo = true;
		for (int i = 0; i < this.quantidadeInput; i++) {
			if (armazenamento[i] < receita[i]) {
				completo = false;
				break;
			}
		}
		if (completo == true) {
			for (int i = 0; i < this.quantidadeInput; i++) {
				armazenamento[i] -= receita[i];
			}
			return outputResource.copia();
		}
		return null;
	}

	public boolean input(Recurso r) {
		boolean entrou = false;
		for (int i = 0; i < quantidadeInput; i++) {
			if (r.getId() == idInput[i]) {
				armazenamento[i]++;
				entrou = true;
				break;
			}
		}
		return entrou;
	}

	public int getRotacao() {
		return rotacao;
	}
	
	public void rotaciona() {
		rotacao = (rotacao + 1) % 4;
		this.rotateImageByDegrees(90);
	}
	
	public void reset() {
		for (int i = 0; i < this.quantidadeInput; i++) {
			this.armazenamento[i] = 0;
		}
	}
}
