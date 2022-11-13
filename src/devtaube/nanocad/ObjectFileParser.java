package devtaube.nanocad;

import devtaube.nanocad.editor.cmd.CommandException;
import devtaube.nanocad.model.*;
import rosequartz.ecb.ECB;
import rosequartz.gfx.Texture;
import rosequartz.math.Vec2;
import rosequartz.math.Vec3;

import java.util.ArrayList;

public final class ObjectFileParser {

    public void parse(String fileText) {
        // get current texture
        Texture[] currentTexture = new Texture[1];
        ECB.get(ModelTextureComponent.class, (modelTexture, modelTextureComponent) -> {
            currentTexture[0] = modelTextureComponent.texture;
        });
        // store parsed data
        ArrayList<Vec2> texMappings = new ArrayList<>();
        ArrayList<Vec3> positions = new ArrayList<>();
        // parse data, line by line
        String[] lines = fileText.split("\r\n|\n|\r");
        for(String line: lines) {
            String[] args = line.strip().split(" ");
            if(args.length == 0) return;
            switch(args[0]) {
                case "vt": {
                    texMappings.add(new Vec2(
                            Float.parseFloat(args[1]),
                            Float.parseFloat(args[2])
                    ));
                } break;
                case "v": {
                    positions.add(new Vec3(
                            Float.parseFloat(args[1]),
                            Float.parseFloat(args[2]),
                            Float.parseFloat(args[3])
                    ));
                } break;
                case "f": {
                    ArrayList<Vertex> builtVertices = new ArrayList<>();
                    int argIndex = 1;
                    while(argIndex < args.length && !args[argIndex].startsWith("#")) {
                        String[] vertexIndices = args[argIndex].split("/");
                        Vec3 position = positions.get(Integer.parseInt(vertexIndices[0]) - 1);
                        Vec2 texMapping = texMappings.get(Integer.parseInt(vertexIndices[1]) - 1);
                        builtVertices.add(new Vertex(
                                position,
                                (int) Math.floor(currentTexture[0].getWidth() * texMapping.x),
                                (int) Math.floor(currentTexture[0].getHeight() * (1 - texMapping.y))
                        ));
                        argIndex++;
                    }
                    String alias = null;
                    if(argIndex + 1 < args.length &&
                            args[argIndex].equals("#") &&
                            args[argIndex + 1].startsWith("nanocad-alias=")) {
                        alias = args[argIndex + 1].substring(14);
                    }
                    String finalAlias = alias;
                    switch(builtVertices.size()) {
                        case 3: {
                            ECB.add(new Triangle(
                                    builtVertices.get(0),
                                    builtVertices.get(1),
                                    builtVertices.get(2)
                            ).get(MeshComponent.class, meshComponent -> meshComponent.alias = finalAlias));
                        } break;
                        case 4: {
                            ECB.add(new Quad(
                                    builtVertices.get(0),
                                    builtVertices.get(1),
                                    builtVertices.get(2),
                                    builtVertices.get(3)
                            ).get(MeshComponent.class, meshComponent -> meshComponent.alias = finalAlias));
                        } break;
                    }
                }
            }
        }
    }

}
