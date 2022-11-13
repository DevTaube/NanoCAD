package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.TriangleInfoRenderBehavior;

public class VertexInfoCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "vinfo", "vi" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 0 }; }

    @Override
    public String getArgUsage() { return ""; }

    @Override
    public String getDescription() { return "Toggles visibility vertex information."; }

    @Override
    protected String run() {
        TriangleInfoRenderBehavior.detailed = !TriangleInfoRenderBehavior.detailed;
        return TriangleInfoRenderBehavior.detailed? "Showing vertex information."
                                                  : "Hidden all vertex information.";
    }

}
