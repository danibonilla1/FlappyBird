package com.dani.main.level;

import org.lwjgl.opengl.GL13;

import com.dani.main.Main;
import com.dani.main.graphics.Shader;
import com.dani.main.graphics.Texture;
import com.dani.main.graphics.VertexArray;
import com.dani.main.input.Input;
import com.dani.main.maths.Matrix4f;
import com.dani.main.maths.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Bird {
	
	private VertexArray bird;
	private Texture texture;
	
	private float SIZE;
	private float angle;
	private float y, velY ,acc, time;
	float JUMPSP;
	private boolean dir;
	private boolean falling;
	private boolean dead = false;
	
	
	public Bird(float SIZE, float y){
		this.SIZE = SIZE;
		this.y = y;
		float[] vertices = new float[] {
				-SIZE/2, SIZE/2, 0.2f,
				-SIZE/2, -SIZE/2, 0.2f,
				SIZE/2, -SIZE/2, 0.2f,
				SIZE/2, SIZE/2, 0.2f,
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
		
		bird = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/bird.png");
		acc = -23f;
		velY = 0;
		time = 0f;
		JUMPSP = 8f;
		falling = false;
	}
	
	public void update(){
		time = (float) Main.getDeltad();
		
		if(Input.isKeyDown(GLFW_KEY_SPACE) && falling == false && y < 4.8){
			velY = JUMPSP;
			dir = true;
		}
		
		if(velY < 0){
			dir = false;
		}
		
		if(dir){
			if(angle < 15)
				angle += 15;
		}else if(!dead){
			if(angle > -50)
				angle -= 6;
		}
		
		y += velY * time;
		velY += acc * time;
	}
	
	public void render(){
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		texture.bind();
		Shader.BIRD.enable();
		bird.bind();
		Shader.BIRD.setUniformMat4f("tr_matrix", Matrix4f.translate(new Vector3f(0, y, 0)).multiply(Matrix4f.rotate(angle)));
		
		bird.draw();
		
		Shader.BIRD.disable();
		bird.unbind();
		texture.unbind();
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float y){
		this.y = y;
	}

	public float getSize() {
		return SIZE;
	}
	
	public void setFall(){
		falling = true;
	}
	
	public void setRest(){
		dead = true;
		angle = 0;
		velY = acc = 0;
	}

}
