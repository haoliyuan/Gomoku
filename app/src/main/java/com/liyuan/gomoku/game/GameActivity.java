package com.liyuan.gomoku.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.liyuan.gomoku.R;

/**
 * Created by Liyuan on 4/4/2015.
 */
public class GameActivity extends ActionBarActivity {

    private GameFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        String playerName1 = getIntent().getExtras().getString("playerName1");
        String playerName2 = getIntent().getExtras().getString("playerName2");
        if (savedInstanceState == null) {
            mFragment = GameFragment.newInstance(playerName1, playerName2);
            Log.d(GameActivity.class.getSimpleName(), "onCreate");
            getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
        }
    }

    public void quitGame() {
        finish();
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                int action = data.getExtras().getInt("action");
                if (action == GO_HOME) {
                    quitGame();
                } else if (action == START_NEW_GAME) {
                    mFragment.startNewGame();
                } else {
                    // miscellaneous
                    quitGame();
                }
            }
            if (resultCode == RESULT_CANCELED) {
                // Should never end up here!!
            }
        }

    }
    */

}
