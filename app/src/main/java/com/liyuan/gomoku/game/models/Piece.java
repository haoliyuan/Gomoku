package com.liyuan.gomoku.game.models;

import com.liyuan.gomoku.R;

/**
 * Created by Liyuan on 4/5/2015.
 */
public class Piece {
    public enum PieceType {
        Black, White, Empty
    }

    private PieceType mType;

    public Piece (PieceType type) {
        mType = type;
    }

    public PieceType getType() {
        return mType;
    }

    public void setType(PieceType type) {
        mType = type;
    }
    public int getImageResource() {
        switch (mType) {
            case Black:
                return R.drawable.black_piece;
            case White:
                return R.drawable.white_piece;
            case Empty:
                return R.drawable.empty_piece;
            default:
                return R.drawable.empty_piece;
        }
    }
}
