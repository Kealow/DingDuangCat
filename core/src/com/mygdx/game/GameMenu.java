package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameMenu implements Screen{
	private Texture texture ;
	private TextureRegion[] regions;
	private World world;
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;
	private SpriteBatch spriteBatch;
	private MyGdxGame game;
	private BitmapFont font;
	private Body rabbit;
	private Body titleBody;
	private MyContactListener cl;
	private final static float RATE=100;
	private Player ra;
	private Texture zitiTexture;
	private TextureRegion startTexture;
	private TextureRegion helpTexture;
	private ImageButton startButton;
	private ImageButton helpButton;
	private Stage stage;
	public GameMenu(MyGdxGame game){
		this.game = game;
		startTexture = new TextureRegion(new Texture(Gdx.files.internal("start.png")));
		helpTexture = new TextureRegion(new Texture(Gdx.files.internal("help.png")));
		texture = new Texture(Gdx.files.internal("fbg.png"));
		world = new World(new Vector2(0,-10f), true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth()/RATE,Gdx.graphics.getHeight()/RATE);
		renderer = new Box2DDebugRenderer();
		regions = TextureRegion.split(new Texture(Gdx.files.internal("bluecat.png")), 180,90)[0];
//		animation = new Animation(regions);
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(0, 0, 255, 0.5f);
		cl = new MyContactListener();
		world.setContactListener(cl);
		stage = new Stage();
		createFloor();
		createTitle();
		createSky();
		createCat();
		createButton();
		Gdx.input.setInputProcessor(stage);

	}

	private void createButton() {
		startButton = new ImageButton(new TextureRegionDrawable(startTexture),
				new TextureRegionDrawable(startTexture));
		startButton.setPosition(Gdx.graphics.getWidth()/2-startTexture.getRegionWidth()/2, Gdx.graphics.getHeight()/2-startTexture.getRegionHeight()/2);
		startButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyGdxGame.touch.play();
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
		helpButton = new ImageButton(new TextureRegionDrawable(helpTexture),
				new TextureRegionDrawable(startTexture));
		helpButton.setPosition(Gdx.graphics.getWidth()-helpTexture.getRegionWidth(), Gdx.graphics.getHeight()-helpTexture.getRegionHeight());
		helpButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyGdxGame.touch.play();
				dispose();
				game.setScreen(new HelpScreen(game));
			return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			});
		stage.addActor(startButton);
		stage.addActor(helpButton);
		
	}

	private void createSky() {
		BodyDef bDef = new BodyDef();
	    FixtureDef fDef = new FixtureDef();
	    bDef.position.set(Gdx.graphics.getWidth()/2/RATE, Gdx.graphics.getHeight()/1.4f/RATE);
	    bDef.type=BodyType.StaticBody;
	    fDef.isSensor=false;
	    PolygonShape ps = new PolygonShape();
	    ps.setAsBox(Gdx.graphics.getWidth()/RATE, 1/RATE);
	    fDef.shape=ps;
	    fDef.friction=0;
	    rabbit = world.createBody(bDef);
	    rabbit.createFixture(fDef).setUserData("sky");
	}

	private void createTitle() {
		zitiTexture = new Texture(Gdx.files.internal("ziti.png"));
		BodyDef bDef = new BodyDef();
	    FixtureDef fDef = new FixtureDef();
	    bDef.position.set(Gdx.graphics.getWidth()/2/RATE, Gdx.graphics.getHeight()/RATE);
	    bDef.type=BodyType.DynamicBody;
	    fDef.isSensor=false;
	    fDef.restitution=0.8f;
	    PolygonShape ps = new PolygonShape();
	    ps.setAsBox(Gdx.graphics.getWidth()/RATE, 10/RATE);
	    fDef.shape=ps;
	    titleBody = world.createBody(bDef);
	    titleBody.createFixture(fDef).setUserData("title");
		
		
	}

	private void createFloor() {
		BodyDef bDef = new BodyDef();
	    FixtureDef fDef = new FixtureDef();
	    bDef.position.set(Gdx.graphics.getWidth()/2/RATE, 0/RATE);
	    bDef.type=BodyType.StaticBody;
	    fDef.isSensor=false;
	    PolygonShape ps = new PolygonShape();
	    ps.setAsBox(Gdx.graphics.getWidth()/RATE, 0/RATE);
	    fDef.shape=ps;
	    fDef.friction=0;
	    rabbit = world.createBody(bDef);
	    rabbit.createFixture(fDef).setUserData("floor");
		
	}

	private void createCat() {
		BodyDef bDef = new BodyDef();
	    FixtureDef fDef = new FixtureDef();
	    bDef.position.set(20/RATE, 50/RATE);
	    bDef.type=BodyType.DynamicBody;
	    bDef.linearVelocity.set(2f, 0);
	    fDef.isSensor=false;
	    PolygonShape ps = new PolygonShape();
	    ps.setAsBox(60/RATE, 50/RATE);
	    fDef.shape=ps;
	    fDef.friction=0;
	    rabbit = world.createBody(bDef);
	    rabbit.createFixture(fDef).setUserData("rabbit");
	    ps.setAsBox(50/RATE, 2/RATE , new Vector2(0, -50/RATE ), 0);
		fDef.isSensor = true;
		fDef.shape=ps;
		rabbit.createFixture(fDef).setUserData("rabbitfoot");
		ra = new Player(rabbit, regions);
		
	    
	}

	@Override
	public void render(float delta) {
	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     camera.update();
     renderer.render(world, camera.combined);
     world.step(delta, 8, 8);
//   spriteBatch.setProjectionMatrix(camera.combined); 
     spriteBatch.begin();
     spriteBatch.draw(texture, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
     spriteBatch.draw(zitiTexture, titleBody.getPosition().x*RATE-zitiTexture.getWidth()/2,titleBody.getPosition().y*RATE-zitiTexture.getHeight()/2);
     spriteBatch.end();
     ra.update(delta);
     ra.render(spriteBatch);
     isOut();
	 isCanJump();
	 stage.act();
	 stage.draw();
	}

	private void isOut() {
		if(ra.getBody().getPosition().x>(Gdx.graphics.getWidth()+regions[0].getRegionWidth())/RATE){
		world.destroyBody(rabbit);
	    createCat();
		}
		
	}

	private void isCanJump() {
		if(cl.menuMove()){
			ra.getBody().setLinearVelocity(ra.getBody().getLinearVelocity().x, 0);
			ra.getBody().applyForceToCenter(0,300f, true);
			MyGdxGame.jump.play();
		}
		
		
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
	public void dispose() {
		world.dispose();
		spriteBatch.dispose();
		texture.dispose();
		zitiTexture.dispose();
		font.dispose();
		stage.dispose();
		
	}

}
