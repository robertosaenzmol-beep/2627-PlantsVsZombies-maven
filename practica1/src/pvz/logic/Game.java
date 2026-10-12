package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.PeashooterList;
import pvz.logic.gameobjects.Sunflower;
import pvz.logic.gameobjects.SunflowerList;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Lógica del juego (la M de MVC).
 *
 * <p>Guarda el estado de la partida (ciclos, soles, plantas y zombis) y ofrece
 * los métodos con los que el controlador y los objetos del juego lo consultan
 * y lo modifican.
 */
public class Game {

	public static final int NUM_ROWS = 4;

	public static final int NUM_COLS = 8;

	public static final int INITIAL_COINS = 50;

	private long seed;

	private Level level;

	private Random rand;

	private int cycles;

	private int coins;

	private boolean playerQuits;

	private SunflowerList sunflowers;

	private PeashooterList peashooters;

	private ZombiesManager zombiesManager;

	public Game(long seed, Level level) {
		this.seed = seed;
		this.level = level;
		reset();
	}

	/**
	 * Deja la partida en su configuración inicial. Se vuelve a crear el
	 * generador aleatorio con la misma semilla para que la partida se repita
	 * exactamente igual.
	 */
	public void reset() {
		this.rand = new Random(seed);
		this.cycles = 0;
		this.coins = INITIAL_COINS;
		this.playerQuits = false;
		this.sunflowers = new SunflowerList();
		this.peashooters = new PeashooterList();
		this.zombiesManager = new ZombiesManager(this, level, rand);
	}

	public int getCycles() {
		return cycles;
	}

	public int getCoins() {
		return coins;
	}

	public int getRemainingZombies() {
		return zombiesManager.getRemainingZombies();
	}

	public void addCoins(int amount) {
		coins += amount;
	}

	public void quit() {
		playerQuits = true;
	}

	public boolean playerQuits() {
		return playerQuits;
	}

	public boolean zombiesWin() {
		return zombiesManager.zombieReachedEnd();
	}

	public boolean playerWins() {
		return !zombiesWin() && zombiesManager.allZombiesDead();
	}

	public boolean hasGameFinished() {
		return playerQuits || zombiesWin() || playerWins();
	}

	public boolean isInsideBoard(Position position) {
		return position.getRow() >= 0 && position.getRow() < NUM_ROWS
				&& position.getCol() >= 0 && position.getCol() < NUM_COLS;
	}

	/**
	 * @return <code>true</code> si en esa casilla no hay ni planta ni zombi.
	 */
	public boolean isEmpty(Position position) {
		return sunflowers.getInPosition(position) == null
				&& peashooters.getInPosition(position) == null
				&& !zombiesManager.isZombieInPosition(position);
	}

	/**
	 * @return <code>true</code> si el nombre corresponde a una planta que se puede añadir.
	 */
	public boolean checkGameObject(String name) {
		return Sunflower.matches(name) || Peashooter.matches(name);
	}

	/**
	 * Añade una planta al tablero y cobra su coste.
	 *
	 * @return <code>true</code> si se ha añadido; <code>false</code> si el nombre
	 *         no es de una planta, la casilla no es válida o no hay soles suficientes.
	 */
	public boolean addGameObject(String name, Position position) {
		if (!isInsideBoard(position) || !isEmpty(position)) {
			return false;
		}
		if (Sunflower.matches(name) && coins >= Sunflower.COST) {
			sunflowers.add(new Sunflower(position, this));
			coins -= Sunflower.COST;
			return true;
		}
		if (Peashooter.matches(name) && coins >= Peashooter.COST) {
			peashooters.add(new Peashooter(position, this));
			coins -= Peashooter.COST;
			return true;
		}
		return false;
	}

	/**
	 * Un lanzaguisantes dispara desde su posición: daña al primer zombi que
	 * tenga a su derecha.
	 */
	public void attackZombie(Position from, int damage) {
		zombiesManager.attackFirstZombie(from, damage);
	}

	/**
	 * Un zombi muerde la casilla indicada.
	 *
	 * @return <code>true</code> si en esa casilla había una planta.
	 */
	public boolean attackPlant(Position position, int damage) {
		Sunflower sunflower = sunflowers.getInPosition(position);
		if (sunflower != null) {
			sunflower.receiveDamage(damage);
			return true;
		}
		Peashooter peashooter = peashooters.getInPosition(position);
		if (peashooter != null) {
			peashooter.receiveDamage(damage);
			return true;
		}
		return false;
	}

	/**
	 * Avanza un ciclo: primero el ordenador decide si aparece un zombi, luego
	 * se actualizan girasoles, lanzaguisantes y zombis (en ese orden) y al
	 * final se retiran los muertos.
	 */
	public void update() {
		zombiesManager.addZombie();

		sunflowers.update();
		peashooters.update();
		zombiesManager.update();

		sunflowers.removeDead();
		peashooters.removeDead();
		zombiesManager.removeDead();

		cycles++;
	}

	/**
	 * @return el texto que hay que pintar en una casilla del tablero.
	 */
	public String positionToString(Position position) {
		Sunflower sunflower = sunflowers.getInPosition(position);
		if (sunflower != null) {
			return sunflower.getIcon();
		}
		Peashooter peashooter = peashooters.getInPosition(position);
		if (peashooter != null) {
			return peashooter.getIcon();
		}
		String zombieIcon = zombiesManager.positionToString(position);
		if (zombieIcon != null) {
			return zombieIcon;
		}
		return Messages.EMPTY_STRING;
	}
}
