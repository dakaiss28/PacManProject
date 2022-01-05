package pacman;

import java.util.Objects;

public class Coordinates {

    private int x;
    private int y;

    //===============// Getters & setters //===============//

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //===============// Object Inherited Method //===============//

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Classes.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}
