import java.io.*;

public class Point implements Serializable{

    public int x;
    public int y;
    public int type;

    public Point(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type+
                "}";
    }

}
