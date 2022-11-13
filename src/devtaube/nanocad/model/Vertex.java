package devtaube.nanocad.model;

import rosequartz.math.Vec3;

public class Vertex implements Cloneable {

    public final Vec3 xyz;
    public int uvX;
    public int uvY;

    public Vertex(Vec3 xyz, int uvX, int uvY) {
        this.xyz = xyz;
        this.uvX = uvX;
        this.uvY = uvY;
    }

    @Override
    public Vertex clone() {
        return new Vertex(new Vec3(xyz), uvX, uvY);
    }

}
