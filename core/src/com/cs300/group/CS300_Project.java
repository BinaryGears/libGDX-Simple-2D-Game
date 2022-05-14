package com.cs300.group;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.tools.jdeps.Graph;

import java.util.Random;

public class CS300_Project extends ApplicationAdapter {

	//Graph code:
	

	//Queue Code:
	Queue<Integer> rocksGenSeeds = new Queue<Integer>();

	//Random Number Code:
	Random rockSeed = new Random();
	Random rockRandom = new Random();

	//Rock Code:
	RockPositions rock1 = new RockPositions();
	RockPositions rock2 = new RockPositions();
	RockPositions rock3 = new RockPositions();
	RockPositions rock4 = new RockPositions();

	boolean drawRockHere = false;

	//Font code:
	BitmapFont gameFont;

	SpriteBatch spriteBatch;
	Texture spriteSheet;
	Texture characterSheet;

	Texture spritePortrait;

	TextureRegion dirt;
	TextureRegion dirtMiddleRightEnd;
	TextureRegion dirtBottomRightEnd;
	TextureRegion dirtBottomMiddle;
	TextureRegion dirtBottomLeftBegin;
	TextureRegion dirtMiddleLeftBegin;
	TextureRegion dirtTopLeftBegin;
	TextureRegion dirtMiddleTop;
	TextureRegion dirtTopRightEnd;

	TextureRegion grassTop;

	TextureRegion waterLeft;

	TextureRegion rock;

	TextureRegion character;

	TextureRegion portrait;

	//Some variables to handle drawing parts of the map:
	int begin = 0;
	int end = begin + 336;
	int boundsCheck = end - 16;
	int grassLayer = end;
	int waterLayer = end;

	//Character related variables:
	int characterX = 0;
	int characterY = 0;

	//Sprite portrait related variables:
	int portraitX = waterLayer + 32;
	int portraitY = grassLayer - 16;

	//Score:
	int rocksMined = 0;

	int totalRockSeeds = 0;

	//Time code:
	int time = 100;
	int timerTick = time;
	int alternateTime = 0;

	//Random code:
	int randomValue;

	@Override
	public void create () {


		//Code for music:
		Music music = Gdx.audio.newMusic(Gdx.files.internal("Farm Night.mp3"));
		music.setLooping(true);
		music.setVolume(0.4f);
		music.play();

		//Font code:
		gameFont = new BitmapFont();
		gameFont.setColor(Color.SKY);

		//Sprite code that takes certain positions from the spritesheet to make individual tiles and sprites:
		spriteBatch = new SpriteBatch();
		spriteSheet = new Texture("tiles_packed.png");
		characterSheet = new Texture("dungeon_sheet.png");
		spritePortrait = new Texture("faelon.png");


		dirt = new TextureRegion(spriteSheet, 336, 64, 16, 16);
		dirtBottomRightEnd = new TextureRegion(spriteSheet, 352, 80, 16, 16);
		dirtMiddleRightEnd = new TextureRegion(spriteSheet, 352, 64, 16, 16);
		dirtBottomMiddle = new TextureRegion(spriteSheet, 336, 80, 16, 16);
		dirtBottomLeftBegin = new TextureRegion(spriteSheet, 320, 80, 16, 16);
		dirtMiddleLeftBegin = new TextureRegion(spriteSheet, 320, 64, 16, 16);
		dirtTopLeftBegin = new TextureRegion(spriteSheet, 320, 48, 16, 16);
		dirtMiddleTop = new TextureRegion(spriteSheet, 336, 48, 16, 16);
		dirtTopRightEnd = new TextureRegion(spriteSheet, 352, 48, 16, 16);

		grassTop = new TextureRegion(spriteSheet, 304, 0, 16, 16);

		waterLeft = new TextureRegion(spriteSheet, 176, 80, 16, 16);

		rock = new TextureRegion(spriteSheet,144 ,112, 16, 16);

		character = new TextureRegion(characterSheet, 304, 112, 16, 16);

		portrait = new TextureRegion(spritePortrait);
	}

