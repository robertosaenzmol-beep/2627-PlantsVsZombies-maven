package tp1.pvz.logic;

import java.util.Random;

import tp1.pvz.control.Level;
import tp1.pvz.logic.gameobjects.*;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Game {

	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
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

	// --- Called by the Controller ---

	public void update() {
		// 1. Game action: try to add a zombie
		this.zombiesManager.addZombie();

		// 2. Update all game objects (order: sunflowers, peashooters, zombies)
		this.sunflowers.update();
		this.peashooters.update();
		this.zombiesManager.update();

		// 3. Remove dead objects
		this.sunflowers.removeDead();
		this.peashooters.removeDead();
		this.zombiesManager.removeDead();

		// 4. Check win/lose conditions
		if (this.zombiesManager.hasZombieReachedEnd()) {
			this.zombiesWin = true;
		} else if (!this.zombiesManager.hasAliveZombies()
				&& this.zombiesManager.getRemainingZombies() == 0) {
			this.playerWins = true;
		}

		// 5. Advance cycle
		this.cycle++;
	}

	// --- Plant placement ---

	public boolean addPlant(String plantName, int col, int row) {
		// Check valid position
		if (!isInsideBoard(col, row)) {
			return false;
		}

		// Check position is empty
		if (!isPositionEmpty(col, row)) {
			return false;
		}

		// Check which plant and if enough coins
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

	public boolean checkGameObject(String input, String fullName, String shortName) {
		return input.equalsIgnoreCase(fullName) || input.equalsIgnoreCase(shortName);
	}

	public boolean tryAddPlant(String plantName, int col, int row) {
		if (!isInsideBoard(col, row)) {
			throw new IllegalArgumentException(Messages.INVALID_POSITION);
		}
		if (!isPositionEmpty(col, row)) {
			throw new IllegalArgumentException(Messages.INVALID_POSITION);
		}
		if (!checkGameObject(plantName, "sunflower", "s")
				&& !checkGameObject(plantName, "peashooter", "p")) {
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

	// --- Position checks ---

	public boolean isInsideBoard(int col, int row) {
		return col >= 0 && col < NUM_COLS && row >= 0 && row < NUM_ROWS;
	}

	public boolean isPositionEmpty(int col, int row) {
		return !this.sunflowers.isPositionOccupied(col, row)
				&& !this.peashooters.isPositionOccupied(col, row)
				&& this.zombiesManager.isPositionEmpty(col, row);
	}

	// Is there a plant or zombie at this position? (used by Zombie to check if it can move)
	public boolean isPositionOccupied(int col, int row) {
		return this.sunflowers.isPositionOccupied(col, row)
				|| this.peashooters.isPositionOccupied(col, row)
				|| this.zombiesManager.isPositionOccupiedByZombie(col, row);
	}

	// --- Combat methods (called by game objects) ---

	public void attackZombieInRow(int row, int fromCol, int damage) {
		Zombie z = this.zombiesManager.getNearestZombieInRow(row, fromCol);
		if (z != null) {
			z.receiveAttack(damage);
		}
	}

	public void attackPlantInPosition(int col, int row, int damage) {
		// Try sunflower first, then peashooter
		this.sunflowers.receiveDamageInPosition(col, row, damage);
		this.peashooters.receiveDamageInPosition(col, row, damage);
	}

	// --- Sun coins ---

	public void addSunCoins(int coins) {
		this.sunCoins += coins;
	}

	public int getSunCoins() {
		return this.sunCoins;
	}

	// --- Game state ---

	public int getCycle() {
		return this.cycle;
	}

	public boolean isFinished() {
		return this.playerQuits || this.zombiesWin || this.playerWins;
	}

	public boolean getPlayerWins() {
		return this.playerWins;
	}

	public boolean getZombiesWin() {
		return this.zombiesWin;
	}

	public boolean getPlayerQuits() {
		return this.playerQuits;
	}

	public void quit() {
		this.playerQuits = true;
	}

	public int getRemainingZombies() {
		return this.zombiesManager.getRemainingZombies();
	}

	public Level getLevel() {
		return this.level;
	}

	// --- Display ---

	public String positionToString(Position pos) {
		int col = pos.getCol();
		int row = pos.getRow();

		// Check sunflower
		Sunflower s = this.sunflowers.getObjectInPosition(col, row);
		if (s != null) {
			return s.getIcon();
		}

		// Check peashooter
		Peashooter p = this.peashooters.getObjectInPosition(col, row);
		if (p != null) {
			return p.getIcon();
		}

		// Check zombie
		String zIcon = this.zombiesManager.getZombieIconInPosition(col, row);
		if (!zIcon.isEmpty()) {
			return zIcon;
		}

		return "";
	}

	// --- Reset ---

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
}
