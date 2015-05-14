#version 400 core

in vec4 position;
in vec2 tc;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix;

out vec2 vs_tc;

void main() {

	gl_Position = pr_matrix * vw_matrix * position;
	vs_tc = tc;

}