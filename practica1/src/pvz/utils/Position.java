package pvz.utils;

/**
 * Una casilla del tablero: una fila y una columna.
 *
 * <p>Es inmutable: una vez creada no se puede cambiar. Si un objeto se
 * mueve, se crea una Position nueva.
 */
public class Position {

	private final int row;

	private final int col;

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	/**
	 * Dos posiciones son iguales si tienen la misma fila y la misma columna,
	 * aunque sean dos objetos distintos en memoria. Por eso las posiciones se
	 * comparan con equals() y no con ==.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != Position.class) {
			return false;
		}
		Position other = (Position) obj;
		return other.row == this.row && other.col == this.col;
	}
}
