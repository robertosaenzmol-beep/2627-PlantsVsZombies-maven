package tp1.pvz.logic.gameobjects;

import tp1.pvz.utils.Position;

public class SunflowerList {

    private static final int MAX_SUNFLOWERS = 32;
    private Sunflower[] sunflowers;
    private int count;

    public SunflowerList() {
        this.sunflowers = new Sunflower[MAX_SUNFLOWERS];
        this.count = 0;
    }

    public void add(Sunflower s) {
        if (this.count < MAX_SUNFLOWERS) {
            this.sunflowers[this.count] = s;
            this.count++;
            s.onEnter();
        }
    }

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.sunflowers[i].update();
        }
    }

    public boolean isPositionOccupied(Position position) {
        return getObjectInPosition(position) != null;
    }

    public Sunflower getObjectInPosition(Position position) {
        for (int i = 0; i < this.count; i++) {
            if (this.sunflowers[i].isInPosition(position)) {
                return this.sunflowers[i];
            }
        }
        return null;
    }

    public void receiveDamageInPosition(Position position, int damage) {
        Sunflower s = getObjectInPosition(position);
        if (s != null) {
            s.receiveDamage(damage);
        }
    }

    public void removeDead() {
        int alive = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.sunflowers[i].isAlive()) {
                this.sunflowers[alive] = this.sunflowers[i];
                alive++;
            } else {
                this.sunflowers[i].onExit();
            }
        }
        for (int i = alive; i < this.count; i++) {
            this.sunflowers[i] = null;
        }
        this.count = alive;
    }

    public void reset() {
        for (int i = 0; i < this.count; i++) {
            this.sunflowers[i] = null;
        }
        this.count = 0;
    }
}
