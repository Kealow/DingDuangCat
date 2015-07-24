package com.mygdx.game;

import javax.swing.text.ChangedCharSetException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

	private BoundCamera worldCamera;
	private OrthographicCamera hudCam;
	public MyGdxGame game;
	private float STAGE_W;
	private float STAGE_H;
	private SpriteBatch sb;
	static public Content res;
	static public Sound touch;
	static public Music bgm;
	static public Sound jump;
	static public Sound collect;
	static public Sound change;
	

	@Override
	public void create() {
		STAGE_W = Gdx.graphics.getWidth();
		STAGE_H = Gdx.graphics.getHeight();
		res = new Content();
		res.loadTexture("redcat.png", "bunny");
		res.loadTexture("bluecat.png", "bunny2");
		res.loadTexture("lingdang1.png", "crystal");
		res.loadTexture("shuihua1.png", "spike");
		res.loadTexture("bg2.png", "bgp");
		res.loadTexture("duang.png", "duang");
		res.loadTexture("return.png", "return");
		res.loadTexture("reselect.png", "reselect");
		res.loadTexture("gray.png", "gray");
		res.loadTexture("jindu.png", "jindu");
		res.loadTexture("xg.png", "xuanguan");
		res.loadTexture("xgbg.png", "xuanguanbg");
		res.loadTexture("tc.png", "back");
		res.loadTexture("jiaocheng0.png", "jc0");
		res.loadTexture("jiaocheng1.png", "jc1");
		res.loadTexture("jiaocheng2.png", "jc2");
		res.loadTexture("jiaocheng3.png", "jc3");
		res.loadTexture("jiaocheng4.png", "jc4");
		res.loadTexture("jiaocheng5.png", "jc5");
//		res.loadMusic("touch.wav", "touch");
//		res.loadMusic("jump.wav", "jump");
//		res.loadMusic("BGM.wav", "bgm");
		touch = Gdx.audio.newSound(Gdx.files.internal("touch.wav"));
		jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
		bgm = Gdx.audio.newMusic(Gdx.files.internal("BGM.mp3"));
		collect = Gdx.audio.newSound(Gdx.files.internal("touch.wav"));
		change = Gdx.audio.newSound(Gdx.files.internal("Select.ogg"));
		worldCamera = new BoundCamera();
		worldCamera.setToOrtho(false, STAGE_W, STAGE_H);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, STAGE_W, STAGE_H);
		sb = new SpriteBatch();
		this.setScreen(new GameMenu(this));

	}

	public BoundCamera getBoundCamera() {
		return worldCamera;
	}

	public OrthographicCamera getOrthographicCamera() {
		return hudCam;
	}

	public SpriteBatch getSpriteBatch() {
		return sb;
	}

}
