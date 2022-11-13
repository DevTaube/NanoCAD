package devtaube.nanocad.model;

import rosequartz.ecb.Entity;
import rosequartz.gfx.RenderTarget;
import rosequartz.gfx.Texture;

public class ModelTexture extends Entity {

    public ModelTexture(Texture texture) {
        Texture tex = texture;
        if(tex == null) {
            RenderTarget defaultTexture = new RenderTarget(new Texture(16, 16));
            defaultTexture.clearColor(1, 0, 0, 1);
            tex = defaultTexture.getTexture();
        }
        add(
                new ModelTextureComponent(tex)
        );
    }

}
