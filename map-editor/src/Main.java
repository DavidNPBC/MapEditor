import java.awt.*;

public class Main {
    public static final int COLS = 20;
    public static final int ROWS = 20;

    public static void main(String[] args) {
        Grid grid = new Grid(COLS,ROWS);
        MapEditor mapEditor = new MapEditor(grid);
    }
}