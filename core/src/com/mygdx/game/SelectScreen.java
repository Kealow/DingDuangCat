package com.mygdx.game;

import sun.tools.jar.resources.jar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

;

public class SelectScreen implements Screen, InputProcessor {
	// 游戏舞台的基本宽高
	public static float GAMESTAGE_WIDTH;
	public static float GAMESTAGE_HEIGHT;

	int i;

	SpriteBatch batch;
	TextureRegion[] pages;
	Texture texture;
	Texture tex;
	Stage stage;
	MyGdxGame game;

	Sound sound;
	Sound soundSelect;

	ImageButton leftButton;
	ImageButton rightButton;
	ImageButton startButton;

	public SelectScreen(MyGdxGame game) {
		this.game = game;
	}

	@Override
	public void show() {

		GAMESTAGE_WIDTH = Gdx.graphics.getWidth();
		GAMESTAGE_HEIGHT = Gdx.graphics.getHeight();
		i = 0;
		batch = new SpriteBatch();
		tex = new Texture(Gdx.files.internal("xuanguan.png"));
		texture = new Texture(Gdx.files.internal("fbg.png"));
		sound = Gdx.audio.newSound(Gdx.files.internal("MenuSelect.ogg"));
		soundSelect = Gdx.audio.newSound(Gdx.files.internal("Select.ogg"));

		pages = TextureRegion.split(tex, 720, 720)[0];

		stage = new Stage();
		stage.getCamera().position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		Gdx.input.setInputProcessor(stage);
		Texture left = new Texture(Gdx.files.internal("jiantouzuo.png"));
		Texture right = new Texture(Gdx.files.internal("jiantouyou.png"));
		leftButton = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(left)), new TextureRegionDrawable(
				new TextureRegion(new Texture(
						Gdx.files.internal("presszuo.png")))));
		leftButton.setPosition(0, GAMESTAGE_HEIGHT / 2 - left.getHeight() / 2);
		leftButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyGdxGame.touch.play();
				if (i > 0) {
					i--;
				}
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
		});

		rightButton = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(right)), new TextureRegionDrawable(
				new TextureRegion(new Texture(
						Gdx.files.internal("pressyou.png")))));
		rightButton.setPosition(GAMESTAGE_WIDTH - right.getWidth(),
				GAMESTAGE_HEIGHT / 2 - right.getHeight() / 2);
		rightButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyGdxGame.touch.play();
				if (i < 1) {
					i++;
				}
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
		});
		Texture starttexture = new Texture(Gdx.files.internal("go.png"));
		startButton = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(starttexture)), new TextureRegionDrawable(
				new TextureRegion(starttexture)));
		startButton.setPosition(GAMESTAGE_WIDTH / 2 - starttexture.getWidth()
				/ 2, 0);
		startButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyGdxGame.touch.play();
				dispose();
				game.setScreen(new ChooseScreen(game));
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
		});
		// stage.addActor(startButton);
		stage.addActor(leftButton);
		stage.addActor(rightButton);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (i == 0) {
			stage.addActor(startButton);
		} else {
			startButton.remove();
		}
		batch.begin();
		batch.draw(texture, 0, 0, GAMESTAGE_WIDTH, GAMESTAGE_HEIGHT);
		batch.draw(pages[i], 0, 0, GAMESTAGE_WIDTH, GAMESTAGE_HEIGHT);
		batch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		sound.dispose();
		soundSelect.dispose();
		stage.dispose();
		batch.dispose();
		tex.dispose();
		texture.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
