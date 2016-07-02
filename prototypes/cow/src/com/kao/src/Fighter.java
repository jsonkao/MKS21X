package com.kao.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Fighter {
	
	public int fighterNumber, x, y, width = 20, height = 20, livingStatus = 2, score, maxAmmo = 3, ammoLeft;
	
	public int globuleSize = 12, ammoIndicatorSize = 3, ammoIndicatorDistance = 20;
	
	public double angle, speedForward = .35, speedMilk = .8, speedRotate = 1;
	public boolean turning = false, globuleActive = false, globuleMoving = false;
	
	public int motionX, motionY, globuleSpeedX, globuleSpeedY;
	public Double velX, velY;
			
	public static int distanceFromBorder = 20, windowCorrectionBottom = 22;
	
	public ArrayList<Milk> blobs = new ArrayList<Milk>();
	
	public int[] keyEvents = new int[2];
				
	public Fighter(Cow cow, int fighterNumber) {
		
		this.fighterNumber = fighterNumber - 1;
		
		ammoLeft = maxAmmo;
		
		switch (fighterNumber) {
		case 1: // lower left corner
			x = 0 + distanceFromBorder;
			y = cow.height - height - distanceFromBorder - windowCorrectionBottom;
			angle = 7 * Math.PI / 4; 
			keyEvents[0] = KeyEvent.VK_A;
			keyEvents[1] = KeyEvent.VK_S;
			break;
		case 2: // upper right corner
			x = cow.width - width - distanceFromBorder;
			y = 0 + distanceFromBorder;
			angle = 3 * Math.PI / 4; 
			keyEvents[0] = KeyEvent.VK_N;
			keyEvents[1] = KeyEvent.VK_M;
			break;
		case 3: // upper left corner
			x = 0 + distanceFromBorder;
			y = 0 + distanceFromBorder;
			angle = 1 * Math.PI / 4; 
			keyEvents[0] = KeyEvent.VK_OPEN_BRACKET;
			keyEvents[1] = KeyEvent.VK_CLOSE_BRACKET;
			break;
		case 4: // lower right corner
			x = cow.width - width - distanceFromBorder;
			y = cow.height - height - distanceFromBorder - windowCorrectionBottom;
			angle = 4 * Math.PI / 4; 
			keyEvents[0] = KeyEvent.VK_C;
			keyEvents[1] = KeyEvent.VK_V;
			break;
		}

	}
	
	public void move() {
		if (turning) {
			angle += Math.PI * speedRotate / 40;
		}
		
		velX = new Double(Math.cos(angle) * 10);
		motionX = (int) (velX.intValue() * speedForward);
		velY = new Double(Math.sin(angle) * 10); 
		motionY = (int) (velY.intValue() * speedForward);
		
		boolean outX = x + motionX < 0 || x + width + motionX > Cow.cow.width;
		boolean outY = y + motionX < 0 || y + height + motionY > Cow.cow.height - windowCorrectionBottom;
		
		if (globuleActive) 
		{
			if (globuleMoving) {
				if (outX) {y += motionY;} 
				else if (outY) {x += motionX;}
				else {x += motionX; y += motionY;}
			} else {
				if (motionX > 0) {motionX--;}
				if (motionY > 0) {motionY--;}
			}
		}
		else 
		{	
			if (outX) {y += motionY;} 
			else if (outY) {x += motionX;}
			else {x += motionX; y += motionY;}
		}
	}
	
	public void shoot() {
		if (ammoLeft > 0) {
			blobs.add(new Milk(this));
			ammoLeft -= 1;
		}
	}
	
	public int[] collisionStatus(Fighter fighter) {
		
		int[] collisionStatus = {0, 0}; // {collision, blob#}
		for (int activeBlob = 0; activeBlob < blobs.size(); activeBlob++) 
		{
			boolean collision = Math.pow(blobs.get(activeBlob).x - fighter.x,2) + Math.pow(blobs.get(activeBlob).y - fighter.y,2) < 100;
			if (collision) {
				collisionStatus[0] = 1;
				collisionStatus[1] = activeBlob;
			}
		}
		return collisionStatus;
	}
		
	public void getHit() {
		
		for (int currentEnemy = 0; currentEnemy < Cow.players.size(); currentEnemy++) 
		{
			if (currentEnemy != fighterNumber) 
			{
				int[] collisionStatus = Cow.players.get(currentEnemy).collisionStatus(Cow.players.get(fighterNumber));
				if (collisionStatus[0] == 1) 
				{
					Cow.players.get(fighterNumber).livingStatus -= 1;
					Cow.players.get(currentEnemy).blobs.remove(collisionStatus[1]);
					Cow.players.get(currentEnemy).ammoLeft += 1;
					
					if (livingStatus == 1) {
						x += width / 4;
						y += height / 4;
						width = globuleSize;
						height = globuleSize;
						globuleActive = true;
					}
					if (livingStatus == 0) {
						Cow.scores[currentEnemy] += 1;
					}
				} 
			}
		}
	}
	
	public void reload() {
		
		for (int i = 0; i < blobs.size(); i++)
		{
			Milk currentBlob = blobs.get(i);
			boolean outX = currentBlob.x + currentBlob.motionX < 0 || currentBlob.x + currentBlob.width + currentBlob.motionX > Cow.cow.width;
			boolean outY = currentBlob.y + currentBlob.motionY < 0 || currentBlob.y + currentBlob.height + currentBlob.motionY > Cow.cow.height;
			
			if (outX || outY) {
				blobs.remove(i);
				ammoLeft++;
			}
		}
	}
	
	public void render(Graphics g) {
		
		if (livingStatus > 0) 
		{
			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);
			
			for (int blobNumber = 0; blobNumber < ammoLeft; blobNumber++) {
				switch (blobNumber) {
				case 0:
					g.setColor(Color.WHITE);
					g.fillRect(x + width/2, y - ammoIndicatorDistance, 2, 2);
					break;
				case 1:
					g.setColor(Color.WHITE);
					g.fillRect(x - ammoIndicatorDistance/2, y + height/2 + ammoIndicatorDistance/2, 2, 2);
					break;
				case 2:
					g.setColor(Color.WHITE);
					g.fillRect(x + width + ammoIndicatorDistance/2, y + height/2 + ammoIndicatorDistance/2, 2, 2);
					break;
				}
			}
			
			for (int blobNumber = 0; blobNumber < blobs.size(); blobNumber++) {
				blobs.get(blobNumber).render(g);
			}
		}

	}

}
