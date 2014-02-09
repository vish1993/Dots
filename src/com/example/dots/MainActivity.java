package com.example.dots;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageView iv = new ImageView (this);
        iv.setImageResource(R.drawable.blue_circle);
        RelativeLayout r1 = (RelativeLayout)findViewById (R.id.rect);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams (
        		RelativeLayout.LayoutParams.WRAP_CONTENT,
        		RelativeLayout.LayoutParams.WRAP_CONTENT
        		);
        lp.addRule (RelativeLayout.BELOW, R.id.ImageButton02);
        lp.addRule (RelativeLayout.ALIGN_PARENT_RIGHT);
        r1.addView (iv,lp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
