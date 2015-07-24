package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Button{
	float x;
	float y;
	float width;
	float height;
	TextureRegion region;
	OrthographicCamera camera;
	TextureRegion[] fonRegions;
	public boolean isClicked;
	public Vector3 vec3;
	public Button(TextureRegion region,float x,float y, OrthographicCamera camera){
		super();
		this.region=region;
		this.x=x;
		this.y=y;
		this.camera=camera;
		vec3 = new Vector3();
		
		Texture texture= MyGdxGame.res.getTexture("hud");
		fonRegions = new TextureRegion[10];
		for(int i=0;i<6;i++){
			fonRegions[i] = new TextureRegion(texture, 32+i*9, 16, 9, 9);
		}
		for(int i=0;i<4;i++){
			fonRegions[i+6] = new TextureRegion(texture, 32+i*9, 25, 9, 9);
		}
	}
	public boolean isClicked(){
		return isClicked;
	}
    public void update(float dt){
    	
    }
}
