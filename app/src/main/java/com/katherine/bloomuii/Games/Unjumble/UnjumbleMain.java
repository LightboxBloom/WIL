package com.katherine.bloomuii.Games.Unjumble;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UnjumbleMain extends AppCompatActivity implements View.OnClickListener {

    String userAnswer1 = "";     //Users answers will be stored in this string
    String correctAnswer1 = "the dog barks";    //Users answer will be compared to this string

    String userAnswer2 = "";     //Users answers will be stored in this string
    String correctAnswer2 = "the cat meows";    //Users answer will be compared to this string

    String userAnswer3 = "";     //Users answers will be stored in this string
    String correctAnswer3 = "he was happy";    //Users answer will be compared to this string

    String userAnswer4 = "";     //Users answers will be stored in this string
    String correctAnswer4 = "she kicked a ball";    //Users answer will be compared to this string

    String userAnswer5 = "";     //Users answers will be stored in this string
    String correctAnswer5 = "the leaves are green";    //Users answer will be compared to this string

    String userAnswer6= "";     //Users answers will be stored in this string
    String correctAnswer6 = "an elephant was walking";    //Users answer will be compared to this string

    String userAnswer7 = "";     //Users answers will be stored in this string
    String correctAnswer7 = "she is a kind girl";    //Users answer will be compared to this string

    String userAnswer8 = "";     //Users answers will be stored in this string
    String correctAnswer8 = "he saw a big cow";    //Users answer will be compared to this string

    String userAnswer9 = "";     //Users answers will be stored in this string
    String correctAnswer9 = "the fat cat ate fish";    //Users answer will be compared to this string

    String userAnswer10 = "";     //Users answers will be stored in this string
    String correctAnswer10 = "the small cat was being chased";    //Users answer will be compared to this string

    String userAnswer11 = "";     //Users answers will be stored in this string
    String correctAnswer11 = "he was carrying a big bag";    //Users answer will be compared to this string

    String userAnswer12 = "";     //Users answers will be stored in this string
    String correctAnswer12 = "she was reading an interesting book";    //Users answer will be compared to this string

    int levelNumber = 1;
    ImageView btnBack;
    private FragmentManager fragmentManager;

    /*
        Level 1-3 = 3 word sentences

    the dog barks

    the cat meows

    he was happy

            Level 4-6 = 4 word sentences

    she kicked a ball

    the leaves are green

    an elephant was walking

        Level 7-9 = 5 Word sentences

    she is a kind girl

    he saw a big cow

    the fat cat ate fish

            Level 10-12 = 6 Word sentences

    the small cat was being chased

    he was carrying a big bag

    she was reading an interesting book

     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unjumble_main);


        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);

        btnBack = (ImageView) findViewById(R.id.btnBack);


        //button9.setVisibility(View.INVISIBLE);
        //button10.setVisibility(View.INVISIBLE);
        //button11.setVisibility(View.INVISIBLE);
        //button12.setVisibility(View.INVISIBLE);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);


        TextView textView1 = findViewById(R.id.textView);
        textView1.setText("");

        shuffle1(button1, button2, button3, button4, button5, button6); //calling shuffle method
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        button6.setVisibility(View.INVISIBLE);

        btnBackClicked();

    }

    //shuffle method randomises which buttons will have which word on them
    public void shuffle1(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"the", "dog", "barks"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText("");
        e.setText("");
        f.setText("");
    }

    public void shuffle2(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"the", "cat", "meows"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText("");
        e.setText("");
        f.setText("");
    }

    public void shuffle3(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"he", "was", "happy"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText("");
        e.setText("");
        f.setText("");
    }

    public void shuffle4(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"she", "kicked", "a", "ball"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText("");
        f.setText("");
    }

    public void shuffle5(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"the", "leaves", "are", "green"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText("");
        f.setText("");
    }

    public void shuffle6(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"an", "elephant", "was", "walking"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText("");
        f.setText("");
    }

    public void shuffle7(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"she", "is", "a", "kind", "girl"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText(shuffleList.get(4));
        f.setText("");
    }

    public void shuffle8(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"he", "saw", "a", "big", "cow"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText(shuffleList.get(4));
        f.setText("");
    }

    public void shuffle9(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"the", "fat", "cat", "ate", "fish"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText(shuffleList.get(4));
        f.setText("");
    }

    public void shuffle10(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"the", "small", "cat", "was", "being", "chased"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText(shuffleList.get(4));
        f.setText(shuffleList.get(5));
    }

    public void shuffle11(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"he", "was", "carrying", "a", "big", "bag"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText(shuffleList.get(4));
        f.setText(shuffleList.get(5));
    }

    public void shuffle12(Button a, Button b, Button c, Button d, Button e, Button f){  //shuffle method randomises which buttons will have which word on them

        String[] words = {"she", "was", "reading", "an", "interesting", "book"};
        List<String> shuffleList = Arrays.asList(words);
        Collections.shuffle(shuffleList);
        a.setText(shuffleList.get(0));
        b.setText(shuffleList.get(1));
        c.setText(shuffleList.get(2));
        d.setText(shuffleList.get(3));
        e.setText(shuffleList.get(4));
        f.setText(shuffleList.get(5));
    }

    @Override
    public void onClick(View v) {   //handles the click events for all the buttons

        Button btn = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        //Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);

        if(levelNumber == 1)
        {
        TextView textView1 = findViewById(R.id.textView);
        switch (v.getId()) {
            case R.id.button1:
                //Button btn = findViewById(R.id.button1);
                btn.setEnabled(false);

                userAnswer1 = userAnswer1 + " " + btn.getText().toString();
                textView1.setText(userAnswer1);
                break;

            case R.id.button2:
                //Button btn2 = findViewById(R.id.button2);
                btn2.setEnabled(false);

                userAnswer1 = userAnswer1 + " " + btn2.getText().toString();
                textView1.setText(userAnswer1);


                break;

            case R.id.button3:
                //Button btn3 = findViewById(R.id.button3);
                btn3.setEnabled(false);

                userAnswer1 = userAnswer1 + " " + btn3.getText().toString();
                textView1.setText(userAnswer1);
                break;

            case R.id.button4:
                //Button btn4 = findViewById(R.id.button4);
                btn4.setEnabled(false);

                userAnswer1 = userAnswer1 + " " + btn4.getText().toString();
                textView1.setText(userAnswer1);
                break;

            case R.id.button5:

                //Button btn5 = findViewById(R.id.button5);
                btn5.setEnabled(false);

                userAnswer1 = userAnswer1 + " " + btn5.getText().toString();
                textView1.setText(userAnswer1);
                break;

            case R.id.button6:

                //Button btn6 = findViewById(R.id.button6);
                btn6.setEnabled(false);

                userAnswer1 = userAnswer1 + " " + btn6.getText().toString();
                textView1.setText(userAnswer1);
                break;


            case R.id.button7:

                if (userAnswer1.contains(correctAnswer1)) { //handles event when the user gives correct answer
                    Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                    levelNumber = 2;

                    shuffle2(btn, btn2, btn3, btn4, btn5, btn6);

                    btn.setEnabled(true);
                    btn2.setEnabled(true);
                    btn3.setEnabled(true);
                    btn4.setEnabled(true);
                    btn5.setEnabled(true);
                    btn6.setEnabled(true);

                    textView1.setText(userAnswer2);

                } else {                                   //handles event when the user gives incorrect answer
                    Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button8:
                Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                userAnswer1 = "";
                textView1.setText(userAnswer1);

                //Button btnOne = findViewById(R.id.button1);
                btn.setEnabled(true);

                //Button btnTwo = findViewById(R.id.button2);
                btn2.setEnabled(true);

                //Button btnThree = findViewById(R.id.button3);
                btn3.setEnabled(true);

                //Button btnFour = findViewById(R.id.button4);
                btn4.setEnabled(true);

                //Button btnFive = findViewById(R.id.button5);
                btn5.setEnabled(true);

                shuffle1(btn, btn2, btn3, btn4, btn5, btn6);

                break;

        }
        }


        else if(levelNumber == 2)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer2 = userAnswer2 + " " + btn.getText().toString();
                    textView1.setText(userAnswer2);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer2 = userAnswer2 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer2);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer2 = userAnswer2 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer2);
                    break;

                case R.id.button4:
                   // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer2 = userAnswer2 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer2);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer2 = userAnswer2 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer2);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer2 = userAnswer2 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer2);
                    break;


                case R.id.button7:

                    if (userAnswer2.contains(correctAnswer2)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 3;

                        shuffle3(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        textView1.setText(userAnswer3);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer2 = "";
                    textView1.setText(userAnswer2);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle2(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 3)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer3 = userAnswer3 + " " + btn.getText().toString();
                    textView1.setText(userAnswer3);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer3 = userAnswer3 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer3);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer3 = userAnswer3 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer3);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer3 = userAnswer3 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer3);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer3 = userAnswer3 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer3);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer3 = userAnswer3 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer3);
                    break;


                case R.id.button7:

                    if (userAnswer3.contains(correctAnswer3)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 4;

                        shuffle4(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        btn4.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer4);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer3 = "";
                    textView1.setText(userAnswer3);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle3(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 4)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer4 = userAnswer4 + " " + btn.getText().toString();
                    textView1.setText(userAnswer4);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer4 = userAnswer4 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer4);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer4 = userAnswer4 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer4);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer4 = userAnswer4 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer4);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer4 = userAnswer4 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer4);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer4 = userAnswer4 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer4);
                    break;


                case R.id.button7:

                    if (userAnswer4.contains(correctAnswer4)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 5;

                        shuffle5(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        //btn4.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer5);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer4 = "";
                    textView1.setText(userAnswer4);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle4(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 5)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer5 = userAnswer5 + " " + btn.getText().toString();
                    textView1.setText(userAnswer5);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer5 = userAnswer5 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer5);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer5 = userAnswer5 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer5);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer5 = userAnswer5 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer5);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer5 = userAnswer5 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer5);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer5 = userAnswer5 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer5);
                    break;


                case R.id.button7:

                    if (userAnswer5.contains(correctAnswer5)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 6;

                        shuffle6(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        //btn4.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer6);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer5 = "";
                    textView1.setText(userAnswer5);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle5(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 6)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer6 = userAnswer6 + " " + btn.getText().toString();
                    textView1.setText(userAnswer6);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer6 = userAnswer6 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer6);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer6 = userAnswer6 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer6);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer6 = userAnswer6 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer6);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer6 = userAnswer6 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer6);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer6 = userAnswer6 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer6);
                    break;


                case R.id.button7:

                    if (userAnswer6.contains(correctAnswer6)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 7;

                        shuffle7(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        btn5.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer7);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer6 = "";
                    textView1.setText(userAnswer6);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle6(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 7)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer7 = userAnswer7 + " " + btn.getText().toString();
                    textView1.setText(userAnswer7);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer7 = userAnswer7 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer7);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer7 = userAnswer7 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer7);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer7 = userAnswer7 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer7);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer7 = userAnswer7 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer7);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer7 = userAnswer7 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer7);
                    break;


                case R.id.button7:

                    if (userAnswer7.contains(correctAnswer7)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 8;

                        shuffle8(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        //btn5.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer8);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer7 = "";
                    textView1.setText(userAnswer7);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle7(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 8)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer8 = userAnswer8 + " " + btn.getText().toString();
                    textView1.setText(userAnswer8);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer8 = userAnswer8 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer8);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer8 = userAnswer8 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer8);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer8 = userAnswer8 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer8);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer8 = userAnswer8 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer8);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer8 = userAnswer8 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer8);
                    break;


                case R.id.button7:

                    if (userAnswer8.contains(correctAnswer8)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 9;

                        shuffle9(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        //btn4.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer9);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer8 = "";
                    textView1.setText(userAnswer8);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle8(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 9)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer9 = userAnswer9 + " " + btn.getText().toString();
                    textView1.setText(userAnswer9);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer9 = userAnswer9 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer9);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer9 = userAnswer9 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer9);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer9 = userAnswer9 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer9);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer9 = userAnswer9 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer9);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer9 = userAnswer9 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer9);
                    break;


                case R.id.button7:

                    if (userAnswer9.contains(correctAnswer9)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 10;

                        shuffle10(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        btn6.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer10);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer9 = "";
                    textView1.setText(userAnswer9);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    shuffle9(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if (levelNumber == 10)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer10 = userAnswer10 + " " + btn.getText().toString();
                    textView1.setText(userAnswer10);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer10 = userAnswer10 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer10);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer10 = userAnswer10 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer10);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer10 = userAnswer10 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer10);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer10 = userAnswer10 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer10);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer10 = userAnswer10 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer10);
                    break;


                case R.id.button7:

                    if (userAnswer10.contains(correctAnswer10)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 11;

                        shuffle11(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        //btn6.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer11);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer10 = "";
                    textView1.setText(userAnswer10);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    btn5.setEnabled(true);

                    btn6.setEnabled(true);

                    shuffle10(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 11)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer11 = userAnswer11 + " " + btn.getText().toString();
                    textView1.setText(userAnswer11);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer11 = userAnswer11 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer11);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer11 = userAnswer11 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer11);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer11 = userAnswer11 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer11);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer11 = userAnswer11 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer11);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer11 = userAnswer11 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer11);
                    break;


                case R.id.button7:

                    if (userAnswer11.contains(correctAnswer11)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "Level Complete! Try this next Level", Toast.LENGTH_SHORT).show();
                        levelNumber = 12;

                        shuffle12(btn, btn2, btn3, btn4, btn5, btn6);

                        btn.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        btn6.setEnabled(true);

                        //btn6.setVisibility(View.VISIBLE);

                        textView1.setText(userAnswer12);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer11 = "";
                    textView1.setText(userAnswer11);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    btn6.setEnabled(true);

                    shuffle11(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

        else if(levelNumber == 12)
        {
            TextView textView1 = findViewById(R.id.textView);
            switch (v.getId()) {
                case R.id.button1:
                    //Button btn = findViewById(R.id.button1);
                    btn.setEnabled(false);

                    userAnswer12 = userAnswer12 + " " + btn.getText().toString();
                    textView1.setText(userAnswer12);
                    break;

                case R.id.button2:
                    //Button btn2 = findViewById(R.id.button2);
                    btn2.setEnabled(false);

                    userAnswer12 = userAnswer12 + " " + btn2.getText().toString();
                    textView1.setText(userAnswer12);


                    break;

                case R.id.button3:
                    //Button btn3 = findViewById(R.id.button3);
                    btn3.setEnabled(false);

                    userAnswer12 = userAnswer12 + " " + btn3.getText().toString();
                    textView1.setText(userAnswer12);
                    break;

                case R.id.button4:
                    // Button btn4 = findViewById(R.id.button4);
                    btn4.setEnabled(false);

                    userAnswer12 = userAnswer12 + " " + btn4.getText().toString();
                    textView1.setText(userAnswer12);
                    break;

                case R.id.button5:

                    //Button btn5 = findViewById(R.id.button5);
                    btn5.setEnabled(false);

                    userAnswer12 = userAnswer12 + " " + btn5.getText().toString();
                    textView1.setText(userAnswer12);
                    break;

                case R.id.button6:

                    //Button btn6 = findViewById(R.id.button6);
                    btn6.setEnabled(false);

                    userAnswer12 = userAnswer12 + " " + btn6.getText().toString();
                    textView1.setText(userAnswer12);
                    break;


                case R.id.button7:

                    if (userAnswer12.contains(correctAnswer12)) { //handles event when the user gives correct answer
                        Toast.makeText(this, "All Levels Complete! Congratulations!", Toast.LENGTH_SHORT).show();
                        levelNumber = 12;

                        //shuffle12(btn, btn2, btn3, btn4, btn5, btn6);

                        //btn.setEnabled(true);
                        //btn2.setEnabled(true);
                        //btn3.setEnabled(true);
                        //btn4.setEnabled(true);
                        //btn5.setEnabled(true);
                        //btn6.setEnabled(true);

                        //btn6.setVisibility(View.VISIBLE);

                        btn8.setVisibility(View.INVISIBLE);


                        textView1.setText(userAnswer12);
                    } else {                                   //handles event when the user gives incorrect answer
                        Toast.makeText(this, "Incorrect. Use all available words or click RESTART to try again", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button8:
                    Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
                    userAnswer12 = "";
                    textView1.setText(userAnswer12);

                    //Button btnOne = findViewById(R.id.button1);
                    btn.setEnabled(true);

                    //Button btnTwo = findViewById(R.id.button2);
                    btn2.setEnabled(true);

                    //Button btnThree = findViewById(R.id.button3);
                    btn3.setEnabled(true);

                    //Button btnFour = findViewById(R.id.button4);
                    btn4.setEnabled(true);

                    //Button btnFive = findViewById(R.id.button5);
                    btn5.setEnabled(true);

                    btn6.setEnabled(true);

                    shuffle12(btn, btn2, btn3, btn4, btn5, btn6);

                    break;

            }
        }

    }
    //click to get back to home fragment
    private void btnBackClicked()
    {
        final Fragment homeFragment = new HomeFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, homeFragment)
                        .commit();
            }
        });
    }
}