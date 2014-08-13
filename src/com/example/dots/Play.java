package com.example.dots;

import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Play extends Activity {

	int score_ct = 0;
	TextView score;
	final GamePiece view_holder[] = new GamePiece[6];
	CountDownTimer countdown;
	final int info_textview_size = 30;
	int maxTime;
	final int seconds = 1000;
	int num_of_pieces = 6;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_play);

		maxTime = 30000;
		
		score = (TextView) findViewById(R.id.score_tracker);
		Typeface tv = Typeface.createFromAsset(getAssets(),
				"fonts/gill-sans-ultra-bold-condensed.ttf");
		score.setTypeface(tv);
		score.setTextColor(this.getResources().getColor(R.color.orange));
		score.setTextSize(info_textview_size);
		
		countdown = addTimer();
		countdown.start();
	
		// Layout params to place image view on
		RelativeLayout r1 = (RelativeLayout) findViewById(R.id.game_field);
		
		int piece_size = 210;
		
		for (int i = 0; i < num_of_pieces; i++) {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(piece_size,piece_size);
			final GamePiece view = new GamePiece(getApplicationContext());
			view.setBackgroundResource (R.drawable.game_circle);
			r1.addView (view);
			final AnimationSet animations = setAnimation(view);
			view.startAnimation(animations);		
			view_holder[i] = view;
		}
	}

	public void onBackPressed(){
		for(int i = 0; i < num_of_pieces; i++){
			Animation temp = new AlphaAnimation(1.0f, 1.0f);
			view_holder[i].setAnimation (temp);
			view_holder[i].startAnimation (temp);
		}	
		countdown.cancel();
		
		final AlertDialog.Builder temp = new AlertDialog.Builder(this)
		.setTitle("Play Again?")
		.setMessage("Would you like exit?")
		.setPositiveButton("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for(int i = 0; i < num_of_pieces; i++){
					AnimationSet temp = setAnimation (view_holder[i]);
					view_holder[i].setAnimation (temp);
					view_holder[i].startAnimation (temp);
				}
				countdown = addTimer();
				countdown.start();
			}
		})
		.setNegativeButton("YES",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		temp.show();
	}

	// Defines the animationset - fade in for 3 sec, fade out for 3 second, then
	// reposition & repeat
	@SuppressLint("NewApi")
	public AnimationSet setAnimation(final GamePiece iv) {
		// to generate width & height of screen & random #'s for replacement

		final int time = Math.max(1000/ ((iv.touch_count/5) + 1),300);
		
		// define animation to fade in
		Animation fadeIn = new AlphaAnimation(0f, 1.0f);
		fadeIn.setInterpolator(new LinearInterpolator()); // add this
		fadeIn.setDuration(time);

		// define animation to fade out
		Animation fadeOut = new AlphaAnimation(1.0f, 0f);
		fadeOut.setInterpolator(new LinearInterpolator()); // and this
		fadeOut.setStartOffset(time);
		fadeOut.setDuration(time);

		// put them all in 1 animationset
		final AnimationSet animations = new AnimationSet(false);
		animations.addAnimation(fadeIn);
		animations.addAnimation(fadeOut);

		// set listener, determine new position, set view to new position &
		// repeat
		animations.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				System.gc();
				movePiece(iv);
				//Animation temp_anim = setAnimation(iv);
				final Random id_gen = new Random();
				int next_id = id_gen.nextInt(iv.max_score) - iv.max_score/2;
				iv.setText(Integer.toString(next_id));
				iv.id = next_id;
				iv.startAnimation(animation);
				System.out.println (time);
			}

			public void onAnimationRepeat(Animation animation) {
			}
		});
		return animations;
	}

	@SuppressLint("NewApi")
	public void movePiece(GamePiece piece) {
		Display display = getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);

		TextView score = (TextView)findViewById(R.id.score_tracker);
		ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) score.getLayoutParams();

		int screen_buffer = 300;
		int info_textview_size = 30;

		final int width = size.x - screen_buffer;
		final int height = size.y - screen_buffer - lp.topMargin - info_textview_size;

		final Random x = new Random();
		final Random y = new Random();

		float next_x = (x.nextInt(width));// need to make it so can fit within
		// screen
		float next_y = (y.nextInt(height - lp.topMargin) + lp.topMargin) + info_textview_size;//

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(210,210);
		params.setMargins ((int)next_x,(int)next_y,0,0);
		piece.setLayoutParams (params);
	}

	public CountDownTimer addTimer(){
		final AlertDialog.Builder temp = new AlertDialog.Builder(this)
		.setTitle("Play Again?")
		.setMessage("Would you like to play again?")
		.setPositiveButton("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		})
		.setNegativeButton("YES",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {
				Intent intent = new Intent(
						getApplicationContext(), Play.class);
				startActivity(intent);
			}
		});

		countdown = new CountDownTimer(maxTime, seconds) {
			TextView timer = (TextView) findViewById(R.id.time_tracker);
			Typeface tv = Typeface.createFromAsset(getAssets(),
					"fonts/gill-sans-ultra-bold-condensed.ttf");

			public void onTick(long time) {
				int timeUsed = (int) (time / 1000);
				int minute = timeUsed / 60;
				int seconds = timeUsed % 60;
				timer.setTypeface(tv);
				timer.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.orange));
				timer.setTextSize(info_textview_size);
				if (seconds >= 10){
					timer.setText("Time: " + minute + ":" + seconds);
				}
				else{
					timer.setText("Time: " + minute + ":0" + seconds);
				}
				maxTime = (int)time;
			}

			public void onFinish() {
				
				for(int i = 0; i < num_of_pieces; i++){
					Animation temp = new AlphaAnimation(1.0f, 1.0f);
					view_holder[i].setAnimation (temp);
					view_holder[i].startAnimation (temp);
				}	
				
				final Scores_DB dbhelper = new Scores_DB(getApplicationContext());
				dbhelper.open();
				List<scores> scores = dbhelper.getAllScores();

				timer.setTypeface(tv);
				timer.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.orange));
				timer.setTextSize(info_textview_size);
				timer.setText ("Time: 0:00");

				scores min_score =(scores.size() != 0) ? scores.get(scores.size() - 1) : new scores ("Bob", -1);
				int min = min_score.score;

				if ((score_ct > min) || scores.size() <= 10){
					if (scores.size() == 10){
						dbhelper.deleteScore(min_score.name, min_score.score);
					}
					final EditText input = new EditText(Play.this);

					new AlertDialog.Builder(Play.this)
					.setTitle("Insert High Score")
					.setMessage("Please enter your name")
					.setView(input)
					.setPositiveButton("It's ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							temp.show();
						}
					})
					.setNegativeButton("Insert Score",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dbhelper.open();
							String name = input.getText().toString();
							name = (name == "") ? "Anonymous" : name;
							dbhelper.insertScore (name,score_ct);
							temp.show();
							dbhelper.close();
							// Do nothing.
						}
					}).show();
				}
				else{
					temp.show();
				}
				dbhelper.close();
			}
		};
		
		return countdown;
	}

	// defines the image view I need with touch already set up so when touch,
	// right now will reset animation
	public class GamePiece extends TextView {
		int touch_count = 0;
		int id;
		int max_score = 10;
		int score_text_size = 52;

		@SuppressLint("NewApi")
		public GamePiece(Context context) {
			super(context);
			Typeface tv = Typeface.createFromAsset(getAssets(),
					"fonts/gill-sans-ultra-bold-condensed.ttf");
			super.setTypeface(tv);
			super.setGravity(Gravity.CENTER);
			super.setBackgroundResource(R.drawable.game_circle);
			final Random id_gen = new Random();
			int next_id = id_gen.nextInt(max_score) - max_score/2;
			this.id = next_id;
			super.setText(Integer.toString(next_id));
			super.setTextColor(this.getResources().getColor(R.color.orange));
			super.setTextSize(score_text_size);
			movePiece(this);
		}

		public boolean onTouchEvent(MotionEvent event) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			boolean sound = prefs.getBoolean("sound", false);
			System.out.println (sound);
			if (sound) {
				int soundtrack = (this.id < 0) ? R.raw.buzzer : R.raw.ding;
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(),
						soundtrack);
				mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.stop();
						if (mp != null) {
							mp.release();
						}
					}
				});
				mp.start();
			}

			score_ct += this.id;
			score.setText("Score: " + score_ct);
			this.touch_count++;
			this.clearAnimation();

			return false;
		}
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	*/
}
