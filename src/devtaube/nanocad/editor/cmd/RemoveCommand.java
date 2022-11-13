package devtaube.nanocad.editor.cmd;

public class RemoveCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "remove", "rem", "rm" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1 }; }

    @Override
    public String getArgUsage() { return "<mesh-id>"; }

    @Override
    public String getDescription() { return "Deletes a mesh."; }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        removeMesh(meshIndex);
        return "Removed mesh with index " + meshIndex + ".";
    }

}
