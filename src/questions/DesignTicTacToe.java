package questions;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DesignTicTacToe {
    /**
     *
     * Requirements:
     *  - In a row
     *  - In a col
     *  - In a diagonal
     *  - Board : n X n { dimensions}
     *
     * - Players - c players can be there
     * - Symbol - x , [] , () , $  :etc
     *
     *
     *
     */


    class PlayingPiece {
        PieceType pieceType;
        PlayingPiece(PieceType pieceType){
            this.pieceType = pieceType;
        }
    }

    enum PieceType {
        X,
        O
    }

    class XPiece extends PlayingPiece {
        XPiece() {
             super(PieceType.X);
        }
    }

    class OPiece extends PlayingPiece {
        OPiece() {
            super(PieceType.O);
        }
    }

    class Board {
        PlayingPiece[][] board;
        int size;
        boolean gameOver;

        Board(int size) {
            this.size = size;
            board = new PlayingPiece[size][size];
            gameOver = false;
        }

        void print(){
            for(int i =0 ; i < board.length ; i++) {
               for(int j = 0 ; j < board[0].length ; j++) {
                   String pos = board[i][j] == null ? " " : board[i][j].pieceType.name();
                   System.out.print("|  "+pos+"  |");
               }
                System.out.println();
            }
        }

        boolean checkCol(int col) {

        }


        boolean checkRow(int row) {

        }

        boolean checkDiag(int row , int col) {

        }

        boolean checkDiag(int row , int col) {
            // checkdiag
        }

        boolean checkAntiDiag(int row , int col) {
           // check anti
        }

    }

    class Player {
        String userId;
        PlayingPiece piece;
        boolean didWin;

        Player(String userId , PlayingPiece piece) {
            this.piece = piece;
            this.userId = userId;
        }

        GameState makeMove(Board board , int x , int y) {
             if(board.board[x][y] != null) return GameState.INVALID;
             board.board[x][y] = piece;
             GameState state = checkGameState(board , x , y); // won , tied , no result
             if(!state.equals(GameState.NO_RESULT))  board.gameOver = true;
             return state;
        }

        GameState checkGameState(Board board , int x , int y) {

        }




    }

    enum GameState{
        WON,
        TIED,
        NO_RESULT,
        INVALID
    }

     class TicTacToeGame {
        Board board;
        Set<Player> players;

        TicTacToeGame(int n) {
            board = new Board(n);
            players = new HashSet<>();
        }

        boolean addPlayer(Player p) {
            if(players.size() + 1 ==  board.size * board.size) return false;
            return players.add(p);
        }

        int[] takeInputForMove() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter row : ");
            int x = scanner.nextInt();
            System.out.println("Enter col : ");
            int y = scanner.nextInt();
            scanner.close();
            return new int[]{x,y};
        }

        private Player simulateGame () {
            while(!board.gameOver) {
                for(Player p : players) {
                    int[] pos = takeInputForMove();
                    while(p.makeMove(board , pos[0] , pos[1]).equals(GameState.INVALID)) {
                        System.out.println("Invalid move try again : ");
                        pos = takeInputForMove();
                    }
                    board.print();
                    if(p.didWin) return p;
                }
            }

            return null;
        }

        void run() {
            Player winner = simulateGame();
            if(winner == null) {
                System.out.println("Game is tied");
            }else {
                System.out.println(winner.userId + " won the game!!");
            }
        }
    }

    void run(int n){
        TicTacToeGame game = new TicTacToeGame(n);
        game.run();
    }

    public static void main(String[] args) {
        DesignTicTacToe game = new DesignTicTacToe();
        game.run(3);
    }

}
