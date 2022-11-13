package devtaube.nanocad.editor;

import rosequartz.ecb.Behavior;
import rosequartz.ecb.ECB;

public class CommandHelpRenderBehavior implements Behavior {

    @Override
    public void run() {
        ECB.get(CommandHelpRenderComponent.class, (commandHelp, commandHelpRenderComponent) -> {
            EditorRenderPipeline.font.render(
                    commandHelpRenderComponent.text,
                    1 - EditorRenderPipeline.font.getTextWidth(
                            commandHelpRenderComponent.text,
                            EditorRenderPipeline.TEXT_HEIGHT / 2,
                            EditorRenderPipeline.TEXT_HEIGHT / 5 / 2
                    ),
                    1 - EditorRenderPipeline.TEXT_HEIGHT / 2 - EditorRenderPipeline.font.getTextHeight(
                            commandHelpRenderComponent.text,
                            EditorRenderPipeline.TEXT_HEIGHT / 2,
                            EditorRenderPipeline.TEXT_HEIGHT / 5 / 2
                    ),
                    EditorRenderPipeline.TEXT_HEIGHT / 2,
                    EditorRenderPipeline.TEXT_HEIGHT / 5 / 2,
                    EditorRenderPipeline.TEXT_HEIGHT / 5 / 2
            );
        });
    }

}
