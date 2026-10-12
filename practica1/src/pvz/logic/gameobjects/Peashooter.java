package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Lanzaguisantes: planta que dispara cada ciclo al primer zombi que tenga
 * a su derecha en su misma fila.
 */
public class Peashooter {

	public static final String NAME = "peashooter";

	public static final String SHORTCUT = "p";

	public static final int COST = 50;

	public static final int ENDURANCE = 3;

	public static final int DAMAGE = 1;

	private Position position;

	private int health;

	private Game game;

	public Peashooter(Position position, Game game) {
		this.position = position;
		this.game = game;
		this.health = ENDURANCE;
	}

	public static String getDescription() {
		return String.format(Messages.PEASHOOTER_DESCRIPTION, COST, DAMAGE, ENDURANCE);
	}

	/**
	 * Comprueba si el texto escrito por el usuario se refiere a esta planta.
	 */
	public static boolean matches(String name) {
		return NAME.equalsIgnoreCase(name) || SHORTCUT.equalsIgnoreCase(name);
	}

	public boolean isAlive() {
		return health > 0;
	}

	public boolean isInPosition(Position other) {
		return position.equals(other);
	}

	public void receiveDamage(int damage) {
		health -= damage;
	}

	public String getIcon() {
		return String.format(Messages.PEASHOOTER_ICON, health);
	}

	/**
	 * Lo que hace el lanzaguisantes en cada ciclo: disparar. Es el juego
	 * quien sabe dónde están los zombis, así que se lo pide a él.
	 */
	public void update() {
		if (!isAlive()) {
			return;
		}
		game.attackZombie(position, DAMAGE);
	}
}
