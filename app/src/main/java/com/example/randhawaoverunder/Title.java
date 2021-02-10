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

//unlike all other projects, there are no themes/grid sizes/ copy pasted code.
//please read all files
//basic title screen, goes to settings for game and instructions
public class Title extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
    }

    public void play(View view) {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    public void instruct(View view) {
        Intent i = new Intent(this, Instructions.class);
        startActivity(i);
    }
}