package devtaube.nanocad.editor;

import rosequartz.ecb.Entity;

public class CommandHelp extends Entity {

    public CommandHelp(String text) {
        add(
                new CommandHelpRenderComponent(text)
        );
    }

}
