package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.ModelTexture;
import devtaube.nanocad.model.ModelTextureComponent;
import rosequartz.ecb.ECB;
import rosequartz.files.Resource;
import rosequartz.gfx.Texture;

public class TextureCommand extends Command {

    @Override
    public String[] getNames() { return new String[] { "texture", "tex" }; }

    @Override
    public int[] getValidArgCounts() { return new int[] { 0 }; }

    @Override
    public String getArgUsage() { return ""; }

    @Override
    public String getDescription() { return "Reloads the texture."; }

    @Override
    protected String run() {
        ECB.get(ModelTextureComponent.class, (modelTexture, modelTextureComponent) -> ECB.remove(modelTexture));
        ECB.add(new ModelTexture(new Texture(new Resource("/texture.png"))));
        return "Loaded texture from \"/res/texture.png\".";
    }

}
