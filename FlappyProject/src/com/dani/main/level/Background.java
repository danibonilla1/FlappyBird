package com.dani.main.level;

import org.lwjgl.opengl.GL13;

import com.dani.main.graphics.Shader;
import com.dani.main.graphics.Texture;
import com.dani.main.graphics.VertexArray;
import com.dani.main.maths.Matrix4f;
import com.dani.main.maths.Vector3f;

public class Background {
	private VertexArray background;
	private Texture texture;
	
	private float x;
	
	public Background(float x){
		float[] vertices = new float[]{
				-10.0f, 10.0f * 9.0f/16.0f, 0f,
				-10.0f, -10.0f * 9.0f/16.0f, 0f,
				10.0f, -10.0f * 9.0f/16.0f, 0f,
				10.0f, 10.0f * 9.0f/16.0f, 0f,
		};
		
		byte[] indices = new byte[]{
				0, 1, 3,
				3, 1, 2
				
		};
		
		float[] tcs = new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		background = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/bg.jpeg");
		
		this.x = x;
		
	}
	
	public void render(){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		texture.bind();
		Shader.BG.enable();
		background.bind();
		Shader.BG.setUniformMat4f("tr_matrix", Matrix4f.translate(new Vector3f(x,0,0)));
		background.draw();
		
		Shader.BG.disable();
		texture.unbind();
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	
}
