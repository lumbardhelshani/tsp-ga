package Models;

public class City {

    private int id;
    private int x, y;


    public City(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return id + " (" + x + ", " + y + ")";
    }
}