package com.kao.src;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener{

	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer loopTimer = new Timer(20,(ActionListener) this);
	
	public static Snake snake;
	public Dimension dim;
	public boolean over = false;
	
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public int ticks = 0, direction = UP, score, tailLength;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	public Point head, cherry;
	
	public Random random;

	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame();
		jframe.pack(); // used to reposition window
		jframe.setVisible(true);
		jframe.setSize(800,600);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null); // window opens in the middle
		
		jframe.add(renderPanel = new RenderPanel());
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // enables X button
		
		random = new Random();
		cherry = new Point(dim.width / SCALE, dim.height / SCALE);
		head = new Point(0,0);
		loopTimer.start(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		renderPanel.repaint();
		ticks++;
		
		if (ticks % 10 == 0 && head != null && over != false){
			snakeParts.add(new Point(head.x,head.y));
			if (direction == UP) {
				if (head.y - 1 > 0)
					head = new Point(head.x, head.y-1);
				else 
					over = true;
				}
			}
			if (direction == DOWN) {
				if (head.y + 1 < dim.height / SCALE)
					head = new Point(head.x, head.y+1);	
				else
					over = true;
			}
			if (direction == LEFT) {
				if (head.x - 1 > 0)
					head = new Point(head.x-1, head.y);
				else
					over = true;
			}
			if (direction == RIGHT) {
				if (head.x + 1 < dim.width / SCALE)
					head = new Point(head.x+1, head.y+1);
				else
					over = true;
			}
			snakeParts.remove(0);
			if (cherry != null) {
				if (head.equals(cherry)){
					score += 10;
					tailLength++;
					cherry.setLocation(dim.width / SCALE, dim.height / SCALE);
					
			}
			
		}
		
	}
	
	public static void main(String args[]){
		snake = new Snake();
	}
}