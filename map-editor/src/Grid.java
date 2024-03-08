import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedHashMap;
import java.util.Map;

public class Grid {
    private Map<String, Cell> cellList;
    private int cols;
    private int rows;
    public static final int CELL_SIZE = 30;
    public static final int PADDING = 10;
    private MapEditor mapEditor;

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        cellList = new LinkedHashMap<>();
        createGrid();
    }

    public MapEditor getMapEditor() {
        return mapEditor;
    }

    public void setMapEditor(MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    public int getCols() {
        return cols;
    }

    public Map<String, Cell> getCellList() {
        return cellList;
    }

    public int getRows() {
        return rows;
    }

    public void createGrid() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Cell cell = new Cell(i, j);
                cellList.put(cell.getKey(), cell);
            }
        }
    }

    public void clear() {
        for (String key : cellList.keySet()) {
            cellList.get(key).clear();
        }
    }

    public void paintCell(String key, Color color) {
        if (mapEditor.isPainting()) {
            cellList.get(key).paint(color);
        }
    }
}
