package devtaube.nanocad;

import rosequartz.ecb.Pipeline;

public class InputHandlerPipeline extends Pipeline {

    public static ActiveInputHandler handler;

    public static void setHandler(ActiveInputHandler h) {
        if(handler != null) handler.receive(false, false, false, false);
        handler = h;
    }

    public InputHandlerPipeline() {
        add(
                new InputHandlerBehavior()
        );
    }

}
