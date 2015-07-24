package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {
	
	private TextureRegion image;
	private OrthographicCamera gameCam;
	private float scale;
	
	private float x;
	private float y;
	private float numDrawX;
	
	private float dx;
	private float dy;
	
	public Background(TextureRegion image,OrthographicCamera gameCam,float scale) {
		this.image = image;
		this.gameCam = gameCam;
		this.scale = scale;

		
		numDrawX = Gdx.graphics.getWidth() / image.getRegionWidth() + 2;
	}
	
	public void setVector(float dx,float dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(float dt){
		x += (dx * scale) * dt;
		y += (dy * scale) * dt;
	}
	
	public void render(SpriteBatch sb){
		
		float x = ((this.x + gameCam.viewportWidth / 2 - gameCam.position.x) * scale)
				% image.getRegionWidth();
		
		sb.begin();
		
		for (int col = 0; col < numDrawX; col++) {
			sb.draw(image, x + col * image.getRegionWidth(),  y,image.getRegionWidth(),Gdx.graphics.getHeight());
	}

		sb.end();
		
	}
	
	
}
