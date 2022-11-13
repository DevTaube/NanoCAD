
layout(location=0) in vec3 in_position;
layout(location=1) in vec2 in_tex_mappings;

out vec2 fragment_tex_mappings;

uniform mat4 PROJECTION_VIEW_MATRIX;

void main() {
    gl_Position = PROJECTION_VIEW_MATRIX * vec4(in_position, 1.0);
    fragment_tex_mappings = in_tex_mappings;
}