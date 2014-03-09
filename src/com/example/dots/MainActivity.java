package com .example.dots;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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

        //Sets the small circles
        final CustomImageView iv = new CustomImageView (this, R.drawable.blue_circle);
        final CustomImageView iv2 = new CustomImageView (this,R.drawable.green_circle);

        //Layout params to place image view on
        RelativeLayout r1 = (RelativeLayout)findViewById (R.id.rect);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams (
        		RelativeLayout.LayoutParams.WRAP_CONTENT,
        		RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        
        //Set up height and width of layout & adds the pictures
        lp.height = 110;
        lp.width = 110;
        r1.addView (iv,lp);
        r1.addView (iv2,lp);

        //defining the animations
        final AnimationSet animation = setAnimation(iv);
        final AnimationSet animation2 = setAnimation(iv2);

        //starting animations
    	iv.startAnimation (animation);
    	iv2.startAnimation (animation2);
    }

    //Defines the animationset - fade in for 3 sec, fade out for 3 second, then reposition & repeat
    public AnimationSet setAnimation (final ImageView iv) {
    	
    	//to generate width & height of screen & random #'s for replacement
    	Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x - 110;
        final int height = size.y - 110;
    	final Random x = new Random();
        final Random y = new Random();
        
        //define animation to fade in
    	 Animation fadeIn = new AlphaAnimation(0f, 1.0f);
         fadeIn.setInterpolator(new LinearInterpolator()); //add this
         fadeIn.setDuration(3000);

         //define animation to fade ou
         Animation fadeOut = new AlphaAnimation(1.0f, 0f);
         fadeOut.setInterpolator(new LinearInterpolator()); //and this
         fadeOut.setStartOffset(3000);
         fadeOut.setDuration(3000);
         
         //put them all in 1 animationset
         AnimationSet animations = new AnimationSet (false);
         animations.addAnimation (fadeIn);
         animations.addAnimation (fadeOut);
        
         //set listener, determine new position, set view to new position & repeat
         animations.setAnimationListener (new Animation.AnimationListener() {
     		public void onAnimationStart (Animation animation){
     		}
     		public void onAnimationEnd (Animation animation){	
     			float next_x = (x.nextInt(width));//need to make it so can fit within screen
     			float next_y = (y.nextInt(height));//
     			//System.out.println (next_x);
     			//System.out.println (next_y);
     			iv.setTranslationX (next_x);
     			iv.setTranslationY(next_y);
     			iv.startAnimation (animation);
     		}
     		public void onAnimationRepeat (Animation animation) {}
     	});
         return animations;
    }
    
    //defines the image view I need with touch already set up so when touch, right now will reset animation
    public class CustomImageView extends ImageView {
    	
    	public CustomImageView(Context context){
    		super (context);
    		super.setVisibility (View.INVISIBLE);
    	}

    	//constructor to set image and then hide it
    	public CustomImageView (Context context, int resource){
    		super (context);
    		super.setImageResource (resource);
    		super.setVisibility (View.INVISIBLE);
    	}
    	public boolean onTouchEvent (MotionEvent event) {
	    		this.clearAnimation();
	    		return false;
	    	}	
    }
    
    @SuppressLint("NewApi")
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
