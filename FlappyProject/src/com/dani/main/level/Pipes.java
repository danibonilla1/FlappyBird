package com.dani.main.level;

import com.dani.main.graphics.Texture;
import com.dani.main.graphics.VertexArray;
import com.dani.main.maths.Matrix4f;
import com.dani.main.maths.Vector3f;

public class Pipes {
	
	private Vector3f position;
	Matrix4f tr_matrix;
	
	private static VertexArray pipe;
	private static Texture texture;
	private static float width = 1.5f;
	private static float height = 8.5f;
	
	
	public Pipes(float x, float y) {
		create();
		position = new Vector3f();
		position.x = x;
		position.y = y;
		tr_matrix = Matrix4f.translate(position);
	}
	
	public static void create(){
		float[] vertices = new float[] {
			width, height, 0.3f,
			width, 0, 0.3f,
			0, 0, 0.3f,
			0, height, 0.3f
		};
		
		byte[] indices = new byte[] {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] tcs = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
		};
		
		pipe = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/pipe.png");
	}

	public Vector3f getPosition() {
		return position;
	}

	public Matrix4f getTr_matrix() {
		return tr_matrix;
	}

	public static VertexArray getPipe() {
		return pipe;
	}

	public static Texture getTexture() {
		return texture;
	}

	public static float getWidth() {
		return width;
	}

	public static float getHeight() {
		return height;
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}
	

}
