package com.SAT.awesomeflashcards;

import com.SAT.awesomeflashcards.AnimationFactory;
import com.SAT.awesomeflashcards.R;
import com.SAT.awesomeflashcards.AnimationFactory.FlipDirection;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class CardStack extends Activity {

    private Animation mInFromRight;
    private Animation mOutToLeft;
    private Animation mInFromLeft;
    private Animation mOutToRight;
    private ViewFlipper mViewFlipper;
    
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcards);
      //CHILD VIEWFLIPPER 
        //FlipAnimation
        
        final ViewFlipper cvf1 = (ViewFlipper)findViewById(R.id.ChildFlipper1);
        cvf1.setOnTouchListener(new OnTouchListener(){
        	
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		AnimationFactory.flipTransition(cvf1, FlipDirection.LEFT_RIGHT);
        		// TODO Auto-generated method stub
        		return false;
        	}});
        final ViewFlipper cvf2 = (ViewFlipper)findViewById(R.id.ChildFlipper2);
        cvf2.setOnTouchListener(new OnTouchListener(){
        	
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		AnimationFactory.flipTransition(cvf2, FlipDirection.LEFT_RIGHT);
        		// TODO Auto-generated method stub
        		return false;
        	}});

//Transitions
        mViewFlipper = (ViewFlipper) findViewById(R.id.FlipperMain);
        mViewFlipper.setDisplayedChild(0);
        initAnimations();
    }

    private void initAnimations() {
        mInFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        mInFromRight.setDuration(500);
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        mInFromRight.setInterpolator(accelerateInterpolator);

        mInFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        mInFromLeft.setDuration(500);
        mInFromLeft.setInterpolator(accelerateInterpolator);

        mOutToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0.0f, Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        mOutToRight.setDuration(500);
        mOutToRight.setInterpolator(accelerateInterpolator);

        mOutToLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        mOutToLeft.setDuration(500);
        mOutToLeft.setInterpolator(accelerateInterpolator);

        final GestureDetector gestureDetector;
        gestureDetector = new GestureDetector(new MyGestureDetector());
//PARENT VIEWFLIPPER
        mViewFlipper.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
            	if (gestureDetector.onTouchEvent(event)) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        

        }

	private class MyGestureDetector extends SimpleOnGestureListener {

        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            System.out.println(" in onFling() :: ");
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                mViewFlipper.setInAnimation(mInFromRight);
                mViewFlipper.setOutAnimation(mOutToLeft);
                mViewFlipper.showNext();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                mViewFlipper.setInAnimation(mInFromLeft);
                mViewFlipper.setOutAnimation(mOutToRight);
                mViewFlipper.showPrevious();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
       
       }
    

				
	@Override
	//create action bar menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		//action bar items and functions	
		case R.id.quit:
			Toast.makeText(this, getString(R.string.ui_quit),
					Toast.LENGTH_SHORT).show();	
			finish(); // close the activity
			return true;
}
		return false;
		}
	


	
}
    
