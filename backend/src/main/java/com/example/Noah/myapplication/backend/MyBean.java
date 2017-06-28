package com.example.Noah.myapplication.backend;

import com.example.JokeSource;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {
    private String myData;
    private JokeSource jokeSource;

    public MyBean() {
        jokeSource = new JokeSource();
    }

    public String getJoke() {
        return jokeSource.tellAJoke();
    }

//    public String getData() {
//        return myData;
//    }
//
//    public void setData(String data) {
//        myData = data;
//    }
}