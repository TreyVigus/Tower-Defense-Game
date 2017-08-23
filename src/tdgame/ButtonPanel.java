
package tdgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tdgame.GamePanel.TurretType;
import tdgame.TDFrame.Modes;

//Panel containing set of controls, mainly buttons to be placed on the GamePanel
public class ButtonPanel extends JPanel
{
    protected JButton buyBasic, sellTurret,cheater;
    protected static JButton pause;
    //labels
    protected static JLabel modeLabel,currencyLabel,healthLabel;
    protected boolean running;
    protected GamePanel gp;
    protected TDFrame tdf;
    
    
    public ButtonPanel(GamePanel g,boolean r,TDFrame td) 
    {
        gp = g;
        running = r;
        tdf = td;
        
        // create the buttons
        buyBasic = new JButton("Basic Turret: $" + g.BASIC_COST);
        //sellTurret = new JButton("Sell");
        pause = new JButton("Run");
        cheater = new JButton("Cheat");
        modeLabel = new JLabel("Game Mode");
        currencyLabel = new JLabel("Money: $" + td.money);
        healthLabel = new JLabel("Health: " + td.life);
        
        // register action listeners
        buyBasic.addActionListener(new BuyBasicTurret());
        //sellTurret.addActionListener(new SellTurret());
        pause.addActionListener(new Pause());
        cheater.addActionListener(new Cheat());
        
        // add the buttons to the panel
        this.add(pause);
        this.add(buyBasic);
        //this.add(sellTurret);
        this.add(cheater);
        this.add(currencyLabel);
        this.add(healthLabel);
        this.add(modeLabel);
        
    }
    
    // listeners for buttons on bPanel
    // Buy basic turret
    private class BuyBasicTurret implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            tdf.mode = Modes.PLACING;
            modeLabel.setText("Placing Mode");
            gp.currentTurretType = TurretType.BASIC;
        }
    }
            
    
    // toggle whether the game is running or not
    private class Pause implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            if(tdf.running)
            {
                gp.pauseGame();
                pause.setText("Run");
                tdf.running = false;
            }
            else
            {
                gp.startGame();
                pause.setText("Pause");
                tdf.running = true;
            }
        }
    }
    //pathetic
    private class Cheat implements ActionListener 
    {
       
        public void actionPerformed(ActionEvent e)
        {
            tdf.money = 1000000;
            ButtonPanel.currencyLabel.setText("Money: $" + tdf.money);
            
        }
    }
}
