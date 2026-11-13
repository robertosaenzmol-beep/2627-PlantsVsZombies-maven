package tp1.pvz.logic.gameobjects;

import tp1.pvz.utils.Position;

public class PeashooterList {

    private static final int MAX_PEASHOOTERS = 32;
    private Peashooter[] peashooters;
    private int count;

    public PeashooterList() {
        this.peashooters = new Peashooter[MAX_PEASHOOTERS];
        this.count = 0;
    }

    public void add(Peashooter p) {
        if (this.count < MAX_PEASHOOTERS) {
            this.peashooters[this.count] = p;
            this.count++;
            p.onEnter();
        }
    }

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.peashooters[i].update();
        }
    }

    public boolean isPositionOccupied(Position position) {
        return getObjectInPosition(position) != null;
    }

    public Peashooter getObjectInPosition(Position position) {
        for (int i = 0; i < this.count; i++) {
            if (this.peashooters[i].isInPosition(position)) {
                return this.peashooters[i];
            }
        }
        return null;
    }

    public void receiveDamageInPosition(Position position, int damage) {
        Peashooter p = getObjectInPosition(position);
        if (p != null) {
            p.receiveDamage(damage);
        }
    }

    public void removeDead() {
        int alive = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.peashooters[i].isAlive()) {
                this.peashooters[alive] = this.peashooters[i];
                alive++;
            } else {
                this.peashooters[i].onExit();
            }
        }
        for (int i = alive; i < this.count; i++) {
            this.peashooters[i] = null;
        }
        this.count = alive;
    }

    public void reset() {
        for (int i = 0; i < this.count; i++) {
            this.peashooters[i] = null;
        }
        this.count = 0;
    }
}
