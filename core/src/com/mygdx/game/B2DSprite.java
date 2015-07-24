package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2DSprite {
public Body body;
public Animation animation;
public float width;
public float height;
public GamingScreen gamingScreen;
public B2DSprite(Body body){
	this.body = body;
	animation = new Animation();
}
public void setAnimation(TextureRegion[] frames,float delay){
	animation.setFrame(frames, delay);
	width = frames[0].getRegionWidth();
	height = frames[0].getRegionHeight();
}
public void update(float dt){
	animation.update(dt);
}
public void render(SpriteBatch sb){
	sb.begin();
	sb.draw(animation.getFrame(), body.getPosition().x*gamingScreen.RATE-width/2, body.getPosition().y*gamingScreen.RATE-height/2);
	sb.end();
}
public Body getBody(){
	return body;
}
public Vector2 getPosition(){
	return body.getPosition();
}
public float getWidth(){
	return width;
}
public float getHeight(){
	return height;
}
}
