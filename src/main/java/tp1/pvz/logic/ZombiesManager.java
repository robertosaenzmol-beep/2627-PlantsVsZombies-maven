package tp1.pvz.logic;

import java.util.Random;

import tp1.pvz.control.Level;
import tp1.pvz.logic.gameobjects.Zombie;
import tp1.pvz.logic.gameobjects.ZombieList;
import tp1.pvz.utils.Position;

public class ZombiesManager {

	private Game game;
	private Level level;
	private Random rand;
	private int remainingZombies;
	private ZombieList zombies;

	public ZombiesManager(Game game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		this.remainingZombies = level.getNumberOfZombies();
		this.zombies = new ZombieList();
	}

	private boolean shouldAddZombie() {
		return rand.nextDouble() < level.getZombieFrequency();
	}

	private int randomZombieRow() {
		return rand.nextInt(Game.NUM_ROWS);
	}

	public boolean addZombie() {
		int row = randomZombieRow();
		return addZombie(row);
	}

	public boolean addZombie(int row) {
		boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie()
				&& isPositionEmpty(Game.NUM_COLS, row);

		if (canAdd) {
			// Zombies spawn outside the board at column NUM_COLS (one past the last visible column)
			Zombie z = new Zombie(new Position(row, Game.NUM_COLS), this.game);
			this.zombies.add(z);
			this.remainingZombies--;
		}
		return canAdd;
	}

	public int getRemainingZombies() {
		return this.remainingZombies;
	}

	public boolean isPositionEmpty(int col, int row) {
		return !this.zombies.isPositionOccupied(col, row);
	}

	public void update() {
		this.zombies.update();
	}

	public void removeDead() {
		this.zombies.removeDead();
	}

	public boolean hasZombieReachedEnd() {
		return this.zombies.hasZombieReachedEnd();
	}

	public boolean hasAliveZombies() {
		return this.zombies.hasAliveZombies();
	}

	public Zombie getNearestZombieInRow(int row, int fromCol) {
		return this.zombies.getNearestZombieInRow(row, fromCol);
	}

	public boolean isPositionOccupiedByZombie(int col, int row) {
		return this.zombies.isPositionOccupied(col, row);
	}

	public String getZombieIconInPosition(int col, int row) {
		Zombie z = this.zombies.getObjectInPosition(col, row);
		if (z != null) {
			return z.getIcon();
		}
		return "";
	}

	public void reset() {
		this.remainingZombies = this.level.getNumberOfZombies();
		this.zombies.reset();
	}
}
