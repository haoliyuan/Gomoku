package com.liyuan.gomoku.game.models;

import java.util.ArrayList;

/**
 * Created by Liyuan on 4/4/2015.
 */
public class Board {

    private ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();

    private ArrayList<ArrayList<Piece>> mPieces = new ArrayList<ArrayList<Piece>>();

    public int getRowNum() {
        return mRowNum;
    }

    public int getColNum() {
        return mColNum;
    }

    private int mRowNum;
    private int mColNum;

    private int mSize;

    public Board (int boardSize) {

        mRowNum = boardSize;
        mColNum = boardSize;
        mSize = boardSize;
        for(int i=0; i< mRowNum; i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            ArrayList<Piece> rowPieces = new ArrayList<Piece>();
            for(int j=0; j< mColNum; j++) {
                rowPieces.add(new Piece(Piece.PieceType.Empty));
                if (i == 0) {
                    if (j == 0) {
                        row.add(new Cell(Cell.CellType.TopLeft));
                    } else if (j == mColNum-1) {
                        row.add(new Cell(Cell.CellType.TopRight));
                    } else {
                        row.add(new Cell(Cell.CellType.Top));
                    }
                } else if (i == mRowNum-1) {
                    if (j == 0) {
                        row.add(new Cell(Cell.CellType.BottomLeft));
                    } else if (j == mColNum-1) {
                        row.add(new Cell(Cell.CellType.BottomRight));
                    } else {
                        row.add(new Cell(Cell.CellType.Bottom));
                    }
                } else {
                    if (j == 0) {
                        row.add(new Cell(Cell.CellType.Left));
                    } else if (j == mColNum-1) {
                        row.add(new Cell(Cell.CellType.Right));
                    } else {
                        row.add(new Cell(Cell.CellType.Center));
                    }
                }
            }
            board.add(row);
            mPieces.add(rowPieces);
        }
    }

    public void clearBoard() {
        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                getPiece(i, j).setType(Piece.PieceType.Empty);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board.get(row).get(col);
    }

    public Piece getPiece(int row, int col) {
        return mPieces.get(row).get(col);
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }
}
