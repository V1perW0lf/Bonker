package com.me.Helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.me.ui.SimpleButton;
import com.me.GameObjects.Piece;
import com.me.GameWorld.GameRenderer;
import com.me.GameWorld.GameWorld;

public class InputHandler implements InputProcessor{
	
	private GameWorld myWorld;
	
	private List<SimpleButton> menuButtons;

	private SimpleButton playButton, leftUpArrow, leftDownArrow, rightUpArrow, rightDownArrow, topScreen,
	bottomScreen, middleScreen, leftScreen, rightScreen, pauseButton, retry, mainMenu, highScore, back,
	howToPlay, helpBack, helpPlay;
	
	private float scaleFactorX;
	private float scaleFactorY;
	
	Piece[] InnerRing = new Piece[120];
	Piece[] OuterRing = new Piece[120];
	
	float screenHeight = Gdx.graphics.getHeight();
	float screenWidth = Gdx.graphics.getWidth();
	float yDifference;
	
	private double spinSpeed;
	private float tempSpeed1;
	private float tempSpeed2;
	private float tempSpeed3;
	
	private Boolean isPaused;

	public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
		
		this.myWorld = myWorld;
		
		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;
		
		if(screenHeight / screenWidth == 0.75) {
			yDifference = 60;
		} else if (screenWidth /  screenHeight == 1.5) {
			yDifference = 33.5f;
		}
		
		spinSpeed = 0.15;
		
		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(86, 191 + yDifference, 103, 48);
		leftUpArrow = new SimpleButton(47, 115 + yDifference, 52, 66);
		leftDownArrow = new SimpleButton(47, 181 + yDifference, 52, 66);
		rightUpArrow = new SimpleButton(542, 115 + yDifference, 52, 66);
		rightDownArrow = new SimpleButton(542, 181 + yDifference, 52, 66);
		topScreen = new SimpleButton(0, 0 + yDifference, 640, 115);
		bottomScreen = new SimpleButton(0, 247 + yDifference, 640, 124);
		middleScreen = new SimpleButton(98, 115 + yDifference, 444, 121);
		leftScreen = new SimpleButton(0, 115 + yDifference, 47, 121);
		rightScreen = new SimpleButton(593, 115 + yDifference, 98, 121);
		pauseButton = new SimpleButton(16, 26 + yDifference, 21, 26);
		retry = new SimpleButton(97, 323 + yDifference, 104, 39);
		mainMenu = new SimpleButton(333, 323 + yDifference, 212, 39);
		highScore = new SimpleButton(277, 256 + yDifference, 280, 50);
		back = new SimpleButton(529, 304 + yDifference, 70, 34);
		howToPlay = new SimpleButton(243, 313 + yDifference, 154, 34);
		helpBack = new SimpleButton(17, 23 + yDifference, 53, 24);
		helpPlay = new SimpleButton(580, 22 + yDifference, 47, 30);
		
		//menuButtons.add(playButton);
		
		for (int i = 0; i < 120; i++) {
		    InnerRing[i] = myWorld.getInnerPiece(i);
		}
		
