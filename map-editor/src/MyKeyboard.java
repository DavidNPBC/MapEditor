import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class MyKeyboard implements KeyboardHandler {
    private MapEditor mapEditor;

    public MyKeyboard(MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_SPACE:
                mapEditor.setPainting(true);
                break;

            case KeyboardEvent.KEY_LEFT:
                mapEditor.moveLeft();
                break;

            case KeyboardEvent.KEY_RIGHT:
                mapEditor.moveRight();
                break;

            case KeyboardEvent.KEY_DOWN:
                mapEditor.moveDown();
                break;

            case KeyboardEvent.KEY_UP:
                mapEditor.moveUp();
                break;

            case KeyboardEvent.KEY_S:
                mapEditor.save();
                break;

            case KeyboardEvent.KEY_L:
                mapEditor.load();
                break;

            case KeyboardEvent.KEY_P:
                mapEditor.load("resources/files/pikachu/pikachu.txt");
                break;

            case KeyboardEvent.KEY_C:
                mapEditor.clear();
                break;

            case KeyboardEvent.KEY_Q:
                System.exit(1);
                break;

            case KeyboardEvent.KEY_Z:
                mapEditor.changeColor();
                break;

            case KeyboardEvent.KEY_M:
                mapEditor.setPlaying(!mapEditor.isPlaying());
                break;

            case KeyboardEvent.KEY_I:
                mapEditor.setShowingCommandList(!mapEditor.isShowingCommandList());
                mapEditor.showCommandList();
                break;
        }

        if (mapEditor.isPainting()) {
            mapEditor.getGrid().paintCell((mapEditor.getCol() - Grid.PADDING) + " " + (mapEditor.getRow() - Grid.PADDING), mapEditor.getColor());
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            mapEditor.setPainting(false);
        }
    }
}
