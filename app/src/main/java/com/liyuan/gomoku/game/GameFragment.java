package com.liyuan.gomoku.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liyuan.gomoku.R;
import com.liyuan.gomoku.game.models.Board;
import com.liyuan.gomoku.game.models.Cell;
import com.liyuan.gomoku.game.models.GameState;
import com.liyuan.gomoku.game.models.Piece;

/**
 * Created by Liyuan on 4/4/2015.
 */
public class GameFragment extends Fragment {

    private LayoutInflater mInflater;
    private ActionBarActivity mContext;
    private GameState mGameState;
    private GridLayout mBoardPieces;
    private static final int BOARD_SIZE = 15;

    public static final int GO_HOME = 0;
    public static final int START_NEW_GAME = 1;

    public static GameFragment newInstance(String playerName1, String playerName2) {
        GameFragment fragment = new GameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("playerName1", playerName1);
        bundle.putString("playerName2", playerName2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String playerName1 = getArguments().getString("playerName1");
        String playerName2 = getArguments().getString("playerName2");

        Log.d("GameFragment", "Hello debug log");
        //Board board = new Board(4, 4);
        mGameState = new GameState(BOARD_SIZE, playerName1, playerName2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        return inflater.inflate(R.layout.fragment_game,container, false);
    }

    public void renderPiece(int x, int y) {
        int index = BOARD_SIZE * x + y;
        ImageView imageView = (ImageView) mBoardPieces.getChildAt(index);
        imageView.setImageResource(mGameState.getBoard().getPiece(x, y).getImageResource());
    }

    public void notifyWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Game Over");
        String winMessage = "Congratulations " + mGameState.getWinner() + ", you have won the game!";
        builder.setMessage(winMessage);

        final View dialogView = mInflater.inflate(R.layout.popup_win_alertdialog, null);
        if (mGameState.getWinner() == 1) {
            dialogView.setRotation(180);
        }
        builder.setView(dialogView);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.button_new_game, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent newGameIntent = new Intent();
                newGameIntent.putExtra("action", 1);
                getActivity().setResult(Activity.RESULT_OK, newGameIntent);
                getActivity().finish();
//                Intent intent = new Intent(getActivity(), GameActivity.class);
//                intent.putExtra("playerName1", mGameState.getPlayer1().getName());
//                intent.putExtra("playerName2", mGameState.getPlayer2().getName());
//                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.button_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("acion", 0);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

        builder.setNeutralButton(R.string.button_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

//        Intent intent = new Intent(getActivity(), WinPopupActivity.class);
//        intent.putExtra("winner", mGameState.getWinner());
//        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == ActionBarActivity.RESULT_OK) {
                int action = data.getExtras().getInt("action");
                if (action == GO_HOME) {
                    getActivity().finish();
                } else if (action == START_NEW_GAME) {
                    startNewGame();
                } else {
                    // miscellaneous
                    getActivity().finish();
                }
            }
        }
    }

        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mContext = (ActionBarActivity) getActivity();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int pxHeight = displayMetrics.heightPixels;
        int pxWidth = displayMetrics.widthPixels;
        int boardSize = (pxWidth < pxHeight) ? pxWidth : pxHeight;

        // Make the board background
        GridLayout gameBoard = (GridLayout) view.findViewById(R.id.game_board);
        gameBoard.removeAllViews();
        final Board board = mGameState.getBoard();
        gameBoard.setColumnCount(board.getColNum());
        gameBoard.setRowCount(board.getRowNum());

        int cellSize = boardSize / board.getRowNum();  // Calculate pixel size of cell
        for (int i=0; i<gameBoard.getRowCount(); i++) {
            for (int j=0; j < gameBoard.getColumnCount(); j++) {
                Cell cell = board.getCell(i, j);
                ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(cell.getImageResource());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cellSize, cellSize);
                imageView.setLayoutParams(layoutParams);
                gameBoard.addView(imageView);
            }
        }

        // Make the overlay pieces
        mBoardPieces = (GridLayout) view.findViewById(R.id.board_pieces);
        mBoardPieces.removeAllViews();
        mBoardPieces.setRowCount(board.getRowNum());
        mBoardPieces.setColumnCount(board.getColNum());
        for (int i=0; i<mBoardPieces.getRowCount(); i++) {
            for (int j=0; j < mBoardPieces.getColumnCount(); j++) {
                final Piece piece = board.getPiece(i, j);
                final ImageView imageView = new ImageView(mContext);
                final int x=i;
                final int y=j;
                imageView.setImageResource(piece.getImageResource());
                imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            mGameState.doMove(x, y);
                            imageView.setImageResource(piece.getImageResource());


                            if (mGameState.checkWin(x, y)) {
                                Log.d("onTouch checkWin", "Player has won! Player id: " + String.valueOf(mGameState.getWinner()));
                                // TODO win notification, new game button
                                notifyWin();
                            }
                        }
                        return true;
                    }
                });
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cellSize, cellSize);
                imageView.setLayoutParams(layoutParams);
                mBoardPieces.addView(imageView);
            }
        }
        TextView textViewPlayer1 = (TextView) view.findViewById(R.id.textViewPlayer1);
        textViewPlayer1.setText(mGameState.getPlayer1().getName());
        TextView textViewPlayer2 = (TextView) view.findViewById(R.id.textViewPlayer2);
        textViewPlayer2.setText(mGameState.getPlayer2().getName());

        Button quitButton1 = (Button) view.findViewById(R.id.button_quit1);
        quitButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).quitGame();
            }
        });

        Button quitButton2 = (Button) view.findViewById(R.id.button_quit2);
        quitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).quitGame();
            }
        });

        Button undoButton1 = (Button) view.findViewById(R.id.button_undo1);
        undoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGameState.getmActivePlayerId() == 1) {
                    GameState.Move lastMove = mGameState.undo();
                    if (lastMove != null) {
                        renderPiece(lastMove.x, lastMove.y);
                    }

                }
            }
        });

        Button undoButton2 = (Button) view.findViewById(R.id.button_undo2);
        undoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGameState.getmActivePlayerId() == 0) {
                    GameState.Move lastMove = mGameState.undo();
                    if (lastMove != null) {
                        renderPiece(lastMove.x, lastMove.y);
                    }
                }
            }
        });


    }

    public void startNewGame() {
        mGameState.startNewGame();
        for (int i = 0; i < mGameState.getBoard().getSize(); i++) {
            for (int j = 0; j < mGameState.getBoard().getSize(); j++) {
                renderPiece(i, j);
            }
        }
    }

}
