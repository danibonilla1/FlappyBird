#version 400 core

in vec4 position;
in vec2 tcs;

out vec2 vs_tcs;

uniform mat4 tr_matrix;
uniform mat4 pr_matrix;
uniform mat4 vw_matrix;

void main(){
	
	gl_Position = pr_matrix * vw_matrix * tr_matrix * position;
	vs_tcs = tcs;

}