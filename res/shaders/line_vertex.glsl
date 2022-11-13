
layout(location=0) in vec3 in_position;
layout(location=1) in vec3 in_color;
layout(location=2) in float in_connection;

out vec3 fragment_color;
out float fragment_connection;

uniform mat4 POSITION_MATRIX;

void main() {
    gl_Position = POSITION_MATRIX * vec4(in_position, 1.0);
    fragment_color = in_color;
    fragment_connection = in_connection;
}