		for (int i = 0; i < 120; i++) {
		    OuterRing[i] = myWorld.getOuterPiece(i);
		}
		
        
	}

	@Override
	public boolean keyDown(int keycode) {
		
		return false;
		
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
		
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
		
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		if (myWorld.isMenu()) {
			
			playButton.isTouchDown(screenX, screenY);
			highScore.isTouchDown(screenX, screenY);
			howToPlay.isTouchDown(screenX, screenY);
			
		} else if (myWorld.isRunning()) {
			
			if(pauseButton.isTouchDown(screenX, screenY)) {
				if(myWorld.getIsPaused()) {
					spinSpeed = 0.15;
					myWorld.getBall1().setSpeed(tempSpeed1);
					myWorld.getBall2().setSpeed(tempSpeed2);
					myWorld.getBall3().setSpeed(tempSpeed3);
					myWorld.setIsPaused(false);
				} else {
					myWorld.setIsPaused(true);
					spinSpeed = 0;
					tempSpeed1 = myWorld.getBall1().getSpeed();
					tempSpeed2 = myWorld.getBall2().getSpeed();
					tempSpeed3 = myWorld.getBall3().getSpeed();
					myWorld.getBall1().setSpeed(0);
					myWorld.getBall2().setSpeed(0);
					myWorld.getBall3().setSpeed(0);
				}
			} 
			
			if(leftUpArrow.isTouchDown(screenX, screenY)) {
				
				for (int i = 0; i < 120; i++) {
					OuterRing[i].setSpinSpeed(spinSpeed);
				}
				
			} else if (leftDownArrow.isTouchDown(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					OuterRing[i].setSpinSpeed(-spinSpeed);
				}
				
			} else if(rightUpArrow.isTouchDown(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					InnerRing[i].setSpinSpeed(spinSpeed);
				}
				
			} else if (rightDownArrow.isTouchDown(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					InnerRing[i].setSpinSpeed(-spinSpeed);
				}
				
			}
		} else if (myWorld.isGameOver()) {
			retry.isTouchDown(screenX, screenY);
			mainMenu.isTouchDown(screenX, screenY);
		} else if (myWorld.isHighScore()) {
			back.isTouchDown(screenX, screenY);
		} else if (myWorld.isHelpMenu()) {
			helpBack.isTouchDown(screenX, screenY);
			helpPlay.isTouchDown(screenX, screenY);
		}
		
		return true;
		
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		if (myWorld.isMenu()) {
			
			if (playButton.isTouchUp(screenX, screenY)) {
				if(myWorld.getScore() == 0) {
					myWorld.start();
				} else {
					myWorld.restart();
				}
				return true;
			} else if(highScore.isTouchUp(screenX, screenY)) {
				myWorld.setHighScore();
			} else if(howToPlay.isTouchUp(screenX, screenY)) {
				myWorld.setHelpMenu();
			}
			
		} else if (myWorld.isRunning()) {
			
			pauseButton.isTouchUp(screenX, screenY);
			
			if(leftUpArrow.isTouchUp(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					OuterRing[i].setSpinSpeed(0);
				}
			
			} else if (leftDownArrow.isTouchUp(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					OuterRing[i].setSpinSpeed(0);
				}
				
			} else if (rightUpArrow.isTouchUp(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					InnerRing[i].setSpinSpeed(0);
				}
				
			} else if (rightDownArrow.isTouchUp(screenX, screenY)) {
				
				for(int i = 0; i < 120; i++) {
					InnerRing[i].setSpinSpeed(0);
				}
				
			}
			
		} else if(myWorld.isGameOver()) {
			if(retry.isTouchUp(screenX, screenY)) {
				myWorld.restart();
			}
			else if (mainMenu.isTouchUp(screenX, screenY)) {
				myWorld.Menu();
			}
			
		} else if (myWorld.isHighScore()) {
			if(back.isTouchUp(screenX, screenY)) {
				myWorld.Menu();
			}
		} else if (myWorld.isHelpMenu()) {
			if(helpBack.isTouchDown(screenX, screenY)) {
				myWorld.Menu();
			} else if (helpPlay.isTouchDown(screenX, screenY)) {
				myWorld.restart();
			}
		}
		
		return true;
		
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		if(leftUpArrow.isTouchDown(screenX, screenY)) {
			
			for(int i = 0; i < 120; i++) {
				OuterRing[i].setSpinSpeed(spinSpeed);
			}
			
		} else if (leftDownArrow.isTouchDown(screenX, screenY)) {
			
			for(int i = 0; i < 120; i++) {
				OuterRing[i].setSpinSpeed(-spinSpeed);
			}
			
		} else if(rightUpArrow.isTouchDown(screenX, screenY)) {
			
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(spinSpeed);
			}
			
		} else if (rightDownArrow.isTouchDown(screenX, screenY)) {
			
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(-spinSpeed);
			}
			
		}
		
		if(topScreen.isTouchDown(screenX, screenY)) {
			
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(0);
				OuterRing[i].setSpinSpeed(0);
			}
			
		} else if(middleScreen.isTouchDown(screenX, screenY)){
			
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(0);
				OuterRing[i].setSpinSpeed(0);
			}
			
		} else if(bottomScreen.isTouchDown(screenX, screenY)) {
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(0);
				OuterRing[i].setSpinSpeed(0);
			}
			
		} else if(leftScreen.isTouchDown(screenX, screenY)){
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(0);
				OuterRing[i].setSpinSpeed(0);
			}
			
		} else if(rightScreen.isTouchDown(screenX, screenY)){
			for(int i = 0; i < 120; i++) {
				InnerRing[i].setSpinSpeed(0);
				OuterRing[i].setSpinSpeed(0);
			}
			
		}
		
		return true;
		
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
		
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
		
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}
	
	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}

}
