#version 400 core

out vec4 color;

in vec2 vs_tc;

uniform sampler2D tex;

void main(){
	
	color = texture(tex, vs_tc);
	//color = vec4(1,1,0,1);


}