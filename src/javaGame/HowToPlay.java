package javaGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class HowToPlay extends BasicGameState {
    public String mouse = "No input yet!";

    public HowToPlay(int state) {

    }

    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
    }

    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor (new org.newdawn.slick.Color(255, 255, 255));
        graphics.drawString (mouse, 100, 20);
        graphics.setColor (new org.newdawn.slick.Color(160, 160, 160));
        graphics.fillRect (910, 5, 100, 30);
        graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
        graphics.drawString ("Back", 920, 12);
    }

    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gc.getInput ();
        int mouseX = Mouse.getX ();
        int mouseY = Mouse.getY ();

        mouse = "Position: " + mouseX + ", " + mouseY;
        if(mouseX >= 910 && mouseX <= 1010 && mouseY >= 735 && mouseY <= 765) {
            if (input.isMouseButtonDown (0)) {
                stateBasedGame.enterState (0);
            }
        }
    }

    public int getID() {
        return 3;
    }
}
