package com.liyuan.gomoku.game.models;

import com.liyuan.gomoku.R;

/**
 * Created by Liyuan on 4/4/2015.
 */
public class Cell {
    public enum CellType {
        TopLeft,Top,TopRight,Left,Center,Right,BottomLeft,Bottom,BottomRight
    }

    private CellType mCellType;

    Cell(CellType cellType) {
        mCellType = cellType;
    }

    public CellType getCellType() {
        return mCellType;
    }

    public int getImageResource() {
        switch (mCellType) {
            case TopLeft :
                return R.drawable.grid_topleft;
            case Top:
                return R.drawable.grid_top;
            case TopRight:
                return R.drawable.grid_topright;
            case Left:
                return R.drawable.grid_left;
            case Center:
                return R.drawable.grid_center;
            case Right:
                return R.drawable.grid_right;
            case BottomLeft:
                return R.drawable.grid_bottomleft;
            case Bottom:
                return R.drawable.grid_bottom;
            case BottomRight:
                return R.drawable.grid_bottomright;
            default:
                return 0;
        }
    }
}
