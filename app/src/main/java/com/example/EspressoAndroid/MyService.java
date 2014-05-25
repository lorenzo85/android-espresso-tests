package com.example.EspressoAndroid;

import com.google.inject.Singleton;

import static java.lang.String.*;

@Singleton
public class MyService {

    public MyService(){}

    public String doSomething(String name) {
        return format("Hello %s", name);
    }
}
