
in vec3 fragment_color;
in float fragment_connection;

out vec4 out_color;

void main() {
    if(fragment_connection < 0.98f) {
        out_color = vec4(0.0, 0.0, 0.0, 1.0);
        gl_FragDepth = 1.0;
    } else {
        out_color = vec4(fragment_color, 1.0);
        gl_FragDepth = 0.0;
    }
}