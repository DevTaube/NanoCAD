package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.InfoRenderer;
import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.ModelTextureComponent;
import devtaube.nanocad.model.Vertex;
import rosequartz.ecb.ECB;
import rosequartz.gfx.Camera;
import rosequartz.gfx.Texture;
import rosequartz.input.InputManager;
import rosequartz.input.Key;

public class UvCommand extends ActiveInputCommand {

    private final InfoRenderer infoRenderer;

    private int meshIndex;
    private Vertex[] vertices;
    private int vertexIndex;

    public UvCommand(Camera<?> camera) {
        infoRenderer = new InfoRenderer(camera);
    }

    @Override
    public String[] getNames() { return new String[] { "uv" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1 }; }

    @Override
    public String getArgUsage() { return "<mesh-index>"; }

    @Override
    public String getDescription() { return "Moves vertex tex mappings."; }

    private String generateText() {
        return "[ENTER to stop] Moving texture mappings of vertex with index " + vertexIndex + " in mesh with index " + meshIndex + ".";
    }

    @Override
    protected String run() {
        meshIndex = getArgMeshIndex(0);
        MeshComponent meshComponent = getMeshComponent(meshIndex);
        vertices = meshComponent.vertices;
        vertexIndex = 0;
        return generateText();
    }

    @Override
    protected void onUpdate() {
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
        // move uv mappings
        Texture[] currentTexture = new Texture[1];
        ECB.get(ModelTextureComponent.class, (modelTexture, modelTextureComponent) -> {
            currentTexture[0] = modelTextureComponent.texture;
        });
        if(getArrowUpNew()) vertices[vertexIndex].uvY = Math.max(vertices[vertexIndex].uvY - 1, 0);
        if(getArrowDownNew()) vertices[vertexIndex].uvY = Math.min(vertices[vertexIndex].uvY + 1, currentTexture[0].getHeight());
        if(getArrowLeftNew()) vertices[vertexIndex].uvX = Math.max(vertices[vertexIndex].uvX - 1, 0);
        if(getArrowRightNew()) vertices[vertexIndex].uvX = Math.min(vertices[vertexIndex].uvX + 1, currentTexture[0].getWidth());
        // set output text
        setOutput(generateText());
        // render indices
        for(int vertexIndex = 0; vertexIndex < vertices.length; vertexIndex++) {
            infoRenderer.render(
                    vertices[vertexIndex].xyz,
                    vertexIndex + " [" + vertices[vertexIndex].uvX + ", " + vertices[vertexIndex].uvY + "]");
        }
    }

    @Override
    protected void onEnd() {}

    @Override
    protected char[] getCommandKeys() {
        return new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
    }

}
