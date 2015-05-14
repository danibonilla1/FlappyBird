package com.dani.main.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	private FileUtils(){
		
	}
	
	public static String loadAsString(String path){
		StringBuilder string = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String buffer = "";
			while((buffer = reader.readLine()) != null){
				string.append(buffer + '\n');
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return string.toString();
		
	}

}
