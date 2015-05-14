package com.dani.main;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.dani.main.GUI.Button;
import com.dani.main.GUI.Menu;
import com.dani.main.GUI.Panel;
import com.dani.main.graphics.Shader;
import com.dani.main.input.Input;
import com.dani.main.level.Level;
import com.dani.main.maths.Matrix4f;

public class Main implements Runnable {

	public int width = 720;
	public int height = 480;
	private static double Delta = 0.01666666666;

	private Thread thread;
	private boolean running;

	private long window;

	GLFWErrorCallback error;
	GLFWKeyCallback key;
	
	Level level;
	
	Panel GameOver;

	public void start() {
			running = true;
			thread = new Thread(this, "Flappy");
			thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		glfwSetErrorCallback(error = errorCallbackPrint(System.err));

		if (glfwInit() != GL11.GL_TRUE) {
			System.err.println("Error starting glfw");

		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);

		window = glfwCreateWindow(width, height, "Flappy Birds!", NULL, NULL);

		if (window == NULL) {
			System.err.println("Error creating the window");
		}
		
		glfwSetKeyCallback(window, key = new Input());

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		glfwMakeContextCurrent(window);

		glfwShowWindow(window);

		GLContext.createFromCurrent();
		
		glfwSwapInterval(0);
		
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		
		
 		//glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Shader.loadAll();
		Menu.loadAll();
		Button.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex", 0);
		
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex", 1);
		
		Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PIPE.setUniform1i("tex", 2);
		
		Shader.MENU.setUniform1i("tex", 3);
		
		
		level = new Level();
		
		GameOver = new Panel(Menu.start, Button.play);

	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;


		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		
		
		glfwDestroyWindow(window);
		key.release();
		
		glfwTerminate();
		error.release();

	}

	private void update() {
		glfwPollEvents();
		if(Input.isKeyDown(GLFW_KEY_ESCAPE)) running = false;
		level.update();
		if(level.isTimeReset()){
			level = new Level();
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		
		level.render();
		if(!level.isGameGoing()){
			GameOver.render();
		}
		
		glfwSwapBuffers(window);
	}
	
	public static double getDeltad(){
		return Delta;
	}
	public static int getDeltai(){
		return (int)Delta;
	}

	public static void main(String args[]) {
		new Main().start();
	}

}
