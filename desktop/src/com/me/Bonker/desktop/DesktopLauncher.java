package com.me.Bonker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.Bonker.Bonker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Bonker";
		cfg.width = 960; //832  640
		cfg.height = 540; //624  360
		new LwjglApplication(new Bonker(), cfg);
	}
}
