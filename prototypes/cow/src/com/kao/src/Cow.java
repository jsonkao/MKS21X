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

	public int gameStatus = 0; // 0 = menu, 1 = playing, 2 = paused
	public int numberOfPlayers = 2;
	
	public static Cow cow = new Cow();
	public Renderer renderer;
	public static ArrayList<Fighter> players;
	public int[] scores;

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
		
		players = new ArrayList<Fighter>(numberOfPlayers);
		scores = new int[numberOfPlayers];
		
		for (int i = 0; i < numberOfPlayers; i++) 
		{
			players.add(new Fighter(this, i + 1));
		}
				
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
			g.drawString("> "+String.valueOf(numberOfPlayers)+" players", width / 2 - 18,height / 2 + 48);
		} 
		
		if (gameStatus == 1 || gameStatus == 2) 
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font(null, 1, 28));
			g.drawString(String.valueOf(players.get(0).score), width / 2 - 40, 60);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font(null, 1, 28));
			g.drawString(String.valueOf(players.get(1).score), width / 2 + 24, 60);
			for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) 
			{
				players.get(playerNumber).render(g);
				g.setColor(Color.WHITE);
				g.setFont(new Font(null, 1, 28));
				g.drawString(String.valueOf(players.get(0).score), width / 2 - 40, 60);
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
		
		for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) 
		{
			players.get(playerNumber).reload();
			players.get(playerNumber).move();
			for (int blobNumber = 0; blobNumber < players.get(playerNumber).blobs.size(); blobNumber++) 
			{
				players.get(playerNumber).blobs.get(blobNumber).move();
				
			}
			players.get(playerNumber).getHit();
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
				if (numberOfPlayers == 4) {
					numberOfPlayers = 2;
				} else {
					numberOfPlayers++;
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
			
			for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) 
			{
				if (id == players.get(playerNumber).keyEvents[0]) {
					players.get(playerNumber).turning = true;
				} else if (id == players.get(playerNumber).keyEvents[1]) {
					players.get(playerNumber).shoot();
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
			for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) 
			{
				if (id == players.get(playerNumber).keyEvents[0]) {
					players.get(playerNumber).turning = false;
				}
			}
		}
	}

}
