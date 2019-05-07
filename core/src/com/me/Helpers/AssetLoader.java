package com.me.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture titleT, menuT, pieces, rings, arrowM, redDots, blueDots, 
    yellowDots, greenDots, pause, play, over, high, help;
    public static TextureRegion ball, title, menu, black, red, green, blue, ringss, arrow, redDot
    , blueDot, yellowDot, greenDot, pauseButton, playButton, gameOver, highScore, helpMenu;
    public static Music titleMusic;
    public static Sound blackSound, blueSound, redSound, yellowSound, greenSound;
    
    public static BitmapFont font, fontOver;

    //public static Animation wolfAnimation;
    
    private static Preferences prefs;

    public static void load() {
    
        titleT = new Texture(Gdx.files.internal("title.png"));
        titleT.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        menuT = new Texture(Gdx.files.internal("menu.png"));
        menuT.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        help = new Texture(Gdx.files.internal("helpMenu.png"));
        help.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        pieces = new Texture(Gdx.files.internal("balltest.png"));
        pieces.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        rings = new Texture(Gdx.files.internal("balltest.png"));
        rings.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        arrowM = new Texture(Gdx.files.internal("arrow.png"));
        arrowM.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        redDots = new Texture(Gdx.files.internal("redDot.png"));
        redDots.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        blueDots = new Texture(Gdx.files.internal("blueDot.png"));
        blueDots.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        yellowDots = new Texture(Gdx.files.internal("yellowDot.png"));
        yellowDots.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        greenDots = new Texture(Gdx.files.internal("greenDot.png"));
        greenDots.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        pause = new Texture(Gdx.files.internal("pauseButton.png"));
        pause.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        play = new Texture(Gdx.files.internal("playButton.png"));
        play.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        over = new Texture(Gdx.files.internal("gameOver.png"));
        over.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        high = new Texture(Gdx.files.internal("highScore.png"));
        high.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        titleMusic =  Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        
        blackSound =  Gdx.audio.newSound(Gdx.files.internal("blackSound.mp3"));
        blueSound =  Gdx.audio.newSound(Gdx.files.internal("blueSound.mp3"));
        redSound =  Gdx.audio.newSound(Gdx.files.internal("redSound.mp3"));
        yellowSound =  Gdx.audio.newSound(Gdx.files.internal("yellowSound.mp3"));
        greenSound =  Gdx.audio.newSound(Gdx.files.internal("greenSound.mp3"));
        
        title = new TextureRegion(titleT, 0, 0, 1920, 1080);
        
        menu = new TextureRegion(menuT, 0, 0, 1920, 1080);
        menu.flip(false, true);
        
        helpMenu = new TextureRegion(help, 0, 0, 1920, 1080);
        helpMenu.flip(false, true);
        
        ringss = new TextureRegion(rings, 0, 0, 64, 64);
        ringss.flip(false, true);
        
        arrow = new TextureRegion(arrowM, 0, 0, 77, 182);
        arrow.flip(false, true);
        
        redDot = new TextureRegion(redDots, 0, 0, 64, 64);
        redDot.flip(false, true);
        
        blueDot = new TextureRegion(blueDots, 0, 0, 64, 64);
        blueDot.flip(false, true);
        
        yellowDot = new TextureRegion(yellowDots, 0, 0, 64, 64);
        yellowDot.flip(false, true);
        
        greenDot = new TextureRegion(greenDots, 0, 0, 64, 64);
        greenDot.flip(false, true);
        
        pauseButton = new TextureRegion(pause, 0, 0, 96, 123);
        pauseButton.flip(false, true);
        
        playButton = new TextureRegion(play, 0, 0, 357, 413);
        playButton.flip(false, true);
        
        gameOver = new TextureRegion(over, 0, 0, 1920, 1080);
        gameOver.flip(false, true);
        
        highScore = new TextureRegion(high, 0, 0, 1920, 1080);
        highScore.flip(false, true);
        
        font = new BitmapFont(Gdx.files.internal("blacktext.fnt"));
		font.setScale(1/4f, -1/4f);
		
		fontOver = new BitmapFont(Gdx.files.internal("blacktext.fnt"));
		fontOver.setScale(.375f, -.375f); 
        
		prefs = Gdx.app.getPreferences("Bonker");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

    }
    
    public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
		
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}
    
    public static void dispose() {
 
        titleT.dispose();
        menuT.dispose();
        pieces.dispose();
        rings.dispose();
        font.dispose();
        
    }
    
}
