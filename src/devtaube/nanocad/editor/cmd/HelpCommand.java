package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.editor.CommandHelp;
import rosequartz.ecb.ECB;

import java.util.Collection;

public class HelpCommand extends Command {

    private final Collection<Command> commands;
    private CommandHelp commandHelp = null;

    public HelpCommand(Collection<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String[] getNames() { return new String[] { "help" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 0 }; }

    @Override
    public String getArgUsage() { return ""; }

    @Override
    public String getDescription() { return "Toggles the command help."; }

    @Override
    protected String run() {
        if(commandHelp == null) {
            // generate command help
            StringBuilder help = new StringBuilder("Commands:\n");
            int longestNamesLength = 0;
            for(Command command: commands) {
                int namesLength = 0;
                for(String name: command.getNames()) {
                    namesLength += name.length();
                }
                namesLength += (command.getNames().length - 1) * 3;
                longestNamesLength = Math.max(longestNamesLength, namesLength);
            }
            for(Command command: commands) {
                int namesLength = 0;
                for(String name: command.getNames()) {
                    help.append(name);
                    help.append(" / ");
                    namesLength += name.length() + 3;
                }
                help.delete(help.length() - 3, help.length());
                namesLength -= 3;
                while(namesLength < longestNamesLength) {
                    help.append(" ");
                    namesLength++;
                }
                help.append(" | ");
                help.append(command.getDescription());
                help.append("\n");
            }
            help.deleteCharAt(help.length() - 1);
            // show command help
            commandHelp = new CommandHelp(help.toString());
            ECB.add(commandHelp);
            return "Showing command help.";
        } else {
            ECB.remove(commandHelp);
            commandHelp = null;
            return "Hidden the command help.";
        }
    }

}
