package com.katherine.bloomuii.Games.Order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.katherine.bloomuii.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderFragment extends Fragment  implements View.OnClickListener {
    public static Button[] buttons = new Button[8]; //Button array used to initialize all buttons
    public static TextView[] textViews = new TextView[3]; //Button array used to initialize all buttons
    public static int levelNumber = -100;
    public static List<Integer> zeroToFour = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
    public static List<Integer> fiveToNine = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9));
    public static List<Integer> tens = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19));
    public static List<Integer> twenties = new ArrayList<Integer>(Arrays.asList(20, 21, 22, 23, 24, 25, 26, 27, 28, 29));
    public static List<Integer> zeroToNinetyNine = new ArrayList<Integer>();
    public static String displayUserAnswer = "";
    public static List<Integer> userAnswer = new ArrayList<Integer>();
    int clickCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true); //Firebase functionality works when offline, online is required for first launch to retrieve data

        OrderHandler.getSetUserLevel();

        for (int i = 0; i < buttons.length; i++)        //initializing buttons
        {
            String buttonID = "button" + (i + 1);

            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            buttons[i] = view.findViewById(resID);
            buttons[i].setOnClickListener(this);
            buttons[i].setVisibility(View.INVISIBLE);
        }

        buttons[6].setEnabled(false);
        buttons[7].setEnabled(true);
        buttons[6].setVisibility(View.VISIBLE);
        buttons[7].setVisibility(View.VISIBLE);

        for (int i = 0; i < textViews.length; i++)        //initializing textViews
        {
            String textViewID = "textView" + (i + 1);

            int resID = getResources().getIdentifier(textViewID, "id", getActivity().getPackageName());
            textViews[i] = view.findViewById(resID);
        }
        return view;
    }

    public static void levelCreate() {
        textViews[1].setText("Level: " + levelNumber);

        if (levelNumber <= 3) {
            List<Integer> shuffleList = new ArrayList<>(zeroToFour);
            Collections.shuffle(shuffleList);
            for (int i = 3; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 3; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 6) {
            List<Integer> shuffleList = new ArrayList<>(fiveToNine);
            Collections.shuffle(shuffleList);
            for (int i = 3; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 3; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 9) {
            List<Integer> shuffleList = new ArrayList<>(zeroToFour);
            shuffleList.addAll(fiveToNine);
            Collections.shuffle(shuffleList);
            for (int i = 4; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 4; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 12) {
            List<Integer> shuffleList = new ArrayList<>(zeroToFour);
            shuffleList.addAll(fiveToNine);
            Collections.shuffle(shuffleList);
            for (int i = 5; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 5; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 15) {
            List<Integer> shuffleList = new ArrayList<>(zeroToFour);
            shuffleList.addAll(fiveToNine);
            Collections.shuffle(shuffleList);
            for (int i = 0; i < 6; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 20) {
            List<Integer> shuffleList = new ArrayList<>(tens);
            Collections.shuffle(shuffleList);
            for (int i = 3; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 3; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 25) {
            List<Integer> shuffleList = new ArrayList<>(tens);
            Collections.shuffle(shuffleList);
            for (int i = 4; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 4; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 30) {
            List<Integer> shuffleList = new ArrayList<>(tens);
            Collections.shuffle(shuffleList);
            for (int i = 5; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 5; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 35) {
            List<Integer> shuffleList = new ArrayList<>(tens);
            Collections.shuffle(shuffleList);

            for (int i = 0; i < 6; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 40) {
            List<Integer> shuffleList = new ArrayList<>(twenties);
            Collections.shuffle(shuffleList);
            for (int i = 3; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 3; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 45) {
            List<Integer> shuffleList = new ArrayList<>(twenties);
            Collections.shuffle(shuffleList);
            for (int i = 4; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 4; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 50) {
            List<Integer> shuffleList = new ArrayList<>(twenties);
            Collections.shuffle(shuffleList);
            for (int i = 5; i < buttons.length - 2; i++) {
                {
                    buttons[i].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 5; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else if (levelNumber <= 60) {
            List<Integer> shuffleList = new ArrayList<>(twenties);
            shuffleList.addAll(tens);
            shuffleList.addAll(fiveToNine);
            shuffleList.addAll(zeroToFour);
            Collections.shuffle(shuffleList);

            for (int i = 0; i < 6; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
        } else {
            for (int i = 0; i < 100; i++) {
                zeroToNinetyNine.add(i);
            }
            List<Integer> shuffleList = new ArrayList<>(zeroToNinetyNine);
            Collections.shuffle(shuffleList);
            for (int i = 0; i < 6; i++) {
                {
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setEnabled(true);
                    buttons[i].setText(shuffleList.get(i).toString());
                }
            }
            textViews[1].setText("Hard Mode Enabled");
        }
    }
    public void checkClicked(int clickCount){
        int requiredClicks = 0;
        for(int i=0; i<buttons.length - 2; i++) {
            {
                if(buttons[i].getVisibility() == View.VISIBLE){
                    requiredClicks++;
                }
            }
        }
        if(requiredClicks == clickCount){
            buttons[6].setEnabled(true);
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //number buttons
            case R.id.button1:
                buttons[0].setEnabled(false); //button is made unclickable so the user cannot use the same number twice
                displayUserAnswer = displayUserAnswer + buttons[0].getText().toString() + "     ";
                textViews[2].setText(displayUserAnswer);
                userAnswer.add(Integer.parseInt(buttons[0].getText().toString()));
                clickCount++;
                checkClicked(clickCount);
                break;

            case R.id.button2:
                buttons[1].setEnabled(false);
                displayUserAnswer = displayUserAnswer + buttons[1].getText().toString() + "     ";
                textViews[2].setText(displayUserAnswer);
                userAnswer.add(Integer.parseInt(buttons[1].getText().toString()));
                clickCount++;
                checkClicked(clickCount);
                break;

            case R.id.button3:
                buttons[2].setEnabled(false);
                displayUserAnswer = displayUserAnswer + buttons[2].getText().toString() + "     ";
                textViews[2].setText(displayUserAnswer);
                userAnswer.add(Integer.parseInt(buttons[2].getText().toString()));
                clickCount++;
                checkClicked(clickCount);
                break;

            case R.id.button4:
                buttons[3].setEnabled(false);
                displayUserAnswer = displayUserAnswer + buttons[3].getText().toString() + "     ";
                textViews[2].setText(displayUserAnswer);
                userAnswer.add(Integer.parseInt(buttons[3].getText().toString()));
                clickCount++;
                checkClicked(clickCount);
                break;

            case R.id.button5:
                buttons[4].setEnabled(false);
                displayUserAnswer = displayUserAnswer + buttons[4].getText().toString() + "     ";
                textViews[2].setText(displayUserAnswer);
                userAnswer.add(Integer.parseInt(buttons[4].getText().toString()));
                clickCount++;
                checkClicked(clickCount);
                break;

            case R.id.button6:
                buttons[5].setEnabled(false);
                displayUserAnswer = displayUserAnswer + buttons[5].getText().toString() + "     ";
                textViews[2].setText(displayUserAnswer);
                userAnswer.add(Integer.parseInt(buttons[5].getText().toString()));
                ++clickCount;
                checkClicked(clickCount);
                break;

            case R.id.button7: //submit
                int correctCount = 0;

                for(int i=0; i<userAnswer.size() - 1; i++) {
                    {
                        if(userAnswer.get(i) < userAnswer.get(i+1)){
                            correctCount++;
                        }
                        else {

                        }
                    }
                }
                if (correctCount == userAnswer.size() -1){
                    if(levelNumber == 60){
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Hard Mode Unlocked")
                                .setMessage("Congratulations you have completed all standard levels. " +
                                        "You may continue playing on Hard Mode which has infinite levels if " +
                                        "you want to test you skills")
                                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //set what would happen when positive button is clicked
                                        levelNumber++;
                                        OrderHandler.getSetUserLevel();
                                        levelCreate();
                                        userAnswer.clear();
                                        displayUserAnswer = "";
                                        textViews[2].setText(displayUserAnswer);
                                        buttons[6].setEnabled(false);
                                        clickCount = 0;
                                    }
                                })
                                .show();
                    }
                    else {
                        Toast.makeText(getContext(), "Correct", Toast.LENGTH_SHORT).show();
                        levelNumber++;
                        OrderHandler.getSetUserLevel();
                        levelCreate();
                        userAnswer.clear();
                        displayUserAnswer = "";
                        textViews[2].setText(displayUserAnswer);
                        buttons[6].setEnabled(false);
                        clickCount = 0;
                    }
                }
                else {
                    Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button8: //restart
                for(int i=0; i<buttons.length - 2; i++) {
                    buttons[i].setEnabled(true);
                }
                userAnswer.clear();
                displayUserAnswer = "";
                textViews[2].setText(displayUserAnswer);
                buttons[6].setEnabled(false);
                clickCount = 0;
                break;
        }
    }
}