package com.example.randhawaoverunder;

public class Player {
    //score = player's score
    //name = player's name
    //isAI = whether they are an AI or not
    private int score;
    private String name;
    private boolean isAI;

    //default constructor
    public Player() {
        score = 0;
        name = "Player";
        isAI = false;
    }

    //custom constructor, used in onCreate
    public Player(int s, String n, boolean b) {
        score = s;
        name = n;
        isAI = b;
    }

    //mutators
    public void setName(String n) {
        name = n;
    }

    public void setScore(int s) {
        score = s;
    }

    public void setAI(boolean b) {
        isAI = b;
    }

    //accessors
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean getAI() {
        return isAI;
    }

    //facilitators
    public int compareTo(Player p) {
        if (score > p.getScore())
            return 1;
        else if (score == p.getScore())
            return 0;
        else
            return -1;
    }

    //score methods, to update the scores.
    //bullseye gives the points to the next player
    public void bullseye(Queue deck) {
        Player p = deck.peek();
        p.setScore(p.getScore() + 3);
    }

    public void correct() {
        score++;
    }

    //toString for textview at top of screen
    public String toString() {
        return "It's " + name + "'s turn, they have " + score + " points.";
    }

    //toString for scoreboard
    public String scoreboard() {
        return name + " has " + score + " points.";
    }

    //AI original guess, with variation of up to 250
    public int getAIoriginal(Card c) {
        int response = c.getAnswer();
        int variation = (int) (Math.random() * 500) - 250;
        while (response + variation < 0) {
            variation = (int) (Math.random() * 500) - 250;
        }
        return response + variation;
    }

    //either knows what the answer is, uses educated guessing, or pure luck depending on brain level of the AI.
    public char getAIoverunder(Card c) {
        int original = c.getOriginal();
        int AIbrainlevel = (int) (Math.random() * 10);
        if (AIbrainlevel < 3) {
            if (original < 100)
                return 'o';
            else if (original > 1000000) {
                return 'u';
            }
        } else if (AIbrainlevel == 7) {
            int ans = c.getAnswer();
            if (original > ans)
                return 'o';
            else
                return 'u';
        } else {
            int choice = (int) (Math.random() * 2);
            if (choice == 0)
                return 'o';
        }
        return 'u';
    }
}
