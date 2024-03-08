import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapEditor {
    private Grid grid;
    private int col;
    private int row;
    private Rectangle frame;
    private Keyboard keyboard;
    private Color color = Color.BLACK;
    private Color[] colorArray = {Color.ORANGE, Color.BLUE, Color.GRAY, Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLACK};
    private int colorIterator;
    private boolean painting = false;
    private boolean isShowingCommandList;
    private Picture pic;
    private boolean isPlaying = true;

    public MapEditor(Grid grid) {
        this.grid = grid;
        grid.setMapEditor(this);
        col = Grid.PADDING;
        row = Grid.PADDING;
        frame = new Rectangle(col, row, Grid.CELL_SIZE, Grid.CELL_SIZE);
        setupKeyboard();
        showBlinkingCursor();
        pic = new Picture(Grid.PADDING * grid.getCols() / 2 , grid.getRows() * Grid.CELL_SIZE + Grid.CELL_SIZE, "resources/files/command-list/commands-list-img.png");
        isShowingCommandList = true;
        showCommandList();
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Grid getGrid() {
        return grid;
    }

    public Color getColor() {
        return color;
    }

    public boolean isPainting() {
        return painting;
    }

    public boolean isShowingCommandList() {
        return isShowingCommandList;
    }

    public void setPainting(boolean painting) {
        this.painting = painting;
    }

    public void setShowingCommandList(boolean showingCommandList) {
        isShowingCommandList = showingCommandList;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void changeColor() {
        color = colorArray[colorIterator];
        colorIterator++;
        frame.setColor(color);

        if (colorIterator == colorArray.length) {
            colorIterator = 0;
        }
    }

    public void showBlinkingCursor() {
        frame.fill();
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }

    public void showCommandList() {
        if (isShowingCommandList) {
            pic.draw();
        } else {
            pic.delete();
        }
    }

    public void moveLeft() {
        int prevCol = col;
        col = col - 1;
        if (col < Grid.PADDING) {
            col = Grid.PADDING;
        }
        frame.translate((col - prevCol) * Grid.CELL_SIZE, 0);
    }

    public void moveRight() {
        int prevCol = col;
        col = col + 1;

        if (col > grid.getCols() + Grid.PADDING - 1) {
            col = grid.getCols() + Grid.PADDING - 1;
        }
        frame.translate((col - prevCol) * Grid.CELL_SIZE, 0);
    }

    public void moveUp() {
        int prevRow = row;
        row = row - 1;
        if (row < Grid.PADDING) {
            row = Grid.PADDING;
        }
        frame.translate(0, (row - prevRow) * Grid.CELL_SIZE);
    }

    public void moveDown() {
        int prevRow = row;
        row = row + 1;
        if (row > grid.getRows() + Grid.PADDING - 1) {
            row = grid.getRows() + Grid.PADDING - 1;
        }

        frame.translate(0, (row - prevRow) * Grid.CELL_SIZE);
    }

    private void setupKeyboard() {

        keyboard = new Keyboard(new MyKeyboard(this));

        KeyboardEvent toggle = new KeyboardEvent();
        toggle.setKey(KeyboardEvent.KEY_SPACE);
        toggle.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(toggle);

        KeyboardEvent untoggle = new KeyboardEvent();
        untoggle.setKey(KeyboardEvent.KEY_SPACE);
        untoggle.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        keyboard.addEventListener(untoggle);

        KeyboardEvent left = new KeyboardEvent();
        left.setKey(KeyboardEvent.KEY_LEFT);
        left.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(left);

        KeyboardEvent right = new KeyboardEvent();
        right.setKey(KeyboardEvent.KEY_RIGHT);
        right.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(right);

        KeyboardEvent up = new KeyboardEvent();
        up.setKey(KeyboardEvent.KEY_UP);
        up.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(up);

        KeyboardEvent down = new KeyboardEvent();
        down.setKey(KeyboardEvent.KEY_DOWN);
        down.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(down);

        KeyboardEvent save = new KeyboardEvent();
        save.setKey(KeyboardEvent.KEY_S);
        save.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(save);

        KeyboardEvent quit = new KeyboardEvent();
        quit.setKey(KeyboardEvent.KEY_Q);
        quit.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(quit);

        KeyboardEvent clear = new KeyboardEvent();
        clear.setKey(KeyboardEvent.KEY_C);
        clear.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(clear);

        KeyboardEvent load = new KeyboardEvent();
        load.setKey(KeyboardEvent.KEY_L);
        load.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(load);

        KeyboardEvent pikachu = new KeyboardEvent();
        pikachu.setKey(KeyboardEvent.KEY_P);
        pikachu.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(pikachu);

        KeyboardEvent changeColor = new KeyboardEvent();
        changeColor.setKey(KeyboardEvent.KEY_Z);
        changeColor.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(changeColor);

        KeyboardEvent instructions = new KeyboardEvent();
        instructions.setKey(KeyboardEvent.KEY_I);
        instructions.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(instructions);

        KeyboardEvent sound = new KeyboardEvent();
        sound.setKey(KeyboardEvent.KEY_M);
        sound.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(sound);
    }

    public void clear() {
        grid.clear();
    }

    public String gridToString() {
        StringBuilder builder = new StringBuilder();
        for (Cell cell : grid.getCellList().values()) {
            Color color = cell.getColor();

            for (int i = 0; i < colorArray.length; i++) {
                if (cell.isPainted() == false || color == null) {
                    builder.append("-1 ");
                    break;
                }
                if (color.equals(colorArray[i])) {
                    builder.append(i + " ");
                    break;
                }
            }
        }
        return builder.toString();
    }

    public void loadGrid(String string) {
        clear();
        String[] loadedGrid = string.split(" ");
        Map<String, String> loadedCellColorMap = new LinkedHashMap<>();
        for (int i = 0; i < getGrid().getCols(); i++) {
            for (int j = 0; j < getGrid().getRows(); j++) {
                int arrayKey;
                int fakeI = i;
                arrayKey = i + j;
                if (i != 0) {
                    fakeI = i * 20;
                    arrayKey = fakeI + j;
                }
                String key = i + " " + j;
                loadedCellColorMap.put(key, loadedGrid[arrayKey]);
            }
        }


        for (int i = 0; i < getGrid().getCols(); i++) {
            for (int j = 0; j < getGrid().getRows(); j++) {
                String key = i + " " + j;
                String loadedCellColor = loadedCellColorMap.get(key);
                if (!loadedCellColor.equals("-1")) {
                    grid.getCellList().get(key).getRectangle().setColor(colorArray[Integer.parseInt(loadedCellColor)]);
                    grid.getCellList().get(key).getRectangle().fill();
                    grid.getCellList().get(key).setColor(colorArray[Integer.parseInt(loadedCellColor)]);
                    grid.getCellList().get(key).setPainted(true);
                } else {
                    grid.getCellList().get(key).setPainted(false);
                }
            }
        }
    }

    public void save() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("resources/files/load.txt"));
            String gridSave = gridToString();
            out.write(gridSave);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/files/load.txt"));
            String loadedGrid = in.readLine();
            if(loadedGrid == null){
                System.out.println("No file was saved yet");
                return;
            }
            loadGrid(loadedGrid);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(String string) {
        if (!(grid.getCols() == Main.COLS && grid.getRows() == Main.ROWS)) {
            System.out.println("Pikachu only works with a 20-20 grid");
            return;
        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(string));
            String loadedGrid = in.readLine();
            loadGrid(loadedGrid);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(700);
                    frame.delete();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    Thread.sleep(500);
                    frame.fill();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
