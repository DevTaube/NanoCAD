package devtaube.nanocad.editor;

import rosequartz.gfx.GraphicsPipeline;

public class EditorRenderPipeline extends GraphicsPipeline {

    public static final EditorFont font = new EditorFont();
    public static final float TEXT_PADDING_X = 0.0125f;
    public static final float TEXT_HEIGHT = 0.025f;

    public EditorRenderPipeline() {
        add(
                new InputRenderBehavior(),
                new CommandOutRenderBehavior(),
                new TriangleInfoRenderBehavior(),
                new CommandHelpRenderBehavior()
        );
    }

}
