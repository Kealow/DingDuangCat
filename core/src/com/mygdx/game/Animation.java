package com.mygdx.game;



import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Animation {
		private TextureRegion[]  frames ;//帧
		float time;//
		float delay;//帧速
		int currentFrame;//帧数
		int timePlayed;
		
	
	public Animation(){
		
	}
	public Animation(TextureRegion[] frames){
		this(frames,1/12f);
	}
	public Animation(TextureRegion[] frames,float delay){
		setFrame(frames, delay);
		
	}
	public void setFrame(TextureRegion[] frames,float delay){
		this.frames = frames;
		this.delay = delay;
		currentFrame=0;
		timePlayed =0;
		time=0;
	}
	public void update(float dt){
		if(delay<=0){//如果设置时间错了
			return;
		}
		time+=dt;
		while(time>delay){
			step();
		}
	}
	private void step() {
		time-=delay;
		currentFrame++;
		if(currentFrame==frames.length){
			currentFrame=0;
			timePlayed++;
		}
		
	}
	public TextureRegion getFrame(){
		return frames[currentFrame];
	}
	public int getTimePlayed(){
		return timePlayed;
	}

}
