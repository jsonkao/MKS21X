package com.kao.src;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
	
	public int paddleNum;
	
	public int x, y, width = 100, height = 500;
	
	public int score;

	public Paddle(Pong pong, int paddleNum) {
		this.paddleNum = paddleNum;
		
		if (paddleNum == 1){
			this.x = width;
		}
		if (paddleNum == 2){
			this.x = pong.width - width;
		}
		this.y = pong.height / 2 - this.height / 2;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		
	}

}