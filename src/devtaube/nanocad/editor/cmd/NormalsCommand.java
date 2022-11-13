package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.render.SurfaceNormalRenderBehavior;

public class NormalsCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "normals", "nor" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 0 }; }

    @Override
    public String getArgUsage() { return ""; }

    @Override
    public String getDescription() { return "Toggles visibility of normal vectors."; }

    @Override
    protected String run() {
        SurfaceNormalRenderBehavior.render = !SurfaceNormalRenderBehavior.render;
        return SurfaceNormalRenderBehavior.render? "Showing surface normals."
                                                 : "Hidden all surface normals.";
    }

}
