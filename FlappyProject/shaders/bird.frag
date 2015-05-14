#version 400 core

in vec2 vs_tcs;

out vec4 color;

uniform sampler2D tex;

void main(){

	color = texture(tex, vs_tcs);
	//color = vec4(1,1,1,1);

}