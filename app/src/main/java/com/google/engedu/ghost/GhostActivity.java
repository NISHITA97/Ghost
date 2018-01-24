/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    boolean music;
    MediaPlayer ring;
    private boolean userTurn = false;
    private Random random = new Random();
    public int userWin=0,compWin=0;
    String wordFragment="",currentWord="";
    TextView text,label,score;
    Button challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
//        AssetManager assetManager = getAssets();
        try{
            InputStream dictionaryWords=getAssets().open("words.txt");
            dictionary=new FastDictionary(dictionaryWords);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        ring= MediaPlayer.create(GhostActivity.this,R.raw.shapeofyou);
        music=false;
        challenge=(Button)findViewById(R.id.challenge);
        text=(TextView)findViewById(R.id.ghostText);
        label=(TextView) findViewById(R.id.gameStatus);
        score=(TextView)findViewById(R.id.score);
       /* SharedPreferences sharedPref = getSharedPreferences("mypref",0);
        //getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(sharedPref.contains(getString(R.string.user_score)))
        {
            int defaultValue = getResources().getInteger(R.string.user_score);
            long highScore = sharedPref.getInt(getString(R.string.user_score), defaultValue);
            userWin+=sharedPref.getInt(getString(R.string.user_score));
            userWin=getResources().geti
        }*/
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn=true;
        // userTurn = random.nextBoolean();
        text.setText("");

        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            Log.e("turn","comp");
            computerTurn();

        }
        return true;
    }

    private void computerTurn() {
        //TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        //wordFragment=text.getText().toString();
        Log.e("Word Fragment", wordFragment);
        if(wordFragment.length()>=4 && dictionary.isWord((wordFragment)))
        {
            label.setText("Computer Win !! :)");
            challenge.setEnabled(false);

            compWin++;
            Log.e("Word in Dict","Comp win");

        }
        else
        {
            String wordFromDictionary=null;
            Log.e("Word from dictionary", "nothing");
            //wordFromDictionary= dictionary.getAnyWordStartingWith(wordFragment);
            wordFromDictionary=dictionary.getGoodWordStartingWith(wordFragment);
            //Log.e("Word from dictionary", wordFromDictionary);
            if(wordFromDictionary!=null) {
                wordFragment = wordFromDictionary.substring(0, wordFragment.length() + 1);
                text.setText(wordFragment);
                currentWord = wordFragment;
                userTurn = true;
            }
            else
            {

                Log.e("no word","wordFromDictionary");
                label.setText("No word can be formed from this prefix");
                text.setText("Match Draw...");
                challenge.setEnabled(false);
                //userTurn = true;
            }
        }
        score.setText(" UserWin = "+userWin+" \n CompWin = "+compWin);
        /*SharedPreferences sharedPref = getSharedPreferences("mypref",0);
        //getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.user_score), userWin);
        editor.putInt(getString(R.string.user_score), userWin);
        editor.commit();*/
        //userTurn = true;
        //label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        if(keyCode>=29 && keyCode<=54)
        {
            char c=(char) event.getUnicodeChar();
            wordFragment=(String) text.getText();
            wordFragment=currentWord + Character.toString(c);
            text.setText(wordFragment);
            computerTurn();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //challenge is only pressed by user, not computerTurn()
    public void challenge(View view) {
        currentWord=text.getText().toString();
        if(dictionary.isWord(currentWord))
        {
            if(userTurn) {
                label.setText("user win");
                userWin++;
            }
            else {
                label.setText("comp win!!");
                compWin++;
            }
            challenge.setEnabled(false);
        }
        else
        {
            label.setText("compwin!!");
            compWin++;
            challenge.setEnabled(false);
        }
        score.setText(" UserWin = "+userWin+" \n CompWin = "+compWin);
        /*SharedPreferences sharedPref = getSharedPreferences("mypref",0);
        //getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.user_score), userWin);
        editor.putInt(getString(R.string.user_score), userWin);
        editor.commit();*/
    }

    public void reset(View view) {
        userTurn=random.nextBoolean();
        text.setText("");
        wordFragment="";
        currentWord="";
        challenge.setEnabled(true);
        if(userTurn)
        {
              label.setText(USER_TURN);
        }
        else
        {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        score.setText(" UserWin = "+userWin+" \n CompWin = "+compWin);
        /*SharedPreferences sharedPref = getSharedPreferences("mypref",0);
        //getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.user_score), userWin);
        editor.putInt(getString(R.string.user_score), userWin);
        editor.commit();*/
    }

    public void Play(View view) {


        if(!music)
        {

            ring.start();
        }
        else
            ring.pause();
        music=!music;
    }
}
