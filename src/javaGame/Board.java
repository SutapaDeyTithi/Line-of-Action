package javaGame;

import java.util.ArrayList;

public class Board {
    public int size;
    public Color matrix[][];
    private Color turn, winner;
    private Color movedC = null, replacedC = null;
    private int noOfBlack, noOfWhite;
    private ArrayList<Checker> checkers;
    ArrayList<Move> legalMoves = new ArrayList<>();

    public Board(int size) {
        this.size = size;
        noOfBlack = (size*2 - 4);
        noOfWhite = (size*2 - 4);
        this.matrix = new Color[size][size];
        this.checkers = new ArrayList<>();
        this.initialize_board(size);
        this.setCheckers();
    }

    public Board(Board board) {
        this.size = board.size;
        this.noOfBlack = (size*2 - 4);
        this.noOfWhite = (size*2 - 4);
        this.matrix = new Color[size][size];
        this.checkers = new ArrayList<>();
        for(int i = 0; i < this.size; i++)
            for(int j = 0; j < this.size; j++)
                this.matrix[i][j] = board.matrix[i][j];
        this.setCheckers();
        this.turn = board.turn;
    }

    public void initialize_board(int size) {
        this.size = size;
        noOfBlack = (size*2 - 4);
        noOfWhite = (size*2 - 4);
        this.matrix = new Color[size][size];
        this.checkers = new ArrayList<>();

        for(int i = 0; i < this.size; i++)
            for(int j = 0; j < this.size; j++) {
                if((i == 0 && j > 0) && (i == 0 && j < size-1))
                    matrix[i][j] = Color.Black;
                else if((i == size-1 && j > 0) && (i == size-1 && j < size-1))
                    matrix[i][j] = Color.Black;
                else if((j == 0 && i > 0) && (j == 0 && i < size-1))
                    matrix[i][j] = Color.White;
                else if((j == size-1 && i > 0) && (j == size-1 && i < size-1))
                    matrix[i][j] = Color.White;
                else
                    matrix[i][j] = Color._____;
            }
        this.setCheckers();
    }

    public void setCheckers() {
        checkers.clear();
        for(int i = 0; i < this.size; i++)
            for(int j = 0; j < this.size; j++) {
                if(matrix[i][j] == Color.Black)
                    checkers.add(new Checker(Color.Black, i, j));
                else if(matrix[i][j] == Color.White)
                    checkers.add(new Checker(Color.White, i, j));
            }
    }

    public ArrayList<Checker> getCheckers() {
        return this.checkers;
    }

    public void showCheckers() {
        System.out.println("Checkers:\n");
        for(int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            System.out.println("Checker" + i + ":");
            System.out.println(checker.getColor() + " Coord: (" +  checker.getRow() + ", " + checker.getCol() + ")");
        }
    }

    public int distance(int r0, int c0, int r1, int c1) {
        int d1 = Math.abs(r0 - r1);
        int d2 = Math.abs(c0 - c1);
        return Math.max(d1, d2);
    }

    public int getFirstBlack() {
        for(int i = 0; i < this.checkers.size(); i++)
            if(this.checkers.get(i).getColor() == Color.Black)
                return i;
        return -1;
    }

    public int getFirstWhite() {
        for(int i = 0; i < this.checkers.size(); i++)
            if(this.checkers.get(i).getColor() == Color.White)
                return i;
        return -1;
    }

    public int bfsTreeSize(Color color, int startIndex) {
        //bfs
        for(int i = 0; i < this.checkers.size(); i++)
            this.checkers.get(i).setVisited(false);
        ArrayList<Checker> queue = new ArrayList<>();
        ArrayList<Checker> bfsTree = new ArrayList<>();
        queue.add(this.checkers.get(startIndex));
        this.checkers.get(startIndex).setVisited(true);

        while (queue.size() > 0) {
            Checker checker = queue.get(0);
            queue.remove(0);
            bfsTree.add(checker);
            //get neighbors
            for(int i = 0; i < this.checkers.size(); i++) {
                Checker neighbor = this.checkers.get(i);
                if(!neighbor.isVisited() && neighbor.getColor() == color)
                    if(distance(checker.getRow(), checker.getCol(), neighbor.getRow(), neighbor.getCol()) == 1) {
                        this.checkers.get(i).setVisited(true);
                        queue.add(this.checkers.get(i));
                    }
            }
        }
        return bfsTree.size();
    }

