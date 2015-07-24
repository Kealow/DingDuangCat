package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class ChooseScreen implements Screen {
	Stage stage;
	Array<ChooseItem> Chooses;
	ChooseItem item;
	MyGdxGame game;
	Texture texture;
	SpriteBatch sb;

	public ChooseScreen(MyGdxGame game) {
		this.game = game;
		stage = new Stage();
		sb = new SpriteBatch();
		this.texture = MyGdxGame.res.getTexture("xuanguanbg");
		createItem();
		Gdx.input.setInputProcessor(stage);

	}

	private void createItem() {
		Texture texture = MyGdxGame.res.getTexture("xuanguan");
		Texture returntTexture = MyGdxGame.res.getTexture("back");
		Chooses = new Array<ChooseItem>();
		for (int i = 1; i <= 5; i++) {
			item = new ChooseItem(game, i, i * Gdx.graphics.getWidth() / 7,
					Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4,
					texture, this);
			Chooses.add(item);
			stage.addActor(item.image);
			stage.addActor(item);
		}
		for (int i = 6; i <= 10; i++) {
			item = new ChooseItem(game, i, (i - 5) * Gdx.graphics.getWidth()
					/ 7, Gdx.graphics.getHeight() / 2, texture, this);
			Chooses.add(item);
			stage.addActor(item.image);
			stage.addActor(item);
		}
		for (int i = 11; i <= 15; i++) {
			item = new ChooseItem(game, i, (i - 10) * Gdx.graphics.getWidth()
					/ 7, Gdx.graphics.getHeight() / 4, texture, this);
			Chooses.add(item);
			stage.addActor(item.image);
			stage.addActor(item);
		}
		Image tcImage = new Image(returntTexture);
		tcImage.setPosition(0, 0);
		tcImage.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyGdxGame.touch.play();
				dispose();
				game.setScreen(new SelectScreen(game));

				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

			}
		});
		stage.addActor(tcImage);

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		sb.draw(texture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		sb.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
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
		// TODO Auto-generated method stub
		stage.dispose();
		Chooses.clear();
		// texture.dispose();
		sb.dispose();

	}

}
