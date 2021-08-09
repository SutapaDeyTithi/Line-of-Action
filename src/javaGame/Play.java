package javaGame;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;
import java.util.ArrayList;

import java.io.IOException;

public class Play extends BasicGameState{
    String mouse = "Pos: ";
    public static GameBackend game01;
    public static int size;
    public static int startIndexRow = Integer.MIN_VALUE, startIndexCol = Integer.MIN_VALUE;
    public static int destIndexRow = Integer.MIN_VALUE, destIndexCol = Integer.MIN_VALUE;
    public static Boolean gameStarted = false, showLegalMoves = false, endGame = false, moving = false;

    public Play(int state) {

    }
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        setMenu (graphics);
        if(gameStarted) {
            initBoard (graphics);
            showCheckers (graphics);

            if (endGame) {
                graphics.setColor (new org.newdawn.slick.Color (255, 255, 255));
                graphics.drawString (this.game01.getBoard ().getWinner () + " has won!", 450, 100);
            } else {
                if (showLegalMoves) {
                    this.showLegalMoves (graphics, this.startIndexRow, this.startIndexCol);
                    giveHumanMove (graphics);
                }
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gc.getInput();
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();

        if (input.isMouseButtonDown(0) && getID() == 1) {
            if (!endGame) {
                if (showLegalMoves && destIndexRow == Integer.MIN_VALUE && destIndexCol == Integer.MIN_VALUE) {
                    destIndexRow = this.getRowIndex(ypos);
                    destIndexCol = this.getColInedex(xpos);
                }
                if (destIndexRow == Integer.MIN_VALUE && destIndexCol == Integer.MIN_VALUE && !showLegalMoves) {
                    if(game01.getBoard().getTurn() == game01.getBoard().getColor(this.getRowIndex(ypos), this.getColInedex(xpos))) {
                        startIndexRow = this.getRowIndex(ypos);
                        startIndexCol = this.getColInedex(xpos);
                        this.showLegalMoves = true;
                        mouse = "World position: " + xpos + ", " + ypos + " (" + startIndexRow + ", " + startIndexCol + ")";
                    }
                }
            }
            if (input.isMouseButtonDown(0) && getID() == 1) {
                if (xpos >= 910 && xpos <= 1010 && ypos >= 735 && ypos <= 765) {
                    //this.init (gc, stateBasedGame);
                    gameStarted = false;
                    Menu.gameOn = false;
                    stateBasedGame.enterState(0);
                }
            }
        }
    }

    public int getRowIndex(int ypos) {
        ypos -= 830;
        int col = (ypos+150) / (-62);
        col--;
        if(col >= 0 && col < this.size)
            return col;
        else
            return -1;
    }

    public int getColInedex(int xpos) {
        if(xpos < 250)
            return -1;
        int row = (xpos-250) / 62;
        if(row >= 0 && row < this.size)
            return row;
        else
            return -1;
    }

    public int getYPos(int rowIndex) {
        return (150 + 62 * rowIndex);
    }

    public int getXPos(int colIndex) {
        return (250 + 62 * colIndex);
    }
    public int getID() {
        return 1;
    }

    public static void initiateGame(int boardSize) throws IOException{
        Menu.gameOn = true;
        Player player01 = new Player(1, Color.Black);
        Player player02 = new Player(2, Color.White);
        size = boardSize;
        Board board = new Board(size);
        game01 = new GameBackend (board, player01, player02, size);
        game01.getBoard ().setTurn(game01.player1.getColor());
    }

    public void setMenu(Graphics graphics) {
        graphics.setColor (new org.newdawn.slick.Color(160, 160, 160));
        graphics.fillRect (910, 5, 100, 30);
        graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
        graphics.drawString ("New game", 920, 12);
        graphics.setColor (new org.newdawn.slick.Color(255, 255, 255));
        if(mouse != null)
            graphics.drawString (mouse, 100, 20);
    }
    public void initBoard(Graphics graphics) {
        // initial grey board only
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                graphics.setColor (new org.newdawn.slick.Color (64, 64, 64));
                graphics.fillRect ((250 + 62 * i), (150 + 62 * j), 60, 60);
                graphics.drawString (j+"",225, (170 + 62 * j));
            }
            graphics.drawString (i+"",(280 + 62 * i), 125);
        }
    }

    public boolean showCheckers(Graphics graphics) {
        /**
         * checker.row = mouse.y
         * checker.col = mouse.x
         */
        graphics.setColor (new org.newdawn.slick.Color(64, 64, 64));
        graphics.fillRect (435, 75, 150, 45);
        graphics.setColor (new org.newdawn.slick.Color(255, 255, 255));
        graphics.drawString (this.game01.getBoard ().getTurn () + "'s turn!", 450, 85);

        for(int i = 0; i < game01.getBoard ().getCheckers ().size (); i++) {
            Checker checker = game01.getBoard ().getCheckers ().get (i);
            if(checker.getColor () == Color.Black) {
                graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
                graphics.fillOval ((255 + 62 * checker.getCol()), (155 + 62 * checker.getRow ()), 50, 50);
            }
            else if(checker.getColor () == Color.White) {
                graphics.setColor (new org.newdawn.slick.Color(255, 255, 255));
                graphics.fillOval ((255 + 62 * checker.getCol()), (155 + 62 * checker.getRow ()), 50, 50);
            }
        }
        return true;
    }

    public void showLegalMoves(Graphics graphics, int matrixRow, int matrixCol) {
        if(game01.withinBound(matrixRow, matrixCol)) {
            ArrayList<Move> legalMoves;
            legalMoves = this.game01.getBoard().getLegalMoves(matrixRow, matrixCol);

            if(legalMoves == null) {
                System.out.println(matrixRow + " -> " + matrixCol);
                graphics.setColor(new org.newdawn.slick.Color(64, 64, 64));
                graphics.fillRect(435, 25, 150, 45);
                graphics.setColor(new org.newdawn.slick.Color(255, 255, 255));
                graphics.drawString("No legal move!", 450, 35);
            }

            int startX = this.getXPos(matrixCol) + 30;
            int startY = this.getYPos(matrixRow) + 30;
            int destX, destY;

            if (legalMoves != null) {
                for (int i = 0; i < legalMoves.size(); i++) {
                    destX = this.getXPos(legalMoves.get(i).getC1()) + 30;
                    destY = this.getYPos(legalMoves.get(i).getR1()) + 30;
                    graphics.drawGradientLine(startX, startY, new org.newdawn.slick.Color(255, 255, 0), destX, destY, new org.newdawn.slick.Color(128, 255, 0));
                }
            }
        }
    }

    public void giveHumanMove(Graphics graphics) {
        if (destIndexRow != Integer.MIN_VALUE && destIndexCol != Integer.MIN_VALUE) {
            graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
            graphics.fillRect (435, 25, 150, 45);

            Move move = this.game01.getLegalMove (startIndexRow, startIndexCol, destIndexRow, destIndexCol);
            if (move != null) {
                game01.getBoard ().showLegalMoves (startIndexRow, startIndexCol);
                System.out.println(game01.getBoard ().getTurn () + " moved from " + move.getR0 () + ", " + move.getC0 () + " to " + move.getR1 () + ", " + move.getC1 ());

                graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
                graphics.fillRect (435, 25, 150, 45);

                game01.getBoard ().makeMove (move);
                if (this.game01.getBoard ().IsEndgame ()) {
                    this.endGame = true;
                }
            }
            graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
            graphics.fillRect (435, 25, 150, 45);
            destIndexRow = Integer.MIN_VALUE;
            destIndexCol = Integer.MIN_VALUE;
            this.showLegalMoves = false;

        }
    }

    public static void initVar() {
        startIndexRow = Integer.MIN_VALUE;
        startIndexCol = Integer.MIN_VALUE;
        destIndexRow = Integer.MIN_VALUE;
        destIndexCol = Integer.MIN_VALUE;
        showLegalMoves = false;
        endGame = false;
        gameStarted = false;
    }
}
