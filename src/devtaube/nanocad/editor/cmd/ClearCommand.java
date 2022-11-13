package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import rosequartz.ecb.ECB;

public class ClearCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "clear" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 0 }; }

    @Override
    public String getArgUsage() { return ""; }

    @Override
    public String getDescription() { return "Deletes all meshes."; }

    @Override
    protected String run() {
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> ECB.remove(mesh));
        return "Removed all meshes.";
    }

}
