package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.Game;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Zombie {
    public static final int DAMAGE = 1;
    public static final int ENDURANCE = 5;
    private static final int SPEED = 2; // moves every 2 cycles

    private int col;
    private int row;
    private int health;
    private Game game;
    private int cycleCounter;
    private boolean firstCycle;

    public Zombie(Position pos, Game game) {
        this.col = pos.getCol();
        this.row = pos.getRow();
        this.health = ENDURANCE;
        this.game = game;
        this.cycleCounter = 0;
        this.firstCycle = true;
    }

    public void update() {
        if (this.firstCycle) {
            this.firstCycle = false;
            return;
        }
        this.cycleCounter++;
        if (this.cycleCounter % SPEED == 0) {
            // Try to move left
            int targetCol = this.col - 1;
            if (!this.game.isPositionOccupied(targetCol, this.row)) {
                // Move left
                this.col = targetCol;
            } else {
                // Can't move — attack the plant to the left
                this.game.attackPlantInPosition(targetCol, this.row, DAMAGE);
            }
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
