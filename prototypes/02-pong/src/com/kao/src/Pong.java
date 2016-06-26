package com.kao.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener {
	
	public static Pong pong;
	
	public int width = 700, height = 700;
	
	public Renderer renderer; 
	
	public Paddle p1, p2;
	
	/* constructor provides initial values for class fields when you create the object */
	public Pong(){
		/* The timer object tells ActionListener to fire an ActionEvent at the specified interval of 20 milliseconds */
		Timer timer = new Timer(20,this);
		JFrame  jframe = new JFrame("Pong");
		
		renderer = new Renderer();
		
		jframe.setSize(width,height);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		
		start();
		
		timer.start();
	}
	
	public void start(){
		p1 = new Paddle(this, 1);
		p2 = new Paddle(this, 2);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		p1.render(g);
		p2.render(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		renderer.repaint(); // repaint: in a frame, all components must be repainted
		
	}
	
	public static void main(String[] args) {
		pong = new Pong();
	}

	

	

}