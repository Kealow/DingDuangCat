package com.mygdx.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ChooseItem extends Actor {
	public MyGdxGame game;
	public int i;
	public float x;
	public float y;
	public Texture texture;
	public Image image;
	public BitmapFont font;
	public TiledMap tiledMap;
	public int num;
	public ChooseScreen chooseScreen;
	public int zero;
	public MyContactListener cl;

	public ChooseItem(MyGdxGame game, int i, float x, float y,Texture texture,ChooseScreen chooseScreen) {
		this.i = i;
		this.game = game;
		this.x = x;
		this.y = y;
		this.zero=0;
	    this.texture = texture;
		this.image = new Image(texture);
		this.chooseScreen = chooseScreen;
		cl = new MyContactListener();
		font = new BitmapFont(Gdx.files.internal("num.fnt"),
				Gdx.files.internal("num.png"), false);
	    createListener();
//	    showTotalDuang();

	}
//	private void showTotalDuang(){
//		this.tiledMap = new TmxMapLoader().load("1-" + i + ".tmx");
//		MapLayer layer = tiledMap.getLayers().get("crystals");
//		num = layer.getObjects().getCount();
//		if(numDuang>zero){
//			zero = numDuang;
//		}
	
//	}

	private void createListener() {
		image.setPosition(x, y);
		image.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.touch.play();
				chooseScreen.dispose();
			game.setScreen(new GamingScreen(game, i));
	 		return true;
	 	}
	 
	 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	 	}
	 });

		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		image.draw(batch, 1);
		font.setColor(Color.WHITE);
		font.draw(batch, ""+i, image.getCenterX()-font.getLineHeight()/5, image.getCenterY()+font.getLineHeight()/2);
		font.setColor(Color.GREEN);
//		font.draw(batch, ""+zero, image.getCenterX()-font.getLineHeight(), image.getCenterY()-font.getLineHeight()/2);
//		font.draw(batch, "/"+num, image.getCenterX(), image.getCenterY()-font.getLineHeight()/2);
		super.draw(batch, parentAlpha);
	}


}
