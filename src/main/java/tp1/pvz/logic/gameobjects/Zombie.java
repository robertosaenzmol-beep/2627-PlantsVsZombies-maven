package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.GameWorld;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Zombie extends GameObject {
    public static final int DAMAGE = 1;
    public static final int ENDURANCE = 5;
    private static final int SPEED = 2;

    private int cycleCounter;

    public Zombie(Position pos, GameWorld game) {
        super(game, pos, ENDURANCE);
        cycleCounter = 0;
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void update() {
        if (!isAlive()) return;

        Position target = new Position(position.getRow(), position.getCol() - 1);
        boolean blocked = !game.isEmpty(target);

        if (blocked) {
            game.attackPlantInPosition(target, DAMAGE);
        } else {
            if (cycleCounter == SPEED) {
                position = target;
                cycleCounter = 0;
                // check if theres a plant in the new position
                Position newTarget = new Position(position.getRow(), position.getCol() - 1);
                if (!game.isEmpty(newTarget)) {
                    game.attackPlantInPosition(newTarget, DAMAGE);
                }
            }
            cycleCounter++;
        }
    }

    @Override
    public void onExit() {
    }



    // los tests usan este nombre; el daño se resta igual que en cualquier objeto
    public void receiveAttack(int damage) {
        receiveDamage(damage);
    }

    public boolean hasReachedEnd() {
        return position.getCol() < 0;
    }

    @Override
    public String getIcon() {
        return String.format(Messages.ZOMBIE_ICON, this.health);
    }

    @Override
    public String getDescription() {
        return String.format(Messages.ZOMBIE_DESCRIPTION, SPEED, DAMAGE, ENDURANCE);
    }
}
