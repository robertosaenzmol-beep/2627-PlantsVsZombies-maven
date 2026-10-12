package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;

/**
 * Lista de zombis guardada en un array.
 *
 * <p>Se reserva sitio para un zombi por casilla, contando también la columna
 * de fuera del tablero en la que aparecen.
 */
public class ZombieList {

	private Zombie[] zombies;

	private int size;

	public ZombieList() {
		this.zombies = new Zombie[Game.NUM_ROWS * (Game.NUM_COLS + 1)];
		this.size = 0;
	}

	public void add(Zombie zombie) {
		zombies[size] = zombie;
		size++;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Busca el zombi que está en una posición.
	 *
	 * @return el zombi o <code>null</code> si no hay ninguno ahí.
	 */
	public Zombie getInPosition(Position position) {
		for (int i = 0; i < size; i++) {
			if (zombies[i].isInPosition(position)) {
				return zombies[i];
			}
		}
		return null;
	}

	/**
	 * Busca el zombi vivo más cercano a la derecha de una posición, en su
	 * misma fila. Los zombis que todavía están fuera del tablero no cuentan.
	 *
	 * @return el zombi o <code>null</code> si no hay ninguno a la derecha.
	 */
	public Zombie getFirstToTheRight(Position position) {
		Zombie closest = null;
		for (int i = 0; i < size; i++) {
			Zombie zombie = zombies[i];
			if (zombie.isAlive() && zombie.isInRow(position.getRow()) && zombie.getCol() > position.getCol()
					&& zombie.getCol() < Game.NUM_COLS
					&& (closest == null || zombie.getCol() < closest.getCol())) {
				closest = zombie;
			}
		}
		return closest;
	}

	/**
	 * @return <code>true</code> si algún zombi ha salido por la izquierda del tablero.
	 */
	public boolean anyReachedEnd() {
		for (int i = 0; i < size; i++) {
			if (zombies[i].hasReachedEnd()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Actualiza todos los zombis en el orden en que se añadieron.
	 */
	public void update() {
		for (int i = 0; i < size; i++) {
			zombies[i].update();
		}
	}

	/**
	 * Quita los zombis muertos, juntando los vivos al principio del array
	 * sin cambiar su orden.
	 */
	public void removeDead() {
		int alive = 0;
		for (int i = 0; i < size; i++) {
			if (zombies[i].isAlive()) {
				zombies[alive] = zombies[i];
				alive++;
			}
		}
		for (int i = alive; i < size; i++) {
			zombies[i] = null;
		}
		size = alive;
	}
}
