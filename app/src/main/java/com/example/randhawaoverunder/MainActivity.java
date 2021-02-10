package com.example.randhawaoverunder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

//Name: Taj Randhawa
//Date: 29 Jan 2021
//Purpose: To create a basic card game using Stacks and Objects.
public class MainActivity extends AppCompatActivity {
    //deck = stack of cards
    //current = the current card on screen
    //turn = queue of players to see who's turn it is
    //playing = tracks who's turn it is
    //num = number of cards that have been used
    Stack deck = new Stack();
    Card current;
    Queue turn = new Queue();
    Player playing;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing the deck
        for (int i = 0; i < 60; i++) {
            deck.push(new Card(i));
        }
        //gets player names and count from files
        try {
            FileInputStream in = openFileInput("playercount.txt");
            int x = in.read();
            in = openFileInput("player.txt");
            String[] names = new String[x];
            for (int i = 0; i < x; i++) {
                names[i] = "";
                int length = in.read();
                for (int j = 0; j < length; j++) {
                    int d = in.read();
                    char c = (char) d;
                    names[i] += c;
                }
            }
            //queues all players with names and score
            for (int i = 0; i < x; i++) {
                turn.enqueue(new Player(0, names[i], false));
            }
            //if 1 player, not possible to play alone so adds AI
            if (turn.size() == 1) {
                turn.enqueue(new Player(0, "AI", true));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //makes a blank card and shuffles the deck to get random order.
        blank();
        deck.shuffle();
    }

    //resets image, makes card clickable, and over/under invisible. basically gets card ready for next turn
    public void blank() {
        ImageView image = (ImageView) findViewById(R.id.card);
        image.setImageResource(R.drawable.card);
        image.setClickable(true);
        TextView text = (TextView) findViewById(R.id.cardtext);
        Button over = (Button) findViewById(R.id.over);
        Button under = (Button) findViewById(R.id.under);
        over.setVisibility(View.INVISIBLE);
        under.setVisibility(View.INVISIBLE);
        text.setText("");
    }

    //when image is clicked, if less than 31 cards have been used, begin turn otherwise declare game over.
    public void imageclick(View view) {
        if (num < 31) {
            current = deck.pop();
            num++;
            playing = turn.dequeue();
            TextView whosturn = (TextView) findViewById(R.id.turn);
            whosturn.setText(playing.toString());
            showPrompt();
        } else {
            winner();
        }
    }

    //shows the prompt and updates visibility of buttons to get to the third phase
    public void showPrompt() {
        TextView text = (TextView) findViewById(R.id.cardtext);
        Button over = (Button) findViewById(R.id.over);
        Button under = (Button) findViewById(R.id.under);
        ImageView image = (ImageView) findViewById(R.id.card);
        image.setImageResource(R.drawable.card);
        image.setClickable(false);
        //ai turn if ai is next
        if (turn.peek().getAI())
            current.setOriginal(playing.getAIoriginal(current));
            //dialog box turn (2nd phase)
        else
            getoriginal();
        current.setText(text);
        over.setVisibility(View.VISIBLE);
        under.setVisibility(View.VISIBLE);
    }

    //sets choice as under and updates back of card text and updates score
    public void under(View view) {
        TextView text = (TextView) findViewById(R.id.cardtext);
        char c = 'u';
        int correct = current.setBacktext(text, c);
        if (correct == 0)
            playing.correct();
        else if (correct == 1)
            playing.bullseye(turn);
        backcard();
    }

    //sets choice as over and updates back of card text and updates score
    public void over(View view) {
        TextView text = (TextView) findViewById(R.id.cardtext);
        char c = 'o';
        int correct = current.setBacktext(text, c);
        if (correct == 0)
            playing.correct();
        else if (correct == 1)
            playing.bullseye(turn);
        backcard();
    }

    //sets choice as random and updates back of card text and updates score
    public void AIchoice() {
        TextView text = (TextView) findViewById(R.id.cardtext);
        char c = playing.getAIoverunder(current);
        int correct = current.setBacktext(text, c);
        String aians = "";
        if (c == 'o') {
            aians += "The AI guessed over! ";
        } else {
            aians += "The AI guessed under! ";
        }
        text.setText(aians + text.getText());
        if (correct == 0)
            playing.correct();
        else if (correct == 1)
            playing.bullseye(turn);
        backcard();
    }

    //updates back of card and makes it clickable for the next turn
    public void backcard() {
        Button over = (Button) findViewById(R.id.over);
        Button under = (Button) findViewById(R.id.under);
        ImageView image = (ImageView) findViewById(R.id.card);
        over.setVisibility(View.INVISIBLE);
        under.setVisibility(View.INVISIBLE);
        current.setSide(image);
        image.setClickable(true);
        turn.enqueue(playing);
    }

    //dialog box to get human original value
    public void getoriginal() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle(turn.peek().getName() + "'s Original Guess")
                .setCancelable(false)
                .setMessage("Hey " + turn.peek().getName() + "! Guess what you think the answer to this is: " + current.getPrompt());
        EditText ans = new EditText(MainActivity.this);
        ans.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ans.setLayoutParams(linear);
        alert.setView(ans);
        alert.setPositiveButton("Set Guess", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String x = ans.getText().toString();
                if (!x.equals("")) {
                    current.setOriginal(Integer.parseInt(x));
                    if (current.getOriginal() == 0)
                        getoriginal();
                } else {
                    getoriginal();
                    Toast.makeText(getApplicationContext(), "You must input an integer!", Toast.LENGTH_SHORT).show();
                }
                //ai choice only happens after a guess has been made
                if (playing.getAI()) {
                    AIchoice();
                }
            }
        }).show();
    }

    //reset button, resets queue and score of players
    public void reset(View view) {
        Player players[] = new Player[4];
        num = 0;
        ImageView image = (ImageView) findViewById(R.id.card);
        int i = 0;
        if (!image.isClickable()) {
            players[0] = playing;
            players[0].setScore(0);
            i++;
        }
        while (turn.size() > 0) {
            players[i] = turn.dequeue();
            players[i].setScore(0);
            i++;
        }
        int j = 0;
        while (j < i) {
            turn.enqueue(players[j]);
            j++;
        }
        shuffle();
    }

    //shuffles the deck
    public void shuffle() {
        deck.clear();
        for (int i = 0; i < 60; i++) {
            deck.push(new Card(i));
        }
        blank();
        deck.shuffle();
    }

    //creates a scoreboard in a dialog box to easily see who is winning
    public void scoreboard(View view) {
        ImageView image = (ImageView) findViewById(R.id.card);
        Player[] players = new Player[4];
        String message = "";
        int i = 0;
        if (!image.isClickable()) {
            players[0] = playing;
            i++;
        }
        int max = 0;
        Player winning = new Player();
        //find max algorithm
        while (turn.size() > 0) {
            players[i] = turn.dequeue();
            message += players[i].scoreboard() + "\n";
            if (players[i].getScore() >= max) {
                max = players[i].getScore();
                winning = players[i];
            }
            i++;
        }
        message += "The frontrunner is currently " + winning.getName() + " with " + winning.getScore() + " points.";
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Current scores!")
                .setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        int j = 0;
        while (j < i) {
            turn.enqueue(players[j]);
            j++;
        }
    }

    //makes dialog box declaring the winner of the game
    public void winner() {
        ImageView image = (ImageView) findViewById(R.id.card);
        Player[] players = new Player[4];
        int i = 0;
        int max = 0;
        Player winner = new Player();
        if (!image.isClickable()) {
            players[0] = playing;
            i++;
        }
        while (turn.size() > 0) {
            players[i] = turn.dequeue();
            if (players[i].getScore() >= max) {
                max = players[i].getScore();
                winner = players[i];
            }
            i++;
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("The winner!")
                .setMessage("The winner of the game is " + winner.getName() + " with " + winner.getScore() + " points!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
        int j = 0;
        while (j < i) {
            turn.enqueue(players[j]);
            j++;
        }
    }

    //goes to instructions
    public void instruct(View view) {
        Intent i = new Intent(this, Instructions.class);
        startActivity(i);
    }
}