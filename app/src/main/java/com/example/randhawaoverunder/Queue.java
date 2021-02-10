package com.example.randhawaoverunder;

public class Queue {
    //completely standard queue but for players
    private Player data[] = new Player[50];
    private int count;
    private int head;

    public Queue() {
        count = 0;
        head = 0;
    }

    public void enqueue(Player value) {
        int tail = (head + count) % data.length;
        data[tail] = value;
        count++;
    }

    public Player dequeue() {
        Player temp = data[head];
        count--;
        head = (head + 1) % data.length;
        return temp;
    }

    public Player peek() {
        return data[head];
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    public boolean isFull() {
        return (count == data.length);
    }
}