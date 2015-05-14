package com.dani.main.GUI;

import org.lwjgl.opengl.GL13;

import com.dani.main.graphics.Shader;
import com.dani.main.graphics.Texture;
import com.dani.main.graphics.VertexArray;
import com.dani.main.maths.Matrix4f;
import com.dani.main.maths.Vector3f;

public class Menu {
	
	public static Menu start;
	private Vector3f position;
	private Vector3f scale;
	VertexArray Box;
	Texture texture;
	
	public Menu(Vector3f position, Vector3f scale){
		
		float[] vertices = new float[]{
				-1.0f, 1.0f, -0.5f,
				-1.0f, -1.0f, -0.5f,
				1.0f, 1.0f, -0.5f,
				1.0f, -1.0f, -0.5f,
		};
		
		
		this.position = position;
		this.scale = scale;
		
		Box = new VertexArray(vertices);
		texture = new Texture("res/GameOver.png");
	}
	
	public Menu(Vector3f position, Vector3f scale, Texture texture){
		
		float[] vertices = new float[]{
				-1.0f, 1.0f, -0.5f,
				-1.0f, -1.0f, -0.5f,
				1.0f, 1.0f, -0.5f,
				1.0f, -1.0f, -0.5f,
		};
		
		
		this.position = position;
		this.scale = scale;
		
		Box = new VertexArray(vertices);
		this.texture = texture;
	}
	
	public static void loadAll(){
		start = new Menu(new Vector3f(0f, 0f, -0.4f), new Vector3f(.60f, .80f, 1f));
	}
	
	
	public void render() {
		
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		texture.bind();
		Shader.MENU.enable();
		Box.bind();
		
		Shader.MENU.setUniformMat4f("tr_matrix", Matrix4f.translate(position).multiply(Matrix4f.scale(scale)));
		
		Box.draw();
		
		Shader.MENU.disable();
		texture.unbind();
		Box.unbind();
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		
	}
	
	
}
