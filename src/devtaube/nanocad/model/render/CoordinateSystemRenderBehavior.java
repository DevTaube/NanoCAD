package devtaube.nanocad.model.render;

import rosequartz.ecb.Behavior;
import rosequartz.files.Resource;
import rosequartz.gfx.Camera;
import rosequartz.gfx.DepthTestingManager;
import rosequartz.gfx.ShaderProgram;
import rosequartz.gfx.VertexArray;

public class CoordinateSystemRenderBehavior implements Behavior {

    private final ShaderProgram shader;
    private final VertexArray array;
    private final Camera<?> camera;

    public CoordinateSystemRenderBehavior(Camera<?> camera) {
        // shader
        shader = new ShaderProgram(
                new Resource("shaders/line_vertex.glsl"),
                new Resource("shaders/line_fragment.glsl")
        );
        // vertex array
        array = new VertexArray(3, 3, 1)
                //       X, Y, Z    R, G, B,    <part of line? 1 : 0>
                .vertex( 0, 0, 0,   1, 0, 0,   1 )
                .vertex( 1, 0, 0,   1, 0, 0,   1 )
                .vertex( 1, 1, 1,   1, 0, 0,   0 )
                .fragment( 0, 1, 2 )
                .vertex( 0, 0, 0,   0, 1, 0,   1 )
                .vertex( 0, 1, 0,   0, 1, 0,   1 )
                .vertex( 1, 1, 1,   0, 1, 0,   0 )
                .fragment( 3, 4, 5 )
                .vertex( 0, 0, 0,   0, 0, 1,   1 )
                .vertex( 0, 0, 1,   0, 0, 1,   1 )
                .vertex( 1, 1, 1,   0, 0, 1,   0 )
                .fragment( 6, 7, 8 )
                .upload();
        // camera
        this.camera = camera;
    }

    @Override
    public void run() {
        // select the shader and set projection view matrix
        shader.select()
                .setUniformMatrix4("POSITION_MATRIX", camera.getProjectionViewMatrix());
        // set depth testing to be true
        DepthTestingManager.get().setEnabled(true);
        // render the lines
        array.render();
    }

}
