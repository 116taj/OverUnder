package com.example.randhawaoverunder;

import android.widget.ImageView;
import android.widget.TextView;

//card object
public class Card {
    //original = original guess player/ai will input later
    //prompt is the question to be answered
    //answer is the real answer
    private int original;
    private String prompt;
    private int answer;

    public Card(int card) {
        //constructor that is looped in Main to add to deck
        String pro[] = {"The population of brampton.",
                "Maximum value of a signed 32-bit integer.",
                "y value of the function y = x^4, x = 13.",
                "Year the US Civil War ended.",
                "The price of an RTX 3080 GPU in USD.",
                "Standard game framerate.",
                "Length of the US-Canada border.",
                "Number of countries in the British Commonwealth.",
                "Number of states in the US during 1812.",
                "Amount of ricin to kill a person (mg/kg).",
                "Amount of bones in an adult human body.",
                "Number of countries Germany shares a border with.",
                "Population of Los Angeles.",
                "Amount of mL in a 12 pack of soda cans.",
                "If a=1,b=2,etc. The value of (O)(V)(E)(R).",
                "If a=26,b=25,etc. The value of (U)(N)(D)(E)(R).",
                "The amount of water in an olympic swimming pool (L)",
                "Number of people that watched the 2020 super bowl.",
                "Year that the programming languaging Java was invented.",
                "y value of the function 2^x*x^2, x = 10.",
                "The first 5 digits of PI squared.",
                "Number of asteroids in the asteroid belt (minimum estimation).",
                "Number of stores in the Square One shopping centre.",
                "Peak of bitcoin price to CAD.",
                "The next century year that is a leap year.",
                "Time in minutes to drive from Miami, Florida to Brampton, Ontario.",
                "Time in seconds to walk from Brampton Centennial SS to the nearest Service Ontario.",
                "Acceptable ping (ms) in an online video game.",
                "Peak viewers of the CS:GO tournament Blast Global Finals 2020.",
                "The product of the longitude and latitude values of Brampton.",
                "Sum of Jupiter and Saturn's moons.",
                "Day length on Mars in minutes.",
                "Amount of bits 16MB could hold.",
                "If a=1,b=2,c=3,etc. The value of A*B*C*D*E*F*G.",
                "Unread emails of the average person.",
                "Average of weight of a male american.",
                "Year Pearl Harbor was attacked.",
                "Year when WWII ended.",
                "Year that the cold war ended.",
                "Number of US States in 1776.",
                "Year that the Spanish Armada was defeated.",
                "How many minutes of 720p video a GB can hold.",
                "Max value of 8 bits.",
                "Population of NYC.",
                "Number of minutes that have passed in the day at 11:53 AM.",
                "Number of countries that Russia borders.",
                "Value of y in the function y = abc + xa + bc (a = 3, b = 19, c = 6, x = abc).",
                "The 401 exit to Mississauga Road.",
                "The 401 exit to the 402.",
                "The amount of people that died in the 9/11 terrorist attacks. :(",
                "Amount of days that passed from Dec 31, 1999 to Jan 1, 2021.",
                "If a=1,b=2,c=3,d=a,etc. The value of (E)(Q)(U)(A)(L).",
                "My current overall average in all computer science courses I've taken.",
                "Number of permutations of the word PROGRAMMING.",
                "Day length of Venus in minutes.",
                "Value of x: 12x = 46656012",
                "Number of US States in 1900.",
                "Population of France.",
                "Year Napoleon was defeated.",
                "Time in minutes to drive to the University of Waterloo from Brampton Centennial."};
        int ans[] = {2147483647,
                603346,
                28561,
                1865,
                699,
                60,
                8891,
                54,
                18,
                1,
                206,
                9,
                3967000,
                4260,
                29700,
                355212,
                2500000,
                102000000,
                1995,
                102400,
                98696,
                1100000,
                360,
                51121,
                2400,
                1302,
                1920,
                40,
                687691,
                3482,
                161,
                1477,
                128000000,
                5040,
                500,
                198,
                1941,
                1945,
                1991,
                13,
                1588,
                210,
                255,
                8419000,
                713,
                14,
                1482,
                336,
                183,
                2977,
                7670,
                36,
                97,
                39916800,
                168120,
                3888001,
                45,
                67060000,
                1815, 59};
        prompt = pro[card];
        answer = ans[card];
    }

    //additional constructor in case it was needed
    public Card(int o, String p, int a) {
        original = o;
        prompt = p;
        answer = a;
    }

    //accessors
    public String getPrompt() {
        return prompt;
    }

    public int getOriginal() {
        return original;
    }

    public int getAnswer() {
        return answer;
    }

    //mutators
    public void setPrompt(String p) {
        prompt = p;
    }

    public void setAnswer(int a) {
        answer = a;
    }

    public void setOriginal(int o) {
        original = o;
    }

    //facilitators, never used
    public boolean equals(Card c) {
        if (this.equals(c))
            return true;
        return false;
    }

    public int compareTo(Card c) {
        return 0;
    }

    //setText of card
    public void setText(TextView text) {
        text.setText(prompt + "\n\nGuess: " + original);
    }

    //set the text on the other side, and determines points
    public int setBacktext(TextView text, char overunder) {
        String win = "\n\n\nIncorrect! The answer is ";
        if (original == answer)
            win = "\n\n\nThe guesser was on point! The answer is ";
        else if (original > answer && overunder == 'u')
            win = "\n\n\nCorrect! The answer is ";
        else if (answer > original && overunder == 'o')
            win = "\n\n\nCorrect! The answer is ";
        text.setText(win + answer);
        if (win.equals("\n\n\nThe guesser was on point! The answer is "))
            return 1;
        else if (win.equals("\n\n\nCorrect! The answer is "))
            return 0;
        return -1;
    }

    //update image based on result
    public void setSide(ImageView i) {
        if (original > answer)
            i.setImageResource(R.drawable.undercard);
        else
            i.setImageResource(R.drawable.overcard);
    }
}
