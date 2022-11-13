package devtaube.nanocad.editor;

import rosequartz.ecb.Behavior;
import rosequartz.gfx.DepthTestingManager;
import rosequartz.input.InputManager;

public class InputRenderBehavior implements Behavior {

    @Override
    public void run() {
        DepthTestingManager.get().setEnabled(false);
        // render the text input value
        EditorRenderPipeline.font.render(
                InputManager.get().textInputText(),
                EditorRenderPipeline.TEXT_PADDING_X, EditorRenderPipeline.TEXT_PADDING_X,
                EditorRenderPipeline.TEXT_HEIGHT,
                EditorRenderPipeline.TEXT_HEIGHT / 5, EditorRenderPipeline.TEXT_HEIGHT / 5
        );
    }

}
