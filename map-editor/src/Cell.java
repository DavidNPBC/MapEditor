import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Cell {

    private boolean isPainted = false;
    private int col;
    private int row;
    private Rectangle rectangle;
    private String key;
    private Color color;


    public Cell(int col, int row) {
        this.col = col;
        this.row = row;
        key = col + " " + row;
        rectangle = new Rectangle(col * Grid.CELL_SIZE + Grid.PADDING, row * Grid.CELL_SIZE + Grid.PADDING, Grid.CELL_SIZE, Grid.CELL_SIZE);
        rectangle.draw();
    }

    public String getKey() {
        return key;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean isPainted() {
        return isPainted;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void clear() {
        rectangle.delete();
        rectangle.setColor(Color.BLACK);
        rectangle.draw();
        isPainted = false;
    }

    public void setPainted(boolean painted) {
        isPainted = painted;
    }

    public void paint(Color color) {
        Color prevColor = this.color;
        this.color = color;
        if (!isPainted) {
            rectangle.setColor(color);
            rectangle.fill();
            isPainted = true;
        } else {
            if (!color.equals(prevColor)) {
                rectangle.setColor(Color.BLACK);
                rectangle.draw();
                rectangle.setColor(color);
                rectangle.fill();
                isPainted = true;
                return;
            }
            rectangle.setColor(Color.BLACK);
            rectangle.draw();
            isPainted = false;
        }
    }
}
