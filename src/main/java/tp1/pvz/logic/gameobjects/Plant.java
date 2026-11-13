package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.GameWorld;
import tp1.pvz.utils.Position;

/**
 * Lo común a todas las plantas: tienen coste, daño y resistencia.
 * Cada planta concreta dice sus valores.
 */
public abstract class Plant extends GameObject {

	Plant(GameWorld game, Position pos, int endurance) {
		super(game, pos, endurance);
	}

	public abstract int getCost();

	public abstract int getDamage();

	public abstract int getEndurance();
}
