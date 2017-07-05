package com.ponthaitay.listmovie.kotlin.javaexample;

public class SingletonExample {
    private static SingletonExample instance;

    public static SingletonExample getInstance() {
        if (instance == null) {
            instance = new SingletonExample();
        }
        return instance;
    }
}


