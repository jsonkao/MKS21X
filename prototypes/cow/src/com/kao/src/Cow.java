package com.kao.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Cow implements ActionListener, KeyListener {
	
	public static Cow cow = new Cow();
	
	public Renderer renderer;
	
	public Fighter p1, p2;
	
	public int width = 700, height = 700;

	public Cow() {
		
		Timer timer = new Timer(20, this);
		JFrame jframe = new JFrame("Cow");
		renderer = new Renderer();
		
		jframe.pack(); // used to reposition window
		jframe.setVisible(true);
		jframe.setSize(width, height);
		jframe.setResizable(true);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jframe.add(renderer);
		jframe.addKeyListener(this);
		
		timer.start();
		start();
	}
	
	public void start() {
		p1 = new Fighter(this, 3);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		p1.render(g);
	}
	
	public void update() {
		p1.move();
	}

	public static void main(String[] args) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		renderer.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();
		
		if (id == KeyEvent.VK_SPACE) {
			p1.turning = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();
			
		if (id == KeyEvent.VK_SPACE) {
			p1.turning = false;
		}
	}

}
