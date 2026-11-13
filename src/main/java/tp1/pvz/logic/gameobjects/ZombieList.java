package tp1.pvz.logic.gameobjects;

import tp1.pvz.logic.GameWorld;
import tp1.pvz.utils.Position;

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
            z.onEnter();
        }
    }

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.zombies[i].update();
        }
    }

    public boolean isPositionOccupied(Position position) {
        return getObjectInPosition(position) != null;
    }

    public Zombie getObjectInPosition(Position position) {
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isInPosition(position)) {
                return this.zombies[i];
            }
        }
        return null;
    }

    // finds nearest zombie to the right of 'from' in the same row
    public Zombie getNearestZombieInRow(Position from) {
        Zombie nearest = null;
        int nearestCol = GameWorld.NUM_COLS;
        for (int i = 0; i < this.count; i++) {
            Position zp = this.zombies[i].getPosition();
            if (zp.getRow() == from.getRow()
                    && zp.getCol() > from.getCol()
                    && zp.getCol() < nearestCol) {
                nearest = this.zombies[i];
                nearestCol = zp.getCol();
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

    public boolean hasAliveZombies(){
        return count > 0;
    }

    public void removeDead(){
        int alive = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isAlive()) {
                this.zombies[alive] = this.zombies[i];
                alive++;
            } else {
                this.zombies[i].onExit();
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
