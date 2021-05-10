package Assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import GUI.Estagio;
import Pecas.Maquina;
import Pecas.Recurso;


public abstract class CriadorEstagio{
	private static Maquina criaMaquina(String linha, ArrayList<Recurso> recursos){
		String[] partes = linha.split(",");
		int n = Integer.parseInt(partes[0]);
		int[] idInput = new int[3], recipe = new int[3];
		for (int i = 0; i < n; i++) {
			idInput[i] = Integer.parseInt(partes[i + 1]);
			recipe[i] = Integer.parseInt(partes[i + 1 + n]);
		}
		Recurso recurso = null;
		int idOutput = Integer.parseInt(partes[2*n+1]);
		for(int i=0;i<recursos.size();i++) {
			if(recursos.get(i).getId()==idOutput)
				recurso = recursos.get(i).copia();
		}
		double custo = Double.parseDouble(partes[2*n+2]);
		return new Maquina(partes[2*n+3], n, idInput, recipe, recurso, custo);
	}
	public static Estagio criaEstagio(String nomeArquivo) throws Exception{
		int tx = 0, ty = 0;
		ArrayList<Recurso> recursos = new ArrayList<Recurso>();
		ArrayList<Objetivo> objetivos = new ArrayList<Objetivo>();
		ArrayList<Maquina> poolMaquina = new ArrayList<Maquina>();
		File arquivo = new File("src/Assembler/estagios/" + nomeArquivo);
		if(arquivo==null) {
			throw new Exception("Arquivo n encontrado");
		}
		Scanner leitor = new Scanner(arquivo);
		char first = '#';
		String linha = "";
		String[] partes;
		while (leitor.hasNextLine() && first == '#') {
			linha = leitor.nextLine();
			first = linha.charAt(0);
		}
		partes = linha.split(",");
		tx = Integer.parseInt(partes[0]);
		ty = Integer.parseInt(partes[1]);
		first = '#';
		if (leitor.hasNext())
			leitor.nextLine();
		while (leitor.hasNextLine() && first != '-') {
			linha = leitor.nextLine();
			first = linha.charAt(0);
			if (first != '#' && first != '-') {
				partes = linha.split(",");
				recursos.add(new Recurso(partes[2], Double.parseDouble(partes[1]), Integer.parseInt(partes[0])));
			}
		}
		first = '#';
		while (leitor.hasNextLine() && first != '-') {
			linha = leitor.nextLine();
			first = linha.charAt(0);
			if (first != '#' && first != '-') {
				poolMaquina.add(CriadorEstagio.criaMaquina(linha, recursos));
			}
		}
		first='#';
		while(leitor.hasNextLine() && first != '-') {
			linha = leitor.nextLine();
			first = linha.charAt(0);
			if (first != '#' && first != '-') {
				partes = linha.split(",");
				objetivos.add(new Objetivo(Integer.parseInt(partes[0]), Integer.parseInt(partes[1])));
			}
		}
		leitor.close();

		return new Estagio(tx, ty, objetivos, poolMaquina);
	}
}
