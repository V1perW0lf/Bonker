package com.me.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.me.GameWorld.GameRenderer;
import com.me.GameWorld.GameWorld;
import com.me.Helpers.InputHandler;

public class GameScreen implements Screen{
	
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	public GameScreen() {
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		
		float gameWidth = 640;
		float gameHeight = 360;
				
		world = new GameWorld();//initialize world
		renderer = new GameRenderer(world);//initialize renderer
		
		Gdx.input.setInputProcessor(new InputHandler(world, renderer.getViewWidth() / gameWidth, renderer.getViewHeight() / gameHeight));

	}

	@Override
	public void render(float delta) {
		
		runTime += delta;
		world.update(delta);
		renderer.render(runTime);
		
	}

	@Override
	public void resize(int width, int height){
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {

		System.out.println("GameScreen - hide called");
		
	}

	@Override
	public void pause() {

		System.out.println("GameScreen - pause called"); 
		
	}

	@Override
	public void resume() {

		System.out.println("GameScreen - resume called");
		
	}

	@Override
	public void dispose() {

		//left blank for now
		
	}

}
