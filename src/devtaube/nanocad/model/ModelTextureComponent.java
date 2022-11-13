package devtaube.nanocad.model;

import rosequartz.ecb.Component;
import rosequartz.gfx.Texture;

public class ModelTextureComponent implements Component {

    public final Texture texture;

    public ModelTextureComponent(Texture texture) {
        this.texture = texture;
    }

}
