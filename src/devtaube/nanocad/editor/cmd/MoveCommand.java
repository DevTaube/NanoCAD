package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.Axis;
import devtaube.nanocad.editor.InfoRenderer;
import devtaube.nanocad.model.Vertex;
import rosequartz.gfx.Camera;
import rosequartz.input.InputManager;
import rosequartz.input.Key;

public class MoveCommand extends ActiveInputCommand {

    private final InfoRenderer infoRenderer;

    private int meshIndex;
    private Vertex[] vertices;
    private int vertexIndex;
    private Axis axis;
    private float stepSize;

    public MoveCommand(Camera<?> camera) {
        infoRenderer = new InfoRenderer(camera);
    }

    @Override
    public String[] getNames() { return new String[] { "move", "mv" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 2 }; }

    @Override
    public String getArgUsage() { return "<mesh-id> <step-size>"; }

    @Override
    public String getDescription() { return "Moves a mesh or vertex."; }

    private String generateText() {
        return vertexIndex == -1? "[ENTER to stop] Moving mesh with index " + meshIndex + " with step size " + stepSize + " on " + axis + "-axis."
                : "[ENTER to stop] Moving vertex with index " + vertexIndex + " in mesh with index " + meshIndex + " with step size " + stepSize + " on " + axis + "-axis.";
    }

    @Override
    protected String run() {
        meshIndex = getArgMeshIndex(0);
        stepSize = getArgFloat(1);
        if(stepSize == 0) throw new CommandException("Step size may not be 0.");
        if(stepSize < 0) throw new CommandException("Step size may not be negative.");
        vertices = getMeshComponent(meshIndex).vertices;
        vertexIndex = -1;
        axis = Axis.X;
        return generateText();
    }

    private void move(float x, float y, float z) {
        if(vertexIndex == -1) {
            for(Vertex vertex: vertices) {
                vertex.xyz.add(x, y, z);
            }
        } else {
            vertices[vertexIndex].xyz.add(x, y, z);
        }
    }

    @Override
    protected void onUpdate() {
        // change axis
        if(InputManager.get().key(Key.X)) axis = Axis.X;
        if(InputManager.get().key(Key.Y)) axis = Axis.Y;
        if(InputManager.get().key(Key.Z)) axis = Axis.Z;
        // change vertex
        if(InputManager.get().key(Key.NUM_0) && vertices.length > 0) vertexIndex = 0;
        if(InputManager.get().key(Key.NUM_1) && vertices.length > 1) vertexIndex = 1;
        if(InputManager.get().key(Key.NUM_2) && vertices.length > 2) vertexIndex = 2;
        if(InputManager.get().key(Key.NUM_3) && vertices.length > 3) vertexIndex = 3;
        if(InputManager.get().key(Key.NUM_4) && vertices.length > 4) vertexIndex = 4;
        if(InputManager.get().key(Key.NUM_5) && vertices.length > 5) vertexIndex = 5;
        if(InputManager.get().key(Key.NUM_6) && vertices.length > 6) vertexIndex = 6;
        if(InputManager.get().key(Key.NUM_7) && vertices.length > 7) vertexIndex = 7;
        if(InputManager.get().key(Key.NUM_8) && vertices.length > 8) vertexIndex = 8;
        if(InputManager.get().key(Key.NUM_9) && vertices.length > 9) vertexIndex = 9;
        if(InputManager.get().key(Key.A)) vertexIndex = -1;
        // move vertex
        if(getArrowUpNew())
            move(axis == Axis.X? stepSize : 0, axis == Axis.Y? stepSize : 0, axis == Axis.Z? stepSize : 0);
        if(getArrowDownNew())
            move(axis == Axis.X? -stepSize : 0, axis == Axis.Y? -stepSize : 0, axis == Axis.Z? -stepSize : 0);
        if(getArrowLeftNew())
            move(axis == Axis.X? -stepSize : 0, axis == Axis.Y? -stepSize : 0, axis == Axis.Z? -stepSize : 0);
        if(getArrowRightNew())
            move(axis == Axis.X? stepSize : 0, axis == Axis.Y? stepSize : 0, axis == Axis.Z? stepSize : 0);
        // set output text
        setOutput(generateText());
        // render indices
        for(int vertexIndex = 0; vertexIndex < vertices.length; vertexIndex++) {
            infoRenderer.render(vertices[vertexIndex].xyz, String.valueOf(vertexIndex));
        }
    }

    @Override
    protected void onEnd() {}

    @Override
    protected char[] getCommandKeys() {
        return new char[] {
                'x', 'y', 'z',
                'a',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
    }

}
