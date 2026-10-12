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

	public ZombiesManager(Game game, Level level, Random rand){
		this.game = game;
		this.level = level;
		this.rand = rand;
		remainingZombies = level.getNumberOfZombies();
		zombies = new ZombieList();
	}

	private boolean shouldAddZombie(){
		return rand.nextDouble() < level.getZombieFrequency();
	}

	private int randomZombieRow(){
		return rand.nextInt(Game.NUM_ROWS);
	}

	public boolean addZombie() {
		// always call both random methods to keep seed in sync
		int row = randomZombieRow();
		boolean shouldAdd = shouldAddZombie();

		boolean canAdd = getRemainingZombies() > 0  && shouldAdd
				&& isPositionEmpty(Game.NUM_COLS, row);
		if (canAdd) {
			Zombie z = new Zombie(new Position(row, Game.NUM_COLS), game);
			zombies.add(z);
			remainingZombies--;
		}
		return canAdd;
	}

	public int getRemainingZombies() {
		return remainingZombies;
	}

	public boolean isPositionEmpty(int col, int row){
		return !zombies.isPositionOccupied(col, row);
	}

	public void update() { zombies.update(); }

	public void removeDead() { zombies.removeDead(); }

	public boolean hasZombieReachedEnd() {
		return zombies.hasZombieReachedEnd();
	}

	public boolean hasAliveZombies(){
		return zombies.hasAliveZombies();
	}

	public Zombie getNearestZombieInRow(int row, int fromCol) {
		return zombies.getNearestZombieInRow(row, fromCol);
	}

	public boolean isPositionOccupiedByZombie(int col, int row){
		return zombies.isPositionOccupied(col, row);
	}

	public String getZombieIconInPosition(int col, int row) {
		Zombie z = zombies.getObjectInPosition(col, row);
		if(z != null) return z.getIcon();
		return "";
	}

	public void reset(){
		remainingZombies = level.getNumberOfZombies();
		zombies.reset();
	}
}
