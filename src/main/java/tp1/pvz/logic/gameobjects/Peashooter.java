package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.Game;
import tp1.pvz.utils.Position;
import tp1.pvz.view.Messages;

public class Peashooter {
    public static final int COST = 50;
    public static final int DAMAGE = 1;
    public static final int ENDURANCE = 3;

    private int col;
    private int row;
    private int health;
    private Game game;

    public Peashooter(Position pos, Game game) {
        this.col = pos.getCol();
        this.row = pos.getRow();
        this.health = ENDURANCE;
        this.game = game;
    }

    public void update() {
        // Shoots the nearest zombie to its right in the same row
        this.game.attackZombieInRow(this.row, this.col, DAMAGE);
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isInPosition(int col, int row) {
        return this.col == col && this.row == row;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public String getIcon() {
        return String.format(Messages.PEASHOOTER_ICON, this.health);
    }

    public static String getDescription() {
        return String.format(Messages.PEASHOOTER_DESCRIPTION, COST, DAMAGE, ENDURANCE);
    }
}
