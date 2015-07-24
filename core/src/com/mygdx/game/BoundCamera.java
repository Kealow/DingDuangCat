package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class BoundCamera extends OrthographicCamera{
	public float xmin;
	public float ymin;
	public float xmax;
	public float ymax;

	public BoundCamera() {
		this(0,0,0,0);

	}
    public BoundCamera(float xmin,float xmax, float ymin,float ymax){
    	super();
    	setBound(xmin, xmax, ymin, ymax);
    	
    }
    public void setBound(float xmin,float xmax, float ymin,float ymax){
    	this.xmin = xmin;
    	this.ymin = ymin;
    	this.xmax = xmax;
    	this.ymax = ymax;
    }
    public void setPosition(float x, float y){
    	setPosition(x, y,0);
    }
    public void setPosition(float x, float y,float z){
    	position.set(x, y, z);
    	fixBound();
    }
    public void fixBound(){
    	if(position.x <xmin +viewportWidth/2){
    		position.x = xmin +viewportWidth/2;
    	}
    	if(position.x >xmax -viewportWidth/2){
    		position.x = xmax -viewportWidth/2;
    	}
    	if(position.y <ymin +viewportHeight/2){
    		position.y = ymin +viewportHeight/2;
    	}
    	if(position.y >ymax -viewportHeight/2){
    		position.y = ymax -viewportHeight/2;
    	}
    }
}
