package tdgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import tdgame.TDFrame.Modes;


//main game panel where drawing is done
public class GamePanel extends JPanel
{
    
    protected Tile[][] grid;
    
    //number of columns
    public static final int xTileCount = 14;
    //number of rows
    public static final int yTileCount = 8;
    
    //experiment with these to get the tiles to show
    public static final int TILE_WIDTH = 58; 
    public static final int TILE_HEIGHT = 67; 
    
    //price of a basic turret
    public static final int BASIC_COST = 200;
    
    public enum TurretType{BASIC}
    protected TurretType currentTurretType;
    protected TDFrame tdf;
    
    //timer used to control how often things redraw
    //turrets implement actionlistener and will be registered with gameTimer
    protected Timer gameTimer;
    //how often the timer ticks (ms)
    public static final int TICK_RATE = 50;
    //number of waves
    public static final int WAVE_COUNT = 5;
    //wave that is currently running
    protected Wave currentWave;
    protected int waveIndex;
    
    
    public GamePanel(TDFrame td) 
    {
        tdf = td;
        //register the action handler with the game timer so animation can occur.
        gameTimer = new Timer(TICK_RATE,new ActionHandler());
        
        
        
        //start the grid of by instantiating a bunch of turret tiles
        grid = new Tile[yTileCount][xTileCount];
        for(int i = 0; i < yTileCount; i++)
        {
            for(int j = 0; j < xTileCount; j++)
            {
                grid[i][j] = new TurretTile(TILE_WIDTH,TILE_HEIGHT,TILE_WIDTH*j,TILE_HEIGHT*i);
            }
        }
        
        //replace some of the turret tiles with hardcoded road tiles
        grid[5][0] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[5][0].position.x,grid[5][0].position.y);
        grid[5][1] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[5][1].position.x,grid[5][1].position.y);
        grid[5][2] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[5][2].position.x,grid[5][2].position.y);
       
        grid[4][2] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[4][2].position.x,grid[4][2].position.y);
        grid[3][2] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[3][2].position.x,grid[3][2].position.y);
        grid[2][2] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[2][2].position.x,grid[2][2].position.y);
        grid[1][2] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[1][2].position.x,grid[1][2].position.y);
        
        grid[1][3] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[1][3].position.x,grid[1][3].position.y);
        grid[1][4] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[1][4].position.x,grid[1][4].position.y);
        grid[1][5] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[1][5].position.x,grid[1][5].position.y);
        
        grid[6][6] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][6].position.x,grid[6][6].position.y);
        grid[5][6] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[5][6].position.x,grid[5][6].position.y);
        grid[4][6] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[4][6].position.x,grid[4][6].position.y);
        grid[3][6] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[3][6].position.x,grid[3][6].position.y);
        grid[2][6] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[2][6].position.x,grid[2][6].position.y);
        grid[1][6] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[1][6].position.x,grid[1][6].position.y);
        
        grid[6][7] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][7].position.x,grid[6][7].position.y);
        grid[6][8] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][8].position.x,grid[6][8].position.y);
        grid[6][9] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][9].position.x,grid[6][9].position.y);
        grid[6][10] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][10].position.x,grid[6][10].position.y);
        grid[6][11] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][11].position.x,grid[6][11].position.y);
        grid[6][12] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[6][12].position.x,grid[6][12].position.y);
        
        grid[5][12] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[5][12].position.x,grid[5][12].position.y);
        grid[4][12] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[4][12].position.x,grid[4][12].position.y);
        
        grid[4][11] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[4][11].position.x,grid[4][11].position.y);
        grid[4][10] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[4][10].position.x,grid[4][10].position.y);
        grid[4][9] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[4][9].position.x,grid[4][9].position.y);
        
        grid[3][9] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[3][9].position.x,grid[3][9].position.y);
        grid[2][9] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[2][9].position.x,grid[2][9].position.y);
        
        grid[2][10] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[2][10].position.x,grid[2][10].position.y);
        grid[2][11] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[2][11].position.x,grid[2][11].position.y);
        grid[2][12] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[2][12].position.x,grid[2][12].position.y);
        
        grid[1][12] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[1][12].position.x,grid[1][12].position.y);
        grid[0][12] = new RoadTile(TILE_WIDTH,TILE_HEIGHT,grid[0][12].position.x,grid[0][12].position.y);
        
        //last tile has the castle as its image
        BufferedImage b = null;
        try{b = ImageIO.read(GamePanel.class.getResource("castleSprite.png"));}
        catch(Exception e){}
        grid[0][12].instanceImage = b;
        
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseHandler());
        
        waveIndex = -1;
        loadNextWave();
        
        
    }
    //start up the next wave 
    public void loadNextWave()
    {
        waveIndex++;
        
        
        switch(waveIndex)
        {
            case 0:
                loadWave0();
                break;
            case 1:
                loadWave1();
                break;
            case 2:
                loadWave2();
                break;
            default:
                JOptionPane.showMessageDialog(this, "You won!");
                break;
        }
        
        
    }
    
    //first and easiest wave
    public void loadWave0()
    {
        
        System.out.println("wave 0");
        BufferedImage enemyImage = null;
        try{enemyImage = ImageIO.read(GamePanel.class.getResource("alienBlackBackground.png"));}
        catch(Exception e ){}
        
        int alienCount = 10;
        Enemy[] aliens = new Enemy[alienCount];
        int hp = 60;
        int vel = 3;
        int value = 15;
        int damage = 10;
        for(int i = 0; i < aliens.length; i++)
        {
            aliens[i] = new Enemy(29,34,-41,349,hp,vel,value,damage,grid,enemyImage,tdf);
        }
        currentWave = new Wave(aliens,1000,tdf);
        gameTimer.addActionListener(currentWave);
    }
    public void loadWave1()
    {
        System.out.println("wave 1");
        BufferedImage enemyImage = null;
        try{enemyImage = ImageIO.read(GamePanel.class.getResource("alienBlackBackground.png"));}
        catch(Exception e ){}
        
        int alienCount = 15;
        Enemy[] aliens = new Enemy[alienCount];
        int hp = 60;
        int vel = 3;
        int value = 15;
        int damage = 10;
        for(int i = 0; i < aliens.length; i++)
        {
            aliens[i] = new Enemy(29,34,-41,349,hp,vel,value,damage,grid,enemyImage,tdf);
        }
        currentWave = new Wave(aliens,1000,tdf);
        gameTimer.addActionListener(currentWave);
    }
    public void loadWave2()
    {
        System.out.println("wave 2");
        BufferedImage enemyImage = null;
        try{enemyImage = ImageIO.read(GamePanel.class.getResource("alienBlackBackground.png"));}
        catch(Exception e ){}
        
        int alienCount = 34;
        Enemy[] aliens = new Enemy[alienCount];
        int hp = 60;
        int vel = 3;
        int value = 15;
        int damage = 10;
        for(int i = 0; i < aliens.length; i++)
        {
            aliens[i] = new Enemy(29,34,-41,349,hp,vel,value,damage,grid,enemyImage,tdf);
        }
        currentWave = new Wave(aliens,1000,tdf);
        gameTimer.addActionListener(currentWave);
    }
    
    public void loadWave3()
    {
        System.out.println("wave 3");
        
        BufferedImage enemyImage = null;
        try{enemyImage = ImageIO.read(GamePanel.class.getResource("alienBlackBackground.png"));}
        catch(Exception e ){}
        
        int alienCount = 30;
        Enemy[] aliens = new Enemy[alienCount];
        int hp = 60;
        int vel = 3;
        int value = 15;
        int damage = 10;
        for(int i = 0; i < aliens.length; i++)
        {
            aliens[i] = new Enemy(29,34,-41,349,hp,vel,value,damage,grid,enemyImage,tdf);
        }
        currentWave = new Wave(aliens,1000,tdf);
        gameTimer.addActionListener(currentWave);
        
    }
    public void loadWave4()
    {
        System.out.println("wave 4");
        BufferedImage enemyImage = null;
        try{enemyImage = ImageIO.read(GamePanel.class.getResource("alienBlackBackground.png"));}
        catch(Exception e ){}
        
        int alienCount = 55;
        Enemy[] aliens = new Enemy[alienCount];
        int hp = 60;
        int vel = 3;
        int value = 15;
        int damage = 10;
        for(int i = 0; i < aliens.length; i++)
        {
            aliens[i] = new Enemy(29,34,-41,349,hp,vel,value,damage,grid,enemyImage,tdf);
        }
        currentWave = new Wave(aliens,700,tdf);
        gameTimer.addActionListener(currentWave);
    }
    
    
    //Start the game timer
    public void startGame()
    {
        gameTimer.start();
    }
    
    //pause the game timer
    public void pauseGame()
    {
        gameTimer.stop();
    }
    
    
    //the turret tiles might be drawing over the projectiles, so they dont display
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        
        //draw all the tiles
        for(int i = 0; i < yTileCount; i++)
        {
            for(int j = 0; j < xTileCount; j++)
            {
                grid[i][j].draw(g);
            }
        }
        ((RoadTile)grid[0][12]).drawWithInstanceImage(g);
        
        
        //draw the turrets(needs to be done after the tiles)
        ActionListener[] listenerArr = gameTimer.getActionListeners();
        for(ActionListener t: listenerArr)
        {
            if(t instanceof Turret)
            {
                ((Turret) t).draw(g);
            }
        }
        
        //draw all the enemies
        for(Enemy e: Turret.targets)
        {
            e.draw(g);
        }
        
    }
    
    //inner class used for animation 
    private class ActionHandler implements ActionListener
    {
        //constantly redraw the panel for animation
        public void actionPerformed(ActionEvent e)
        {
            repaint();
        }
        
    }
    
    
    // handle mouse inputs
    private class MouseHandler extends MouseAdapter  {
        public void mousePressed(MouseEvent e) 
        {
           
        }
        public void mouseClicked(MouseEvent e)
        {
            Point p = new Point(e.getPoint());
            //only place the turret if we're in placing mode(i.e. if a buy turret button was pressed)
            if(tdf.mode == Modes.PLACING)
            {
                
                //determines the tile that the mouse has been clicked in
                //uses integer arithmetic to truncate the decimal(equivalent to taking the floor)
                int xTile = p.x/TILE_WIDTH;
                int yTile = p.y/TILE_HEIGHT;
                
                //if the user has clicked on a turret tile
                if(grid[yTile][xTile] instanceof TurretTile)
                {
                    //reference to the tile clicked
                    TurretTile currentTile = (TurretTile)grid[yTile][xTile];
                    switch(currentTurretType)
                    {
                        case BASIC:
                            //if they have sufficient funds
                            if(tdf.money >= BASIC_COST)
                            {
                                //projectile for a basic turret
                                //image used for the basic turret
                                BufferedImage basicImage = null;
                                try{basicImage = ImageIO.read(GamePanel.class.getResource("turretOnGrassScaled70.png"));}
                                catch(Exception ex){}
                            
                                //add the turret to the tile
                                Turret newBasic = new Turret(currentTile,basicImage);
                                int vel = 15;
                                int dmg = 10;
                                Projectile basicProjectile = new Projectile(10,10,newBasic.barrelTip.x,newBasic.barrelTip.y,vel,dmg);
                                newBasic.setProjectile(basicProjectile);
                                currentTile.addTurret(newBasic);
                                //register the turret with the timer, so it can begin firing
                                gameTimer.addActionListener(newBasic);
                                //update the player's balance
                                tdf.money = tdf.money - BASIC_COST;
                                
                            }
                            break;
                    }
                    //need this for the new turret to draw
                    repaint();
                }
                
                
            }
            //set back to game mode
            tdf.mode = Modes.GAME;
            //update labels
            ButtonPanel.modeLabel.setText("Game Mode");
            ButtonPanel.currencyLabel.setText("Money: $" + tdf.money);
        }
    
        public void mouseDragged(MouseEvent e) 
        {
        
        }
        //when you release the mouse, the game should no longer be in placing mode.
        public void mouseReleased(MouseEvent e) 
        {
            
        }
    }
}
