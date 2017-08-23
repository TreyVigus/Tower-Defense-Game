package tdgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

//Enemies are the targets that the player must try to defeat
public class Enemy extends Sprite
{
   
    protected int health;
    protected int moneyAwarded;
    protected int damage;
    //how many pixels/tick the enemy moves
    protected int velocityMagnitude;
    //velocities in x and y directions.
    //Note: at all times 1 of these will be set to 0 and the other will be
    //set to veloctiyMagnitude, because there is no diagonal movement.
    protected int xVelocity;
    protected int yVelocity;
    //set of tiles on the board
    protected Tile[][] tileArr;
    //indices for the x and y tiles that the enemy is currently on
    protected int currentYIndex;
    protected int currentXIndex;
    protected Tile currentTile;
    //indices for the last tile the enemy was on.
    protected int previousYIndex;
    protected int previousXIndex;
    protected Tile previousTile;
    //indices for the next tile the enemy needs to travel to
    protected int nextYIndex;
    protected int nextXIndex;
    protected Tile nextTile;
    protected TDFrame tdf;
    
   
    public Enemy(int w,int h,int x,int y,int hp,int vel,int money,int dmg,Tile[][] grid,BufferedImage bf,TDFrame td) 
    {
        super(w,h,x,y);
        health = hp;
        velocityMagnitude = vel;
        moneyAwarded = money;
        tileArr = grid;
        damage = dmg;
        tileArr = grid;
        instanceImage = bf;
        
        //first road tile on the map is [5][0],so set the indices to this
        nextYIndex = 5;
        nextXIndex = 0;
        nextTile = tileArr[5][0];
        currentYIndex = 5;
        currentXIndex = -1;
        previousYIndex = 5;
        previousXIndex = -2;
        //determine the xVelocity and yVelocity
        determineVelocity();
        
        tdf = td;
    }
   
    // Enemy will take damage that will affect the current health
    public void takeDamage(int dmg) 
    {
        health = health - dmg;
        if(health <= 0)
        {
            //if the enemy dies, hide it from the screen
            hideSprite();
            //award the player their money
            tdf.money = tdf.money + moneyAwarded;
            ButtonPanel.currencyLabel.setText("Money: $" + tdf.money);
        }
    }
    
    //deal damage to the player.
    public void dealDamage()
    {
        tdf.life = tdf.life - damage;
        ButtonPanel.healthLabel.setText("Health " + tdf.life);
        //GAME OVER
        if(tdf.life <= 0)
        {
            tdf.running = false;
            JOptionPane.showMessageDialog(tdf, "You Lose!");
            System.exit(0);
        }
    }
    
    public void move() 
    {
        
       //update position and set the bounding box
       position.x = position.x + xVelocity;
       position.y = position.y + yVelocity;
       updateBoundingBox();
       
       //if the middle of the enemy reaches the center of the next
       //tile this exectues.
       //NOTE: if the velocity of the enemy is too fast, it will "jump"
       //over the mid box of the tile, and thus never change direction.
       if(getMidBox().contains(nextTile.getCenter()))
       {
            //current tile becomes the previous one
            previousTile = currentTile;
            previousYIndex = currentYIndex;
            previousXIndex = currentXIndex;
            
            //next tile becomes the new current tile
            currentTile = nextTile;
            currentYIndex = nextYIndex;
            currentXIndex = nextXIndex;
            
            //now we need to find the new next tile
            findNextTile();
            //velocity is based on the next tile's location 
            determineVelocity();
        }
       
    }
   
    
    //determines the enemy's velocity based on the next tile it needs to go to.
    //there are only 4 possible movements: left,right,up,down...no diagonals
    public void determineVelocity()
    {
       if(nextYIndex == currentYIndex)
       {
            if(nextXIndex > currentXIndex)
            {
                xVelocity = velocityMagnitude;
                yVelocity = 0;
            }
            else if(nextXIndex < currentXIndex)
            {
                xVelocity = -velocityMagnitude;
                yVelocity = 0;
            }
        }
        else if(nextXIndex == currentXIndex)
        {
            if(nextYIndex > currentYIndex)
            {
                xVelocity = 0;
                yVelocity = velocityMagnitude;
            }
            else if(nextYIndex < currentYIndex)
            {
                xVelocity = 0;
                yVelocity = -velocityMagnitude;
            }
        }
        
    }
    
    //finds the next tile the enemy needs to travel to.
    //there are only 4 possibilities because there is no diagonal movement:
    //tile directly to the right, tile direcly below, tile directly above, 
    //tile directly to the left
    public void findNextTile()
    {
        
        Tile possibleTile;
        //check the tile to the right
        if(currentXIndex + 1 < GamePanel.xTileCount)
        {
            possibleTile = tileArr[currentYIndex][currentXIndex+1];
            //if the tile is the next tile 
            if(determineIfNextTile(possibleTile))
            {
                nextYIndex = currentYIndex;
                nextXIndex = currentXIndex + 1;
                nextTile = possibleTile;
                return;
            }
        }
        //check the tile above
        if(currentYIndex - 1 > -1)
        {
            possibleTile = tileArr[currentYIndex - 1][currentXIndex];
            if(determineIfNextTile(possibleTile))
            {
                nextYIndex = currentYIndex - 1;
                nextXIndex = currentXIndex;
                nextTile = possibleTile;
                return;
            }
        }
        //check the tile below
        if(currentYIndex + 1 < GamePanel.yTileCount)
        {
            possibleTile = tileArr[currentYIndex + 1][currentXIndex];
            if(determineIfNextTile(possibleTile))
            {
                nextYIndex = currentYIndex + 1;
                nextXIndex = currentXIndex;
                nextTile = possibleTile;
                return;
            }
        }
        //check the tile to the left
        if(currentXIndex - 1 > -1)
        {
            possibleTile = tileArr[currentYIndex][currentXIndex-1];
            if(determineIfNextTile(possibleTile))
            {
                nextYIndex = currentYIndex;
                nextXIndex = currentXIndex-1;
                nextTile = possibleTile;
                return;
            }
        }
        
        //these two lines only execute if a new tile can't be found
        //this means they have reached the end of the road.
        dealDamage();
        hideSprite();
        
        
    }
    
    //determine if a potential tile is the next tile on the road.
    //the potential tile must be a road tile and it must not
    //be the enemy's previous tile.
    public boolean determineIfNextTile(Tile t)
    {
        return (t instanceof RoadTile) && (t != previousTile);
    }
    
    
    public void draw(Graphics g)
    {
        if(!hidden)
        {
            g.drawImage(instanceImage,position.x,position.y,width,height,null);
            move();
        }
    }
}
