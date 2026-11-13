package tp1.pvz.logic;

/**
 * Lo que permite alterar el estado de la partida. Es el punto de entrada de las
 * acciones del usuario y lo que conoce el controlador.
 */
public interface GameModel {

	void reset();

	void update();

	void quit();

	/**
	 * Intenta añadir una planta al tablero.
	 *
	 * @throws IllegalArgumentException si la posición, la planta o las monedas
	 *                                  no son válidas (el mensaje describe el error).
	 */
	boolean tryAddPlant(String plantName, int col, int row);

	boolean hasGameFinished();
}
