package tp1.pvz.logic.gameobjects;

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
        }
    }

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.sunflowers[i].update();
        }
    }

    public boolean isPositionOccupied(int col, int row) {
        for (int i = 0; i < this.count; i++) {
            if (this.sunflowers[i].isInPosition(col, row)) {
                return true;
            }
        }
        return false;
    }

    public Sunflower getObjectInPosition(int col, int row) {
        for (int i = 0; i < this.count; i++) {
            if (this.sunflowers[i].isInPosition(col, row)) {
                return this.sunflowers[i];
            }
        }
        return null;
    }

    public void receiveDamageInPosition(int col, int row, int damage) {
        Sunflower s = getObjectInPosition(col, row);
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
            }
        }
        // Clear leftover references
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
