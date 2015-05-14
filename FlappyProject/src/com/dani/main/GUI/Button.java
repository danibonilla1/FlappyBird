package com.dani.main.GUI;

import org.lwjgl.opengl.GL13;

import com.dani.main.graphics.Shader;
import com.dani.main.graphics.Texture;
import com.dani.main.graphics.VertexArray;
import com.dani.main.maths.Matrix4f;
import com.dani.main.maths.Vector3f;

public class Button{
	
	public static Button play;
	Vector3f position;
	Vector3f scale;
	VertexArray Box;
	Texture texture;

	public Button(Vector3f position, Vector3f scale, Texture texture) {
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
		play = new Button(new Vector3f(0f, -0.54f, -0.45f), new Vector3f(.40f, .50f, 1f), new Texture("res/Button.png"));
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
