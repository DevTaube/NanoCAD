package devtaube.nanocad.editor;

import devtaube.nanocad.editor.cmd.EditorCommandParser;
import rosequartz.ecb.Behavior;
import rosequartz.gfx.Camera;
import rosequartz.input.InputManager;
import rosequartz.input.Key;

public class InputParseBehavior implements Behavior {

    public static char[] commandKeys = null;

    private final EditorCommandParser parser;
    private boolean lastFrameEnterDown = false;

    public InputParseBehavior(Camera<?> camera) {
        parser = new EditorCommandParser(camera);
    }

    @Override
    public void run() {
        if(commandKeys != null && InputManager.get().textInputText().length() > 0) {
            for(char commandKey: commandKeys) {
                if(InputManager.get().textInputText().charAt(0) == commandKey) {
                    String textInputText = InputManager.get().textInputText();
                    InputManager.get().startTextInput(textInputText.substring(0, textInputText.length() - 1), false);
                    break;
                }
            }
        }
        if(InputManager.get().key(Key.ENTER) && !lastFrameEnterDown) {
            parser.parse(InputManager.get().textInputText());
            InputManager.get().startTextInput("", false);
        }
        lastFrameEnterDown = InputManager.get().key(Key.ENTER);
    }

}
