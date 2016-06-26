package com.kao.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel{
	
	public static int currentColor = 0;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(currentColor));
		g.fillRect(0, 0, 800, 700);
		Snake snake = Snake.snake;
		for (Point point : snake.snakeParts){
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE,  Snake.SCALE,  Snake.SCALE);
			g.setColor(Color.red);
		}
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE,  Snake.SCALE,  Snake.SCALE);
		g.setColor(Color.red);
	}
}