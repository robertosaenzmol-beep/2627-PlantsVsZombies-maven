package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.ZombieList;
import pvz.utils.Position;

/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries
 *
 */
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

	/**
	 * Checks if the game should add (if possible) a zombie to the game.
	 * 
	 * @return <code>true</code> if a zombie should be added to the game.
	 */
	private boolean shouldAddZombie() {
		return rand.nextDouble() < level.getZombieFrequency();
	}
	
	/**
	 * Return a random row within the board limits.
	 * 
	 * @return a random row.
	 */
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

		if(canAdd) {
			zombies.add(new Zombie(new Position(row, Game.NUM_COLS), game));
			remainingZombies--;
		}
		return canAdd;
	}

	public int getRemainingZombies() {
		return remainingZombies;
	}

	private boolean isPositionEmpty(int col, int row) {
		return game.isEmpty(new Position(row, col));
	}

	public boolean isZombieInPosition(Position position) {
		return zombies.getInPosition(position) != null;
	}

	/**
	 * @return the icon of the zombie in that position or <code>null</code> if there is none.
	 */
	public String positionToString(Position position) {
		Zombie zombie = zombies.getInPosition(position);
		if (zombie == null) {
			return null;
		}
		return zombie.getIcon();
	}

	/**
	 * Damages the closest zombie to the right of the given position, if any.
	 */
	public void attackFirstZombie(Position from, int damage) {
		Zombie zombie = zombies.getFirstToTheRight(from);
		if (zombie != null) {
			zombie.receiveAttack(damage);
		}
	}

	public void update() {
		zombies.update();
	}

	public void removeDead() {
		zombies.removeDead();
	}

	/**
	 * @return <code>true</code> if a zombie has left the board through the left side.
	 */
	public boolean zombieReachedEnd() {
		return zombies.anyReachedEnd();
	}

	/**
	 * @return <code>true</code> if there are no zombies left, neither to appear nor in the board.
	 */
	public boolean allZombiesDead() {
		return remainingZombies == 0 && zombies.isEmpty();
	}
}
