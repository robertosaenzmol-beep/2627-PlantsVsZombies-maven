package tp1.pvz.logic;

import tp1.pvz.utils.Position;

/**
 * Lo que la vista ({@code GamePrinter}) necesita saber del juego para poder
 * pintarlo. Solo consultas: nada de aquí modifica la partida.
 */
public interface GameStatus {

	int getCycles();

	int getCoins();

	int getRemainingZombies();

	/** Texto (iconos) de lo que hay dibujado en una casilla; "" si está vacía. */
	String positionToString(Position position);

	boolean getPlayerWins();

	boolean getZombiesWin();

	boolean playerQuits();
}
