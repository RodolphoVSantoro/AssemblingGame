package util;

public enum ModoCursor {
	NADA(6), START(1), REMOVE(2), RODA(3), ESTEIRA(4), SELL(5), MAQUINA(0);

	public int id;
	ModoCursor(){
	}
	ModoCursor(int id){
		this.id=id;
	}
}
