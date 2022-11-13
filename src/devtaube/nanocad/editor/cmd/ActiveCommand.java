package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.CommandOutRenderBehavior;

public abstract class ActiveCommand extends Command {

    protected abstract void onUpdate();

    protected abstract void onEnd();

    @Override
    public String run(String[] args) {
        String output = super.run(args);
        ActiveCommandBehavior.activeCommand = this;
        return output;
    }

    protected void setOutput(String text) {
        CommandOutRenderBehavior.text = text;
    }

    public void update() {
        onUpdate();
    }

    public void end() {
        ActiveCommandBehavior.activeCommand = null;
        onEnd();
    }

}
