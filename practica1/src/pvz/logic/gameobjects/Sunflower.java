package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Girasol: planta que no ataca, pero genera soles cada cierto número de ciclos.
 */
public class Sunflower {

	public static final String NAME = "sunflower";

	public static final String SHORTCUT = "s";

	public static final int COST = 20;

	public static final int ENDURANCE = 1;

	public static final int DAMAGE = 0;

	/** Ciclos que tarda en generar soles. */
	public static final int COOLDOWN = 3;

	/** Soles que genera cada vez. */
	public static final int GENERATED_COINS = 10;

	private Position position;

	private int health;

	/** Ciclos transcurridos desde la última vez que generó soles. */
	private int cycles;

	private Game game;

	public Sunflower(Position position, Game game) {
		this.position = position;
		this.game = game;
		this.health = ENDURANCE;
		this.cycles = 0;
	}

	public static String getDescription() {
		return String.format(Messages.SUNFLOWER_DESCRIPTION, COST, DAMAGE, ENDURANCE);
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
		return String.format(Messages.SUNFLOWER_ICON, health);
	}

	/**
	 * Lo que hace el girasol en cada ciclo: contar, y cuando han pasado
	 * COOLDOWN ciclos, dar soles al jugador y volver a empezar la cuenta.
	 */
	public void update() {
		if (!isAlive()) {
			return;
		}
		if (cycles == COOLDOWN) {
			game.addCoins(GENERATED_COINS);
			cycles = 0;
		}
		cycles++;
	}
}
