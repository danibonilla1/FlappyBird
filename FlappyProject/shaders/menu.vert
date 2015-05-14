#version 400 core

in vec4 position;

out vec2 tcs;

uniform mat4 tr_matrix;

void main(){
	
	gl_Position = tr_matrix * position;
	tcs = vec2((position.x + 1.0) / 2.0, 1 - (position.y + 1.0) / 2.0);

}