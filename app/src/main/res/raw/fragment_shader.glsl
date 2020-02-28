precision mediump float;

uniform sampler2D u_Texture;
uniform vec4 vColor;

varying vec2 v_TexCoordinate;

void main() {
    //gl_FragColor = vColor;
    //gl_FragColor = vec4(v_TexCoordinate, 0.0, 1.0);
    gl_FragColor = texture2D(u_Texture, v_TexCoordinate);
}