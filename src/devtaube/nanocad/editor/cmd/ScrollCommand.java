package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.TriangleInfoRenderBehavior;

public class ScrollCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "scroll", "scr", "s" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1 }; }

    @Override
    public String getArgUsage() { return "<mesh-id>"; }

    @Override
    public String getDescription() { return "Scrolls to the given mesh / vertex."; }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        TriangleInfoRenderBehavior.scrollOffset = meshIndex - TriangleInfoRenderBehavior.MAX_LINES_ON_SCREEN / 2;
        TriangleInfoRenderBehavior.markedIndex = meshIndex;
        return "Scrolled to mesh with index " + meshIndex + ".";
    }

}
