package devtaube.nanocad.editor.cmd;


import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.Vertex;
import rosequartz.gfx.Camera;
import rosequartz.math.Vec3;

public class LookCommand extends ActiveCommand {

    private final Camera<?> camera;
    private Vertex[] vertices;

    public LookCommand(Camera<?> camera) {
        this.camera = camera;
    }

    @Override
    public String[] getNames() { return new String[] { "look", "lk" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1, 2 }; }

    @Override
    public String getArgUsage() {
        return "<mesh-id> (<vertex-id>)";
    }

    @Override
    public String getDescription() { return "Looks at a mesh or vertex."; }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        int vertexIndex;
        switch(getArgCount()) {
            case 2: vertexIndex = getArgInt(1); break;
            default: vertexIndex = -1;
        }
        MeshComponent mesh = getMeshComponent(meshIndex);
        if(vertexIndex != -1 && mesh.vertices.length <= vertexIndex)
            throw CommandException.vertexFindException(meshIndex, vertexIndex);
        // keep reference to modified vertices
        vertices = vertexIndex == -1? mesh.vertices
                                    : new Vertex[] { mesh.vertices[vertexIndex] };
        return vertexIndex == -1? "[ENTER to stop] Looking at mesh with index " + meshIndex + "."
                : "[ENTER to stop] Looking at vertex with index " + vertexIndex + " in mesh with index " + meshIndex + ".";
    }

    @Override
    protected void onUpdate() {
        // calculate the point to look at
        Vec3 pointToLookAt = new Vec3();
        for(Vertex vertex: vertices) {
            pointToLookAt.add(vertex.xyz);
        }
        pointToLookAt.div(vertices.length, vertices.length, vertices.length);
        // look at the point
        camera.getConfiguration().setLookAt(pointToLookAt);
    }

    @Override
    protected void onEnd() {
        // look at 0, 0, 0
        camera.getConfiguration().setLookAt(0, 0, 0);
    }

}
