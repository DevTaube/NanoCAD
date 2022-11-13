package devtaube.nanocad.model.render;

import devtaube.nanocad.ActiveInputHandler;
import devtaube.nanocad.InputHandlerPipeline;
import rosequartz.RoseQuartz;
import rosequartz.ecb.Behavior;
import rosequartz.gfx.VertexArray;
import rosequartz.input.InputManager;
import rosequartz.input.Key;
import rosequartz.math.Vec2;

import java.util.function.Consumer;

public class CameraRotationBehavior implements Behavior {

    public static final float ROTATION_SPEED = 45f;

    private static final boolean[] ARROW_INPUT = { false, false, false, false }; // up, down, left, right
    public static final ActiveInputHandler INPUT_HANDLER = (up, down, left, right) -> {
        ARROW_INPUT[0] = up;
        ARROW_INPUT[1] = down;
        ARROW_INPUT[2] = left;
        ARROW_INPUT[3] = right;
    };

    private final Consumer<Vec2> resultConsumer;

    public CameraRotationBehavior(Consumer<Vec2> resultAction) {
        InputHandlerPipeline.setHandler(INPUT_HANDLER);
        resultConsumer = resultAction;
    }

    private final Vec2 frameRotation = new Vec2();
    private final Vec2 rotation = new Vec2();

    @Override
    public void run() {
        frameRotation.set(0, 0);
        if(ARROW_INPUT[0]) frameRotation.x += 1;
        if(ARROW_INPUT[2]) frameRotation.y -= 1;
        if(ARROW_INPUT[1]) frameRotation.x -= 1;
        if(ARROW_INPUT[3]) frameRotation.y += 1;
        if(frameRotation.x != 0 || frameRotation.y != 0) {
            frameRotation.normalize()
                    .scale((float) RoseQuartz.deltaTime() * ROTATION_SPEED)
                    .scale((float) Math.PI / 180); // to radians
            rotation.add(frameRotation);
            rotation.x = Math.min(Math.max(rotation.x, (float) Math.toRadians(-85f)), (float) Math.toRadians(85f));
        }
        resultConsumer.accept(rotation);
    }

}
