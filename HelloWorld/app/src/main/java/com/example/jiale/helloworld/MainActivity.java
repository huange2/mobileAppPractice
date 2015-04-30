package com.example.jiale.helloworld;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/*
 * CSCI 412, Erica Huang
 * Rock Paper Scissor's game
 * Assignment 1
 * Date: 4/8/15
 * Overview: A simple game of rock paper scissor against computer.
 */
public class MainActivity extends ActionBarActivity {
    private int points = 0;
    private int compPoints = 0;

    /*
     * Event handler for Rock button
     */
    public void rock_click( View view) {
        Button tv = (Button) findViewById(R.id.rock);
        randomChoice(R.id.rock);
    }

    /*
     * Event handler for Paper button
     */
    public void paper_click( View view) {
        Button tv = (Button) findViewById(R.id.paper);
        randomChoice(R.id.paper);

    }

    /*
     * Event handler for Scissor button
     */
    public void scissor_click( View view) {
        Button tv = (Button) findViewById(R.id.scissor);
        randomChoice(R.id.scissor);

    }

    /*
     * Game Logic for Rock Paper Scissor
     * Computer randomly chooses rock paper scissor and
     * compares choice with user.
     */
    public void randomChoice(int id) {
        Button choice = (Button) findViewById(id); // id of button user choose
        TextView score = (TextView) findViewById(R.id.score); // Scoreboard
        String[] random = {"Scissor", "Paper", "Rock"};
        Random generator = new Random();
        int index = generator.nextInt(3);

        // Logic for the game
        if(random[index].equals(choice.getText())){
            score.setText("Computer's Choice: " + random[index] + "\nYour choice: " + choice.getText() + "\nTie.No one wins.... \n Your score is: " + points +
                    "\nComputer's score is: " + compPoints);
        }
        else if(random[index].equals("Scissor") && choice.getText().equals("Rock")) {
            points++;
            score.setText("Computer's Choice: " + random[index] +  "\nYour choice: " + choice.getText() + "\nYou wins this round.. \n Your score is: " + points +
                    "\nComputer's score is: " + compPoints);
        }
        else if(random[index].equals("Paper") && choice.getText().equals("Scissor")) {
            points++;
            score.setText("Computer's Choice: " + random[index] + "\nYour choice: " + choice.getText() + "\nYou wins this round.. \n Your score is: " + points +
                    "\nComputer's score is: " + compPoints);
        }
        else if(random[index].equals("Rock") && choice.getText().equals("Paper")) {
            points++;
            score.setText("Computer's Choice: " + random[index] + "\nYour choice: " + choice.getText() +"\nYou wins this round.. \n Your score is: " + points +
                    "\nComputer's score is: " + compPoints);
        }
        else {
            compPoints++;
            score.setText("Computer's Choice: " + random[index] + "\nYour choice: " + choice.getText() +"\nHa Ha! Computer wins this round.\n Your score is: " + points +
                    "\nComputer's score is: " + compPoints);
        }

    }

    /*
     * Android Studio default functions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView score = (TextView) findViewById(R.id.score);
        score.setText("Your score is: "+ points); // Intialize scoreboard
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
