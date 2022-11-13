package devtaube.nanocad.model;

import rosequartz.ecb.Entity;

public class Quad extends Entity {

    public Quad(Vertex a, Vertex b, Vertex c, Vertex d) {
        add(
                new MeshComponent(a, b, c, d)
        );
    }

}
