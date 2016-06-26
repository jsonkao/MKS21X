package com.kao.src.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import com.kao.src.GlobalPosition;

public class Player extends GlobalPosition {

	private String playerimage= "/images/player.png"; // path to images folder
	
	double velX = 0;
	double velY = 0;
			
	public Player(int x, int y) { // Auto-generated player constructor 
		super(x, y); // feeds the code in public class GlobalPosition 
	}
	
	public void update(){ // updates position of p
		// trig is in radians
		x += velX;
		y += velY;
	}
	
	public void draw(Graphics2D g2d){ // g2d is the graphics variable we draw on
		BufferedImage player_image = LoadImage("playerimage");
		
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.PI/3,player_image.getWidth()/2,player_image.getWidth()/2);
		g2d.drawImage(getPlayerImage(), at, null); // observer null means it's not referring to this class
		
	}
	
	private BufferedImage LoadImage(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getPlayerImage(){ // returns image
		ImageIcon i = new ImageIcon(getClass().getResource(playerimage)); // gets image
		return i.getImage(); 
	}
		
	public void rotateImate(double degrees){
		
	}
	
	public void keyPressed(KeyEvent e){ // will also call KeyInput.keyPressed
		int key = e.getKeyCode();
		
		if (key==KeyEvent.VK_S){
			velX = 1;
		}
	}
	
	public void keyReleased(KeyEvent e){ // will also call KeyInput.keyReleased
		int key = e.getKeyCode();
		
		if (key==KeyEvent.VK_S){
			velX = 0;
		}
	}

	
}