package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class GamingScreen implements Screen, InputProcessor {
	public float STAGE_W;
	public float STAGE_H;
	protected final static float RATE = 100;// 1m=40px；
	private World world;
	private Body player;
	private BodyDef bdef;
	private Box2DDebugRenderer renderer; // box2D渲染器
	// private OrthographicCamera worldCamera;// 世界相机
	private BoundCamera worldCamera;
	private TiledMapRenderer tiledMapRenderer;// map渲染
	private TiledMap tiledMap;// map
	public static final short BIT_PLAYER = 2;
	public static final short BIT_RED = 4;
	public static final short BIT_BLUE = 8;
	public static final short BIT_GREEN = 16;
	public static final short BIT_CRYSTAL = 32;
	public static final short BIT_SPIKE = 64;
	private Player bunny;
	// static public Content res;
	private SpriteBatch sb;
	public Array<Crystal> crystals;
	public Array<Spike> spikes;
	public MyContactListener cl;
	public BitmapFont font;
	public OrthographicCamera hudCam;
	public Background[] backgrounds;
	public TextureRegion[] regions;
	public TextureRegion[] regions1;
	public TextureRegion[] regions2;
	public MyGdxGame game;
	public float score;
	public Sound sound;
	public int i;
	public Texture lingdangTexture;
	private int tileMapWidth;
	private int tileMapHeight;
	private int tileWidth;
	private int tileHeight;
	private Texture buttonTexture;
	private ImageButton returnButton;
	private ImageButton reselectButton;
	private Texture windowTexture;
	private Window window;
	private Stage stage;
	private TextureRegion[] processRegion;

	public GamingScreen(MyGdxGame game, int i) {
		this.game = game;
		this.i = i;
		STAGE_W = Gdx.graphics.getWidth();
		STAGE_H = Gdx.graphics.getHeight();
		// STAGE_W=1080;
		// STAGE_H=720;
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("num.fnt"),
				Gdx.files.internal("num.png"), false);
		world = new World(new Vector2(0.3f, -30f), false);// 世界 -30
		renderer = new Box2DDebugRenderer();
		worldCamera = game.getBoundCamera();
		// worldCamera.setToOrtho(false, STAGE_W, STAGE_H);
		hudCam = game.getOrthographicCamera();
		// hudCam.setToOrtho(false, STAGE_W, STAGE_H);
		sb = new SpriteBatch();
		cl = new MyContactListener();
		world.setContactListener(cl);
		lingdangTexture = MyGdxGame.res.getTexture("duang");
		regions = TextureRegion.split(MyGdxGame.res.getTexture("bunny"), 180,
				90)[0];
		regions1 = TextureRegion.split(MyGdxGame.res.getTexture("bunny"), 180,
				90)[0];
		regions2 = TextureRegion.split(MyGdxGame.res.getTexture("bunny2"), 180,
				90)[0];

		sound = Gdx.audio.newSound(Gdx.files.internal("MenuSelect.ogg"));
		font.setScale(1.5f, 1.5f);
		//播放音乐
		MyGdxGame.bgm.setLooping(true);
		MyGdxGame.bgm.play();
		// 创建背景
		createBackground();
		// 创建进度条
		createProcess();
		// 创建地图
		createTiles(i);
		// 主角
		createPlayer();
		// 创建萝卜
		createCrystal();
		// 创建辣椒
		createSpike();
		//
		createWindow();

		// hud = new HUD(bunny);
		Gdx.input.setInputProcessor(this);

	}

	private void createProcess() {
		processRegion = new TextureRegion[2];
		processRegion[0] = new TextureRegion(MyGdxGame.res.getTexture("jindu"),
				0, 0, 476, 120);
		processRegion[1] = new TextureRegion(MyGdxGame.res.getTexture("jindu"),
				0, 120, 476, 120);

	}

	private void createWindow() {
		buttonTexture = new Texture(Gdx.files.internal("overbtn.png"));
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
				game.setScreen(new GamingScreen(game, i));
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
				game.setScreen(new ChooseScreen(game));
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}

		});
		windowTexture = new Texture(Gdx.files.internal("overwindow.png"));
		WindowStyle windowStyle = new WindowStyle(font, Color.BLUE,
				new TextureRegionDrawable(new TextureRegion(windowTexture)));
		window = new Window("", windowStyle);
		window.setPosition(STAGE_W / 2 - window.getWidth() / 2, STAGE_H
				- window.getHeight());
		window.addActor(reselectButton);
		window.addActor(returnButton);
		window.setSize(STAGE_W, STAGE_H);
	}

	private void createBackground() {
		Texture texture = MyGdxGame.res.getTexture("bgp");
		TextureRegion fishRegion = new TextureRegion(texture, 0, 0, 1280, 720);
		TextureRegion shayuRegion = new TextureRegion(texture, 0, 720, 1280,
				720);
		TextureRegion skyRegion = new TextureRegion(texture, 0, 1440, 1280, 720);
		TextureRegion cloudRegion = new TextureRegion(texture, 0, 2160, 1280,
				720);
		backgrounds = new Background[4];
		backgrounds[0] = new Background(skyRegion, worldCamera, 0.3f);
		backgrounds[1] = new Background(cloudRegion, worldCamera, 0.1f);
		backgrounds[3] = new Background(shayuRegion, worldCamera, 0.2f);
		backgrounds[2] = new Background(fishRegion, worldCamera, 0.6f);

	}

	private void createPlayer() {
		bdef = new BodyDef();
		bdef.position.set(STAGE_W / 4 / RATE, STAGE_H / 2 / RATE);
		bdef.type = BodyType.DynamicBody;
		bdef.linearVelocity.set(3f, 0);
		player = world.createBody(bdef);
		// player.applyForce(new Vector2(500f,0), bdef.position, true);
		// player.applyForce(PLAYER_X_V, bdef.position, true);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50 / RATE, 20 / RATE);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_RED | BIT_CRYSTAL | BIT_SPIKE;
		fdef.isSensor = false;
		player.createFixture(fdef).setUserData("box");
		// bunny = new Player(player,regions);
		// 创建适配器
		shape.setAsBox(20 / RATE, 2 / RATE, new Vector2(0, -20 / RATE), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_RED;
		fdef.isSensor = true;
		player.createFixture(fdef).setUserData("foot");
		bunny = new Player(player, regions);

	}

	private void createSpike() {
		spikes = new Array<Spike>();
		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		MapLayer layer = tiledMap.getLayers().get("spikes");
		for (MapObject mObject : layer.getObjects()) {
			bDef.type = BodyType.StaticBody;
			float x = mObject.getProperties().get("x", Float.class);
			float y = mObject.getProperties().get("y", Float.class);
			bDef.position.set(x / RATE, y / RATE);
			CircleShape cShape = new CircleShape();
			cShape.setRadius(80 / RATE);
			fDef.shape = cShape;
			fDef.isSensor = true;
			fDef.filter.categoryBits = BIT_SPIKE;
			fDef.filter.maskBits = BIT_PLAYER;
			Body body = world.createBody(bDef);
			body.createFixture(fDef).setUserData("spike");
			Spike s = new Spike(body);
			body.setUserData(s);
			spikes.add(s);
			cShape.dispose();
		}

	}

	private void createCrystal() {
		crystals = new Array<Crystal>();
		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		MapLayer layer = tiledMap.getLayers().get("crystals");
		for (MapObject mo : layer.getObjects()) {
			bDef.type = BodyType.StaticBody;
			float x = mo.getProperties().get("x", Float.class);
			float y = mo.getProperties().get("y", Float.class);
			bDef.position.set(x / RATE, y / RATE);

			CircleShape cShape = new CircleShape();
			cShape.setRadius(60 / RATE);
			fDef.shape = cShape;
			fDef.isSensor = true;
			fDef.filter.categoryBits = BIT_CRYSTAL;
			fDef.filter.maskBits = BIT_PLAYER;

			Body body = world.createBody(bDef);
			body.createFixture(fDef).setUserData("crystal");

			Crystal c = new Crystal(body);
			body.setUserData(c);
			crystals.add(c);
			cShape.dispose();

		}

	}

	private void createLayer(TiledMapTileLayer layer, short bits, float width,
			float height) {
		BodyDef bDef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		for (int row = 0; row < layer.getWidth(); row++)
			for (int col = 0; col < layer.getHeight(); col++) {
				Cell cell = layer.getCell(row, col);
				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;
				// 创建body,夹具注意贴图坐标与刚体坐标对上
				// 创建链条，确定左下角（-ts，-ts），左上角（-ts，ts），右上角（+，+），并将这3个链接起来，形成的图形。
				bDef.type = BodyType.StaticBody;
				bDef.position.set((row + 0.5f) * width / RATE, (col + 0.5f)
						* height / RATE);
				// 链式图
				ChainShape cShape = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-width / 2 / RATE, -height / 2 / RATE);
				v[1] = new Vector2(-width / 2 / RATE, height / 2 / RATE);
				v[2] = new Vector2(width / 2 / RATE, height / 2 / RATE);
				cShape.createChain(v);
				fdef.shape = cShape;
				fdef.friction = 0;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bDef).createFixture(fdef);
			}

	}

	private void createTiles(int i) {
		tiledMap = new TmxMapLoader().load("1-" + i + ".tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		// tilesize = (Integer) tiledMap.getProperties().get("tildwidth");//
		// 单元格尺寸
		TiledMapTileLayer layer;
		float width;
		float height;
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("red");// 读取图层
		width = layer.getTileWidth();
		height = layer.getTileHeight();
		createLayer(layer, BIT_RED, width, height);
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("blue");// 读取图层
		width = layer.getTileWidth();
		height = layer.getTileHeight();
		createLayer(layer, BIT_BLUE, width, height);
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("green");// 读取图层
		width = layer.getTileWidth();
		height = layer.getTileHeight();
		createLayer(layer, BIT_GREEN, width, height);
		tileMapWidth = tiledMap.getProperties().get("width", Integer.class);
		tileMapHeight = tiledMap.getProperties().get("height", Integer.class);
		tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
		tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
		worldCamera.setBound(0, tileMapWidth * tileWidth, 0, tileMapHeight
				* tileHeight);

	}

	@Override
	public void dispose() {
		sb.dispose();
		font.dispose();
		tiledMap.dispose();
		renderer.dispose();
		stage.dispose();
		world.dispose();
		tiledMap.dispose();
		sound.dispose();
	}

	@Override
	public void render(float delta) {
		// renderer.render(world, camera.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(bunny.getPosition().x*RATE+STAGE_W*3/4<tileMapWidth*tileWidth){
		worldCamera.position.set(bunny.getPosition().x * RATE + STAGE_W / 4,
				STAGE_H / 2, 0);
		}
		// hudCam.position.set(STAGE_W / 2, STAGE_H / 2, 0);
		// hudCam.update();
		worldCamera.update();
		bunny.update(delta);
		sb.setProjectionMatrix(hudCam.combined);
		for (int i = 0; i < backgrounds.length; i++) {
			backgrounds[i].render(sb);
		}

		tiledMapRenderer.setView(worldCamera);
		tiledMapRenderer.render();
		isOver();
		if (isOver() == false) {
			isFinsh();
			world.step(delta*9/10, 10, 10);
			sb.setProjectionMatrix(hudCam.combined);
			// hud.render(sb);
			sb.begin();
			sb.draw(lingdangTexture, 0, STAGE_H - lingdangTexture.getHeight());
			// sb.draw(region, x, y, originX, originY, width, height, scaleX,
			// scaleY, rotation)
			font.drawMultiLine(sb, " x " + cl.getDuangNum() + "/"
					+ crystals.size, lingdangTexture.getWidth(), STAGE_H);
			font.drawMultiLine(sb, "Score:"
					+ (int) bunny.getBody().getPosition().x, 0,
					STAGE_H - font.getLineHeight());

			sb.draw(processRegion[1],
					STAGE_W / 2 - processRegion[1].getRegionWidth() / 2,
					STAGE_H - processRegion[1].getRegionHeight(), 0, 0, bunny
							.getBody().getPosition().x/tileMapWidth*processRegion[0].getRegionWidth(), processRegion[1]
							.getRegionHeight(), 1, 1, 0);
			sb.draw(processRegion[0],
					STAGE_W / 2 - processRegion[1].getRegionWidth() / 2,
					STAGE_H - processRegion[0].getRegionHeight(), 0, 0,
					processRegion[0].getRegionWidth(), processRegion[0].getRegionHeight(), 1, 1,
					0);

			sb.end();
			// renderer.render(world, camera.combined);
			sb.setProjectionMatrix(worldCamera.combined);
			bunny.render(sb);
			for (int i = 0; i < crystals.size; i++) {
				crystals.get(i).update(delta);
				crystals.get(i).render(sb);
			}
			for (int i = 0; i < spikes.size; i++) {
				spikes.get(i).update(delta);
				spikes.get(i).render(sb);
			}

			Array<Body> bodies = cl.getRemoveBody();
			for (int i = 0; i < bodies.size; i++) {
				Body b = bodies.get(i);
				crystals.removeValue((Crystal) b.getUserData(), true);
				world.destroyBody(b);
				cl.collect();
				sound.play(15f);
			}
			bodies.clear();
		}
		stage.act();
		stage.draw();

		// sb.begin();
		// sb.draw(res.getTexture("bunny"), 100, 100);
		// sb.end();

	}

	private void isFinsh() {
		if (bunny.getBody().getPosition().x * RATE > tileMapWidth * tileWidth) {
			game.setScreen(new GameOverScreen(cl.getDuangNum(), game,i));
		}

	}

	private boolean isOver() {
		if (bunny.getBody().getPosition().y < 0) {
			MyGdxGame.bgm.stop();
			stage.addActor(window);
			sb.begin();
			sb.draw(MyGdxGame.res.getTexture("gray"), 0, 0, STAGE_W, STAGE_H);
			sb.draw(lingdangTexture, STAGE_W / 2 - lingdangTexture.getWidth(),
					STAGE_H / 2 - lingdangTexture.getHeight() / 2);
			font.drawMultiLine(sb, " x " + cl.getDuangNum(), STAGE_W / 2,
					STAGE_H / 2 + font.getLineHeight() / 2);
			// font.drawMultiLine(sb, "Score:"
			// + (int) bunny.getBody().getPosition().x, STAGE_W / 2,
			// STAGE_H / 2 - font.getLineHeight());
			sb.end();
			Gdx.input.setInputProcessor(stage);
			return true;
			// game.setScreen(new GameOverScreen((int) bunny.getBody()
			// .getPosition().x, cl.getDuangNum(), game));
			// game.setScreen(new SelectScreen(game));
			// this.dispose();
			// game.setScreen(new GameOverScreen((int)bunny.getPosition().x,
			// (int)score, game));
		} else {
			return false;
		}

	}

	private void switchBlock() {
		Filter filter = bunny.getBody().getFixtureList().get(1).getFilterData();
		short bits = filter.maskBits;
		if (bits == BIT_RED) {
			bits = BIT_BLUE;
			regions = TextureRegion.split(MyGdxGame.res.getTexture("bunny2"),
					180, 90)[0];
		} else if (bits == BIT_BLUE) {
			bits = BIT_RED;
			regions = TextureRegion.split(MyGdxGame.res.getTexture("bunny"),
					180, 90)[0];
		}// else if (bits == BIT_BLUE) {
			// bits = BIT_RED;
			// regions = TextureRegion.split(res.getTexture("bunny"), 180,
			// 90)[0];
			// }
		filter.maskBits = bits;
		bunny.getBody().getFixtureList().get(1).setFilterData(filter);

		bits |= BIT_CRYSTAL | BIT_SPIKE;
		filter.maskBits = bits;
		bunny.getBody().getFixtureList().get(0).setFilterData(filter);
		bunny = new Player(player, regions);
		// bunny.sprites = regions;
		// bunny.setAnimation(regions,1/8);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
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
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX < Gdx.graphics.getWidth() / 2) {
			MyGdxGame.change.play();
			switchBlock();
			// bunny = new Player(player,regions);
		} else if (screenX > Gdx.graphics.getWidth() / 2) {
			if (cl.playerCanJump()) {
				MyGdxGame.jump.play();
				// player.applyForce(new Vector2(0,300f),
				// bunny.getBody().getPosition(), true);
				bunny.getBody().setLinearVelocity(
						bunny.getBody().getLinearVelocity().x, 0);
				// bunny.getBody().applyForceToCenter(0, 1200,true);
				bunny.getBody().applyForceToCenter(0, 700f, true);//700
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
