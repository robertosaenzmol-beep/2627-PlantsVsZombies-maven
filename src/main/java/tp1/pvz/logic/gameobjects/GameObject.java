package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.GameWorld;
import tp1.pvz.utils.Position;

/**
 * Entidad abstracta de la que heredan todos los objetos del juego.
 *
 * <p>Guarda lo común (el mundo en el que viven y su posición) y declara los
 * métodos del ciclo de vida que cada objeto concreto implementa a su manera:
 * {@link #onEnter()}, {@link #update()}, {@link #onExit()} e {@link #isAlive()}.
 */
public abstract class GameObject {

	protected GameWorld game;
	protected Position position;
	// vida que le queda; cuando llega a 0 el objeto muere
	protected int health;

	GameObject(GameWorld game, Position p, int health) {
		this.game = game;
		this.position = p;
		this.health = health;
	}

	// igual para todos: está vivo mientras le quede vida
	public boolean isAlive() {
		return health > 0;
	}

	public void receiveDamage(int damage) {
		health -= damage;
	}

	public boolean isInPosition(Position other) {
		return this.position.equals(other);
	}

	public Position getPosition() {
		return this.position;
	}

	abstract public String getIcon();

	abstract public String getDescription();

	/** Se llama cuando el objeto entra en el juego. */
	abstract public void onEnter();

	/** Se llama en cada ciclo del juego. */
	abstract public void update();

	/** Se llama cuando el objeto sale del juego, desapareciendo. */
	abstract public void onExit();
}
