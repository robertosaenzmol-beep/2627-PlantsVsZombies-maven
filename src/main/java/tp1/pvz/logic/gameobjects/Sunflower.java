package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.GameWorld;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Sunflower extends Plant {
    public static final int COST = 20;
    public static final int DAMAGE = 0;
    public static final int ENDURANCE = 1;
    private static final int SUN_COINS_GENERATED = 10;
    private static final int GENERATION_FREQUENCY = 3;

    private int cycleCounter;
    private boolean firstCycle;

    public Sunflower(Position pos, GameWorld game) {
        super(game, pos, ENDURANCE);
        cycleCounter = 0;
        firstCycle = true;
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void update() {
        if (!isAlive()) return;

        if (firstCycle) {
            firstCycle = false;
            return;
        }
        cycleCounter++;
        if (cycleCounter % GENERATION_FREQUENCY == 0) {
            game.addSunCoins(SUN_COINS_GENERATED);
        }
    }

    @Override
    public void onExit() {
    }



    @Override
    public String getIcon() {
        return String.format(Messages.SUNFLOWER_ICON, this.health);
    }

    @Override
    public String getDescription() {
        return String.format(Messages.SUNFLOWER_DESCRIPTION, COST, DAMAGE, ENDURANCE);
    }

    @Override
    public int getCost() { return COST; }

    @Override
    public int getDamage() { return DAMAGE; }

    @Override
    public int getEndurance() { return ENDURANCE; }
}
