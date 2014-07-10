package com.blackaplysia.gestures;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.util.Log;

import java.lang.StringBuffer;
import java.lang.Integer;
import java.lang.Float;
import java.util.List;

public class GesturesActivity extends Activity
{

    private static final String getTag() {
	return "GesturesActivity";
    }

    StringBuffer _logSequence = new StringBuffer();

    void log(String message) {
	Log.i(getTag(), message);
	_logSequence = _logSequence.append("> ").append(message).append("\n");
	((TextView)findViewById(R.id.log)).setText(_logSequence.toString());
    }

    GestureDetector _gestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	_gestureDetector = new GestureDetector(this);
	_gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
		@Override
		public boolean onGesture(Gesture gesture) {
		    log("BaseListener: " + gesture);
		    return false;
		}
	    });
	_gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
		@Override
		public void onFingerCountChanged(int previousCount, int currentCount) {
		    log("FingerListener: " + Integer.valueOf(previousCount).toString() + " -> " + Integer.valueOf(currentCount).toString());
		}
	    });
	_gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
		@Override
		public boolean onScroll(float displacement, float delta, float velocity) {
		    log("ScrollListener: disp=" + Float.valueOf(displacement).toString() + ", delta=" + Float.valueOf(delta).toString() + ", v=" + Float.valueOf(velocity).toString());
		    return false;
		}
	    });
	_gestureDetector.setTwoFingerScrollListener(new GestureDetector.TwoFingerScrollListener() {
		@Override
		public boolean onTwoFingerScroll(float displacement, float delta, float velocity) {
		    log("TwoFingerScrollListener: disp=" + Float.valueOf(displacement).toString() + ", delta=" + Float.valueOf(delta).toString() + ", v=" + Float.valueOf(velocity).toString());
		    return false;
		}
	    });

	setContentView(R.layout.main);
	log("onCreate()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	log("KeyDown: " + KeyEvent.keyCodeToString(keyCode) + " (" + Integer.valueOf(keyCode).toString() + "), " + event);
	return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
	log("KeyUp: " + KeyEvent.keyCodeToString(keyCode) + " (" + Integer.valueOf(keyCode).toString() + "), " + event);
	return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
	if (_gestureDetector != null) {
	    return _gestureDetector.onMotionEvent(event);
	}
	return false;
    }

}
