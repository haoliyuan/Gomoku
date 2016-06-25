package com.liyuan.gomoku;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.liyuan.gomoku.game.GameActivity;


/**
 * Created by Liyuan on 4/4/2015.
 */
public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    private LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        Button newGameButton = (Button) view.findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    public void startGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("builder message").setTitle("builder title");

        final View dialogView = mInflater.inflate(R.layout.popup_game_settings, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText playerNameInput1 = (EditText) dialogView.findViewById(R.id.playerNameInput1);
                EditText playerNameInput2 = (EditText) dialogView.findViewById(R.id.playerNameInput2);
                String playerName1 = playerNameInput1.getText().toString();
                String playerName2 = playerNameInput2.getText().toString();
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("playerName1", playerName1);
                intent.putExtra("playerName2", playerName2);
                startActivity(intent);
            }
        })
        .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
