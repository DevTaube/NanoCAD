package devtaube.nanocad.model.render;

import rosequartz.gfx.CameraConfiguration;
import rosequartz.gfx.GraphicsPipeline;
import rosequartz.gfx.PerspectiveCamera;

public class ModelRenderPipeline extends GraphicsPipeline {

    public final PerspectiveCamera camera;

    public ModelRenderPipeline() {
        // set the camera
        camera = new PerspectiveCamera(
                new CameraConfiguration()
                        .setPosition(0, 0, 0)
                        .setLookAt(0, 0, 0, 0, 1, 0)
        ).setFieldOfViewDegrees(60)
                .setClipPlanes(0.1f, 100);
        // add behaviors
        CameraConfigurationBehavior cameraConfigurationBehavior = new CameraConfigurationBehavior(camera);
        add(
                new CameraRotationBehavior(cameraConfigurationBehavior::calculate),
                cameraConfigurationBehavior,
                new ModelRenderBehavior(camera),
                new CoordinateSystemRenderBehavior(camera),
                new SurfaceNormalRenderBehavior(camera)
        );
    }

}
