package com.kao.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Cow implements ActionListener, KeyListener {
	
	public int width = 700, height = 700; 
	public static int ticks;

	public int gameStatus = 0; // 0 = menu, 1 = playing, 2 = paused
	public int numberOfFighterList = 2;
	
	public static Cow cow = new Cow();
	public Renderer renderer;
	public static ArrayList<Fighter> FighterList;
	public static int[] scores = new int[4];

	public Cow() {
		
		Timer timer = new Timer(20, this);
		JFrame jframe = new JFrame("Cow");
		renderer = new Renderer();
		
		jframe.pack(); // used to reposition window
		jframe.setVisible(true);
		jframe.setFocusable(true);
		jframe.setSize(width, height);
		jframe.setResizable(true);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jframe.add(renderer);
		jframe.addKeyListener(this);
		
		timer.start();
	}
	
	public void start() {
		gameStatus = 1;
		
		FighterList = new ArrayList<Fighter>(numberOfFighterList);
		
		for (int i = 0; i < numberOfFighterList; i++) 
		{
			FighterList.add(new Fighter(this, i + 1));
		}
				
	}
	
	public boolean shouldRestart() {
		int FighterListLeft = 0;
		for (int fNum = 0; fNum < FighterList.size(); fNum++) {
			if (FighterList.get(fNum).livingStatus != 0) {
				FighterListLeft++;
			}
		}
		return FighterListLeft == 1;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		if (gameStatus == 0) 
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font(null, 1, 28));
			g.drawString("COW", width / 2 - 18,height / 2 - 18);
			
			g.setFont(new Font(null, 1, 12));
			g.drawString("Press Enter for multiplayer.", width / 2 - 70, height / 2 + 20);
			
			g.setFont(new Font(null, 1, 12));
			g.drawString("> "+String.valueOf(numberOfFighterList)+" FighterList", width / 2 - 18,height / 2 + 48);
		} 
		
		if (gameStatus == 1 || gameStatus == 2) 
		{
			for (int fNum = 0; fNum < FighterList.size(); fNum++) {
				FighterList.get(fNum).render(g);
			}
			for (int fNum = 0; fNum < numberOfFighterList; fNum++) {
				g.setColor(Color.WHITE);
				g.setFont(new Font(null, 1, 28));
				g.drawString(String.valueOf(scores[fNum]), width / 2, 60 + 30 * fNum);
			}
		}
		
		if (gameStatus == 2) 
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font(null, 1, 28));
			g.drawString("PAUSED", width / 2 - 48,height / 2 - 18);
		}
		
		
	}
	
	public void update() {
		ticks++;
		
		if (shouldRestart()) {
			start();
		}
				
		for (int fNum = 0; fNum < FighterList.size(); fNum++) 
		{
			FighterList.get(fNum).reload();
			for (int blobNumber = 0; blobNumber < FighterList.get(fNum).blobs.size(); blobNumber++) 
			{
				FighterList.get(fNum).blobs.get(blobNumber).move();
			}
			FighterList.get(fNum).move();
			FighterList.get(fNum).getHit();
		}
	}

	public static void main(String[] args) throws ScriptException {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameStatus == 1) {
			update();
		}
		renderer.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int id = e.getKeyCode();
		
		if (gameStatus == 0) 
		{
			if (id == KeyEvent.VK_ENTER) {
				start();
			} else if (id == KeyEvent.VK_RIGHT) {
				if (numberOfFighterList == 4) {
					numberOfFighterList = 2;
				} else {
					numberOfFighterList++;
				}
			}
		} 
		else if (gameStatus == 1) 
		{
			if (id == KeyEvent.VK_SPACE) {
				gameStatus = 2;
			} else if (id == KeyEvent.VK_ESCAPE) {
				gameStatus = 0;
			}
			
			for (int fNum = 0; fNum < FighterList.size(); fNum++) 
			{
				if (id == FighterList.get(fNum).keyEvents[0]) {
					FighterList.get(fNum).turning = true;
				} 
				
				if (id == FighterList.get(fNum).keyEvents[1]) 
				{
					if (FighterList.get(fNum).globuleStatus[1] == 1) {
						FighterList.get(fNum).globuleStatus[2] = 1;
					} else {
						FighterList.get(fNum).shoot();
					}
				}
			}
		}
		else if (gameStatus == 2)
		{
			if (id == KeyEvent.VK_SPACE) {
				gameStatus = 1;
			} else if (id == KeyEvent.VK_ESCAPE) {
				gameStatus = 0;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();
			
		if (gameStatus == 1) {
			for (int fNum = 0; fNum < FighterList.size(); fNum++) 
			{
				if (id == FighterList.get(fNum).keyEvents[0]) {
					FighterList.get(fNum).turning = false;
				} 
				if (id == FighterList.get(fNum).keyEvents[1] && FighterList.get(fNum).globuleStatus[1] == 1) {
					FighterList.get(fNum).globuleStatus[2] = 0;
				}
			}
		}
	}

}
