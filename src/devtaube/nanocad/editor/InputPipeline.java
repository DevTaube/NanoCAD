package devtaube.nanocad.editor;

import devtaube.nanocad.editor.cmd.ActiveCommandBehavior;
import rosequartz.gfx.Camera;
import rosequartz.gfx.GraphicsPipeline;
import rosequartz.input.InputManager;

public class InputPipeline extends GraphicsPipeline {

    public InputPipeline(Camera<?> manipulatedCamera) {
        InputManager.get().startTextInput("", false);
        add(
                new InputParseBehavior(manipulatedCamera),
                new ActiveCommandBehavior()
        );
    }

}
