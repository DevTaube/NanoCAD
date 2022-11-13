package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.Quad;
import devtaube.nanocad.model.Triangle;
import devtaube.nanocad.model.Vertex;
import rosequartz.ecb.ECB;
import rosequartz.ecb.Entity;
import rosequartz.math.Vec3;

public class NewCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "new", "nw" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 1, 2 }; }

    @Override
    public String getArgUsage() { return "<vertex-count (\"3\"/\"4\")> (<new-mesh-alias>)"; }

    @Override
    public String getDescription() { return "Creates a new Mesh."; }

    @Override
    protected String run() {
        int vertices = getArgInt(0);
        Entity addedEntity;
        switch(vertices) {
            case 3: {
                addedEntity = new Triangle(
                        new Vertex(new Vec3(1, 0, 0), 0, 0),
                        new Vertex(new Vec3(0, 0, 0), 0, 0),
                        new Vertex(new Vec3(0, 1, 0), 0, 0)
                );
            } break;
            case 4: {
                addedEntity = new Quad(
                        new Vertex(new Vec3(0, 0, 0), 0, 0),
                        new Vertex(new Vec3(0, 1, 0), 0, 0),
                        new Vertex(new Vec3(1, 1, 0), 0, 0),
                        new Vertex(new Vec3(1, 0, 0), 0, 0)
                );
            } break;
            default: {
                throw new CommandException("Given vertex count is illegal; not able to create.");
            }
        }
        if(getArgCount() == 2) addedEntity.get(MeshComponent.class).alias = AliasCommand.checkNewAlias(getArgString(1));
        ECB.add(addedEntity);
        return "Created mesh with " + vertices + " vertices.";
    }

}
