package com.mygdx.game;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener{
public int numFootContacts;
public Array<Body> removeBody;
public boolean isDead;
public int jump;
public int getDuang;
public MyContactListener(){
	super();
	getDuang=0;
	removeBody = new Array<Body>();
}
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if(fa.getUserData()!=null && fa.getUserData()=="foot"){
			numFootContacts++;
		}
		if(fb.getUserData()!=null && fb.getUserData()=="foot"){
			numFootContacts++;
		}
		if(fa.getUserData()!=null && fa.getUserData()=="crystal"){
            removeBody.add(fa.getBody());
		}
		if(fb.getUserData()!=null && fb.getUserData()=="crystal"){
			removeBody.add(fb.getBody());
		}
		if(fa.getUserData()!=null && fa.getUserData()=="spike"){
			numFootContacts++;
		}
		if(fb.getUserData()!=null && fb.getUserData()=="spike"){
			numFootContacts++;
		}
		if(fa.getUserData()!=null && fa.getUserData()=="floor"){
			jump++;
		}
		if(fb.getUserData()!=null && fb.getUserData()=="floor"){
			jump++;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if(fa==null||fb==null)return;
		if(fa.getUserData()!=null && fa.getUserData()=="foot"){
			numFootContacts--;
		}
		if(fb.getUserData()!=null && fb.getUserData()=="foot"){
			numFootContacts--;
		}
		if(fa.getUserData()!=null && fa.getUserData()=="spike"){
			numFootContacts--;
		}
		if(fb.getUserData()!=null && fb.getUserData()=="spike"){
			numFootContacts--;
		}
		if(fa.getUserData()!=null && fa.getUserData()=="floor"){
			jump--;
		}
		if(fb.getUserData()!=null && fb.getUserData()=="floor"){
			jump--;
		}
		
	}
	public Array<Body> getRemoveBody(){
		return removeBody;
	}
	public boolean playerCanJump() {
		return numFootContacts > 0;
	}
	public boolean isPlayerDead(){
		return isDead;
	}
	public boolean menuMove(){
		return jump>0;
	}
	public void collect(){
		getDuang++;
	}
	public int getDuangNum(){
		return getDuang;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
