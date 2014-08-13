package com.example.dots;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Options extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.options);
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		boolean playMusic = prefs.getBoolean("music", false);
		boolean playSound = prefs.getBoolean ("sound", false);
		
		final ImageView music_btn = (ImageView) findViewById (R.id.checkbox1);
		if (playMusic) {
			music_btn.setImageResource (R.drawable.checked_circle);
			music_btn.setTag (R.drawable.checked_circle);
		} 
		else { 
			music_btn.setTag (R.drawable.game_circle);
		}
		
		final ImageView sound_btn = (ImageView) findViewById (R.id.checkbox2);
		if (playSound) {
			sound_btn.setTag (R.drawable.checked_circle);
			sound_btn.setImageResource(R.drawable.checked_circle);
		} 
		else { 
			sound_btn.setTag (R.drawable.game_circle);
		}
	
		music_btn.setOnTouchListener (new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				boolean isChecked = checkBox(music_btn);
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor edit = prefs.edit();
				edit.putBoolean("music", isChecked);
				edit.commit();
				return false;
			}
		});
		
		sound_btn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				boolean isChecked = checkBox(sound_btn);
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor edit = prefs.edit();
				edit.putBoolean("sound", isChecked);
				edit.commit();
				return false;
			}
		});
	}

	public boolean checkBox (ImageView iv){
		int checked_circle = (Integer)(R.drawable.checked_circle);
		int unchecked_circle = (Integer)(R.drawable.game_circle);
		Object drawable = iv.getTag();
		int id = (drawable == null) ? -1 : Integer.parseInt (drawable.toString());
		int new_resource = (id == checked_circle) ? unchecked_circle : checked_circle;
		iv.setImageResource(new_resource);
		iv.setTag (new_resource);
		boolean newResource = (new_resource == checked_circle) ? true : false;
		return newResource;
	}
	
	public void clearScore(View view){
		final Scores_DB dbhelper = new Scores_DB(getApplicationContext());
		dbhelper.clear();
	}
	
	
	@Override 
	
	public void onBackPressed(){
		Intent intent = new Intent (this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.object, menu);
		return true;
	}

}
