package com.me.Bonker;

import com.badlogic.gdx.Game;
import com.me.Helpers.AssetLoader;
import com.me.Screens.SplashScreen;

public class Bonker extends Game {
	@Override
	public void create() {

		AssetLoader.load();
		setScreen(new SplashScreen(this));
		
	}

	@Override
	public void dispose() {
	    super.dispose();
	    AssetLoader.dispose();
	}
	
}
