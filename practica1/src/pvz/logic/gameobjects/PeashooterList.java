package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;

/**
 * Lista de lanzaguisantes guardada en un array.
 *
 * <p>Un array tiene tamaño fijo, así que se reserva sitio para el máximo
 * posible (una planta por casilla) y se lleva la cuenta de cuántos huecos
 * están usados de verdad en {@code size}.
 */
public class PeashooterList {

	private Peashooter[] peashooters;

	private int size;

	public PeashooterList() {
		this.peashooters = new Peashooter[Game.NUM_ROWS * Game.NUM_COLS];
		this.size = 0;
	}

	public void add(Peashooter peashooter) {
		peashooters[size] = peashooter;
		size++;
	}

	/**
	 * Busca el lanzaguisantes que está en una posición.
	 *
	 * @return el lanzaguisantes o <code>null</code> si no hay ninguno ahí.
	 */
	public Peashooter getInPosition(Position position) {
		for (int i = 0; i < size; i++) {
			if (peashooters[i].isInPosition(position)) {
				return peashooters[i];
			}
		}
		return null;
	}

	/**
	 * Actualiza todos los lanzaguisantes en el orden en que se añadieron.
	 */
	public void update() {
		for (int i = 0; i < size; i++) {
			peashooters[i].update();
		}
	}

	/**
	 * Quita los lanzaguisantes muertos, juntando los vivos al principio del array
	 * sin cambiar su orden.
	 */
	public void removeDead() {
		int alive = 0;
		for (int i = 0; i < size; i++) {
			if (peashooters[i].isAlive()) {
				peashooters[alive] = peashooters[i];
				alive++;
			}
		}
		for (int i = alive; i < size; i++) {
			peashooters[i] = null;
		}
		size = alive;
	}
}
