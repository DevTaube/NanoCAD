package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.ObjectFileParser;
import rosequartz.files.FileManager;

public class LoadCommand extends Command {

    private final ObjectFileParser objParser = new ObjectFileParser();

    @Override
    public String[] getNames() { return new String[] { "load", "ld" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1 }; }

    @Override
    public String getArgUsage() { return "<model-name>"; }

    @Override
    public String getDescription() { return "Loads the model."; }

    @Override
    protected String run() {
        String modelName = getArgString(0);
        if(modelName.contains("/") || modelName.contains("\\"))
            throw new CommandException("Model name may not contain file system path separators.");
        try {
            String objText = FileManager.get().readFileString(modelName + ".obj");
            objParser.parse(objText);
            return "Loaded model from \"/files/" + modelName + ".obj\".";
        } catch(Exception ignored) {
            throw new CommandException("Unable to load model file from \"/files/" + modelName + ".obj\".");
        }
    }

}
