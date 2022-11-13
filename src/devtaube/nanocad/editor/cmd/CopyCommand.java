package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.Vertex;
import rosequartz.ecb.ECB;
import rosequartz.ecb.Entity;

public class CopyCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "copy", "cp" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1, 2 }; }

    @Override
    public String getArgUsage() { return "<mesh-id> (<new-mesh-alias>)"; }

    @Override
    public String getDescription() { return "Creates a copy of a mesh."; }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        MeshComponent meshComponent = getMeshComponent(meshIndex);
        Vertex[] newVertices = new Vertex[meshComponent.vertices.length];
        for(int vertexIndex = 0; vertexIndex < meshComponent.vertices.length; vertexIndex++)
            newVertices[vertexIndex] = meshComponent.vertices[vertexIndex].clone();
        MeshComponent newMeshComponent = new MeshComponent(newVertices);
        if(getArgCount() == 2) newMeshComponent.alias = AliasCommand.checkNewAlias(getArgString(1));
        ECB.add(new Entity().add(newMeshComponent));
        return "Duplicated mesh with index " + meshIndex + ".";
    }

}