	@Override
	public void render () {


		/*
		This is the code that checks for user input on keyboard button release:
		I did on keyboard release since the character moves in an int type 16 pixel at a time fashion:
		If we were using a character that moved with finer movement, pixel or 2 at a time for instance, then
		we would use floats to represent the x and y of the character and would most likely take input
		at the time of and while the button was being held down.

		To be more specific, some libraries such as GLFW, have a built-in way to calculate when a button
		has just been released. LibGDX does not specifically have a method to do so that I am aware of but
		there is a method for just pressed that can be used for similar results.
		*/
		if(Gdx.input.isKeyJustPressed(Input.Keys.W) && characterY < end - 16) {
			characterY = characterY + 16;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.S) && characterY > begin) {
			characterY = characterY - 16;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.A) && characterX > begin) {
			characterX = characterX - 16;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.D) && characterX < end - 16) {
			characterX = characterX + 16;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && rock1.isHere(characterX, characterY)) {
			rock1.putIsNotActive();
			rocksMined++;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && rock2.isHere(characterX, characterY)) {
			rock2.putIsNotActive();
			rocksMined++;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && rock3.isHere(characterX, characterY)) {
			rock3.putIsNotActive();
			rocksMined++;
		}

		else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && rock4.isHere(characterX, characterY)) {
			rock4.putIsNotActive();
			rocksMined++;
		}

		//This sets the background color for the map:
		ScreenUtils.clear(0, 0, 0, 1);

		//This starts the drawing process in relation to sprites:
		spriteBatch.begin();

		/*
		This is the part of the code that generates the dirt map and does so using
		logic for when to place certain types of blocks depending on if they are on the sides or corners
		 */
		for (int y = 0; y < end; y = y + 16) {
			for (int x = 0; x < end; x = x + 16) {
				//Code to determine if a rock is here or not and stop drawing of tiles if that is the case:
				//if (rock1.x == x && rock1.y == y || rock2.x == x && rock2.y == y || rock3.x == x && rock3.y == y || rock4.x == x && rock4.y == y) {
				//	canDrawHere = false;
				//}

				if (rock1.getX() == x && rock1.getY() == y && rock1.isActive()) {
					drawRockHere = true;
				}

				if (rock2.getX() == x && rock2.getY() == y && rock2.isActive()) {
					drawRockHere = true;
				}

				if (rock3.getX() == x && rock3.getY() == y && rock3.isActive()) {
					drawRockHere = true;
				}

				if (rock4.getX() == x && rock4.getY() == y && rock4.isActive()) {
					drawRockHere = true;
				}

				if (x == begin && y == begin) {
					spriteBatch.draw(dirtBottomLeftBegin, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (x == boundsCheck && y == begin)  {
					spriteBatch.draw(dirtBottomRightEnd, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (x == boundsCheck && y == boundsCheck) {
					spriteBatch.draw(dirtTopRightEnd, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (x == begin && y == boundsCheck) {
					spriteBatch.draw(dirtTopLeftBegin, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (x == boundsCheck) {
					spriteBatch.draw(dirtMiddleRightEnd, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (y == boundsCheck) {
					spriteBatch.draw(dirtMiddleTop, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (x == begin) {
					spriteBatch.draw(dirtMiddleLeftBegin, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else if (y == begin) {
					spriteBatch.draw(dirtBottomMiddle, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				else {
					spriteBatch.draw(dirt, x, y);
					if (drawRockHere) {
						spriteBatch.draw(rock, x, y);
					}
				}

				if (characterX == x && characterY == y ) {
					spriteBatch.draw(character, x, y);
				}

				///Rock render logic
				randomValue = rockRandom.nextInt(19);
				if (!rock1.isActive() && !rocksGenSeeds.isEmpty() && timerTick == 0) {
					if (rocksGenSeeds.get(0) % 2 == 0 || alternateTime > 2000 && !rock2.isHere((randomValue * 16), y + 64) && !rock3.isHere((randomValue * 16), y + 64) && !rock4.isHere((randomValue * 16), y + 64)) {
						rock1.putIsActive();
						rock1.setCoords((randomValue * 16), y + 64);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else if (rocksGenSeeds.get(0) % 2 == 0 || alternateTime > 2000 && !rock2.isHere(x, y) && !rock3.isHere(x, y) && !rock4.isHere(x, y)) {
						rock1.putIsActive();
						rock1.setCoords(x, y);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else {
						rocksGenSeeds.removeFirst();
						totalRockSeeds--;
					}
				}

				else if (!rock2.isActive() && !rocksGenSeeds.isEmpty() && timerTick == 0) {
					if (alternateTime > 20 && !rock1.isHere((randomValue * 16), y + 128) && !rock3.isHere((randomValue * 16), y + 128) && !rock4.isHere((randomValue * 16), y + 128)) {
						rock2.putIsActive();
						rock2.setCoords((randomValue * 16), y + 128);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else if (alternateTime > 20 && !rock1.isHere(x, y) && !rock3.isHere(x, y) && !rock4.isHere(x, y)) {
						rock2.putIsActive();
						rock2.setCoords(x, y);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else {
						rocksGenSeeds.removeFirst();
						totalRockSeeds--;
					}
				}

				else if (!rock3.isActive() && !rocksGenSeeds.isEmpty() && timerTick == 0) {
					if (alternateTime > 20 && !rock1.isHere((randomValue * 16), y + 32) && !rock2.isHere((randomValue * 16), y + 32) && !rock4.isHere((randomValue * 16), y + 32)) {
						rock3.putIsActive();
						rock3.setCoords(x + 128, y + 32);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else if (alternateTime > 20 && !rock1.isHere(x, y) && !rock2.isHere(x, y) && !rock4.isHere(x, y)) {
						rock3.putIsActive();
						rock3.setCoords(x, y);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else {
						rocksGenSeeds.removeFirst();
						totalRockSeeds--;
					}
				}

				else if (!rock4.isActive() && !rocksGenSeeds.isEmpty() && timerTick == 0) {
					if (alternateTime > 20 && !rock1.isHere((randomValue * 16), 256) && !rock2.isHere((randomValue * 16), 256) && !rock3.isHere((randomValue * 16), 256)) {
						rock4.putIsActive();
						rock4.setCoords((randomValue * 16), 256);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else if (alternateTime > 20 && !rock1.isHere(x, y) && !rock2.isHere(x, y) && !rock3.isHere(x, y)) {
						rock4.putIsActive();
						rock4.setCoords(x, y);
						rocksGenSeeds.removeFirst();
						timerTick = time;
						totalRockSeeds--;
						alternateTime = 0;
					}

					else {
						rocksGenSeeds.removeFirst();
						totalRockSeeds--;
					}
				}

				//Allow drawing again with next set of x and y:
				drawRockHere = false;
			}
		}

		//Draw the top layer of grass:
		for (int x = 0; x < end; x = x + 16) {
			spriteBatch.draw(grassTop, x, grassLayer);
		}

		//Draw the vertical line if water to the left of the map:
		for (int y = 0; y < end + 16; y = y + 16) {
			spriteBatch.draw(waterLeft, waterLayer, y);
		}

		//Draw text to the screen above the grass at some position:
		gameFont.draw(spriteBatch, ("Rocks Mined: " + rocksMined), portraitX, portraitY - 16);
		//Draw a sprite portrait for the speaker:
		spriteBatch.draw(spritePortrait, portraitX, portraitY);

		//Draw text above map for keys and text:
		gameFont.draw(spriteBatch, "Oh no! There are rocks in my dirt garden... can you help me?", 0, grassLayer + 256);



		//Game logic for random rock generation magic:
		if (totalRockSeeds < 6) {
			rocksGenSeeds.addLast(rockSeed.nextInt(0, 100));
			totalRockSeeds++;
		}

		//Decrement timer:
		if (timerTick > 0) {
			timerTick--;
		}

		alternateTime++;

		//End the drawing:
		spriteBatch.end();
	}

	//Clean everything up code:
	@Override
	public void dispose () {
		spriteBatch.dispose();
		spriteSheet.dispose();
		characterSheet.dispose();
		spritePortrait.dispose();
	}
}
