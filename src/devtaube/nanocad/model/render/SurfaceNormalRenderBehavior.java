package devtaube.nanocad.model.render;

import devtaube.nanocad.editor.InfoRenderer;
import devtaube.nanocad.model.MeshComponent;
import rosequartz.ecb.Behavior;
import rosequartz.ecb.ECB;
import rosequartz.files.Resource;
import rosequartz.gfx.Camera;
import rosequartz.gfx.DepthTestingManager;
import rosequartz.gfx.ShaderProgram;
import rosequartz.gfx.VertexArray;
import rosequartz.math.Vec3;

public class SurfaceNormalRenderBehavior implements Behavior {

    public static boolean render = false;

    private final ShaderProgram shader;
    private final VertexArray array;
    private final Camera<?> camera;

    private final InfoRenderer infoRenderer;

    public SurfaceNormalRenderBehavior(Camera<?> camera) {
        // shader
        shader = new ShaderProgram(
                new Resource("shaders/line_vertex.glsl"),
                new Resource("shaders/line_fragment.glsl")
        );
        // vertex array
        array = new VertexArray(3, 3, 1);
        // camera
        this.camera = camera;
        // info renderer
        infoRenderer = new InfoRenderer(camera);
    }

    @Override
    public void run() {
        if(!render) return;
        // put all normals into the array
        array.clear();
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            Vec3 start = meshComponent.calculateAveragePosition();
            Vec3 end = start.addN(meshComponent.calculateSurfaceNormal().scale(0.5f));
            int offset = array.getVertexCount();
            array
                    .vertex( start.x,     start.y,     start.z,       1, 0, 1,   1 )
                    .vertex( end.x,       end.y,       end.z,         1, 0, 1,   1 )
                    .vertex( start.x + 1, start.y + 1, start.z + 1,   1, 0, 1,   0 )
                    .fragment(offset, offset + 1, offset + 2);
        });
        array.upload();
        // select the shader and set projection view matrix
        shader.select()
                .setUniformMatrix4("POSITION_MATRIX", camera.getProjectionViewMatrix());
        // set depth testing to be true
        DepthTestingManager.get().setEnabled(true);
        // render the lines
        array.render();
        // for each Mesh, render its index at the normal vector start position
        int[] meshIndex = { 0 };
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            infoRenderer.render(meshComponent.calculateAveragePosition(), String.valueOf(meshIndex[0]));
            meshIndex[0]++;
        });
    }

}
