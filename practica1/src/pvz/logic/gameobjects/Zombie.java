package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Zombi común: avanza hacia la izquierda una casilla cada SPEED ciclos y
 * muerde a la planta que tenga justo delante.
 */
public class Zombie {

	public static final int ENDURANCE = 5;

	public static final int DAMAGE = 1;

	/** Ciclos que tarda en avanzar una casilla. */
	public static final int SPEED = 2;

	private Position position;

	private int health;

	/** Ciclos transcurridos desde la última vez que avanzó. */
	private int cycles;

	private Game game;

	public Zombie(Position position, Game game) {
		this.position = position;
		this.game = game;
		this.health = ENDURANCE;
		this.cycles = 0;
	}

	public boolean isAlive() {
		return health > 0;
	}

	public boolean isInPosition(Position other) {
		return position.equals(other);
	}

	public boolean isInRow(int row) {
		return position.getRow() == row;
	}

	public int getCol() {
		return position.getCol();
	}

	/**
	 * Un zombi gana cuando sale del tablero por la izquierda (columna -1).
	 */
	public boolean hasReachedEnd() {
		return isAlive() && position.getCol() < 0;
	}

	public void receiveAttack(int damage) {
		health -= damage;
	}

	public String getIcon() {
		return String.format(Messages.ZOMBIE_ICON, health);
	}

	/**
	 * Lo que hace el zombi en cada ciclo: si ya le toca moverse y la casilla
	 * de su izquierda está libre, avanza. Después muerde a la planta que tenga
	 * justo a su izquierda, si hay alguna (mientras haya planta no puede avanzar).
	 */
	public void update() {
		if (!isAlive()) {
			return;
		}
		if (cycles >= SPEED && game.isEmpty(leftPosition())) {
			position = leftPosition();
			cycles = 0;
		}
		cycles++;
		game.attackPlant(leftPosition(), DAMAGE);
	}

	private Position leftPosition() {
		return new Position(position.getRow(), position.getCol() - 1);
	}
}
