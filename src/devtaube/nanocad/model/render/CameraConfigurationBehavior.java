package devtaube.nanocad.model.render;

import rosequartz.ecb.Behavior;
import rosequartz.gfx.PerspectiveCamera;
import rosequartz.gfx.RenderTarget;
import rosequartz.math.Vec2;

public class CameraConfigurationBehavior implements Behavior {

    private final PerspectiveCamera camera;

    public static float cameraDistance = 5f;

    private float x;
    private float y;
    private float z;

    public CameraConfigurationBehavior(PerspectiveCamera camera) {
        this.camera = camera;
    }

    public void calculate(Vec2 rotation) {
        x = (float) (Math.sin(rotation.y) * Math.cos(rotation.x)) * cameraDistance;
        y = (float) Math.sin(rotation.x) * cameraDistance;
        z = (float) (Math.cos(rotation.y) * Math.cos(rotation.x)) * cameraDistance;
    }

    @Override
    public void run() {
        camera.getConfiguration().setPosition(x, y, z);
        camera.setAspectRatio(RenderTarget.getCurrent().getWidth(), RenderTarget.getCurrent().getHeight());
    }

}
