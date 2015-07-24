package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Content {
	private HashMap<String, Texture> texture;
	private HashMap<String, TiledMap> titleMap;
	private HashMap<String, Sound> soundHashMap;

	public Content() {
		texture = new HashMap<String, Texture>();
		titleMap = new HashMap<String, TiledMap>();
	}

	public void loadTexture(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		texture.put(key, tex);

	}
	public void loadMusic(String path , String key){
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		soundHashMap.put(key, sound);
	}

	public void loadTitleMap(String path, String key) {
		TiledMap Map = new TmxMapLoader().load(path);
		titleMap.put(key, Map);
	}

	public Texture getTexture(String key) {
		return texture.get(key);

	}
	public Sound getSound(String key){
		return soundHashMap.get(key);
	}

	public TiledMap getTiledMap(String key) {
		return titleMap.get(key);

	}

	public void disposeTexture(String key) {
		Texture tex = texture.get(key);
		if (tex != null) {
			tex.dispose();
		}
	}

	public void disposeTiledMap(String key) {
		TiledMap map = titleMap.get(key);
		if (map != null) {
			map.dispose();
		}
	}
	public void dispseSound(String key){
	    Sound sound = soundHashMap.get(key);
	    if(sound!=null){
	    	sound.dispose();
	    }
	}
}
