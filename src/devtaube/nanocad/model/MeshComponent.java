package devtaube.nanocad.model;

import rosequartz.ecb.Component;
import rosequartz.math.Vec3;

public class MeshComponent implements Component {

    public final Vertex[] vertices;
    public String alias = null;

    public MeshComponent(Vertex... vertices) {
        this.vertices = vertices;
    }

    public Vec3 calculateSurfaceNormal() {
        Vec3 normal = new Vec3();
        for(int vertexIndex = 0; vertexIndex < vertices.length; vertexIndex++) {
            Vec3 current = vertices[vertexIndex].xyz;
            Vec3 next = vertices[(vertexIndex + 1) % vertices.length].xyz;
            normal.add(
                    (current.y - next.y) * (current.z + next.z),
                    (current.z - next.z) * (current.x + next.x),
                    (current.x - next.x) * (current.y + next.y)
            );
        }
        return normal.normalize();
    }

    public Vec3 calculateAveragePosition() {
        Vec3 average = new Vec3();
        for(Vertex vertex: vertices) average.add(vertex.xyz);
        return average.div(vertices.length, vertices.length, vertices.length);
    }

}
