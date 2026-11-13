package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.GameWorld;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Peashooter extends Plant {
    public static final int COST = 50;
    public static final int DAMAGE = 1;
    public static final int ENDURANCE = 3;


    public Peashooter(Position pos, GameWorld game) {
        super(game, pos, ENDURANCE);
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void update() {
        game.attackZombieInRow(position, DAMAGE);
    }

    @Override
    public void onExit() {
    }



    @Override
    public String getIcon() {
        return String.format(Messages.PEASHOOTER_ICON, this.health);
    }

    @Override
    public String getDescription() {
        return String.format(Messages.PEASHOOTER_DESCRIPTION, COST, DAMAGE, ENDURANCE);
    }

    @Override
    public int getCost() { return COST; }

    @Override
    public int getDamage() { return DAMAGE; }

    @Override
    public int getEndurance() { return ENDURANCE; }
}
