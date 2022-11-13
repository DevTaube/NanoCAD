package devtaube.nanocad.model;

import rosequartz.ecb.Entity;

public class Triangle extends Entity {

    public Triangle(Vertex a, Vertex b, Vertex c) {
        add(
                new MeshComponent(a, b, c)
        );
    }

}
