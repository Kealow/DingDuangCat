package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Spike extends B2DSprite{
	public Spike(Body body){
		super(body);
		Texture texture = MyGdxGame.res.getTexture("spike");
		TextureRegion[] regions = TextureRegion.split(texture, 90, 90)[0];
		setAnimation(regions, 1/10f);
	}
}
