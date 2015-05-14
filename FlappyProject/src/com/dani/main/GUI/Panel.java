package com.dani.main.GUI;



public class Panel {
	
	Button button;
	Menu menu;
	
	public Panel(Menu menu, Button button){
		
		this.menu = menu;
		this.button = button;
		
	}
	//AllerDisplay.ttf

	
	public void render(){
		this.menu.render();
		this.button.render();
		
	}
	
	

}
