package com.example.dots;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	MediaPlayer mp;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setContentView(R.layout.activity_main); 

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		boolean PlayMusic = prefs.getBoolean("music", false);
		mp = MediaPlayer.create(getApplicationContext(), R.raw.avril);
	
		if (PlayMusic) {
			mp.start();
		}
	}

	@Override
	protected void onDestroy() {
		if (mp.isPlaying()) {
			mp.stop();
		}
		super.onDestroy();
	}

	@Override 
	public void onBackPressed(){
		final AlertDialog temp = new AlertDialog.Builder (this)
		.setTitle ("Exit")
		.setMessage ("Are you sure you want to leave?")
		.setPositiveButton ("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.setNegativeButton ("YES", new DialogInterface.OnClickListener(){
			public void onClick (DialogInterface dialog, int which){	
				finish();
			}
		})
		.show();
	}
	
	public void goPlay(View view) {
		if (mp.isPlaying()) {
			mp.stop();
		}
		Intent intent = new Intent(this, Play.class);
		startActivity(intent);
	}

	public void goOptions(View view) {
		if (mp.isPlaying()) {
			mp.stop();
		}
		Intent intent = new Intent(this, Options.class);
		startActivity(intent);
	}

	
	public void seeScores (View view){
		if (mp.isPlaying()) {
			mp.stop();
		}
		Intent intent = new Intent(this, HighScores.class);
		startActivity(intent);
	}
	
	/*
	@SuppressLint("NewApi")
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/
}
