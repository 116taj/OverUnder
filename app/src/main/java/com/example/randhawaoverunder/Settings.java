package com.example.randhawaoverunder;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Settings extends AppCompatActivity {
    //remove sets id's for layouts as to not make too many of them. remove2 tracks the previous value of remove
    int remove = 0;
    int remove2 = 0;
    //nameid tracks id's of edittexts for files
    int nameid = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create files
        File count = new File(getFilesDir(), "playercount.txt");
        File names = new File(getFilesDir(), "player.txt");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //adding a listener for text change to dynamically change number of edittexts visible
        EditText playercount = (EditText) findViewById(R.id.playercount);
        playercount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //if a layout was already made, delete it
                if (remove != remove2) {
                    LinearLayout delete = (LinearLayout) findViewById(remove2);
                    delete.removeAllViews();
                    remove2++;
                }
                String x = playercount.getText().toString();
                if (!x.equals("")) {
                    int players = Integer.parseInt(x);
                    if (4 >= players) {
                        //create layout and edit texts
                        LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);
                        l.setId(remove);
                        remove++;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 670, 0, 100);
                        l.setLayoutParams(params);
                        for (int i = 0; i < players; i++) {
                            EditText e = new EditText(getApplicationContext());
                            e.setHint("Player " + (i + 1) + "'s Name");
                            e.setTextColor(Color.WHITE);
                            e.setHintTextColor(Color.WHITE);
                            e.setLinkTextColor(Color.WHITE);
                            e.setHighlightColor(Color.WHITE);
                            e.setDrawingCacheBackgroundColor(Color.WHITE);
                            e.setId(nameid);
                            nameid++;
                            l.addView(e);
                        }
                        addContentView(l, l.getLayoutParams());
                        //writes player count for queue
                        try {
                            FileOutputStream out = openFileOutput("playercount.txt", MODE_PRIVATE);
                            out.write(players);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Too many players!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //continue button, writes to files and goes to game
    public void game(View view) {
        EditText count = findViewById(R.id.playercount);
        if (!count.getText().toString().equals("")) {
            try {
                FileInputStream in = openFileInput("playercount.txt");
                FileOutputStream out = openFileOutput("player.txt", MODE_PRIVATE);
                int x = in.read();
                EditText[] names = new EditText[x];
                for (int i = 0; i < x; i++) {
                    names[i] = (EditText) findViewById(i + 100);
                    if (!names[i].getText().toString().equals("")) {
                        char c[] = names[i].getText().toString().toCharArray();
                        out.write(c.length);
                        for (int j = 0; j < c.length; j++) {
                            out.write((int) c[j]);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Input names!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                out.flush();
                out.close();
            } catch (IOException e) {

            }
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}