
package tdgame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;

//the game will load waves of enemies into the GamePanel for the turrets 
//to shoot at.
public class Wave implements ActionListener 
{

    protected ArrayDeque<Enemy> enemies;
    //number of milliseconds until a new enemy is released
    protected int releaseDelay;
    //number of milliseconds since an enemy was released
    protected int timeSinceReleased;
    //frame
    TDFrame tdf;
    //number of enemies initially added
    protected int numEnemies;

    public Wave(Enemy[] enemyArr, int delay,TDFrame td) 
    {
        enemies = new ArrayDeque<Enemy>(enemyArr.length);
        //put the enemies into the queue
        for (Enemy e : enemyArr) 
        {
            enemies.add(e);
        }
        releaseDelay = delay;
        timeSinceReleased = 0;
        tdf = td;
        numEnemies = enemyArr.length;
        
    }

    //release the enemy that is first in line
    public void releaseEnemy() 
    {
        if(timeSinceReleased >= releaseDelay)
        {
            //add the enemy at the front of the line to the targets queue in Turret
            if(!enemies.isEmpty())
            {
                Turret.targets.add(enemies.remove());
            }
            timeSinceReleased = timeSinceReleased - releaseDelay;
        }
    }
    //returns true if the wave is still going
    //false if all the enemies are dead
    public boolean waveRunning()
    {
        //if not all enemies have been sent to the target queue, the wave must
        //be running
        if(Turret.targets.size() < numEnemies)
            return true;
        //go through all of the targets and if even one is still alive return true;
        for(Enemy e: Turret.targets)
        {
            if(!e.hidden)
            {
                return true;
            }
        }
        //return false if all enemies have been loaded into the targets list
        //and if they are all killed/hidden.
        return false;
        
    }

    public void actionPerformed(ActionEvent e) 
    {
        //if the wave is over, load the next wave.
        if(!waveRunning())
        {
            tdf.gPanel.gameTimer.removeActionListener(this);
            tdf.running = false;
            tdf.gPanel.pauseGame();
            tdf.gPanel.loadNextWave();
            ButtonPanel.pause.setText("Run");
            timeSinceReleased = 0;
            //remove the projectiles from the turret's array list at the end
            //of the wave.
            ActionListener[] al = tdf.gPanel.gameTimer.getActionListeners();
            for(ActionListener a: al)
            {
                if(a instanceof Turret)
                {
                    ((Turret) a).clearProjectiles();
                }
            }
        }
        timeSinceReleased = timeSinceReleased + GamePanel.TICK_RATE;
        releaseEnemy();
    }

}
