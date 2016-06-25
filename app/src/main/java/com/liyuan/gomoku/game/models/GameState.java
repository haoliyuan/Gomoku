package com.liyuan.gomoku.game.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liyuan on 4/5/2015.
 */
public class GameState {

    public Board getBoard() {
        return mBoard;
    }

    private Board mBoard = null;

    private Player mPlayer1;
    private Player mPlayer2;

    private int mActivePlayerId;

    private List<Move> mHistory = new ArrayList<Move>();

    public GameState(int boardSize, String playerName1, String playerName2 /*, black white*/) {
        mBoard = new Board(boardSize);
        if (playerName1 != null && !playerName1.equals("")) {
            mPlayer1 = new Player(0, playerName1);
        } else {
            mPlayer1 = new Player(0, "Player 1");
        }

        if (playerName2 != null && !playerName2.equals("")) {
            mPlayer2 = new Player(0, playerName2);
        } else {
            mPlayer2 = new Player(0, "Player 2");
        }
        mActivePlayerId = mPlayer1.getId();
    }

    public void startNewGame() {
        mActivePlayerId = 0;
        mHistory.clear();
        mBoard.clearBoard();
    }

    public void setActivePlayer(int player) {
        mActivePlayerId = player;
    }

    public int getmActivePlayerId() {
        return mActivePlayerId;
    }

    public int getWinner() {
        return (mActivePlayerId ==0) ? 1 : 0;
    }

    public Player getPlayer1() {
        return mPlayer1;
    }

    public void setPlayer1(Player mPlayer1) {
        this.mPlayer1 = mPlayer1;
    }

    public Player getPlayer2() {
        return mPlayer2;
    }

    public void setPlayer2(Player mPlayer2) {
        this.mPlayer2 = mPlayer2;
    }

    public Move undo() {
        if (mHistory.isEmpty())
            return null;
        Move lastMove = mHistory.get(mHistory.size()-1);
        mHistory.remove(mHistory.size()-1);
        Piece piece = mBoard.getPiece(lastMove.x, lastMove.y);

        piece.setType(Piece.PieceType.Empty);
        // reset active player
        if (mActivePlayerId ==0) {
            mActivePlayerId = 1;
        } else {
            mActivePlayerId = 0;
        }

        Log.d("GameState", "Undo: x: " + lastMove.x + " y: " + lastMove.y);

        return lastMove;
    }

    public void doMove(int x, int y) {
        Piece piece = mBoard.getPiece(x, y);
        if (piece.getType() == Piece.PieceType.Empty) {
            if(mActivePlayerId == 0) {    // active player 0 plays black
                piece.setType(Piece.PieceType.Black);
                setActivePlayer(1);    // set white to active player
            } else {
                piece.setType(Piece.PieceType.White);
                setActivePlayer(0);
            }
            storeMove(x, y);
        }
    }

    public boolean checkWin(int x, int y) {
        Log.d(GameState.class.getSimpleName(), "checkWin");

        int count = 1;
        Piece piece = mBoard.getPiece(x, y);
        Piece.PieceType pieceType = piece.getType();
        int boardSize = mBoard.getSize();
        if(pieceType == Piece.PieceType.Empty) {
            return false;
        }

        // vertical
        int i = x;
        do {
            i--;
            if ( count < 5 && i >= 0 && mBoard.getPiece(i, y).getType() == pieceType) {
                count++;
            } else if (count == 5) {
                return true;
            } else {
                i = x;
                do {
                    i++;
                    if ( count < 5 && i < boardSize && mBoard.getPiece(i, y).getType() == pieceType) {
                        count++;
                    } else if (count == 5) {
                        return true;
                    } else {
                        break;
                    }
                } while (true);
                break;
            }
        } while (true);

        // horizontal
        int j = y;
        count = 1;
        do {
            j--;
            if ( count < 5 && j >= 0 && mBoard.getPiece(x, j).getType() == pieceType) {
                count++;
            } else if (count == 5) {
                return true;
            } else {
                j = y;
                do {
                    j++;
                    if ( count < 5 && j < boardSize && mBoard.getPiece(x, j).getType() == pieceType) {
                        count++;
                    } else if (count == 5) {
                        return true;
                    } else {
                        break;
                    }
                } while (true);
                break;
            }
        } while (true);

        // forward slash
        i = x;
        j = y;
        count = 1;
        do {
            i--;
            j++;
            if ( count < 5 && i >= 0 && j < boardSize && mBoard.getPiece(i, j).getType() == pieceType) {
                count++;
            } else if (count == 5) {
                return true;
            } else {
                i = x;
                j = y;
                do {
                    i++;
                    j--;
                    if ( count < 5 && i < boardSize && j > 0 && mBoard.getPiece(i, j).getType() == pieceType) {
                        count++;
                    } else if (count == 5) {
                        return true;
                    } else {
                        break;
                    }
                } while (true);
                break;
            }
        } while (true);

        // backward slash
        i = x;
        j = y;
        count = 1;
        do {
            i--;
            j--;
            if ( count < 5 && i >= 0 && j > 0 && mBoard.getPiece(i, j).getType() == pieceType) {
                count++;
            } else if (count == 5) {
                return true;
            } else {
                i = x;
                j = y;
                do {
                    i++;
                    j++;
                    if ( count < 5 && i < boardSize && j < boardSize && mBoard.getPiece(i, j).getType() == pieceType) {
                        count++;
                    } else if (count == 5) {
                        return true;
                    } else {
                        break;
                    }
                } while (true);
                break;
            }
        } while (true);

        return false;
    }

    public void storeMove(int x, int y) {
        mHistory.add(new Move(x, y));
    }

    public class Move {
        public int x;
        public int y;

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Move getMove() {
            return this;
        }
    }

}
