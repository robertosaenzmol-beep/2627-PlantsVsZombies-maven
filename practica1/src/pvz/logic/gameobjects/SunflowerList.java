package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;

/**
 * Lista de girasoles guardada en un array.
 *
 * <p>Un array tiene tamaño fijo, así que se reserva sitio para el máximo
 * posible (una planta por casilla) y se lleva la cuenta de cuántos huecos
 * están usados de verdad en {@code size}.
 */
public class SunflowerList {

	private Sunflower[] sunflowers;

	private int size;

	public SunflowerList() {
		this.sunflowers = new Sunflower[Game.NUM_ROWS * Game.NUM_COLS];
		this.size = 0;
	}

	public void add(Sunflower sunflower) {
		sunflowers[size] = sunflower;
		size++;
	}

	/**
	 * Busca el girasol que está en una posición.
	 *
	 * @return el girasol o <code>null</code> si no hay ninguno ahí.
	 */
	public Sunflower getInPosition(Position position) {
		for (int i = 0; i < size; i++) {
			if (sunflowers[i].isInPosition(position)) {
				return sunflowers[i];
			}
		}
		return null;
	}

	/**
	 * Actualiza todos los girasoles en el orden en que se añadieron.
	 */
	public void update() {
		for (int i = 0; i < size; i++) {
			sunflowers[i].update();
		}
	}

	/**
	 * Quita los girasoles muertos, juntando los vivos al principio del array
	 * sin cambiar su orden.
	 */
	public void removeDead() {
		int alive = 0;
		for (int i = 0; i < size; i++) {
			if (sunflowers[i].isAlive()) {
				sunflowers[alive] = sunflowers[i];
				alive++;
			}
		}
		for (int i = alive; i < size; i++) {
			sunflowers[i] = null;
		}
		size = alive;
	}
}
