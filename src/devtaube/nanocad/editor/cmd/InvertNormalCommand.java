package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.Vertex;

public class InvertNormalCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "invnor", "inor" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1 }; }

    @Override
    public String getArgUsage() { return "<mesh-id>"; }

    @Override
    public String getDescription() { return "Inverts the surface normal of a mesh."; }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        MeshComponent mesh = getMeshComponent(meshIndex);
        Vertex[] newVertices = new Vertex[mesh.vertices.length];
        for(int vertexIndex = 0; vertexIndex < mesh.vertices.length; vertexIndex++) {
            newVertices[mesh.vertices.length - 1 - vertexIndex] = mesh.vertices[vertexIndex];
        }
        System.arraycopy(newVertices, 0, mesh.vertices, 0, mesh.vertices.length);
        return "Inverted surface normal of mesh.";
    }

}
