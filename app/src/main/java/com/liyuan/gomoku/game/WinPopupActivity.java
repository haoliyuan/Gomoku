package com.liyuan.gomoku.game;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.liyuan.gomoku.R;

/**
 * Created by Liyuan on 4/7/2015.
 */
public class WinPopupActivity extends FragmentActivity {
    private WinPopupFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int winner = extras.getInt("winner");

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mFragment = WinPopupFragment.newInstance();
            mFragment.setWinner(winner);
            Log.d(WinPopupActivity.class.getSimpleName(), "onCreate");
            getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
        }
    }

    public void goHome() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("action", 0);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void startNewGame() {
        Intent newGameIntent = new Intent();
        newGameIntent.putExtra("action", 1);
        setResult(RESULT_OK, newGameIntent);
        finish();
    }
}
