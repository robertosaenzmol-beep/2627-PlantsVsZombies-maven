package tp1.pvz.logic;

import tp1.pvz.utils.Position;

/**
 * Lo que los objetos del juego (plantas, zombis...) necesitan para saber qué
 * pasa en el mundo del juego e interactuar con él.
 */
public interface GameWorld {

	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;

	boolean isEmpty(Position position);

	boolean isInsideBoard(Position position);

	void addSunCoins(int coins);

	/** Ataca al zombi más cercano de la fila de {@code from}, a su derecha. */
	void attackZombieInRow(Position from, int damage);

	/** Ataca a la planta que haya en la casilla indicada (si hay alguna). */
	void attackPlantInPosition(Position position, int damage);
}
