package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.Vertex;

public class EditCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "edit", "ed" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 7 }; }

    @Override
    public String getArgUsage() { return "<mesh-id> <vertex-id> <pos-x/\"_\"> <pos-y/\"_\"> <pos-z/\"_\"> <tex-mapping-x/\"_\"> <tex-mapping-y/\"_\">"; }

    @Override
    public String getDescription() { return "Edits the data of a vertex."; }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        int vertexIndex = getArgInt(1);
        MeshComponent meshComponent = getMeshComponent(meshIndex);
        if(meshComponent.vertices.length <= vertexIndex)
            throw CommandException.vertexFindException(meshIndex, vertexIndex);
        Vertex vertex = meshComponent.vertices[vertexIndex];
        vertex.xyz.set(
                getArgFloatOptional(2, vertex.xyz.x),
                getArgFloatOptional(3, vertex.xyz.y),
                getArgFloatOptional(4, vertex.xyz.z)
        );
        vertex.uvX = getArgIntOptional(5, vertex.uvX);
        vertex.uvY = getArgIntOptional(6, vertex.uvY);
        return "Edited data of vertex with index " + vertexIndex + " in mesh with index " + meshIndex + ".";
    }

}
