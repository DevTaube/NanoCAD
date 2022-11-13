package devtaube.nanocad;

import devtaube.nanocad.model.render.CameraRotationBehavior;
import rosequartz.ecb.Behavior;
import rosequartz.input.InputManager;
import rosequartz.input.Key;

public class InputHandlerBehavior implements Behavior {

    @Override
    public void run() {
        if(InputHandlerPipeline.handler == null) return;
        (InputManager.get().key(Key.CTRL)? CameraRotationBehavior.INPUT_HANDLER
                                         : InputHandlerPipeline.handler).receive(
                InputManager.get().key(Key.ARROW_UP),
                InputManager.get().key(Key.ARROW_DOWN),
                InputManager.get().key(Key.ARROW_LEFT),
                InputManager.get().key(Key.ARROW_RIGHT)
        );
        if(!InputManager.get().key(Key.CTRL) && InputHandlerPipeline.handler != CameraRotationBehavior.INPUT_HANDLER) {
            CameraRotationBehavior.INPUT_HANDLER.receive(false, false, false, false);
        }
    }

}
