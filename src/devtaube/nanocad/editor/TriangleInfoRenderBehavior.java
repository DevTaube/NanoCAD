package devtaube.nanocad.editor;

import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.Vertex;
import rosequartz.ecb.Behavior;
import rosequartz.ecb.ECB;
import rosequartz.gfx.DepthTestingManager;
import rosequartz.input.InputManager;

import java.text.DecimalFormat;

public class TriangleInfoRenderBehavior implements Behavior {

    public static final int MAX_LINES_ON_SCREEN = 64;

    public static boolean detailed = false;

    public static int scrollOffset = 0;
    public static int markedIndex = -1;

    private String getShownText(String fullText) {
        // get the total amount of meshes
        int[] meshCount = { 0 };
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> meshCount[0] += 1);
        // only display MAX_LINES_ON_SCREEN lines, according to the offset
        String[] lines = fullText.split("\n");
        if(lines.length < MAX_LINES_ON_SCREEN) return "Meshes (" + meshCount[0] + "):\n" + fullText;
        String[] shownLines = new String[MAX_LINES_ON_SCREEN];
        System.arraycopy(lines, scrollOffset, shownLines, 0, MAX_LINES_ON_SCREEN);
        String finalLines = String.join("\n", shownLines);
        return "Meshes (" + meshCount[0] + "):\n" + (scrollOffset == 0? "\n" : "[...]\n") + finalLines + (scrollOffset == lines.length - MAX_LINES_ON_SCREEN? "" : "\n[...]");
    }

    @Override
    public void run() {
        DepthTestingManager.get().setEnabled(false);
        // generate String to render
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        StringBuilder renderedString = new StringBuilder();
        int[] index = { 0 };
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            if(index[0] == markedIndex) {
                renderedString.append(">> ");
            }
            if(meshComponent.alias != null) {
                renderedString.append(meshComponent.alias)
                        .append(" ");
            }
            renderedString
                    .append("[")
                    .append(index[0])
                    .append("] ");
            if(detailed) {
                for(Vertex vertex: meshComponent.vertices) {
                    renderedString
                            .append("<[")
                            .append(df.format(vertex.xyz.x))
                            .append(", ")
                            .append(df.format(vertex.xyz.y))
                            .append(", ")
                            .append(df.format(vertex.xyz.z))
                            .append("] [")
                            .append(vertex.uvX)
                            .append(", ")
                            .append(vertex.uvY)
                            .append("]> ");
                }
            } else {
                renderedString.append("<")
                        .append(meshComponent.vertices.length)
                        .append(">");
            }
            renderedString.append("\n");
            index[0] += 1;
        });
        // scrolling changes offset
        int lineCount = renderedString.toString().split("\n").length;
        scrollOffset -= (int) Math.floor(InputManager.get().scrolledY());
        if(lineCount > MAX_LINES_ON_SCREEN) scrollOffset = Math.min(Math.max(scrollOffset, 0), lineCount - MAX_LINES_ON_SCREEN);
        else scrollOffset = 0;
        InputManager.get().setScrolledDist(0, 0);
        // render the string
        String shownText = getShownText(renderedString.toString());
        float textSize = EditorRenderPipeline.TEXT_HEIGHT / 2.5f;
        EditorRenderPipeline.font.render(
                shownText,
                EditorRenderPipeline.TEXT_PADDING_X,
                1 - EditorRenderPipeline.TEXT_PADDING_X - EditorRenderPipeline.font.getTextHeight(
                        shownText,
                        textSize,
                        textSize / 5
                ),
                textSize,
                textSize / 5,
                textSize / 5
        );
    }

}
