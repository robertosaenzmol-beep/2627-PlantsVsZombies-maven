package tp1.pvz.logic;

import java.util.Random;

import tp1.pvz.control.Level;
import tp1.pvz.logic.gameobjects.Zombie;
import tp1.pvz.logic.gameobjects.ZombieList;
import tp1.pvz.utils.Position;

public class ZombiesManager {

	private GameWorld game;
	private Level level;
	private Random rand;
	private int remainingZombies;
	private ZombieList zombies;

	public ZombiesManager(GameWorld game, Level level, Random rand){
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
		return rand.nextInt(GameWorld.NUM_ROWS);
	}

	public boolean addZombie() {
		int row = randomZombieRow();
		boolean shouldAdd = shouldAddZombie();

		Position position = new Position(row, GameWorld.NUM_COLS);
		boolean canAdd = getRemainingZombies() > 0  && shouldAdd
				&& isPositionEmpty(position);
		if (canAdd) {
			Zombie z = new Zombie(position, game);
			zombies.add(z);
			remainingZombies--;
		}
		return canAdd;
	}

	public int getRemainingZombies() {
		return remainingZombies;
	}

	public boolean isPositionEmpty(Position position){
		return !zombies.isPositionOccupied(position);
	}

	public void update() { zombies.update(); }

	public void removeDead() { zombies.removeDead(); }

	public boolean hasZombieReachedEnd() {
		return zombies.hasZombieReachedEnd();
	}

	public boolean hasAliveZombies(){
		return zombies.hasAliveZombies();
	}

	public Zombie getNearestZombieInRow(Position from) {
		return zombies.getNearestZombieInRow(from);
	}

	public String getZombieIconInPosition(Position position) {
		Zombie z = zombies.getObjectInPosition(position);
		if(z != null) return z.getIcon();
		return "";
	}

	public void reset(){
		remainingZombies = level.getNumberOfZombies();
		zombies.reset();
	}
}
