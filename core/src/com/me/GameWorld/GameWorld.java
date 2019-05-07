package com.me.GameWorld;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.me.GameObjects.Ball;
import com.me.GameObjects.Piece;
import com.me.Helpers.AssetLoader;

public class GameWorld {
	
	private Ball myBall1, myBall2, myBall3;
	
	private Float moveX, moveY;
	
	Piece[] InnerRing = new Piece[120];
	Piece[] OuterRing = new Piece[120];
	
	private Random rand = new Random();
	private float randomBlue1, randomBlue2, randomRed1, randomRed2, randomYellow1, randomYellow2,
	randomGreen1, randomGreen2;
	private int randDestroy, randCreate, innerOrOuter;
	
	private GameState currentState;
	
	private Sound blackSound, blueSound, redSound, yellowSound, greenSound;
	
	private int score, lives, piecesOnScreen;
	
	private Boolean isPaused = false;
	
	public enum GameState {
		MENU, RUNNING, GAMEOVER, HIGHSCORE, HELP
	}
	
	public GameWorld() {
		currentState = GameState.MENU;
		
		myBall1 = new Ball(960/3, 540/3, 20/3, 20/3);
		myBall2 = new Ball(-9000, -9000, 0, 0);
		myBall3 = new Ball(-9000, -9000, 0, 0);
		
		score = 0;
		lives = 3;
		piecesOnScreen = 240;
		
		randomBlue1 = rand.nextInt(119) + 1;
		randomBlue2 = rand.nextInt(119) + 1;
		
		randomRed1 = rand.nextInt(119) + 1;
		randomRed2 = rand.nextInt(119) + 1;
		
		randomYellow1 = rand.nextInt(119) + 1;
		randomYellow2 = rand.nextInt(119) + 1;
		
		randomGreen1 = rand.nextInt(119) + 1;
		randomGreen2 = rand.nextInt(119) + 1;
		
		innerOrOuter = rand.nextInt(239) + 1;
		randDestroy = rand.nextInt(119) + 1;
		randCreate = rand.nextInt(119) + 1;
		
		int m = 0;
		for (int i = 0; i < 120; i++) {
		    InnerRing[i] = new Piece(0, 0, 20/3, 20/3);
		    InnerRing[i].setNewCirc(m);
		    m += 3;
		    
		}
		
		int o = 0;
		for (int i = 0; i < 120; i++) {
		    OuterRing[i] = new Piece(0, 0, 25/3, 25/3);
		    OuterRing[i].setRadius(525/3);
		    OuterRing[i].setNewCirc(o);
		    o += 3;
		    
		}
		
		blackSound = AssetLoader.blackSound;
		blueSound = AssetLoader.blueSound;
		redSound = AssetLoader.redSound;
		yellowSound = AssetLoader.yellowSound;
		greenSound = AssetLoader.greenSound;
		
	}
	
	public void update(float delta) {

		switch (currentState) {
		case MENU:
			break;
		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}
	}
	
