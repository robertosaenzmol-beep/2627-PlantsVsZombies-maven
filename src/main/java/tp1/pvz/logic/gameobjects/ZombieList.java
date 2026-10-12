package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.Game;

public class ZombieList {

    private static final int MAX_ZOMBIES = 32;
    private Zombie[] zombies;
    private int count;

    public ZombieList() {
        this.zombies = new Zombie[MAX_ZOMBIES];
        this.count = 0;
    }

    public void add(Zombie z) {
        if (this.count < MAX_ZOMBIES) {
            this.zombies[this.count] = z;
            this.count++;
        }
    }

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.zombies[i].update();
        }
    }

    public boolean isPositionOccupied(int col, int row) {
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isInPosition(col, row)) {
                return true;
            }
        }
        return false;
    }

    public Zombie getObjectInPosition(int col, int row) {
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isInPosition(col, row)) {
                return this.zombies[i];
            }
        }
        return null;
    }

    // Find the nearest zombie to the right of fromCol in the given row
    public Zombie getNearestZombieInRow(int row, int fromCol) {
        Zombie nearest = null;
        int nearestCol = Game.NUM_COLS;
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].getRow() == row
                    && this.zombies[i].getCol() > fromCol
                    && this.zombies[i].getCol() < nearestCol) {
                nearest = this.zombies[i];
                nearestCol = this.zombies[i].getCol();
            }
        }
        return nearest;
    }

    public boolean hasZombieReachedEnd() {
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].hasReachedEnd()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAliveZombies() {
        return this.count > 0;
    }

    public void removeDead() {
        int alive = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isAlive()) {
                this.zombies[alive] = this.zombies[i];
                alive++;
            }
        }
        for (int i = alive; i < this.count; i++) {
            this.zombies[i] = null;
        }
        this.count = alive;
    }

    public void reset() {
        for (int i = 0; i < this.count; i++) {
            this.zombies[i] = null;
        }
        this.count = 0;
    }
}