    public boolean endGameBlack() {
        int startIndex = this.getFirstBlack();
        if(startIndex != -1) {
            int size = this.bfsTreeSize(Color.Black, startIndex);
                if (size == this.noOfBlack) {
                    this.winner = Color.Black;
                    return true;
                }
            }
        return false;
    }

    public boolean endGameWhite() {
        int startIndex = this.getFirstWhite();
        if(startIndex != -1) {
            int size = this.bfsTreeSize(Color.White, startIndex);
            if (size == this.noOfWhite) {
                this.winner = Color.White;
                return true;
            }
        }
        return false;
    }

    public Boolean IsEndgame() {
        if(endGameBlack() && endGameWhite()) {
            this.winner = this.oppositeTurn();
            return true;
        }
        else if(endGameBlack()) {
            this.winner = Color.Black;
            return true;
        }
        else if(endGameWhite()) {
            this.winner = Color.White;
            return true;
        }
        return false;
    }

    public void setTurn(Color turn) {
        this.turn = turn;
    }

    public Color getTurn() {
        return turn;
    }

    public Color oppositeTurn() {
        if(this.turn == Color.Black) {
            return Color.White;
        }
        else if(this.turn == Color.White) {
            return Color.Black;
        }
        return null;
    }

    public void alterTurn() {
        if(turn == Color.Black)
            turn = Color.White;
        else if(turn == Color.White)
            turn = Color.Black;
    }

    public Color getColor(int i, int j) {
        if(withinBound(i, j)) {
            return this.matrix[i][j];
        }
        return Color._____;
    }

    public Color getWinner() {
        return this.winner;
    }
    public int getNoOfBlack() {
        return noOfBlack;
    }

    public int getNoOfWhite() {
        return noOfWhite;
    }

