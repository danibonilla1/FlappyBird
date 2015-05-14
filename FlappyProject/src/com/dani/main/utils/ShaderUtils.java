package com.dani.main.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL11;

public class ShaderUtils {
	
	private ShaderUtils(){
		
	}
	
	public static int load(String vertPath, String fragpath){
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragpath);
		return create(vert, frag);
	}
	
	public static int create(String vert, String frag){
		int programID = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		
		glCompileShader(vertID);
		if(glGetShaderi(vertID, GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.err.println("Failed to create vertex shader");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}
		
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}
		
		glAttachShader(programID, vertID);
		glAttachShader(programID, fragID);
		glLinkProgram(programID);
		glValidateProgram(programID);
		
		glDeleteShader(vertID);
		glDeleteShader(fragID);
		
		return programID;
		
		
	}
}