	public void updateRunning(float delta) {
		
		if (delta > .15f) { //?
			delta = .15f;
		}
		
		System.out.print(piecesOnScreen + " ");
		
		if(score > AssetLoader.getHighScore()) {
    		AssetLoader.setHighScore(score);
		}
		
		if(myBall1.getHeight() == 0 && myBall2.getHeight() == 0 && myBall3.getHeight() == 0) {
			lives -= 1;
			if(lives == 0) {
				currentState = GameState.GAMEOVER;
			} else {
				restart();
			}
			
		}
		
		myBall1.update(delta);
		myBall2.update(delta);
		if (myBall2.getHeight() == 0) {
			myBall2.setX(0);
		}
		myBall3.update(delta);
		if (myBall3.getHeight() == 0) {
			myBall3.setX(0);
		}
		
		if(myBall1.collides2(myBall2)) {
			
			if(myBall1.getX() > myBall2.getX()){
				moveX = 2f;
			} else {
				moveX = -2f;
			}
			if(myBall1.getY() > myBall2.getY()){
				moveY = 2f;
			} else {
				moveY = -2f;
			}
			 
			myBall1.setX(myBall1.getX() + moveX);
			myBall1.setY(myBall1.getY() + moveY);
		}
		if(myBall1.collides2(myBall3)) {
			
			if(myBall1.getX() > myBall2.getX()){
				moveX = 2f;
			} else {
				moveX = -2f;
			}
			if(myBall1.getY() > myBall2.getY()){
				moveY = 2f;
			} else {
				moveY = -2f;
			}
			myBall1.setX(myBall1.getX() + moveX);
			myBall1.setY(myBall1.getY() + moveY);
		}
		if(myBall2.collides2(myBall3)) {
			
			if(myBall2.getX() > myBall3.getX()){
				moveX = 2f;
			} else {
				moveX = -2f;
			}
			if(myBall2.getY() > myBall3.getY()){
				moveY = 2f;
			} else {
				moveY = -2f;
			}
			myBall2.setX(myBall2.getX() + moveX);
			myBall2.setY(myBall2.getY() + moveY);
		}
		
		for(int i = 0; i < 120; i++) {
	
			InnerRing[i].update(delta);
			
			if(myBall1.collides(InnerRing[i])) {

				if(InnerRing[i].getColorState() == 1) {
					piecesOnScreen -= 1;
					blueSound.play();
					if(myBall2.getHeight() == 0) {
						myBall2.setX(960/3);
						myBall2.setY(540/3);
						myBall2.setWidth(20/3);
					    myBall2.setHeight(20/3);
					    myBall2.setAngle(2 * Math.PI * Math.random());
					    myBall2.setSpeed(250/3);
					} else if(myBall3.getHeight() == 0) {
						myBall3.setX(960/3);
						myBall3.setY(540/3);
						myBall3.setWidth(20/3);
					    myBall3.setHeight(20/3);
					    myBall3.setSpeed(250/3);
						myBall3.setAngle(2 * Math.PI * Math.random());
					}
					
				} else if(InnerRing[i].getColorState() == 2) {
					score -= 3;
					redSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen >= 3) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randDestroy].getRadius() != 9001 && InnerRing[randDestroy].getColorState() == 0) {
									piecesOnScreen -= 1;
									InnerRing[randDestroy].setRadius(9001);
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randDestroy].getRadius() != 9001 && OuterRing[randDestroy].getColorState() == 0) {
									piecesOnScreen -= 1;
									OuterRing[randDestroy].setRadius(9001);
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else if(InnerRing[i].getColorState() == 3) {
					piecesOnScreen -= 1;
					yellowSound.play();
					myBall1.setSpeed(175/3);
				} else if(InnerRing[i].getColorState() == 4) {
					score += 3;
					greenSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen < 237) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									InnerRing[randCreate].setGreenToBlack(1);
									InnerRing[randCreate].setRadius(420/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									OuterRing[randCreate].setGreenToBlack(1);
									OuterRing[randCreate].setRadius(525/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else {
					score += 1;
					piecesOnScreen -= 1;
					blackSound.play();
				}	
				
			}
			
			InnerRing[i].update(delta);
			
			if(myBall2.collides(InnerRing[i])) {
				
				if(InnerRing[i].getColorState() == 1) {
					piecesOnScreen -= 1;
					blueSound.play();
					if(myBall3.getHeight() == 0) {
						myBall3.setX(960/3);
						myBall3.setY(540/3);
						myBall3.setWidth(20/3);
					    myBall3.setHeight(20/3);
					    myBall3.setSpeed(250/3);
						myBall3.setAngle(2 * Math.PI * Math.random());
					} else if(myBall1.getHeight() == 0) {
						myBall1.setX(960/3);
						myBall1.setY(540/3);
						myBall1.setWidth(20/3);
					    myBall1.setHeight(20/3);
					    myBall1.setSpeed(250/3);
						myBall1.setAngle(2 * Math.PI * Math.random());
					}
					
				} else if(InnerRing[i].getColorState() == 2) {
					score -= 3;
					redSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen >= 3) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randDestroy].getRadius() != 9001 && InnerRing[randDestroy].getColorState() == 0) {
									InnerRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randDestroy].getRadius() != 9001 && OuterRing[randDestroy].getColorState() == 0) {
									OuterRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else if(InnerRing[i].getColorState() == 3) {
					piecesOnScreen -= 1;
					yellowSound.play();
					myBall2.setSpeed(175/3);
				} else if(InnerRing[i].getColorState() == 4) {
					score += 3;
					greenSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen < 237) {
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									InnerRing[randCreate].setGreenToBlack(1);
									InnerRing[randCreate].setRadius(420/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									OuterRing[randCreate].setGreenToBlack(1);
									OuterRing[randCreate].setRadius(525/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else {
					piecesOnScreen -= 1;
					score += 1;
					blackSound.play();
				}
				
			} 
			
			InnerRing[i].update(delta);
			
			if(myBall3.collides(InnerRing[i])) {
				
				if(InnerRing[i].getColorState() == 1) {
					piecesOnScreen -= 1;
					blueSound.play();	
					
					if(myBall2.getHeight() == 0) {
						myBall2.setX(960/3);
						myBall2.setY(540/3);
						myBall2.setWidth(20/3);
					    myBall2.setHeight(20/3);
					    myBall2.setSpeed(250/3);
						myBall2.setAngle(2 * Math.PI * Math.random());
					} else if(myBall1.getHeight() == 0) {
						myBall1.setX(960/3);
						myBall1.setY(540/3);
						myBall1.setWidth(20/3);
					    myBall1.setHeight(20/3);
					    myBall1.setSpeed(250/3);
						myBall1.setAngle(2 * Math.PI * Math.random());
					}
					
				} else if(InnerRing[i].getColorState() == 2) {
					score -= 3;
					redSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen >= 3) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randDestroy].getRadius() != 9001 && InnerRing[randDestroy].getColorState() == 0) {
									InnerRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randDestroy].getRadius() != 9001 && OuterRing[randDestroy].getColorState() == 0) {
									OuterRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else if(InnerRing[i].getColorState() == 3) {
					piecesOnScreen -= 1;
					yellowSound.play();
					myBall3.setSpeed(175/3);
				} else if(InnerRing[i].getColorState() == 4) {
					score += 3;
					greenSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen < 237) {

							if(innerOrOuter <= 120) {
								
								if(InnerRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									InnerRing[randCreate].setGreenToBlack(1);
									InnerRing[randCreate].setRadius(420/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									OuterRing[randCreate].setGreenToBlack(1);
									OuterRing[randCreate].setRadius(525/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else {
					piecesOnScreen -= 1;
					score += 1;
					blackSound.play();
				}
				
			}
			
		}

			
		for(int i = 0; i < 120; i++) {
			
			OuterRing[i].update(delta);
			
			if(myBall1.collides(OuterRing[i])) {

				if(OuterRing[i].getColorState() == 1) {
					piecesOnScreen -= 1;
					blueSound.play();
					
					if(myBall2.getHeight() == 0) {
						myBall2.setX(960/3);
						myBall2.setY(540/3);
						myBall2.setWidth(20/3);
					    myBall2.setHeight(20/3);
					    myBall2.setSpeed(250/3);
						myBall2.setAngle(2 * Math.PI * Math.random());
					} else if(myBall3.getHeight() == 0) {
						myBall3.setX(960/3);
						myBall3.setY(540/3);
						myBall3.setWidth(20/3);
					    myBall3.setHeight(20/3);
					    myBall3.setSpeed(250/3);
						myBall3.setAngle(2 * Math.PI * Math.random());
					}
					
				} else if(OuterRing[i].getColorState() == 2) {
					score -= 3;
					redSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen >= 3) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randDestroy].getRadius() != 9001 && InnerRing[randDestroy].getColorState() == 0) {
									InnerRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randDestroy].getRadius() != 9001 && OuterRing[randDestroy].getColorState() == 0) {
									OuterRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else if(OuterRing[i].getColorState() == 3) {
					piecesOnScreen -= 1;
					yellowSound.play();
					myBall1.setSpeed(175/3);
				} else if(OuterRing[i].getColorState() == 4) {
					score += 3;
					greenSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen < 237) {

							if(innerOrOuter <= 120) {
								
								if(InnerRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									InnerRing[randCreate].setGreenToBlack(1);
									InnerRing[randCreate].setRadius(420/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									OuterRing[randCreate].setGreenToBlack(1);
									OuterRing[randCreate].setRadius(525/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else {
					piecesOnScreen -= 1;
					score += 1;
					blackSound.play();	
				} 
				
			}  
			
			OuterRing[i].update(delta);
			
			if(myBall2.collides(OuterRing[i])) {
				
				if(OuterRing[i].getColorState() == 1) {
					piecesOnScreen -= 1;
					blueSound.play();
					if(myBall3.getHeight() == 0) {
						myBall3.setX(960/3);
						myBall3.setY(540/3);
						myBall3.setWidth(20/3);
					    myBall3.setHeight(20/3);
					    myBall3.setSpeed(250/3);
						myBall3.setAngle(2 * Math.PI * Math.random());
					} else if (myBall1.getHeight() == 0) {
						myBall1.setX(960/3);
						myBall1.setY(540/3);
						myBall1.setWidth(20/3);
					    myBall1.setHeight(20/3);
					    myBall1.setSpeed(250/3);
						myBall1.setAngle(2 * Math.PI * Math.random());
					}
					
				} else if(OuterRing[i].getColorState() == 2) {
					score -= 3;
					redSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen >= 3) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randDestroy].getRadius() != 9001 && InnerRing[randDestroy].getColorState() == 0) {
									InnerRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randDestroy].getRadius() != 9001 && OuterRing[randDestroy].getColorState() == 0) {
									OuterRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else if(OuterRing[i].getColorState() == 3) {
					piecesOnScreen -= 1;
					yellowSound.play();
					myBall2.setSpeed(175/3);
				} else if(OuterRing[i].getColorState() == 4) {
					score += 3;
					greenSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen < 237) {

							if(innerOrOuter <= 120) {
								
								if(InnerRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									InnerRing[randCreate].setGreenToBlack(1);
									InnerRing[randCreate].setRadius(420/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									OuterRing[randCreate].setGreenToBlack(1);
									OuterRing[randCreate].setRadius(525/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else {
					piecesOnScreen -= 1;
					score += 1;
					blackSound.play();
				}
			}
			
			OuterRing[i].update(delta);
			
			if(myBall3.collides(OuterRing[i])) {
				
				if(OuterRing[i].getColorState() == 1) {
					piecesOnScreen -= 1;
					blueSound.play();		
					
					if(myBall2.getHeight() == 0) {
						myBall2.setX(960/3);
						myBall2.setY(540/3);
						myBall2.setWidth(20/3);
					    myBall2.setHeight(20/3);
					    myBall2.setSpeed(250/3);
						myBall2.setAngle(2 * Math.PI * Math.random());
					} else if(myBall1.getHeight() == 0) {
						myBall1.setX(960/3);
						myBall1.setY(540/3);
						myBall1.setWidth(20/3);
					    myBall1.setHeight(20/3);
					    myBall1.setSpeed(250/3);
						myBall1.setAngle(2 * Math.PI * Math.random());
					}
					
				} else if(OuterRing[i].getColorState() == 2) {
					score -= 3;
					redSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen >= 3) {
							
							if(innerOrOuter <= 120) {
								
								if(InnerRing[randDestroy].getRadius() != 9001 && InnerRing[randDestroy].getColorState() == 0) {
									InnerRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randDestroy].getRadius() != 9001 && OuterRing[randDestroy].getColorState() == 0) {
									OuterRing[randDestroy].setRadius(9001);
									piecesOnScreen -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randDestroy = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else if(OuterRing[i].getColorState() == 3) {
					piecesOnScreen -= 1;
					yellowSound.play();
					myBall3.setSpeed(175/3);
				} else if(OuterRing[i].getColorState() == 4) {
					score += 3;
					greenSound.play();
					for(int g = 0; g < 3; g++) {
						if(piecesOnScreen < 237) {

							if(innerOrOuter <= 120) {
								
								if(InnerRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									InnerRing[randCreate].setGreenToBlack(1);
									InnerRing[randCreate].setRadius(420/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							} else {
								
								if(OuterRing[randCreate].getRadius() == 9001) {
									piecesOnScreen += 1;
									OuterRing[randCreate].setGreenToBlack(1);
									OuterRing[randCreate].setRadius(525/3);
									randCreate = rand.nextInt(119) + 1;
								}
								else {
									g -= 1;
									randCreate = rand.nextInt(119) + 1;
								}
							}
							innerOrOuter = rand.nextInt(239) + 1;
						}
					}
				} else {
					piecesOnScreen -= 1;
					score += 1;
					blackSound.play();
				}
				
			}
			
		} 
		
		if(myBall1.getX() > 1920/3 || myBall1.getX() < 0 || myBall1.getY() > 1080/3 || myBall1.getY() < 0) {
			myBall1.setWidth(0);
			myBall1.setHeight(0);
			myBall1.setY(-500);
			myBall1.setX(-500);
			myBall1.setSpeed(0);
		}
		
		if(myBall2.getX() > 1920/3 || myBall2.getX() < 0 || myBall2.getY() > 1080/3 || myBall2.getY() < 0) {
			myBall2.setWidth(0);
			myBall2.setHeight(0);
			myBall2.setY(-500);
			myBall2.setX(-500);
			myBall2.setSpeed(0);
		}
		
		if(myBall3.getX() > 1920/3 || myBall3.getX() < 0 || myBall3.getY() > 1080/3 || myBall3.getY() < 0) {
			myBall3.setWidth(0);
			myBall3.setHeight(0);
			myBall3.setY(-500);
			myBall3.setX(-500);
			myBall3.setSpeed(0);
		}
			
		
    }

    public Ball getBall1() {
    	return myBall1;   	
    }
    
    public Ball getBall2() {
    	return myBall2;   	
    }
    
    public Ball getBall3() {
    	return myBall3;   	
    }

    public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
	
    public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}
    
    public boolean isHelpMenu() {
		return currentState == GameState.HELP;
	}
    
    public void setHighScore() {
		currentState = GameState.HIGHSCORE;
	}
    
    public void setHelpMenu() {
		currentState = GameState.HELP;
	}
    public void start() {
		currentState = GameState.RUNNING;
	}

	public void Menu() {
		currentState = GameState.MENU;
		//renderer.prepareTransition(0, 0, 0, 1f);
	}

	public void restart() {
		
		if(lives == 0) {
			score = 0;
		}
		
		piecesOnScreen = 240;
		
		myBall1.setX(960/3);
		myBall1.setY(540/3);
		myBall1.setWidth(20/3);
		myBall1.setHeight(20/3);
		myBall1.setSpeed(83);
		
		myBall2.setWidth(0);
		myBall2.setHeight(0);
		myBall2.setY(-500);
		myBall2.setX(-500);
		
		myBall3.setWidth(0);
		myBall3.setHeight(0);
		myBall3.setY(-500);
		myBall3.setX(-500);
		
		randomBlue1 = rand.nextInt(119) + 1;
		randomBlue2 = rand.nextInt(119) + 1;
		
		randomRed1 = rand.nextInt(119) + 1;
		randomRed2 = rand.nextInt(119) + 1;
		
		randomYellow1 = rand.nextInt(119) + 1;
		randomYellow2 = rand.nextInt(119) + 1;
		
		randomGreen1 = rand.nextInt(119) + 1;
		randomGreen2 = rand.nextInt(119) + 1;
		
		innerOrOuter = rand.nextInt(239) + 1;
		randDestroy = rand.nextInt(119) + 1;
		randCreate = rand.nextInt(119) + 1;
		
		int m = 0;
		for (int i = 0; i < 120; i++) {
			InnerRing[i].setColorState(0);
		    InnerRing[i].setWidth(20/3);
		    InnerRing[i].setHeight(20/3);
		    InnerRing[i].setRadius(420/3);
		    InnerRing[i].setNewCirc(m);
		    m += 3;
		    
		}
		
		int o = 0;
		for (int i = 0; i < 120; i++) {
			OuterRing[i].setColorState(0);
		    OuterRing[i].setWidth(25/3);
		    OuterRing[i].setHeight(25/3);
		    OuterRing[i].setRadius(525/3);
		    OuterRing[i].setNewCirc(o);
		    o += 3;
		    
		}
		start();
	}
	
	public Piece getInnerPiece(int i) {
		return InnerRing[i];
	}
	
	public Piece getOuterPiece(int i) {
		return OuterRing[i];
	}
	
	public float getRandomBlue1() {
		return randomBlue1;
	}
	
	public float getRandomBlue2() {
		return randomBlue2;
	}
	
	public float getRandomRed1() {
		return randomRed1;
	}
	
	public float getRandomRed2() {
		return randomRed2;
	}
	
	public float getRandomYellow1() {
		return randomYellow1;
	}
	
	public float getRandomYellow2() {
		return randomYellow2;
	}
	
	public float getRandomGreen1() {
		return randomGreen1;
	}
	
	public float getRandomGreen2() {
		return randomGreen2;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setIsPaused(boolean b) {
		isPaused = b;
	}
	
	public Boolean getIsPaused() {
		return isPaused;
	}
    
   
}