    public String strBoard() {
        String str = "\n      |     ";
        for(int j = 0; j < this.size; j++)
            str += j + "          ";
        str += "\n";
        for(int j = 0; j < this.size; j++)
            str += "------------";
        str += "\n";
        for(int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if(j == 0) {
                    str += i + "     |     ";
                }
                str += matrix[i][j] + "      ";
            }
            str += "\n";
        }
        return str;
    }

    public void makeMove(Move move) {
        move.setMovedC(this.matrix[move.getR0()][move.getC0()]);
        move.setReplacedC(this.matrix[move.getR1()][move.getC1()]);
        this.movedC = move.getMovedC();
        this.replacedC = move.getReplacedC();
        if(movedC != Color._____ && replacedC != Color._____ && movedC != replacedC) {
            if(replacedC == Color.White)
                this.noOfWhite--;
            else if(replacedC == Color.Black)
                this.noOfBlack--;
        }
        Color temp = this.matrix[move.getR0()][move.getC0()];
        this.matrix[move.getR0()][move.getC0()] = Color._____;
        this.matrix[move.getR1()][move.getC1()] = temp;
        this.setCheckers();
        this.alterTurn();
    }

    public boolean isLegalMove(Move move) {
        if(move == null)
            return false;
        if(this.getColor(move.getR0(), move.getC0()) == Color._____)
            return false;
        else if(this.getColor(move.getR0(), move.getC0()) != this.turn)
            return false;
        else if(move.getR0 () == move.getC0 () && move.getC0 ()== move.getC1 ())
            return false;
        LoA loA = getLoA(move);
        if(loA.getdR() == 0 && loA.getdC() == 0)
            return false;
        if(move.noOfSquaresMoved() != noOfCheckerInLoA(move, loA))
            return false;
        if(isBlocked(move, loA))
            return false;
        return true;
    }

    public boolean isBlocked(Move move, LoA loA) {
        if(move == null)
            return true;
        if(this.matrix[move.getR1()][move.getC1()] == this.matrix[move.getR0()][move.getC0()])   // same color at target
            return true;

        int startR = move.getR0();
        int startC = move.getC0();
        int endR = move.getR1();
        int endC = move.getC1();
        while(startC != endC || startR != endR) {
            if(!withinBound(startR, startC))
                return true;
            if(this.matrix[startR][startC] == this.oppositeTurn())
                return true;
            startR += loA.getdR();
            startC += loA.getdC();
        }
        return false;
    }

    public int noOfCheckerInLoA(Move move, LoA loA) {
        int startR = move.getR0();
        int startC = move.getC0();
        int count = -1;
        while(true) {
            if(withinBound(startR, startC) && this.matrix[startR][startC] != Color._____)
            {
                count++;
            }
            startR += loA.getdR();
            startC += loA.getdC();
            if(!withinBound(startR, startC))
                break;
        }
        startR = move.getR0();
        startC = move.getC0();
        while(true) {
            if(withinBound(startR, startC) && this.matrix[startR][startC] != Color._____)
            {
                count++;
            }
            startR -= loA.getdR();
            startC -= loA.getdC();

            if(!withinBound(startR, startC))
                break;
        }
        return count;
    }

    /**
     * get direction for a coord
     */

    public LoA getLoA(Move move) {
        LoA loA;
        int dr = move.getR1() - move.getR0();
        int dc = move.getC1() - move.getC0();
        boolean diagCoord = false;
        if( Math.abs(dr) == Math.abs(dc))
            diagCoord = true;

        if (dc == 0 && dr > 0) {
            loA = new LoA(1, 0);
        }
        else if (dc == 0 && dr < 0) {
            loA = new LoA(-1, 0);
        }
        else if (dc < 0 && dr == 0) {
            loA = new LoA(0, -1);
        }
        else if (dc > 0 && dr == 0) {
            loA = new LoA(0, 1);
        }
        else if (dc > 0 && dr > 0 && diagCoord) { //prob
            loA = new LoA(1, 1);
        }
        else if (dc < 0 && dr > 0 && diagCoord) {
            loA = new LoA(1, -1);
        }
        else if (dc > 0 && dr < 0 && diagCoord) {
            loA = new LoA(-1, 1);
        }
        else if (dc < 0 && dc < 0 && diagCoord) {
            loA = new LoA(-1, -1);
        }
        else loA = new LoA(0, 0);
        return loA;
    }

    /**
     * whether a coord is within the board
     */

    public boolean withinBound(int r, int c) {
        if((r >= 0 && r < this.size) && (c >= 0 && c < this.size))
            return true;
        else
            return false;
    }

    /**
     * Legal moves for a coord
     */

    public void showLegalMoves(int r, int c) {
        this.legalMoves.clear(); //???is it problem to clear twice??
        if(withinBound (r, c) && this.matrix[r][c] != Color._____ && this.turn == this.matrix[r][c]) {
            this.setLegalMoves (r, c);
            for (int i = 0; i < this.legalMoves.size (); i++)
                System.out.println ("move " + i + ": " + this.legalMoves.get (i).getR0 () + ", " + this.legalMoves.get (i).getC0 () + " to " + this.legalMoves.get (i).getR1 () + ", " + this.legalMoves.get (i).getC1 ());
        }
        else
            System.out.println ("No legal move!");
    }
    public ArrayList<Move> getLegalMoves(int r, int c) {
        this.legalMoves.clear(); //???is it problem to clear twice??
        if(withinBound (r, c) && this.matrix[r][c] != Color._____ && this.turn == this.matrix[r][c]) {
            this.setLegalMoves (r, c);
            return this.legalMoves;
        }
        else return null;
    }

    public void setLegalMoves(int r, int c) {
        this.legalMoves.clear();
        LoA loA = new LoA(0, 0);
        loA.nextLoA();
        while (loA.getdR() != 0 || loA.getdC() != 0) {
            Move move = this.getLegalMoveInloA(r, c, loA);
            if(move != null)
                this.legalMoves.add(move);
            loA.nextLoA();
        }
    }

    public Move getLegalMoveInloA(int r, int c, LoA loA) {
        Move move = new Move();
        move.setR0(r);
        move.setC0(c);
        int len = noOfCheckerInLoA(move, loA);
        if(loA.getdR() > 0) {
            move.setR1(r+len);
        }
        else if(loA.getdR() < 0) {
            move.setR1(r-len);
        }
        else move.setR1(r);

        if(loA.getdC() > 0) {
            move.setC1(c+len);
        }
        else if(loA.getdC() < 0) {
            move.setC1(c-len);
        }
        else
            move.setC1(c);

        if(withinBound(move.getR1(), move.getC1()) && !isBlocked(move, loA)) {
            move.setMovedC(this.turn);
            move.setReplacedC(this.matrix[move.getR1()][move.getC1()]);
            return move;
        }
        else return null;
    }

    public ArrayList<Move> getAllLegalMoves(Color color) {
        ArrayList<Move> allLegalMoves = new ArrayList<>();
        for (int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if (checker.getColor() == color) {
                int row = checker.getRow();
                int col = checker.getCol();
                LoA loA = new LoA(0, 0);
                loA.nextLoA();
                while (loA.getdR() != 0 || loA.getdC() != 0) {
                    Color trn = this.getTurn();
                    this.setTurn(color);
                    Move move = this.getLegalMoveInloA(row, col, loA);
                    if(move != null)
                        allLegalMoves.add(move);
                    this.turn = trn;
                    loA.nextLoA();
                }
            }
        }
        return allLegalMoves;
    }

    public int evalQuad(Color color) {
        int value = 0;
        for (int i = 0; i < size-1; i++) {
           for(int j = 0; j < size-1; j++) {
               int quad = 0;
               if(this.matrix[i][j] == color)
                   quad++;
               if(this.matrix[i][j+1] == color)
                   quad++;
               if(this.matrix[i+1][j] == color)
                   quad++;
               if(this.matrix[i+1][j+1] == color)
                   quad++;
               if(quad == 3 || quad == 4)
                   value++;
           }
        }
        return value;
    }

    public int noOfSameColorAdjacent(int row, int col, Color color) {
        int value = 0;
        LoA loA = new LoA(0, 0);
        loA.nextLoA();
        while (loA.getdR() != 0 || loA.getdC() != 0) {
            if(this.withinBound(row+loA.getdR(), col+loA.getdC()))
                if(this.matrix[row+loA.getdR()][col+loA.getdC()] == color)
                    value++;
            loA.nextLoA();
        }
        return value;
    }

    public int evalArea(Color color) {
        int row_min = size+1, row_max = -1, col_min = size+1, col_max = -1;
        for (int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if (checker.getColor() == color) {
                int row = checker.getRow();
                int col = checker.getCol();
                if(row < row_min)
                    row_min = row;
                if(row > row_max)
                    row_max = row;
                if(col < col_min)
                    col_min = col;
                if(col > col_max)
                    col_max = col;
            }
        }
        return ((row_max-row_min+1) * (col_max-col_min+1));
    }

    public int evalDensity(Color color) {
        int centerRow = 0, centerCol = 0;
        for(int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if(checker.getColor() == color) {
                centerRow += checker.getRow();
                centerCol += checker.getCol();
            }
        }
        if(color == Color.Black) {
            centerRow /= this.noOfBlack;
            centerCol /= this.noOfBlack;
        }
        else {
            centerRow /= this.noOfWhite;
            centerCol /= this.noOfWhite;
        }
        int distance = 0;
        for(int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if(checker.getColor() == color) {
                distance += this.distance (checker.getRow (), checker.getCol (), centerRow, centerCol);
            }
        }
        return distance;
    }

    public int evalConnectedness(Color color) {
        int value = 0;
        for(int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if(checker.getColor() == color) {
                int row = checker.getRow();
                int col = checker.getCol();
                value += this.noOfSameColorAdjacent(row, col, color);
            }
        }
        return value;
    }

    public int evalMobility(Color color) {
        int value = 0;
        for(int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if(checker.getColor() == color) {
                int row = checker.getRow();
                int col = checker.getCol();
                LoA loA = new LoA(0, 0);
                loA.nextLoA();
                while (loA.getdR() != 0 || loA.getdC() != 0) {
                    Color trn = this.turn;
                    this.turn = color;
                    if(this.getLegalMoveInloA(row, col, loA) != null)
                        value++;
                    this.turn = trn;
                    loA.nextLoA();
                }
            }
        }
        return value;
    }

    public int evalPieceSquareTable(Color color) {
        int value = 0;
        for(int i = 0; i < this.checkers.size(); i++) {
            Checker checker = this.checkers.get(i);
            if(checker.getColor() == color) {
                int row = checker.getRow();
                int col = checker.getCol();
                if (size == 8)
                    value += pieceSquareTable8x8[row][col];
                else if(size == 6)
                    value += pieceSquareTable6x6[row][col];
            }
        }
        return value;
    }

    int pieceSquareTable8x8[][] =
    {

            {-80, -25, -20, -20, -20, -20, -25, -80},
            {-25,  10,  10,  10,  10,  10,  10,  -25},
            {-20,  10,  25,  25,  25,  25,  10,  -20},
            {-20,  10,  25,  50,  50,  25,  10,  -20},
            {-20,  10,  25,  50,  50,  25,  10,  -20},
            {-20,  10,  25,  25,  25,  25,  10,  -20},
            {-25,  10,  10,  10,  10,  10,  10,  -25},
            {-80, -25, -20, -20, -20, -20, -25, -80}

    };
    int pieceSquareTable6x6[][] =
    {

            {-50, -25, -20, -20, -25, -50},
            {-25,  10,  10,  10,  10,  -25},
            {-20,  10,  25,  25,  10,  -20},
            {-20,  10,  25,  25,  10,  -20},
            {-25,  10,  10,  10,  10,  -25},
            {-50, -25, -20, -20, -25, -50}

    };
}
