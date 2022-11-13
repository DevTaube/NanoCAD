package devtaube.nanocad.editor;

import rosequartz.ecb.Behavior;
import rosequartz.gfx.DepthTestingManager;

public class CommandOutRenderBehavior implements Behavior {

    public static String text = "NanoCAD (made by DevTaube) - enter \"help\" for a list of commands";

    @Override
    public void run() {
        DepthTestingManager.get().setEnabled(false);
        // render the text value
        EditorRenderPipeline.font.render(
                text,
                EditorRenderPipeline.TEXT_PADDING_X, EditorRenderPipeline.TEXT_HEIGHT + EditorRenderPipeline.TEXT_PADDING_X * 2,
                EditorRenderPipeline.TEXT_HEIGHT / 2,
                EditorRenderPipeline.TEXT_HEIGHT / 5 / 2, EditorRenderPipeline.TEXT_HEIGHT / 5 / 2
        );
    }

}
