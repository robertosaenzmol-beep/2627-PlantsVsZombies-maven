package tp1.pvz.logic.gameobjects;

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
        }
    }

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.peashooters[i].update();
        }
    }

    public boolean isPositionOccupied(int col, int row) {
        for (int i = 0; i < this.count; i++) {
            if (this.peashooters[i].isInPosition(col, row)) {
                return true;
            }
        }
        return false;
    }

    public Peashooter getObjectInPosition(int col, int row) {
        for (int i = 0; i < this.count; i++) {
            if (this.peashooters[i].isInPosition(col, row)) {
                return this.peashooters[i];
            }
        }
        return null;
    }

    public void receiveDamageInPosition(int col, int row, int damage) {
        Peashooter p = getObjectInPosition(col, row);
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
