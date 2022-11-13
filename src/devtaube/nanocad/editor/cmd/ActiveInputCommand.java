package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.InputHandlerPipeline;
import devtaube.nanocad.editor.InputParseBehavior;
import devtaube.nanocad.model.render.CameraRotationBehavior;
import rosequartz.input.Key;

import java.util.List;

public abstract class ActiveInputCommand extends ActiveCommand {

    private final boolean[] arrowInput = { false, false, false, false };
    private final boolean[] lastFrameArrowInput = { false, false, false, false };

    protected abstract char[] getCommandKeys();

    @Override
    public String run(String[] args) {
        String output = super.run(args);
        InputHandlerPipeline.setHandler((up, down, left, right) -> {
            arrowInput[0] = up;
            arrowInput[1] = down;
            arrowInput[2] = left;
            arrowInput[3] = right;
        });
        InputParseBehavior.commandKeys = getCommandKeys();
        return output;
    }

    @Override
    public void update() {
        super.update();
        lastFrameArrowInput[0] = arrowInput[0];
        lastFrameArrowInput[1] = arrowInput[1];
        lastFrameArrowInput[2] = arrowInput[2];
        lastFrameArrowInput[3] = arrowInput[3];
    }

    @Override
    public void end() {
        super.end();
        InputHandlerPipeline.setHandler(CameraRotationBehavior.INPUT_HANDLER);
        InputParseBehavior.commandKeys = null;
    }

    public boolean getArrowUp() { return arrowInput[0]; }

    public boolean getArrowDown() { return arrowInput[1]; }

    public boolean getArrowLeft() { return arrowInput[2]; }

    public boolean getArrowRight() { return arrowInput[3]; }

    public boolean getArrowUpNew() { return arrowInput[0] && !lastFrameArrowInput[0]; }

    public boolean getArrowDownNew() { return arrowInput[1] && !lastFrameArrowInput[1]; }

    public boolean getArrowLeftNew() { return arrowInput[2] && !lastFrameArrowInput[2]; }

    public boolean getArrowRightNew() { return arrowInput[3] && !lastFrameArrowInput[3]; }

}
