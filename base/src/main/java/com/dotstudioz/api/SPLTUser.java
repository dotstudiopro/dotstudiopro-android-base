package com.dotstudioz.api;

public class SPLTUser {
    private static final SPLTUser ourInstance = new SPLTUser();

    public static SPLTUser getInstance() {
        return ourInstance;
    }

    private SPLTUser() {
    }



}
