package javaGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameBackend {

    private Board board;
    public Player player1, player2;
    private boolean on;
    private Move bestMove;

    public GameBackend(Board board, Player p1, Player p2, int size) {
        this.board = board;
        this.board.size = board.size;
        this.board.initialize_board (size);
        this.on = true;
        this.player1 = p1;
        this.player2 = p2;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean getState() {
        return on;
    }

    public boolean withinBound(int r, int c) {
        if ((r >= 0 && r < board.size) && (c >= 0 && c < board.size))
            return true;
        else
            return false;
    }

    public Move createMoveInBoard(int r0, int c0, int r1, int c1) {
        if (withinBound (r0, c0) && withinBound (r1, c1)) {
            Color moved = this.board.getColor (r0, c0);
            Color replaced = this.board.getColor (r1, c1);
            return new Move (r0, c0, r1, c1, moved, replaced);
        } else
            return null;
    }

    public int getUtility(Board board, Color color) {
        int utility = board.evalPieceSquareTable(color);
     //   utility += board.evalMobility(color);
     //   utility -= board.evalArea(color);
        utility += (board.evalQuad(color));
        utility += board.evalConnectedness(color);
        utility -= board.evalDensity (color);
        return utility;
    }

    public Move getLegalMove(int r0, int c0, int r1, int c1) {
        Move move = createMoveInBoard (r0, c0, r1, c1);
        if (move == null) {
            //  System.out.println("Invalid move: outside board");
        }
        else if (this.board.getColor (move.getR0 (), move.getC0 ()) == Color._____) {
            // System.out.println("Invalid move: empty checker");
        }
        else if (this.board.getColor (move.getR0 (), move.getC0 ()) != this.board.getTurn ()) {
            // System.out.println("Invalid move: choosing opponent checker");
        }
        else if (!this.on) {
            //System.out.println("Game hasn't started.");
        }
        else {
            if (this.board.isLegalMove (move))
                return move;
            else {
                // System.out.println("Illegal move");
                return null;
            }
        }
        return null;
    }

    public void getAiMove() {
        long startTime = System.currentTimeMillis ();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int bestValue = Integer.MIN_VALUE;
        this.board.setTurn (Color.Black);
        ArrayList<Move> moves = board.getAllLegalMoves (Color.Black);
        Collections.shuffle(moves, new Random (System.currentTimeMillis()));
        for (int i = 0; i < moves.size (); i++) {
            Move move = moves.get (i);
            Board newBoard = new Board (this.board);
            newBoard.makeMove (move);
            if ((System.currentTimeMillis () - startTime) / 1000 > 2)
                break;
            int val = miniMax (Color.White, new Board (newBoard), 3, alpha, beta);
            if (bestValue < val) {
                bestValue = val;
                bestMove = move;
            }
            alpha = Math.max(alpha, bestValue);
            if ((System.currentTimeMillis () - startTime) / 1000 > 2)
                break;
        }
        System.out.println ("Time: " + (System.currentTimeMillis () - startTime) / 1000 + " sec");
        System.out.println ("BestValue: " + bestValue + " => " + "(AI moving) " + bestMove.strMove ());
        this.board.setTurn (Color.Black);
        this.board.makeMove (bestMove);
        System.out.println("Black moved from " + bestMove.getR0 () + ", " + bestMove.getC0 () + " to " + bestMove.getR1 () + ", " + bestMove.getC1 ());
    }

    public int miniMax(Color color, Board board, int depth, int alpha, int beta) {
        if (board.IsEndgame ()) {
            if (board.getWinner () == Color.Black)
                return Integer.MAX_VALUE;
            else if (board.getWinner () == Color.White)
                return Integer.MIN_VALUE;
        }
        if (depth == 0) {
            //  return this.getUtilityCombined (board);
            int utilityAI = this.getUtility(board, Color.Black);
            int utilityHuman = this.getUtility(board, Color.White);
            return (utilityAI-utilityHuman);
        }
        if (color == Color.Black) {
            int best = Integer.MIN_VALUE;
            this.board.setCheckers ();
            board.setTurn (color);
            ArrayList<Move> moves = board.getAllLegalMoves (color);
            Collections.shuffle(moves, new Random (System.currentTimeMillis()));
            for (int i = 0; i < moves.size (); i++) {
                Move move = moves.get (i);
                Board newBoard = new Board (board);
                newBoard.makeMove (move);
                int val = miniMax (Color.White, new Board (newBoard), depth - 1, alpha, beta);
                best = Math.max (best, val);
                alpha = Math.max (alpha, best);
                if (beta <= alpha)
                    break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            this.board.setCheckers ();
            board.setTurn (color);
            ArrayList<Move> moves = board.getAllLegalMoves (color);
            Collections.shuffle(moves, new Random (System.currentTimeMillis()));
            for (int i = 0; i < moves.size (); i++) {
                Move move = moves.get (i);
                Board newBoard = new Board (board);
                newBoard.makeMove (move);
                int val = miniMax (Color.Black, new Board (newBoard), depth - 1, alpha, beta);
                best = Math.min (best, val);
                beta = Math.min (beta, best);
                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }

//    public Move miniMaxFinal(Color color, Board board, int depth, int alpha, int beta) {
//        if(board.IsEndgame()) {
//            if(board.getWinner() == Color.Black) {
//                Move move = new Move();
//                move.cost =  Integer.MAX_VALUE;
//                return move;
//            }
//            else if(board.getWinner() == Color.White) {
//                Move move = new Move();
//                move.cost =  Integer.MIN_VALUE;
//                return move;
//            }
//        }
//        if(depth == 0) {
//            //  return this.getUtilityCombined (board);
//            int utilityAI = this.getUtility(board, Color.Black);
//            int utilityHuman = this.getUtility(board, Color.White);
//            Move move = new Move();
//            move.cost =  utilityAI - utilityHuman;
//            return move;
//        }
//        if(color == Color.Black) {
//            Move bestMove = new Move();
//            bestMove.cost = Integer.MIN_VALUE;
//            int best = Integer.MIN_VALUE;
//            this.board.setCheckers();
//            board.setTurn(color);
//            ArrayList<Move> moves = board.getAllLegalMoves(color);
//            Collections.shuffle(moves, new Random (System.currentTimeMillis()));
//            for(int i = 0; i < moves.size(); i++) {
//                Move move = moves.get(i);
//                Board newBoard = new Board(board);
//                newBoard.makeMove(move);
//                Move val = miniMaxFinal(Color.White, new Board(newBoard), depth-1, alpha, beta);
//                move.cost = val.cost;
//                bestMove = maxMove(bestMove, move);
//                alpha = Math.max(alpha, bestMove.cost);
//                if (beta <= alpha)
//                    break;
//            }
//            return bestMove;
//        }
//        else {
//            Move bestMove = new Move();
//            bestMove.cost = Integer.MAX_VALUE;
//
//            this.board.setCheckers();
//            board.setTurn(color);
//            ArrayList<Move> moves = board.getAllLegalMoves(color);
//            Collections.shuffle(moves, new Random (System.currentTimeMillis()));
//            for(int i = 0; i < moves.size(); i++) {
//                Move move = moves.get(i);
//                Board newBoard = new Board(board);
//                newBoard.makeMove(move);
//                Move val = miniMaxFinal(Color.Black, new Board(newBoard), depth-1, alpha, beta);
//                move.cost = val.cost;
//                bestMove = minMove(bestMove, move);
//                beta = Math.min(beta, bestMove.cost);
//                if (beta <= alpha)
//                    break;
//            }
//            return bestMove;
//        }
//    }
//
//    public void getAiMoveFinal() {
//        long startTime = System.currentTimeMillis();
//        int alpha = Integer.MIN_VALUE;
//        int beta = Integer.MAX_VALUE;
//        int bestValue = Integer.MIN_VALUE;
//
//        this.board.setTurn(Color.Black);
//        Move bestMove = miniMaxFinal (Color.Black, new Board (board), 4, alpha, beta);
//        System.out.println("BestValue: " + bestMove.cost + " => " + bestMove.strMove());
//
//        this.board.setTurn(Color.Black);
//        this.board.makeMove(bestMove);
//
//        System.out.println ("Time: " + (System.currentTimeMillis() - startTime)/1000);
//    }
//
//    public Move maxMove(Move move1, Move move2) {
//        if(move1.cost > move2.cost)
//            return move1;
//        else
//            return move2;
//    }
//
//    public Move minMove(Move move1, Move move2) {
//        if(move1.cost < move2.cost)
//            return move1;
//        else
//            return move2;
//    }

}