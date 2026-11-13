package tp1.pvz.logic;

import java.util.Random;

import tp1.pvz.control.Level;
import tp1.pvz.logic.gameobjects.*;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

/**
 * Clase principal de la lógica del juego. Se usa desde distintas partes de la
 * aplicación, pero cada una la ve a través de la interfaz que le corresponde:
 * <ul>
 * <li>{@link GameStatus}: la vista (GamePrinter) para pintar el estado.</li>
 * <li>{@link GameWorld}: los objetos del juego para interactuar con el mundo.</li>
 * <li>{@link GameModel}: el controlador para alterar la partida.</li>
 * </ul>
 */
public class Game implements GameStatus, GameWorld, GameModel {

	public static final int INITIAL_COINS = 50;

	private int cycle;
	private int sunCoins;
	private Level level;
	private Random rand;
	private long seed;
	private boolean playerQuits;
	private boolean zombiesWin;
	private boolean playerWins;

	private SunflowerList sunflowers;
	private PeashooterList peashooters;
	private ZombiesManager zombiesManager;

	public Game(long seed, Level level) {
		this.seed = seed;
		this.level = level;
		this.rand = new Random(seed);
		this.cycle = 0;
		this.sunCoins = INITIAL_COINS;
		this.playerQuits = false;
		this.zombiesWin = false;
		this.playerWins = false;
		this.sunflowers = new SunflowerList();
		this.peashooters = new PeashooterList();
		this.zombiesManager = new ZombiesManager(this, level, this.rand);
	}

	// --- GameModel: alter the state of the game ---

	@Override
	public void update() {
		// try to add a zombie
		this.zombiesManager.addZombie();

		// update all game objects
		this.sunflowers.update();
		this.peashooters.update();
		this.zombiesManager.update();

		// remove dead
		sunflowers.removeDead();
		peashooters.removeDead();
		zombiesManager.removeDead();

		// check win/lose
		if (zombiesManager.hasZombieReachedEnd()) {
			this.zombiesWin = true;
		} else if (!zombiesManager.hasAliveZombies()
				&& zombiesManager.getRemainingZombies() == 0) {
			this.playerWins = true;
		}
		this.cycle++;
	}

	public boolean addPlant(String plantName, int col, int row) {
		if (!isInsideBoard(col, row)) {
			return false;
		}
		if (!isPositionEmpty(col, row)) {
			return false;
		}
		if (checkGameObject(plantName, "sunflower", "s")) {
			if (this.sunCoins < Sunflower.COST) {
				return false;
			}
			this.sunflowers.add(new Sunflower(new Position(row, col), this));
			this.sunCoins -= Sunflower.COST;
			return true;
		} else if (checkGameObject(plantName, "peashooter", "p")) {
			if (this.sunCoins < Peashooter.COST) {
				return false;
			}
			this.peashooters.add(new Peashooter(new Position(row, col), this));
			this.sunCoins -= Peashooter.COST;
			return true;
		}
		return false;
	}

	public boolean addGameObject(String plantName, Position pos){
		return addPlant(plantName, pos.getCol(), pos.getRow());
	}

	public boolean checkGameObject(String input, String fullName, String shortName){
		return input.equalsIgnoreCase(fullName) || input.equalsIgnoreCase(shortName);
	}

	public boolean checkGameObject(String input) {
		return checkGameObject(input, "sunflower", "s")
				|| checkGameObject(input, "peashooter", "p");
	}

	@Override
	public boolean tryAddPlant(String plantName, int col, int row) {
		if (!isInsideBoard(col, row)) {
			throw new IllegalArgumentException(Messages.INVALID_POSITION);
		}
		if (!isPositionEmpty(col, row)) {
			throw new IllegalArgumentException(Messages.INVALID_POSITION);
		}
		if (!checkGameObject(plantName)) {
			throw new IllegalArgumentException(Messages.INVALID_GAME_OBJECT);
		}

		int cost = 0;
		if (checkGameObject(plantName, "sunflower", "s")) {
			cost = Sunflower.COST;
		} else {
			cost = Peashooter.COST;
		}

		if (this.sunCoins < cost) {
			throw new IllegalArgumentException(Messages.NOT_ENOUGH_COINS);
		}

		if (checkGameObject(plantName, "sunflower", "s")) {
			this.sunflowers.add(new Sunflower(new Position(row, col), this));
		} else {
			this.peashooters.add(new Peashooter(new Position(row, col), this));
		}
		this.sunCoins -= cost;
		return true;
	}

	@Override
	public void quit() {
		this.playerQuits = true;
	}

	@Override
	public void reset() {
		this.cycle = 0;
		this.sunCoins = INITIAL_COINS;
		this.playerQuits = false;
		this.zombiesWin = false;
		this.playerWins = false;
		this.rand = new Random(this.seed);
		this.sunflowers.reset();
		this.peashooters.reset();
		this.zombiesManager = new ZombiesManager(this, this.level, this.rand);
	}

	@Override
	public boolean hasGameFinished() {
		return this.playerQuits || this.zombiesWin || this.playerWins;
	}

	// --- GameWorld: what the game objects need ---

	@Override
	public boolean isInsideBoard(Position pos) {
		return isInsideBoard(pos.getCol(), pos.getRow());
	}

	@Override
	public boolean isEmpty(Position pos) {
		return isPositionEmpty(pos.getCol(), pos.getRow());
	}

	@Override
	public void addSunCoins(int coins) {
		this.sunCoins += coins;
	}

	@Override
	public void attackZombieInRow(Position from, int damage) {
		Zombie z = zombiesManager.getNearestZombieInRow(from);
		if (z != null) {
			z.receiveDamage(damage);
		}
	}

	@Override
	public void attackPlantInPosition(Position position, int damage) {
		sunflowers.receiveDamageInPosition(position, damage);
		peashooters.receiveDamageInPosition(position, damage);
	}

	// --- GameStatus: what the view needs ---

	@Override
	public int getCycles() {
		return this.cycle;
	}

	@Override
	public int getCoins() {
		return this.sunCoins;
	}

	@Override
	public int getRemainingZombies() {
		return this.zombiesManager.getRemainingZombies();
	}

	@Override
	public boolean getPlayerWins() {
		return this.playerWins;
	}

	@Override
	public boolean getZombiesWin() {
		return this.zombiesWin;
	}

	@Override
	public boolean playerQuits() {
		return this.playerQuits;
	}

	@Override
	public String positionToString(Position pos) {
		Sunflower s = sunflowers.getObjectInPosition(pos);
		if (s != null) return s.getIcon();

		Peashooter p = peashooters.getObjectInPosition(pos);
		if (p != null) return p.getIcon();

		String zIcon = zombiesManager.getZombieIconInPosition(pos);
		if (!zIcon.isEmpty()) return zIcon;

		return "";
	}

	// --- Helpers ---

	public boolean isInsideBoard(int col, int row) {
		return col >= 0 && col < NUM_COLS && row >= 0 && row < NUM_ROWS;
	}

	public boolean isPositionEmpty(int col, int row) {
		Position pos = new Position(row, col);
		return !this.sunflowers.isPositionOccupied(pos)
				&& !this.peashooters.isPositionOccupied(pos)
				&& this.zombiesManager.isPositionEmpty(pos);
	}

	public Level getLevel() {
		return this.level;
	}
}
