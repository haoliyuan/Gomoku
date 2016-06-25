package com.liyuan.gomoku.game.models;

/**
 * Created by Liyuan on 4/5/2015.
 */
public class Player {

    private int mId;
    private String mName;

    public Player(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
