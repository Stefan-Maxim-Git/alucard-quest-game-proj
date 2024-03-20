package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;
    ContextSettings cs;
    public KeyHandler(ContextSettings cs) {
        this.cs = cs;
    }
    @Override
    public void keyTyped(KeyEvent e) {                  // keyTyped useless for character controls (maybe inventory access?)

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();                      // ASCII code for Key Pressed (for example, generates a code when W is pressed)

        // PAUSE SCREEN CONTROLS:

        if (code == KeyEvent.VK_P) {
            if (cs.gameState == cs.playState) cs.gameState = cs.pauseState;
            else if (cs.gameState == cs. pauseState) cs.gameState = cs.playState;
        }

       // TITLE STATE CONTROLS
        if (cs.gameState == cs.titleState) {
            if (code == KeyEvent.VK_W) {
                cs.ui.optionSelected--;
                if (cs.ui.optionSelected <= 0)
                    cs.ui.optionSelected = 3;
            }
            if (code == KeyEvent.VK_S) {
                cs.ui.optionSelected++;
                if (cs.ui.optionSelected > 3)
                    cs.ui.optionSelected = 1;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (cs.ui.optionSelected) {
                    case 1 -> {
                        cs.gameState = cs.playState;
                        cs.setupGame(0);
                    }
                    case 2 -> {
                        cs.gameState = cs.playState;
                    }
                    case 3 -> System.exit(0);
                }
            }
        }

        // NEXT LEVEL STATE CONTROLS
        if (cs.gameState == cs.pauseState && cs.mobCount[cs.currentLevel] == 0) {
            if (code == KeyEvent.VK_W) {
                cs.ui.optionSelected++;
                if (cs.ui.optionSelected > 2)
                    cs.ui.optionSelected = 1;
            }
            if (code == KeyEvent.VK_S) {
                cs.ui.optionSelected--;
                System.out.println(cs.ui.optionSelected);
                if (cs.ui.optionSelected < 1)
                    cs.ui.optionSelected = 2;
            }

            if (code == KeyEvent.VK_ENTER) {
                System.out.println("Enter Pressed!" + cs.ui.optionSelected);
                switch (cs.ui.optionSelected){
                    case 1 -> {
                        System.out.println("Enter Pressed 2!");
                        if (cs.currentLevel < 3) {
                            cs.gameState = cs.playState;
                            cs.currentLevel++;
                            cs.setupGame(cs.currentLevel);
                        }
                    }
                    case 2 -> System.exit(0);
                }
            }
        } else if (cs.gameState == cs.pauseState) {
            if (code == KeyEvent.VK_W) {
                cs.ui.optionSelected--;
                if (cs.ui.optionSelected <= 0)
                    cs.ui.optionSelected = 3;
            }
            if (code == KeyEvent.VK_S) {
                cs.ui.optionSelected++;
                if (cs.ui.optionSelected > 3)
                    cs.ui.optionSelected = 1;
            }
        }
       // GAME STATE CONTROLS
        if (cs.gameState == cs.playState) {
            if (code == KeyEvent.VK_W) {upPressed = true;}
            if (code == KeyEvent.VK_A) {leftPressed = true;}
            if (code == KeyEvent.VK_S) {downPressed = true;}
            if (code == KeyEvent.VK_D) {rightPressed = true;}
            if (code == KeyEvent.VK_K) {attackPressed = true;}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();                      // ASCII code for Key Pressed (for example, generates a code when W is pressed)

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_K) {
            attackPressed = false;
        }

        if (code == KeyEvent.VK_ENTER) {
            if (cs.gameState == cs.pauseState) {
                switch(cs.ui.optionSelected) {
                    case 1 -> cs.gameState = cs.playState;
                    case 3 -> {
                        cs.gameState = cs.titleState;
                        cs.ui.optionSelected = 1;
                    }
                }
            }
        }

    }
}
