package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends B2DSprite{

GamingScreen gamingScreen;
public int numLuobo;
public int totalLuobo;
public TextureRegion[] sprites;
public Player(Body body,TextureRegion[] sprites){
	super(body);
	this.sprites = sprites;
//	TextureRegion[] sprites = TextureRegion.split(texture, 32, 32)[0];
	setAnimation(this.sprites, 1/8f);
}
public void collect(){
	numLuobo++;
}
public void setLuobo(int numLuobo){
	this.numLuobo=numLuobo;
}
public int getLuobo(){
	return numLuobo;
}
public void setTotal(int num){
	this.totalLuobo=num;
}
public int getTotal(){
	return totalLuobo;
}
}
