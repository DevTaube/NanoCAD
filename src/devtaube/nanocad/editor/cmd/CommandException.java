package devtaube.nanocad.editor.cmd;

public class CommandException extends RuntimeException {

    public static CommandException parseException() {
        return new CommandException("Unable to parse input.");
    }

    public static CommandException meshFindException(int meshIndex) {
        return new CommandException("Unable to find mesh with index " + meshIndex + ".");
    }

    public static CommandException vertexFindException(int meshIndex, int vertexIndex) {
        return new CommandException("Unable to find vertex with index " + vertexIndex + " in mesh with index " + meshIndex + ".");
    }

    public final String reason;

    public CommandException(String reason) {
        super(reason);
        this.reason = reason;
    }

}
