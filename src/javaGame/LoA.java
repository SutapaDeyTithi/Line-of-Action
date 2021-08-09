package javaGame;

public class LoA {
    private int dr, dc;
    public LoA(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }
    public void nextLoA() {
        if(this.dr == 0 && this.dc == 0)
            this.dr = 1;
        else if(this.dr == 1 && this.dc == -1) {
            this.dr = this.dc = 0;
        }
        else if(this.dr+1 <= 1 && this.dc == -1)
            this.dr++;
        else if(this.dr-1 >= -1 && this.dc == 1)
            this.dr--;
        else if(this.dr + this.dc == 0)
            this.dc = 0;
        else if(this.dc == 0)
            this.dc = this.dr;
    }
    public int getdR() {
        return dr;
    }

    public void setdR(int dr) {
        this.dr = dr;
    }

    public int getdC() {
        return dc;
    }

    public void setC(int dc) {
        this.dc = dc;
    }
}
