package com.example.dots;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighScores extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_high_scores);
		
		Scores_DB dbhelper = new Scores_DB(getApplicationContext());
		dbhelper.open();
		List<scores> scores = dbhelper.getAllScores();
		
		LinearLayout ll = (LinearLayout)findViewById(R.id.rl1);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
			
		// Set up height and width of layout & adds the pictures
		
		lp.height = 100;
		lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
		
		int info_textview_size = 30;
		
		TextView highscore = new TextView (this);
		SpannableString content = new SpannableString("High Scores");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		Typeface tv = Typeface.createFromAsset(getAssets(),
				"fonts/gill-sans-ultra-bold-condensed.ttf");
		highscore.setTypeface(tv);
		highscore.setTextColor(this.getResources().getColor(R.color.orange));
		highscore.setTextSize(info_textview_size);
		highscore.setText (content);
		ll.addView (highscore,lp);
		
		for (int i = 0; i < scores.size(); i++){
			TextView score = new TextView (this);
			score.setTypeface(tv);
			score.setTextColor(this.getResources().getColor(R.color.orange));
			score.setTextSize(info_textview_size);
			score.setText ((i + 1) + ". " + scores.get(i).name + " " + scores.get(i).score);
			ll.addView (score,lp);
		}
		dbhelper.close();
	}

	public void onBackPressed(){
		Intent intent = new Intent (this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

}
