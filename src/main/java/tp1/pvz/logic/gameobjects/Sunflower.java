package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.Game;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Sunflower {
    public static final int COST = 20;
    public static final int DAMAGE = 0;
    public static final int ENDURANCE = 1;
    private static final int SUN_COINS_GENERATED = 10;
    private static final int GENERATION_FREQUENCY = 3;

    private int col;
    private int row;
    private int health;
    private Game game;
    private int cycleCounter;
    private boolean firstCycle;

    public Sunflower(Position pos, Game game){
        this.col = pos.getCol();
        this.row = pos.getRow();
        this.health = ENDURANCE;
        this.game = game;
        cycleCounter = 0;
        firstCycle = true;
    }

    public void update() {
        if(!isAlive()) return;

        if (firstCycle) {
            firstCycle = false;
            return;
        }
        cycleCounter++;
        if (cycleCounter % GENERATION_FREQUENCY == 0) {
            game.addSunCoins(SUN_COINS_GENERATED);
        }
    }

    public void receiveDamage(int damage){
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isInPosition(int col, int row){
        return this.col == col && this.row == row;
    }

    public boolean isInPosition(Position pos) {
        return isInPosition(pos.getCol(), pos.getRow());
    }

    public int getCol() { return col; }

    public int getRow() { return row; }

    public String getIcon() {
        return String.format(Messages.SUNFLOWER_ICON, this.health);
    }

    public static String getDescription() {
        return String.format(Messages.SUNFLOWER_DESCRIPTION, COST, DAMAGE, ENDURANCE);
    }
}
