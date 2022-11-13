package devtaube.nanocad.editor.cmd;

import rosequartz.ecb.Behavior;

public class ActiveCommandBehavior implements Behavior {

    public static ActiveCommand activeCommand;

    @Override
    public void run() {
        if(activeCommand == null) return;
        activeCommand.update();
    }

}
