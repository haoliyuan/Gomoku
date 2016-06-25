package com.liyuan.gomoku.game;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liyuan.gomoku.R;

/**
 * Created by Liyuan on 4/7/2015.
 */
public class WinPopupFragment extends DialogFragment {

    private int mWinner;

    public int getWinner() {
        return mWinner;
    }

    public void setWinner(int mWinner) {
        this.mWinner = mWinner;
    }

    public static WinPopupFragment newInstance() {
        Log.d(DialogFragment.class.getSimpleName(), "newInstance");
        return new WinPopupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(DialogFragment.class.getSimpleName(), "oncreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(DialogFragment.class.getSimpleName(), "oncreateview");
        View view = inflater.inflate(R.layout.popup_win_notification,container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        Log.d(DialogFragment.class.getSimpleName(), "onviewcreated");

        LinearLayout winDialogLayout = (LinearLayout) view.findViewById(R.id.fragment_win_popup);
        if(mWinner == 1) {
            winDialogLayout.setRotation(180f);
        }

        TextView text = (TextView) view.findViewById(R.id.win_message);
        text.setText("Congratulations Player " + mWinner + ", you won!");

        Button button_new_game = (Button) view.findViewById(R.id.button_new_game_win);
        button_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WinPopupActivity) getActivity()).startNewGame();
                Log.d("WinPopup new game: ", "startNewGame()");
            }
        });

        Button buttton_quit = (Button) view.findViewById(R.id.button_go_back);
        buttton_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WinPopupActivity) getActivity()).goHome();
                Log.d("WinPopup quit: ", "goHome()");
            }
        });

        Button button_close = (Button) view.findViewById(R.id.button_close_dialog);

    }
}
