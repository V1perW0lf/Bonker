package com.me.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.GameObjects.Ball;
import com.me.GameObjects.Piece;
import com.me.Helpers.AssetLoader;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	
	//Game Objects
	private Ball myBall1, myBall2, myBall3;

	//Game Assets
	private TextureRegion menu, rings, arrow, blueDot, redDot, yellowDot, 
		greenDot, pauseButton, playButton, gameOver, highScore, helpMenu;
	
	private Music music;
	
	//ring pieces
    private Piece[] InnerRing = new Piece[120];
    private Piece[] OuterRing = new Piece[120];
    
    private float blackCircleNum = 120;
    
	float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    BitmapFont bit;
    
    float virtWidth = 640;
    float virtHeight = 360;
    float midPointY = virtHeight/2;
    
    private Viewport view;
    private Stage stage;
    
	public GameRenderer(GameWorld world) {
		
		myWorld = world;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, virtWidth, virtHeight);
		
		view = new FitViewport(virtWidth, virtHeight, cam);
		stage = new Stage(view);
		stage.setViewport(view);
		
		stage.getCamera().position.set(480/2, 272/2, 0);
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		initGameObjects();
        initAssets();
        
        music.setLooping(true);
        
        bit = new BitmapFont(true);
		
	}
	
	private void initGameObjects() {
		
		myBall1 = myWorld.getBall1();
		myBall2 = myWorld.getBall2();
		myBall3 = myWorld.getBall3();
		
		
		for (int i = 0; i < blackCircleNum; i++) {
		    InnerRing[i] = myWorld.getInnerPiece(i);
		}
		
		for (int i = 0; i < blackCircleNum; i++) {
		    OuterRing[i] = myWorld.getOuterPiece(i);
		}
		
    }
	
	private void drawMenu() {
		
		if (myWorld.isMenu()) {
			batcher.draw(menu, 0, 0, 1920/3, 1080/3);
			if(!music.isPlaying()) {
				//music.play();
			}
		}
        
    }
	
	private void drawGame() {
		
		if (myWorld.isRunning()) {
			music.stop();
			
			//arrow buttons
			batcher.draw(arrow, 141/3, 345/3, 154/3, 400/3);
			batcher.draw(arrow, 1626/3, 345/3, 154/3, 400/3);
			
			//pause button
			if(myWorld.getIsPaused()) {
				batcher.draw(playButton, 50/3, 75/3, 64/3, 82/3);
			} else {
			batcher.draw(pauseButton, 16, 26, 21, 27);
			}
			
			//ball
			batcher.draw(rings, myBall1.getX(), myBall1.getY(), myBall1.getWidth(), myBall1.getHeight());
			batcher.draw(rings, myBall2.getX(), myBall2.getY(), myBall2.getWidth(), myBall2.getHeight());
			batcher.draw(rings, myBall3.getX(), myBall3.getY(), myBall3.getWidth(), myBall3.getHeight());
			
			//rings
			for(int i = 0; i < blackCircleNum; i++) {
				
				if(InnerRing[i].getGreenToBlack() == 0) {
				
					if(i % myWorld.getRandomBlue1() == 9 || i % myWorld.getRandomBlue2() == 19) {
						InnerRing[i].setColorState(1);
						batcher.draw(blueDot, InnerRing[i].getX(), InnerRing[i].getY(), 
								InnerRing[i].getWidth(), InnerRing[i].getHeight());
					}  
					else if((i % myWorld.getRandomRed1() == 8 || i % myWorld.getRandomRed2() == 17) && (InnerRing[i].getColorState() == 0 ||
							InnerRing[i].getColorState() == 2) && myWorld.getScore() >= 15) { //try random blue here too
						InnerRing[i].setColorState(2);
						batcher.draw(redDot, InnerRing[i].getX(), InnerRing[i].getY(), 
								InnerRing[i].getWidth(), InnerRing[i].getHeight());
				    }
					else if((i % myWorld.getRandomYellow1() == 25 || i % myWorld.getRandomYellow2() == 21) && (InnerRing[i].getColorState() == 0 ||
							InnerRing[i].getColorState() == 3)) { //try random blue here too
						InnerRing[i].setColorState(3);
						batcher.draw(yellowDot, InnerRing[i].getX(), InnerRing[i].getY(), 
								InnerRing[i].getWidth(), InnerRing[i].getHeight());
				    }
					else if((i % myWorld.getRandomGreen1() == 25 || i % myWorld.getRandomGreen2() == 13) && (InnerRing[i].getColorState() == 0 ||
							InnerRing[i].getColorState() == 4) && myWorld.getScore() >= 15) { //try random blue here too
						InnerRing[i].setColorState(4);
						batcher.draw(greenDot, InnerRing[i].getX(), InnerRing[i].getY(), 
								InnerRing[i].getWidth(), InnerRing[i].getHeight());
				    }
					else {
						batcher.draw(rings, InnerRing[i].getX(), InnerRing[i].getY(), 
							InnerRing[i].getWidth(), InnerRing[i].getHeight());
					}
					
				} else {
					batcher.draw(rings, InnerRing[i].getX(), InnerRing[i].getY(), 
							InnerRing[i].getWidth(), InnerRing[i].getHeight());
				}
				
			}
			
			for(int i = 0; i < blackCircleNum; i++) {
				
				if(OuterRing[i].getGreenToBlack() == 0) {
				
					if(i % myWorld.getRandomBlue1() == 17 || i % myWorld.getRandomBlue2() == 21) {
						OuterRing[i].setColorState(1);
						batcher.draw(blueDot, OuterRing[i].getX(), OuterRing[i].getY(), 
								OuterRing[i].getWidth(), OuterRing[i].getHeight());
					} 
					else if((i % myWorld.getRandomRed1() == 7 || i % myWorld.getRandomRed2() == 16) && (OuterRing[i].getColorState() == 0  ||
							OuterRing[i].getColorState() == 2) && myWorld.getScore() >= 15) { //try random blue here too
						OuterRing[i].setColorState(2);
						batcher.draw(redDot, OuterRing[i].getX(), OuterRing[i].getY(), 
								OuterRing[i].getWidth(), OuterRing[i].getHeight());
				    }
					else if((i % myWorld.getRandomYellow1() == 26 || i % myWorld.getRandomYellow2() == 20) && (OuterRing[i].getColorState() == 0 ||
							OuterRing[i].getColorState() == 3)) {
						OuterRing[i].setColorState(3);
						batcher.draw(yellowDot, OuterRing[i].getX(), OuterRing[i].getY(), 
								OuterRing[i].getWidth(), OuterRing[i].getHeight());
				    }
					else if((i % myWorld.getRandomGreen1() == 11 || i % myWorld.getRandomGreen2() == 23) && (OuterRing[i].getColorState() == 0 ||
							OuterRing[i].getColorState() == 4) && myWorld.getScore() >= 15) { //try random blue here too
						OuterRing[i].setColorState(4);
						batcher.draw(greenDot, OuterRing[i].getX(), OuterRing[i].getY(), 
								OuterRing[i].getWidth(), OuterRing[i].getHeight());
				    }
					else {
						batcher.draw(rings, OuterRing[i].getX(), OuterRing[i].getY(), 
							OuterRing[i].getWidth(), OuterRing[i].getHeight());
					}
					
				} else {
					batcher.draw(rings, OuterRing[i].getX(), OuterRing[i].getY(), 
							OuterRing[i].getWidth(), OuterRing[i].getHeight());
				}
				
			}
			
		} else if (myWorld.isGameOver()) {
			batcher.draw(gameOver, 0, 0, 640, 360);
		} else if (myWorld.isHelpMenu()) {
			batcher.draw(helpMenu, 0, 0, 640, 360);
		}
        
    }
	
	private void initAssets() {
	
		menu = AssetLoader.menu;
		helpMenu = AssetLoader.helpMenu;
		music = AssetLoader.titleMusic;
		rings = AssetLoader.ringss;
		arrow = AssetLoader.arrow;
		redDot = AssetLoader.redDot;
		blueDot = AssetLoader.blueDot;
		yellowDot = AssetLoader.yellowDot;
		greenDot = AssetLoader.greenDot;
		pauseButton = AssetLoader.pauseButton;
		playButton = AssetLoader.playButton;
		gameOver = AssetLoader.gameOver;
		highScore = AssetLoader.highScore;
		
	}

	public void render(float runTime) {
		
		view.update((int)screenWidth, (int)screenHeight, true);
		
        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //shapeRenderer.begin(ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
        
        //shapeRenderer.circle(p1.getBoundingCirc().x, p1.getBoundingCirc().y, p1.getBoundingCirc().radius);
        //shapeRenderer.circle(myBall.getBoundingCirc().x, myBall.getBoundingCirc().y, myBall.getBoundingCirc().radius);

        //shapeRenderer.end();
        batcher.begin();
        

        //batcher.disableBlending();

        drawMenu();
        drawGame();
        String score = myWorld.getScore() + "";
        String zeroes = "00";
        if(score.length() == 2) {
        	zeroes = "0";
        }
        else if(score.length() > 2) {
        	zeroes = "";
        }
        if(myWorld.isRunning()){
        	AssetLoader.font.draw(batcher, zeroes + myWorld.getScore(), 1700/3, 75/3);
        	AssetLoader.font.draw(batcher, "X" + myWorld.getLives(), 45, 29);
        } else if (myWorld.isGameOver()){
        	AssetLoader.fontOver.draw(batcher, "" + myWorld.getScore(), 1250/3, 387/3);
        	AssetLoader.fontOver.draw(batcher, "" + AssetLoader.getHighScore(), 450/3, 566/3);
        } else if (myWorld.isHighScore()) {
        	batcher.draw(highScore, 0, 0, 1910/3, 1080/3);
        	AssetLoader.fontOver.draw(batcher, "" + AssetLoader.getHighScore(), 1470/3, 60/3);
        }

        //batcher.enableBlending();
		
        batcher.end();

    }
	
	public float getViewHeight() {
		return view.getViewportHeight();
	}
	
	public float getViewWidth() {
		return view.getViewportWidth();
	}
	
	public GameWorld getMyWorld() {
		return myWorld;
	}

}
