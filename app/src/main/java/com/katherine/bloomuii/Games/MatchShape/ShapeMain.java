package com.katherine.bloomuii.Games.MatchShape;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.Random;

public class ShapeMain extends Activity implements OnTouchListener {
    private ImageView temp, shape1, shape2, shape3, shape4, shape5;                       // The shapes that the user drags.
    private ImageView shapeToMatch;                                                     // The shape outline that the user is supposed to drag shapes to.
    private AbsoluteLayout mainLayout;
    private Integer index, shapeNumber = 0, level = 1;
    ArrayList<Shape> tempShapeList = new ArrayList<>();
    ArrayList<Shape> initialShapeList = new ArrayList<>();
    ArrayList<Shape> finalShapeList = new ArrayList<>();
    ArrayList<Shape> outlineShapeList = new ArrayList<>();
    private boolean dragging = false;
    private Rect hitRect = new Rect();
    private Random randomGenerator = new Random();
    private FragmentManager fragmentManager;
    ImageView btnBack;

    /*    onCreate
        When the activity loads, this is the first method to be called.
        Matthew Talbot 25/06/2020 Version 2  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Method is used to Call methods when the activity first runs
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_main);
        mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(this);

        btnBack = (ImageView) findViewById(R.id.btnBack);

        declareComponents();
        retrieveFromDatabase();
        assignResources();
        btnBackClicked();

        //set on touch listeners for each Image View (Shape)
        temp.setOnTouchListener(this);
        shape1.setOnTouchListener(this);
        shape2.setOnTouchListener(this);
        shape3.setOnTouchListener(this);
        shape4.setOnTouchListener(this);
        shape5.setOnTouchListener(this);
    }

    /*    onTouch
    What happens when the User touchs an Image View on the screen
    Matthew Talbot 25/06/2020 Version 2  */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //method is used when a image view has been touched, dragged and dropped.
        //the image view is passed as a parameter to the method
        temp.setVisibility(View.VISIBLE);
        shapeToMatch.setImageDrawable(outlineShapeList.get(shapeNumber).shape_Resource);
        boolean eventConsumed = true;
        int x = (int) event.getX();
        int y = (int) event.getY();
        temp.setX(x - (temp.getWidth() / 2));
        temp.setY(y - (temp.getHeight() / 2));
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (v == shape1) {
                temp.setImageDrawable(shape1.getDrawable());
                dragging = true;
                index = 0;
                eventConsumed = false;
            } else if (v == shape2) {
                temp.setImageDrawable(shape2.getDrawable());
                dragging = true;
                index = 1;
                eventConsumed = false;
            } else if (v == shape3) {
                temp.setImageDrawable(shape3.getDrawable());
                dragging = true;
                index = 2;
                eventConsumed = false;
            } else if (v == shape4) {
                temp.setImageDrawable(shape4.getDrawable());
                dragging = true;
                index = 3;
                eventConsumed = false;
            } else if (v == shape5) {
                temp.setImageDrawable(shape5.getDrawable());
                dragging = true;
                index = 4;
                eventConsumed = false;
            } else if (v == temp) {
                dragging = true;
                eventConsumed = false;
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (dragging) {
                shapeToMatch.getHitRect(hitRect);
                if (hitRect.contains(x, y)) {
                    //when you drop shape into outline
                    //shape_to_match.setImageDrawable(temp.getDrawable());
                    setSameAbsoluteLocation(temp, shapeToMatch);
                    confirmMatch();
                }
            }
            dragging = false;
            eventConsumed = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (v != shape1 || v != shape2 || v != shape3 || v != shape4 || v != shape5) {
                if (dragging) {
                    setAbsoluteLocationCentered(temp, x, y);
                }
            }
        }
        return eventConsumed;
    }

    /*    declareComponents
    method to declare all the existing components on the activity
    Matthew Talbot 25/06/2020 Version 2  */
    private void declareComponents() {
        //this method is called first and is used to declare what components exist on the activity
        temp = (ImageView) findViewById(R.id.temp);
        shape1 = (ImageView) findViewById(R.id.shape1_img);
        shape2 = (ImageView) findViewById(R.id.shape2_img);
        shape3 = (ImageView) findViewById(R.id.shape3_img);
        shape4 = (ImageView) findViewById(R.id.shape4_img);
        shape5 = (ImageView) findViewById(R.id.shape5_img);
        shapeToMatch = (ImageView) findViewById(R.id.shape_to_match_img);
    }

    /*    retrieveFromDatbase
    method to retrieve the shapes from the database
    Matthew Talbot 25/06/2020 Version 2  */
    private void retrieveFromDatabase() {
        //this method will contact the database and will pull in all the shapes from the database
        initialShapeList.clear();
        outlineShapeList.clear();
        //retrieve list of Shapes from Database
        initialShapeList.add(new Shape("Square", getDrawable(R.drawable.square)));
        initialShapeList.add(new Shape("Circle", getDrawable(R.drawable.circle)));
        initialShapeList.add(new Shape("Rectangle", getDrawable(R.drawable.rectangle)));
        initialShapeList.add(new Shape("Triangle", getDrawable(R.drawable.triangle)));
        initialShapeList.add(new Shape("Oval", getDrawable(R.drawable.oval)));

        //retrieve the shape to match against from database
        outlineShapeList.add(new Shape("Square", getDrawable(R.drawable.square_outline)));
        outlineShapeList.add(new Shape("Circle", getDrawable(R.drawable.circle_outline)));
        outlineShapeList.add(new Shape("Rectangle", getDrawable(R.drawable.rectangle_outline)));
        outlineShapeList.add(new Shape("Triangle", getDrawable(R.drawable.triangle_outline)));
        outlineShapeList.add(new Shape("Oval", getDrawable(R.drawable.oval_outline)));
    }

    /*    assignResources
    method to Assign the shapes from the database into local variables
    Matthew Talbot 25/06/2020 Version 2  */
    private void assignResources() {
        //once the shapes are pulled into the List, here is where they will be altered and custom
        // made into the shapes we want to appear in the front end
        if (shapeNumber < outlineShapeList.size()) {
            shapeToMatch.setImageDrawable(outlineShapeList.get(shapeNumber).shape_Resource);

            tempShapeList.clear();
            tempShapeList.add(initialShapeList.get(shapeNumber));
            initialShapeList.remove(shapeNumber.intValue());
            while (tempShapeList.size() <= (level)) {
                int randomNumber = randomGenerator.nextInt(initialShapeList.size());
                tempShapeList.add(initialShapeList.get(randomNumber));
                initialShapeList.remove(randomNumber);
            }
            finalShapeList.clear();
            while (tempShapeList.size() > 0) {
                int randomNumber = randomGenerator.nextInt(tempShapeList.size());
                finalShapeList.add(tempShapeList.get(randomNumber));
                tempShapeList.remove(randomNumber);
            }
            //assign image view resources to Object resources
            displayShapes(level);
        } else if (level == 4 && shapeNumber == 5) {
            Toast.makeText(this, "Game Mode Completed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Onto the next Level", Toast.LENGTH_SHORT).show();
            shapeNumber = 0;
            level++;
            retrieveFromDatabase();
            assignResources();
        }
        //use to load Drawable from a string reference
        // yourImageView.setImageResource(context.getResources().
        //        getIdentifier("drawable/" + imageName, null,context.getPackageName()));
    }

    /*    confirmMatch
    method to confirm if the shape that is being dragged, matches the shape in the middle
    Matthew Talbot 25/06/2020 Version 2  */
    private void confirmMatch() {
        //this method searches the Lists to find the Shape objects at the relevant index and compares to see if they match.
        if (outlineShapeList.get(shapeNumber).shape_Name == finalShapeList.get(index).shape_Name) {
            Toast.makeText(this, "Its a Match", Toast.LENGTH_SHORT).show();
            shapeNumber++;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    retrieveFromDatabase();
                    assignResources();
                    temp.setVisibility(View.INVISIBLE);

                }
            }, 2000);
        } else {
            Toast.makeText(this, "No Match", Toast.LENGTH_LONG).show();
            temp.setVisibility(View.INVISIBLE);
        }
    }

    /*    setSameAbsoluteLocation
    method to drop the dragged image into the same image view as the shape to match
    Matthew Talbot 25/06/2020 Version 2  */
    private void setSameAbsoluteLocation(View v1, View v2) {
        AbsoluteLayout.LayoutParams alp2 = (AbsoluteLayout.LayoutParams) v2.getLayoutParams();
        setAbsoluteLocation(v1, alp2.x, alp2.y);
    }

    /*    setAbsoluteLocationCentered
    method to center the dropped image to match the outlined image
    Matthew Talbot 25/06/2020 Version 2  */
    private void setAbsoluteLocationCentered(View v, int x, int y) {
        setAbsoluteLocation(v, x - v.getWidth() / 2, y - v.getHeight() / 2);
    }

    /*    setAbsoluteLocation
    method to set the location of the dragged image
    Matthew Talbot 25/06/2020 Version 2  */
    private void setAbsoluteLocation(View v, int x, int y) {
        AbsoluteLayout.LayoutParams alp = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
        alp.x = x;
        alp.y = y;
        v.setLayoutParams(alp);
    }

    /*    displayShapes
    method to display the correct amount of shapes related to level number
    Matthew Talbot 25/06/2020 Version 2  */
    private void displayShapes(int lvl) {
        switch (lvl) {
            case 1:
                shape1.setVisibility(View.VISIBLE);
                shape2.setVisibility(View.VISIBLE);
                shape1.setImageDrawable(finalShapeList.get(0).shape_Resource);
                shape2.setImageDrawable(finalShapeList.get(1).shape_Resource);
                break;
            case 2:
                shape1.setVisibility(View.VISIBLE);
                shape2.setVisibility(View.VISIBLE);
                shape3.setVisibility(View.VISIBLE);
                shape1.setImageDrawable(finalShapeList.get(0).shape_Resource);
                shape2.setImageDrawable(finalShapeList.get(1).shape_Resource);
                shape3.setImageDrawable(finalShapeList.get(2).shape_Resource);
                break;
            case 3:
                shape1.setVisibility(View.VISIBLE);
                shape2.setVisibility(View.VISIBLE);
                shape3.setVisibility(View.VISIBLE);
                shape4.setVisibility(View.VISIBLE);
                shape1.setImageDrawable(finalShapeList.get(0).shape_Resource);
                shape2.setImageDrawable(finalShapeList.get(1).shape_Resource);
                shape3.setImageDrawable(finalShapeList.get(2).shape_Resource);
                shape4.setImageDrawable(finalShapeList.get(3).shape_Resource);
                break;
            case 4:
                shape1.setVisibility(View.VISIBLE);
                shape2.setVisibility(View.VISIBLE);
                shape3.setVisibility(View.VISIBLE);
                shape4.setVisibility(View.VISIBLE);
                shape5.setVisibility(View.VISIBLE);
                shape1.setImageDrawable(finalShapeList.get(0).shape_Resource);
                shape2.setImageDrawable(finalShapeList.get(1).shape_Resource);
                shape3.setImageDrawable(finalShapeList.get(2).shape_Resource);
                shape4.setImageDrawable(finalShapeList.get(3).shape_Resource);
                shape5.setImageDrawable(finalShapeList.get(4).shape_Resource);
                break;
        }
    }

    //click to get back to home fragment
    private void btnBackClicked() {
        final Fragment homeFragment = new HomeFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, homeFragment)
                        .commit();
            }
        });
    }
}
