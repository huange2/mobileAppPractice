/**
 * Name: Erica Huang
 * Date: 4-19-15
 * Assignment 2
 * Overview: Hangman Game
 *      Basic Controls
 *      reset = restarts game
 *      forfeit = displays answers
 *      Choose words from the list.
 *      red - word is wrong
 *      green - word is right
 *
 */
package com.example.jiale.hangman;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;


public class HangmanActivity extends ActionBarActivity {

    // Global Variables for the game
    int[] hangmanArray = {R.drawable.hangman0,R.drawable.hangman1, R.drawable.hangman2, R.drawable.hangman3,
                            R.drawable.hangman4, R.drawable.hangman5,R.drawable.hangman6};
    int guesses = 6;
    String hangmanWord = "";
    String displayWord = "";
    boolean gameStatus = true;

    /**
     * pickWord - Randomly chooses the word out of the dictionary
     * and sets globals
     * @return
     */
    private String pickWord(){
        String[] dictionary = getResources().getStringArray(R.array.words);
        int length = dictionary.length;
        Random generator = new Random();
        int index = generator.nextInt(length);
        // Intialize displayword
        char[] chars = new char[dictionary[index].length()];
        Arrays.fill(chars, '$');
        displayWord = new String(chars);

        return dictionary[index];
    }

    /**
     * reset - reset game features including button colors, # guesses, hangman picture and
     *        randomly chooses another word
     * @param view
     */
    public void reset(View view) {
        hangmanWord = pickWord();
        guesses = 6;
        gameStatus = true;
        ImageView hangmanPicture = (ImageView) findViewById(R.id.imageView);
        hangmanPicture.setImageResource(hangmanArray[guesses]);
        TextView hangmanNote = (TextView) findViewById(R.id.Guesses);
        hangmanNote.setText("You have 6 guesses left");
        TextView hangmanDisplay = (TextView) findViewById(R.id.textView2);
        hangmanDisplay.setText(displayWord);
        GridLayout parent = (GridLayout) findViewById(R.id.gridLayout);
        int length = parent.getChildCount();
        // reset button colors
        for(int i = 0; i < length; i++) {
            Button alphaButton = (Button) parent.getChildAt(i);
            alphaButton.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * getAnswer - action when FORFEIT  is clicked.
     *           displays the word and score status
     * @param view
     */
    public void getAnswer(View view) {
        displayWord = hangmanWord;
        TextView hangmanDisplay = (TextView) findViewById(R.id.textView2);
        hangmanDisplay.setText(displayWord);
        TextView hangmanNote = (TextView) findViewById(R.id.Guesses);
        hangmanNote.setText("You gave up the word was " + displayWord);
        gameStatus=false;
    }

    /**
     * buttonClicked - when a user chooses a letter
     *                 this is where the game logic is
     *                 if letter is right update display word and
     *                 button color, else make button red.
     *                 Also determine if user won the game or not
     * @param view
     */
   public void buttonClicked(View view) {
       //intialize view variables so I can manipulate them
       Button alphaChoice = (Button) view;
       boolean alphaExist = false;
       int length = hangmanWord.length();
       String newDisplayWord = "";
       ImageView hangmanPic = (ImageView) findViewById(R.id.imageView);
       TextView hangmanNote = (TextView) findViewById(R.id.Guesses);
       TextView hangmanDisplay = (TextView) findViewById(R.id.textView2);
       String alphaChar = (String) alphaChoice.getText();
       ColorDrawable buttonColor = (ColorDrawable) alphaChoice.getBackground();
       int colorId = buttonColor.getColor();
       // If game is not over check letter
       if (gameStatus) {
           //If button has not yet been click process it
           if (colorId == Color.WHITE) {
               // replace char with the same char as letter clicked
               for (int i = 0; i < length; i++) {
                   if (hangmanWord.charAt(i) == alphaChar.charAt(0)) {
                       newDisplayWord = newDisplayWord + alphaChar;
                       alphaExist = true;
                   }
                   else {
                       newDisplayWord = newDisplayWord + displayWord.charAt(i);
                   }
               }
               //Determine if word has letter chosen update status
               if (alphaExist) {
                   if (!newDisplayWord.contains("$")) {
                       hangmanNote.setText("You Won. Hit reset to play again.");
                       gameStatus = false;
                   }
                   else {
                       hangmanNote.setText("You have " + guesses + " left.");
                   }
                   alphaChoice.setBackgroundColor(Color.GREEN);
                   displayWord = newDisplayWord;
                   hangmanDisplay.setText(displayWord);
               }
               else {
                   alphaChoice.setBackgroundColor(Color.RED);
                   if (guesses > 1) {
                       if (!displayWord.contains("$")) {
                           hangmanNote.setText("You Won. Hit reset to play again.");
                           gameStatus = false;
                       }
                       else {
                           guesses = guesses - 1;
                           hangmanPic.setImageResource(hangmanArray[guesses]);
                           hangmanNote.setText("You have " + guesses + " left.");
                       }
                   }
                   else {
                       if (!displayWord.contains("$")) {
                           hangmanNote.setText("You Won. Hit reset to play again.");
                       }
                       else {
                           hangmanNote.setText("You LOSE");
                           guesses = guesses - 1;
                           hangmanPic.setImageResource(hangmanArray[guesses]);
                           gameStatus = false;
                       }
                   }
               }
           }
       } else {
           hangmanNote.setText("Please hit the reset button to restart game");
       }
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        reset(findViewById(R.id.reset)); // intialize game
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hangman, menu);
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
