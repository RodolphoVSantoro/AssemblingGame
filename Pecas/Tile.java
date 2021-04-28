package Pecas;

public interface Tile {
	// rotaciona 90 graus horario
	public void rotaciona();
	
	public int getRotacao();

	public Recurso output();

	public boolean input(Recurso r);
	
	public void reset();
}
