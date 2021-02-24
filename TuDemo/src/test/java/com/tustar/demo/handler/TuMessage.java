package com.tustar.demo.handler;

public class TuMessage {

    public int what;
    public Object obj;
    public long when;
    //
    TuHandler target;
    Runnable callback;
    TuMessage next;
}
