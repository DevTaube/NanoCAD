package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.*;
import rosequartz.gfx.Camera;

import java.util.ArrayList;

public class EditorCommandParser {

    private final ArrayList<Command> commands = new ArrayList<>();

    public EditorCommandParser(Camera<?> camera) {
        // commands
        commands.add(new HelpCommand(commands));
        commands.add(new VertexInfoCommand());
        commands.add(new NormalsCommand());
        commands.add(new ScrollCommand());
        commands.add(new NewCommand());
        commands.add(new AliasCommand());
        commands.add(new LookCommand(camera));
        commands.add(new EditCommand());
        commands.add(new MoveCommand(camera));
        commands.add(new UvCommand(camera));
        commands.add(new InvertNormalCommand());
        commands.add(new CopyCommand());
        commands.add(new RemoveCommand());
        commands.add(new ClearCommand());
        commands.add(new LoadCommand());
        commands.add(new SaveCommand());
        commands.add(new TextureCommand());
    }

    public void parse(String text) {
        String[] params = text.strip().split(" ");
        if(ActiveCommandBehavior.activeCommand != null) ActiveCommandBehavior.activeCommand.end();
        CommandOutRenderBehavior.text = "";
        if(params.length == 0 || params[0].equals("")) return;
        for(Command command: commands) {
            for(String name: command.getNames()) {
                if(params[0].equals(name)) {
                    try {
                        CommandOutRenderBehavior.text = command.run(params);
                    } catch(CommandException e) {
                        CommandOutRenderBehavior.text = e.reason;
                    }
                    return;
                }
            }
        }
        CommandOutRenderBehavior.text = "Command \"" + params[0] + "\" does not exist.";
    }

}
