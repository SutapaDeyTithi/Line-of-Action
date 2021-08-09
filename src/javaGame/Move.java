package javaGame;

public class Move {
    private int r0;
    private int c0;
    private int r1;
    private int c1;
    private Color movedC, replacedC;
    public int cost = 0;

    public Move() {
    }

    public Move(int r0, int c0,  int r1, int c1, Color movedC, Color replacedC) {
        this.r0 = r0;
        this.c0 = c0;
        this.r1 = r1;
        this.c1 = c1;
        this.movedC = movedC;
        this.replacedC = replacedC;
    }
    public Move(int r0, int c0,  int r1, int c1) {
        this.r0 = r0;
        this.c0 = c0;
        this.r1 = r1;
        this.c1 = c1;
    }

    public int getC0() {
        return c0;
    }

    public void setC0(int c0) {
        this.c0 = c0;
    }

    public int getR0() {
        return r0;
    }

    public void setR0(int r0) {
        this.r0 = r0;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public Color getMovedC() {
        return movedC;
    }

    public void setMovedC(Color movedC) {
        this.movedC = movedC;
    }

    public Color getReplacedC() {
        return replacedC;
    }

    public void setReplacedC(Color replacedC) {
        this.replacedC = replacedC;
    }

    public int noOfSquaresMoved() {
        int d1 = Math.abs(this.r0 - this.r1);
        int d2 = Math.abs(this.c0 - this.c1);
        return Math.max(d1, d2);
    }
    public String strMove() {
        String str = "Move from (" + this.r0 + ", " + this.c0 + ") to (" + this.r1 + ", " + this.c1 + ")";
        return str;
    }

}
