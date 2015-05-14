#version 400 core

in vec2 vs_tcs;

out vec4 color;


uniform sampler2D tex;
uniform int down;

void main(){
	
	if(down == 1){
		
		color = texture(tex, vec2(vs_tcs.x, -vs_tcs.y));
	}else{
		color = texture(tex, vs_tcs);
	}
	//color = vec4(1,1,0,1);
	//vec2 vs_tcs_d = vec2(vs_tcs.y, vs_tcs.x)
}