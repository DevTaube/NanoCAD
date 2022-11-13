package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import rosequartz.ecb.ECB;
import rosequartz.input.InputManager;

public class AliasCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "alias", "al" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1, 2 }; }

    @Override
    public String getArgUsage() { return "<mesh-id> (<alias>)"; }

    @Override
    public String getDescription() { return "Gives a mesh a name."; }

    public static String checkNewAlias(String alias) {
        // check that alias is not too long
        if(alias.length() > 32) throw new CommandException("Alias may not be longer than 32 characters.");
        // check that alias is not just a number
        try {
            Integer.parseInt(alias);
            throw new CommandException("Alias can also be interpreted as a number.");
        } catch(NumberFormatException ignored) {}
        // check that alias is not already used
        String finalAlias = alias;
        boolean[] aliasExists = { false };
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            if(meshComponent.alias != null && meshComponent.alias.equals(finalAlias)) aliasExists[0] = true;
        });
        if(aliasExists[0]) throw new CommandException("Alias \"" + alias + "\" is already used.");
        return alias;
    }

    @Override
    protected String run() {
        int meshIndex = getArgMeshIndex(0);
        String alias = null;
        if(getArgCount() == 2) {
            alias = checkNewAlias(getArgString(1));
        }
        MeshComponent mesh = getMeshComponent(meshIndex);
        mesh.alias = alias;
        return alias == null? "Removed alias of mesh with index " + meshIndex + "."
                            : "Set alias of mesh with index " + meshIndex + " to \"" + alias + "\".";
    }

}
