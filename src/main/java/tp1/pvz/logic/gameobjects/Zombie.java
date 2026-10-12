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

    public Zombie(Position pos, Game game){
        this.col = pos.getCol();
        this.row = pos.getRow();
        this.health = ENDURANCE;
        this.game = game;
        cycleCounter = 0;
    }

    public void update() {
        if (!isAlive()) return;

        int targetCol = col - 1;
        boolean blocked = game.isPositionOccupied(targetCol, row);

        if (blocked) {
            game.attackPlantInPosition(targetCol, row, DAMAGE);
        } else {
            if (cycleCounter == SPEED) {
                col = targetCol;
                cycleCounter = 0;
                // check if theres a plant in the new position
                int newTarget = col - 1;
                if (game.isPositionOccupied(newTarget, row)) {
                    game.attackPlantInPosition(newTarget, row, DAMAGE);
                }
            }
            cycleCounter++;
        }
    }

    public void receiveAttack(int damage){
        this.health -= damage;
    }

    public boolean isAlive(){
        return health > 0;
    }

    public boolean isInPosition(int col, int row){
        return this.col == col && this.row == row;
    }

    public boolean isInPosition(Position pos){
        return isInPosition(pos.getCol(), pos.getRow());
    }

    public boolean hasReachedEnd(){
        return col < 0;
    }

    public int getCol() { return col; }

    public int getRow() { return row; }

    public String getIcon() {
        return String.format(Messages.ZOMBIE_ICON, this.health);
    }
}
