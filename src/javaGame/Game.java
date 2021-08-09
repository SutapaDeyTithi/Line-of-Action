package javaGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
    public static final String gameName = "Lines Of Action!";
    public static final int menu = 0;
    public static final int play = 1;
    public static final int playAiVsHuman = 2;
    public static final int howToPlay = 3;

    public Game(String gameName) {
        super(gameName);
        this.addState (new Menu(menu));
        this.addState (new Play(play));
        this.addState (new PlayAIVsHuman (playAiVsHuman));
        this.addState (new HowToPlay (howToPlay));
    }

    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState (menu).init (gc, this);
        this.getState (play).init (gc, this);
        this.getState (playAiVsHuman).init (gc, this);
        this.getState (howToPlay).init (gc, this);
        this.enterState (menu);
    }

    public static void main(String args[]) {
        AppGameContainer appGameContainer;
        try {
            appGameContainer = new AppGameContainer (new Game (gameName));
            appGameContainer.setDisplayMode (1024, 768, false);
            appGameContainer.start ();
        }
        catch (SlickException e) {
            e.printStackTrace ();
        }
    }
}
