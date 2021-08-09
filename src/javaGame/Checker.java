package javaGame;

public class Checker {
    private Color color;
    private int row;
    private int col;
    private boolean visited;
    public Checker(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.visited = false;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
