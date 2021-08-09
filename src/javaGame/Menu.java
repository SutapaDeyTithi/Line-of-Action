package javaGame;

import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class Menu extends BasicGameState {
    public String mouse = "No input yet!";
    Image logo;
    public static boolean gameOn = false;

    public Menu(int state) {

    }

    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        logo = new Image ("res/loa.png");
    }

    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //graphics.setBackground(new org.newdawn.slick.Color(255, 0, 0));
        graphics.setColor (new org.newdawn.slick.Color(255, 255, 255));
        graphics.drawString (mouse, 100, 20);
        graphics.setColor (new org.newdawn.slick.Color(160, 160, 160));
        graphics.fillRect (710, 210, 140, 40);
        graphics.fillRect (770, 260, 80, 25);
        graphics.fillRect (770, 295, 80, 25);
        graphics.fillRect (710, 335, 140, 40);
        graphics.fillRect (770, 385, 80, 25);
        graphics.fillRect (770, 420, 80, 25);
        graphics.fillRect (710, 460, 140, 40);
        graphics.fillRect (710, 510, 140, 40);
        logo.draw (350, -80);
        graphics.setColor (new org.newdawn.slick.Color(0, 0, 0));
        graphics.drawString ("Play Human vs Human", 674, 220);
        graphics.drawString ("8x8", 780, 265);
        graphics.drawString ("6x6", 780, 300);
        graphics.drawString ("Play AI vs Human", 674, 345);
        graphics.drawString ("8x8", 780, 390);
        graphics.drawString ("6x6", 780, 425);
        graphics.drawString ("How to play", 720, 470);
        graphics.drawString ("Exit", 720, 520);
    }

    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if(!gameOn) {
            Input input = gc.getInput ();
            int mouseX = Mouse.getX ();
            int mouseY = Mouse.getY ();

            mouse = "Position: " + mouseX + ", " + mouseY;

            if (mouseX >= 570 && mouseX <= 850) {
                if (getID () == 0) {
                    if (mouseY >= 485 && mouseY <= 510) {
                        if (input.isMouseButtonDown (0) && !Play.gameStarted) {
                            Play.initVar ();
                            try {
                                Play.initiateGame (8);
                            } catch (IOException e) {
                                e.printStackTrace ();
                            }
                            Play.gameStarted = true;
                            stateBasedGame.enterState (1);
                        }
                    } else if (mouseY >= 450 && mouseY <= 475) {
                        if (input.isMouseButtonDown (0) && !Play.gameStarted) {
                            Play.initVar ();
                            try {
                                Play.initiateGame (6);
                            } catch (IOException e) {
                                e.printStackTrace ();
                            }
                            Play.gameStarted = true;
                            stateBasedGame.enterState (1);
                        }
                    } else if (mouseY >= 360 && mouseY <= 385) {
                        if (input.isMouseButtonDown (0) && !PlayAIVsHuman.gameStarted) {
                            PlayAIVsHuman.initVar ();
                            try {
                                PlayAIVsHuman.initiateGame (8);
                            } catch (IOException e) {
                                e.printStackTrace ();
                            }
                            PlayAIVsHuman.gameStarted = true;
                            stateBasedGame.enterState (2);
                        }
                    } else if (mouseY >= 325 && mouseY <= 350) {
                        if (input.isMouseButtonDown (0) && !PlayAIVsHuman.gameStarted) {
                            PlayAIVsHuman.initVar ();
                            try {
                                PlayAIVsHuman.initiateGame (6);
                            } catch (IOException e) {
                                e.printStackTrace ();
                            }
                            PlayAIVsHuman.gameStarted = true;
                            stateBasedGame.enterState (2);
                        }
                    }
                }
            }
            if (mouseX >= 710 && mouseX <= 850 && mouseY >= 270 && mouseY <= 310) {
                if (input.isMouseButtonDown (0)) {
                    stateBasedGame.enterState (3);
                }
            }
            if (mouseX >= 710 && mouseX <= 850 && mouseY >= 220 && mouseY <= 260) {
                if (input.isMouseButtonDown (0)) {
                    System.exit (0);
                }
            }
        }

    }

    public int getID() {
        return 0;
    }
}
