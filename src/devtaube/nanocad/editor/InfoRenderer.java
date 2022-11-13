package devtaube.nanocad.editor;

import rosequartz.gfx.Camera;
import rosequartz.gfx.Graphics;
import rosequartz.math.Vec3;
import rosequartz.math.Vec4;

public class InfoRenderer {

    private final Camera<?> camera;

    public InfoRenderer(Camera<?> camera) {
        this.camera = camera;
    }

    public void render(Vec3 pos, String text) {
        // get position of vertex in space and transform it into NDC
        Vec4 position = new Vec4(pos, 1)
                .mul(camera.getProjectionViewMatrix());
        position.div(position.w, position.w, position.w, position.w)
                .add(1, 1, 1, 0)
                .div(2, 2, 2, 1);
        // draw index at that position
        float textHeight = EditorRenderPipeline.TEXT_HEIGHT * 0.75f;
        float textWidth = textHeight * Graphics.windowHeight() / Graphics.windowWidth();
        EditorRenderPipeline.font.render(
                text,
                position.x - textWidth / 2,
                position.y - textHeight / 2,
                textHeight,
                textHeight / 5,
                textHeight / 5
        );
    }

}
