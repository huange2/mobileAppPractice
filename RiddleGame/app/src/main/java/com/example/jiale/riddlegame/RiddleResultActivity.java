package com.example.jiale.riddlegame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RiddleResultActivity = Display user ranking in game
 */
public class RiddleResultActivity extends ActionBarActivity {

    int score = 0; //Obtain from previous activity

    /**
     * Set ranking picture
     */
    protected  void setRanking() {
        ImageView rankImage = (ImageView) findViewById(R.id.resultImage);
        TextView rankText = (TextView) findViewById(R.id.result);
       if(score > 6 ){
           rankImage.setImageResource(R.drawable.gold);
           rankText.setText("You are a true riddle master.");
       }
        else if (score > 3){
           rankImage.setImageResource(R.drawable.silver);
           rankText.setText("You are an average riddle master.");
       }
        else {
           rankImage.setImageResource(R.drawable.poop);
           rankText.setText("You are what you see");
       }
    }
    public void restart(View view){
        Intent intent = new Intent(this, RiddleStartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle_result);
        Intent prevInt = getIntent();
        score = prevInt.getIntExtra("score", 8);
        setRanking();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_riddle_result, menu);
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
