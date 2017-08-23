/*
 * The main game window that is used to access the gui and view the game
 */
package tdgame;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Main window that the game panel sits on
//Also has several fields that represent the player
public class TDFrame extends JFrame
{
    public static final int WINDOW_WIDTH = 815; 
    public static final int WINDOW_HEIGHT = 600; 
    public static final int START_LIFE = 100;
    public static final int START_MONEY = 400;
    //all possible modes the game can be in
    public enum Modes {GAME, PLACING, SELLING}
    // panel with buttons to trigger certian actions
    protected ButtonPanel bPanel;
    // Panel that displays the game as it runs
    protected GamePanel gPanel;
    //player's currency
    protected int money;
    //life total
    protected int life;
    //mode the game is in
    protected Modes mode;
    //whether or not the game is running
    protected boolean running;
    protected Wave nextWave;
    
    //default constructor
    public TDFrame() 
    {
        // set the title of the window
        setTitle("Tower Defense");
        
        // Set close action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // BorderLayout manager
        setLayout(new BorderLayout());
        
        
        mode = Modes.GAME;
        life = START_LIFE;
        money = START_MONEY;
        running = false;
        
        
        // Create the custom panels
        gPanel = new GamePanel(this);
        bPanel = new ButtonPanel(gPanel,running,this);
        
        
        // add components to the content pane
        add(gPanel, BorderLayout.CENTER);
        add(bPanel, BorderLayout.SOUTH);
        
        
        // make window static size to prevent resizing
        this.setResizable(false);
        
        // set window size
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // make window visible
        setVisible(true);
        
        JOptionPane.showMessageDialog(this, "\nInstructions: Buy turrets by clicking the buy turret button. (if you have enough money)\n"
                + "After pressing the button, click a grass tile where you want to place your turret.\n"
                + "Killing enemies will give you money, which you can use to buy more turrets.\n"
                + "The game will pause after each wave.\nPress run to start the next wave. "
                + "(the game can also be paused manually by pressing the pause button.)\n"
                + "You lose health if the enemies reach the end of the path. Good luck!");
                
                
        
    }
    
    
   
}
