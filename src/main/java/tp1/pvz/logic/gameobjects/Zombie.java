package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.Game;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Zombie {
    public static final int DAMAGE = 1;
    public static final int ENDURANCE = 5;
    private static final int SPEED = 2;

    private int col;
    private int row;
    private int health;
    private Game game;
    private int cycleCounter;

    public Zombie(Position pos, Game game) {
        this.col = pos.getCol();
        this.row = pos.getRow();
        this.health = ENDURANCE;
        this.game = game;
        this.cycleCounter = 0;
    }

    public void update() {
        if (!isAlive()) return;

        int targetCol = this.col - 1;
        boolean blocked = this.game.isPositionOccupied(targetCol, this.row);

        if (blocked) {
            this.game.attackPlantInPosition(targetCol, this.row, DAMAGE);
        } else {
            if (this.cycleCounter == SPEED) {
                this.col = targetCol;
                this.cycleCounter = 0;

                int newTarget = this.col - 1;
                if (this.game.isPositionOccupied(newTarget, this.row)) {
                    this.game.attackPlantInPosition(newTarget, this.row, DAMAGE);
                }
            }
            this.cycleCounter++;
        }
    }

    public void receiveAttack(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isInPosition(int col, int row) {
        return this.col == col && this.row == row;
    }

    public boolean isInPosition(Position pos) {
        return isInPosition(pos.getCol(), pos.getRow());
    }

    public boolean hasReachedEnd() {
        return this.col < 0;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public String getIcon() {
        return String.format(Messages.ZOMBIE_ICON, this.health);
    }
}
