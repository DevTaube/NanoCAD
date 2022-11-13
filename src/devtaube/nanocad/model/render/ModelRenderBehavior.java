package devtaube.nanocad.model.render;

import devtaube.nanocad.model.MeshComponent;
import devtaube.nanocad.model.ModelTextureComponent;
import devtaube.nanocad.model.Vertex;
import rosequartz.RoseQuartz;
import rosequartz.ecb.Behavior;
import rosequartz.ecb.ECB;
import rosequartz.files.Resource;
import rosequartz.gfx.*;

public class ModelRenderBehavior implements Behavior {

    public static float CLEAR_COLOR_RED = 0;
    public static float CLEAR_COLOR_GREEN = 0;
    public static float CLEAR_COLOR_BLUE = 0;

    public final ShaderProgram shader;
    public final VertexArray array;
    private final Camera<?> camera;

    public ModelRenderBehavior(Camera<?> camera) {
        // compile shaders
        shader = new ShaderProgram(
                new Resource("shaders/model_vertex.glsl"),
                new Resource("shaders/model_fragment.glsl")
        );
        // create vertex array
        array = new VertexArray(3, 2);
        // camera
        this.camera = camera;
    }

    @Override
    public void run() {
        // select the shader and set the texture
        float[] textureSize = { 0, 0 };
        shader.select();
        ECB.get(ModelTextureComponent.class, (modelTexture, modelTextureComponent) -> {
            shader.setUniformTexture("TEXTURE", modelTextureComponent.texture);
            textureSize[0] = modelTextureComponent.texture.getWidth();
            textureSize[1] = modelTextureComponent.texture.getHeight();
        });
        // fill the vertex array with all the needed data
        array.clear();
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            int offset = array.getVertexCount();
            for(Vertex vertex: meshComponent.vertices) {
                array.vertex(
                        vertex.xyz.x, vertex.xyz.y, vertex.xyz.z, // xyz
                        (float) vertex.uvX / textureSize[0], 1 - ((float) vertex.uvY / textureSize[1]) // uv
                );
            }
            switch(meshComponent.vertices.length) {
                case 3: {
                    array.fragment(offset, offset + 1, offset + 2);
                } break;
                case 4: {
                    array.fragment(offset, offset + 1, offset + 2)
                            .fragment(offset, offset + 2, offset + 3);
                } break;
                default: throw new IllegalArgumentException(
                        "Illegal amount of vertices in mesh: " + meshComponent.vertices.length
                );
            }
        });
        array.upload();
        // set depth testing to be true
        DepthTestingManager.get().setEnabled(true);
        // clear the target
        RenderTarget.getCurrent()
                .clearColor(CLEAR_COLOR_RED, CLEAR_COLOR_GREEN, CLEAR_COLOR_BLUE, 1)
                .clearDepth(1);
        // set projection view matrix
        shader.setUniformMatrix4("PROJECTION_VIEW_MATRIX", camera.getProjectionViewMatrix());
        // render the model
        array.render();
    }

}
