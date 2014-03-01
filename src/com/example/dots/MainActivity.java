package com .example.dots;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
    @SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To figure setting out how to create image views through code
        final ImageView iv = new ImageView (this);
        iv.setImageResource(R.drawable.blue_circle);
        iv.setVisibility (View.INVISIBLE);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x - 110;
        final int height = size.y - 110;
        
        //Layout params to place image view on
        RelativeLayout r1 = (RelativeLayout)findViewById (R.id.rect);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams (
        		RelativeLayout.LayoutParams.WRAP_CONTENT,
        		RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        
        //Set up height and width of layout
        lp.height = 110;
        lp.width = 110;
        lp.addRule (RelativeLayout.BELOW, R.id.ImageButton02);
        lp.addRule (RelativeLayout.ALIGN_PARENT_RIGHT);
        r1.addView (iv,lp);
        
        final Random x = new Random();
        final Random y = new Random();
        //defining the animations
        Animation fadeIn = new AlphaAnimation(0f, 1.0f);
        fadeIn.setInterpolator(new LinearInterpolator()); //add this
        fadeIn.setDuration(3000);

        Animation fadeOut = new AlphaAnimation(1.0f, 0f);
        fadeOut.setInterpolator(new LinearInterpolator()); //and this
        fadeOut.setStartOffset(3000);
        fadeOut.setDuration(3000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
    
        animation.setAnimationListener (new Animation.AnimationListener() {
    		public void onAnimationStart (Animation animation){
    		}
    		public void onAnimationEnd (Animation animation){	
    			float next_x = (x.nextInt(width)) * -1;//need to make it so can fit within screen
    			float next_y = (y.nextInt(height));//
    			iv.setTranslationX (next_x);
    			iv.setTranslationY(next_y);
    			animation.reset();
    			animation.start();
    		}
    		public void onAnimationRepeat (Animation animation) {}
    	});
    	iv.startAnimation (animation);
    }


    @SuppressLint("NewApi")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
