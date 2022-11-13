
in vec2 fragment_tex_mappings;

out vec4 out_color;

uniform sampler2D TEXTURE;

void main() {
    out_color = texture(TEXTURE, fragment_tex_mappings);
}