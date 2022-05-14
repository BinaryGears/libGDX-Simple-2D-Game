package com.cs300.group;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.cs300.group.CS300_Project;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration configStuff = new Lwjgl3ApplicationConfiguration();
		configStuff.setForegroundFPS(60);
		configStuff.setTitle("YetAnotherIndieGame");
		configStuff.setWindowIcon("Faelon.png");
		configStuff.useVsync(true);
		configStuff.setWindowedMode(600, 600);
		new Lwjgl3Application(new CS300_Project(), configStuff);
	}
}
