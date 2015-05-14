package com.dani.main.level;

import java.util.Random;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL13;

import com.dani.main.graphics.Shader;
import com.dani.main.graphics.Texture;
import com.dani.main.graphics.VertexArray;
import com.dani.main.input.Input;
import com.dani.main.maths.Matrix4f;
import com.dani.main.maths.Vector3f;

public class Level {
	
	Bird bird;
	private int index = 0;
	private int xScroll = 0;
	private int limit = -1;
	private int Offset = 5;
	
	VertexArray BackGround;
	Texture BG;
	private Pipes[] pipes = new Pipes[5 * 2];
	Random random = new Random();
	
	private boolean running;
	private boolean reset = false;
	
	
	public Level(){
		
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

		BackGround = new VertexArray(vertices, indices, tcs);
		BG = new Texture("res/bg.jpeg");
		
		bird = new Bird(1f, 0f);
		createPipes();
		running = true;
	}
	
	
	
	private void createPipes() {
		
		for(int i = 0; i < 5 *2; i+=2){
			pipes[i] = new Pipes(Offset + index * 3.0f, random.nextFloat() * 4.0f);
			pipes[i+1] = new Pipes(pipes[i].getX(), pipes[i].getY() - 11.9f);
			
			index+=2;
		}
		
		
	}
	
	private void updatePipes(){
		pipes[index%10] = new Pipes(Offset + index * 3.0f, random.nextFloat() * 4.0f);
		pipes[(index%10) + 1] = new Pipes(pipes[index%10].getX(), pipes[index%10].getY() - 11.9f);
		index += 2;
	}
	
	private void renderPipes(){
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		Pipes.getTexture().bind();
		Shader.PIPE.enable();
		Pipes.getPipe().bind();
		Shader.PIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.03f, 0.0f, 0.0f)));
		
		for(int i = 0; i < 5 * 2; i++){
			Shader.PIPE.setUniformMat4f("tr_matrix", pipes[i].getTr_matrix());
			Shader.PIPE.setUniform1i("down", i % 2 == 0? 0 : 1);
			Pipes.getPipe().draw();
		}
		
		
		Shader.PIPE.disable();
		Pipes.getPipe().unbind();
		Pipes.getTexture().unbind();
	}
	
	private boolean collision(){
		for (int i = 0; i < 5 * 2; i++) {
			float bx = -xScroll * 0.03f;
			float by = bird.getY();
			float px = pipes[i].getX();
			float py = pipes[i].getY();
			
			float bx0 = bx - bird.getSize()/3;
			float bx1 = bx + bird.getSize()/3;
			float by0 = by - bird.getSize()/3;
			float by1 = by + bird.getSize()/3;
			
			float px0 = px;
			float px1 = px + Pipes.getWidth();
			float py0 = py;
			float py1 = py + Pipes.getHeight();
			
			
			if ( bx0 < px1 && bx1 > px0) {
				if (by0 < py1 && by1 > py0) {
					return true;
				}
			}
		}
		
		return false;
	}


	public void update(){
		if(running){
			xScroll--;
			if (-xScroll % 335 == 0) limit++;
			if (-xScroll > 500 && -xScroll % 200 == 0)
				updatePipes();
		}
		if(collision() && running){
			running = false;
			bird.setFall();
			
		}
		
		
		if(!running && Input.isKeyDown(GLFW.GLFW_KEY_SPACE)){
			reset = true;
		}
		
		if(bird.getY() < -4.8 - bird.getSize()/2){
			running = false;
			bird.setRest();
		}
			
		bird.update();

	}




	public boolean isTimeReset() {
		return reset;
	}
	
	public boolean isGameGoing() {
		return running;
	}



	public void render(){
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		BG.bind();
		Shader.BG.enable();
		BackGround.bind();
		
		for(int i = limit; i < limit + 4 ; i++) {
			Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
			BackGround.draw();
		}
		
		Shader.BG.disable();
		BackGround.unbind();
		BG.unbind();
		
		bird.render();
		renderPipes();
		
	}
}
