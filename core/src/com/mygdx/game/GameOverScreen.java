package com.mygdx.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
public class GameOverScreen implements Screen {

	private MyGdxGame game;
	private SpriteBatch sb;
	private ImageButton returnButton;
	private ImageButton reselectButton;
	private Stage stage;
	private float STAGE_W;
	private float STAGE_H;
	private int getDuangNum;
	private Texture windowTexture;
	private Texture buttonTexture;
	private Texture bgTexture;
	private TextureRegion[] regions;
	private Window window;
	private int i;
	private BitmapFont font;

	public GameOverScreen(int getDuangNum, MyGdxGame game,int i) {
		this.getDuangNum = getDuangNum;
		this.game = game;
		stage = new Stage();
		STAGE_W = Gdx.graphics.getWidth();
		STAGE_H = Gdx.graphics.getHeight();
		this.i =i;
		font = new BitmapFont(Gdx.files.internal("word.fnt"),
				Gdx.files.internal("word.png"), false);
		font.setScale(1.5f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		sb.draw(bgTexture, 0, 0, STAGE_W, STAGE_H);
		// sb.draw(windowTexture, STAGE_W / 2 - windowTexture.getWidth() / 2,
		// STAGE_H / 2 - windowTexture.getHeight() / 2);
		 sb.draw(MyGdxGame.res.getTexture("duang"), STAGE_W / 2
		 - MyGdxGame.res.getTexture("duang").getWidth(), STAGE_H / 2);
		// font.drawMultiLine(sb, "Distance:" + totalMeter, STAGE_W / 2,
		// STAGE_H / 2);
		 font.drawMultiLine(sb, "x"+getDuangNum, STAGE_W / 2, STAGE_H
		 / 2 + font.getLineHeight());
		sb.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		sb = new SpriteBatch();
		bgTexture = new Texture(Gdx.files.internal("overbg.png"));
		buttonTexture = new Texture(Gdx.files.internal("winbtn.png"));
		regions = TextureRegion.split(buttonTexture, 180, 130)[0];
		returnButton = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(regions[0])));
		returnButton.setPosition(
				Gdx.graphics.getWidth() / 2 + regions[0].getRegionWidth(),
				Gdx.graphics.getHeight() / 4);
		returnButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				dispose();
				i=i+1 ;
				game.setScreen(new GamingScreen(game,i));
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}

		});
		reselectButton = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(regions[1])));
		reselectButton.setPosition(
				Gdx.graphics.getWidth() / 2 - regions[1].getRegionWidth() * 2,
				Gdx.graphics.getHeight() / 4);
		reselectButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				dispose();
				game.setScreen(new SelectScreen(game));
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}

		});
		windowTexture = new Texture(Gdx.files.internal("winwindow.png"));
		WindowStyle windowStyle = new WindowStyle(font, Color.BLUE,
				new TextureRegionDrawable(new TextureRegion(windowTexture)));
		window = new Window("", windowStyle);
		window.setPosition(STAGE_W / 2 - window.getWidth() / 2, STAGE_H
				- window.getHeight());
		window.addActor(reselectButton);
		window.addActor(returnButton);
		window.setSize(STAGE_W, STAGE_H);
		stage.addActor(window);
		// stage.addActor(returnButton);
		// stage.addActor(reselectButton);
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		sb.dispose();
		bgTexture.dispose();
		buttonTexture.dispose();
		windowTexture.dispose();
		font.dispose();

	}

}
