package com.katherine.bloomuii.Games.Puzzle;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/*
Class name: Gesture Detect Grid
Reason: This class is used to detect and implement the motion
        events passed to it.
Parameters:
Team Member: Cameron White
Date: 9 June 2020
Version: 1
Date: 10 June 2020
Version: 2
Date: 14 Aug 2020
Version: 3
*/

public class GestureDetectGridView extends GridView {

    private GestureDetector gDetector;
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;

    //swipe distances for method activation
    //ToDo: May need to change distances depending on device
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        //listen for user gesture
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            //check direction of movement and speed
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                //get position on screen
                final int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(e1.getX()), Math.round(e1.getY()));

                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
                            || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                        //Code for swipe UP
                        PuzzleLogic.moveTiles(context, PuzzleLogic.UP, position);
                    } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                        //Code for swipe DOWN
                        PuzzleLogic.moveTiles(context, PuzzleLogic.DOWN, position);
                    }
                } else {
                    if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                        //Code for swipe LEFT
                        PuzzleLogic.moveTiles(context, PuzzleLogic.LEFT, position);
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                        //Code for swipe RIGHT
                        PuzzleLogic.moveTiles(context, PuzzleLogic.RIGHT, position);
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    //translate and return motion events from the user
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        //get user point of contact
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }
}