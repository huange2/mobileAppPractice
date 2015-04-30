package com.example.jiale.riddlegame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Handler;

/**
 * RiddleGameMain - main activity of the game
 *
 */
public class RiddleGameMain extends ActionBarActivity {
    //intialize game variables
    int score = 0;
    int wordIndex = 0;
    int index = 0;
    int hint = 3;
    String displayWord = "";
    boolean gameStatus = true;
    Hashtable riddles = new Hashtable();
    int[] hangmanArray = {R.drawable.background,R.drawable.hangman0, R.drawable.hangman1, R.drawable.hangman2,
            R.drawable.hangman3, R.drawable.hangman4,R.drawable.hangman5};
    String[] wordArray = {"man", "promise", "secret", "incorrectly", "palm", "mushroom", "watermelon", "meat", "towel", "stamp"};

    /**
     * Sets up the display word string
     * @return
     */
    private boolean pickWord(){
        int length = wordArray.length;
        if(wordIndex < length) {
            // Intialize displayword
            char[] chars = new char[wordArray[wordIndex].length()];
            Arrays.fill(chars, '$');
            displayWord = new String(chars);
        }
        else {
            gameStatus = false;
        }
        return gameStatus;
    }

    /**
     * Sets up the game
     */
    protected void initRiddles() {
        TextView scoreView = (TextView) findViewById(R.id.score);
        RelativeLayout gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
        TextView questionView = (TextView) findViewById(R.id.question);
        TextView answerView = (TextView) findViewById(R.id.answer);
        riddles.put("man", "Which creature walks on four legs in the morning, two legs in the afternoon, and three legs in the evening?\n ");
        riddles.put("promise", "What gets broken without being held? ");
        riddles.put("secret", "If you have me, you want to share me. If you share me, you haven't got me. What am I?\n");
        riddles.put("incorrectly", "Which word in the dictionary is spelled incorrectly?");
        riddles.put("palm", "What kind of tree can you carry in your hand? ");
        riddles.put("mushroom", "What kind of room has no doors or windows?  ");
        riddles.put("watermelon", "There was a green house. Inside the green house there was a white house. Inside the white house there was a red house. Inside the red house there were lots of babies. What is it?  "
                );
        riddles.put("meat","Paul's height is six feet, he's an assistant at a butcher's shop, and wears size 9 shoes. What does he weigh?\n" );
        riddles.put("towel","What gets wetter and wetter the more it dries? ");
        riddles.put("stamp", "What can travel around the world while staying in a corner?\n ");
        hint = 3;
        score = 0;
        index = 0;
        wordIndex = 0;
        GridLayout parent = (GridLayout) findViewById(R.id.gridLayout);
        int length = parent.getChildCount();
        // reset button colors
        for (int i = 0; i < length; i++) {
            Button alphaButton = (Button) parent.getChildAt(i);
            alphaButton.setBackgroundColor(Color.WHITE);
        }
        scoreView.setText(" Your score is " + score + ".");
        gameLayout.setBackgroundResource(hangmanArray[index]);
        pickWord();
        questionView.setText((String) riddles.get(wordArray[wordIndex]));
        answerView.setText(displayWord);
    }

    /**
     * resets the screen
     */
    public void reset() {
        TextView scoreView = (TextView) findViewById(R.id.score);
        TextView questionView = (TextView) findViewById(R.id.question);
        TextView answerView = (TextView) findViewById(R.id.answer);
        wordIndex++;
        CharSequence text = "";
        int duration = Toast.LENGTH_LONG;
        boolean game = pickWord();
        if(game && gameStatus) {
            score++;
            scoreView.setText("Your score is " + score + ".");
            GridLayout parent = (GridLayout) findViewById(R.id.gridLayout);
            int length = parent.getChildCount();
            text = "On to the next riddle....";
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            // reset button colors
            for (int i = 0; i < length; i++) {
                Button alphaButton = (Button) parent.getChildAt(i);
                alphaButton.setBackgroundColor(Color.WHITE);
            }
            pickWord();
            questionView.setText((String) riddles.get(wordArray[wordIndex]));
            answerView.setText(displayWord);
        }
        else {
            text = "Your Riddle master ranking...";
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            Intent intent = new Intent(this, RiddleResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Give up event handler
     * @param view
     */
    public void giveUp(View view) {
        String text = "Your Riddle master ranking...";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, RiddleResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    /**
     * Game logic

     */
    public void buttonClicked(View view) {
        //intialize view variables so I can manipulate them
        Button alphaChoice = (Button) view;
        boolean alphaExist = false;
        int length = displayWord.length();
        String newDisplayWord = "";
        RelativeLayout gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
        TextView score = (TextView) findViewById(R.id.score);
        TextView answerView = (TextView) findViewById(R.id.answer);
        String alphaChar = (String) alphaChoice.getText();
        ColorDrawable buttonColor = (ColorDrawable) alphaChoice.getBackground();
        int colorId = buttonColor.getColor();
        // If game is not over check letter
        if (gameStatus) {
            //If button has not yet been click process it
            if (colorId == Color.WHITE) {
                // replace char with the same char as letter clicked
                for (int i = 0; i < length; i++) {
                    if (wordArray[wordIndex].charAt(i) == alphaChar.charAt(0)) {
                        newDisplayWord = newDisplayWord + alphaChar;
                        alphaExist = true;
                    }
                    else {
                        newDisplayWord = newDisplayWord + displayWord.charAt(i);
                    }
                }
                //Determine if word has letter chosen update status
                if (alphaExist) {
                    alphaChoice.setBackgroundColor(Color.GREEN);
                    displayWord = newDisplayWord;
                    answerView.setText(displayWord);
                    if (!newDisplayWord.contains("$")) {
                        reset();
                    }
                }
                else {
                    alphaChoice.setBackgroundColor(Color.RED);
                    if (index < 5) {
                        if (!displayWord.contains("$")) {
                            reset();
                        }
                        else {
                            index = index+ 1;
                            gameLayout.setBackgroundResource(hangmanArray[index]);
                        }
                    }
                    else {
                        if (!displayWord.contains("$")) {
                            reset();
                        }
                        else {
                            index = index + 1;
                            gameLayout.setBackgroundResource(hangmanArray[index]);
                            wordIndex = 10;
                            gameStatus= false;
                            reset();
                        }
                    }
                }
            }
        }
        else {
            reset();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle_game_main);;
        initRiddles();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_riddle_game2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
