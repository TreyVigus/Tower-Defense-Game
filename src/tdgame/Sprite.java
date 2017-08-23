package tdgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//Anything that needs to be drawn in the game (e.g. tiles, turrets, projectiles, enemies,
//etc are subclasses of Sprite
public abstract class Sprite 
{
    //if the image is the same for all instances, using this will save memory
    protected static BufferedImage image = null;
    //if the image may vary for each instance use this
    protected BufferedImage instanceImage;
    protected int width, height;
    protected Point position;
    protected Rectangle boundingBox;
    //if set to true the sprite will basically not exist(wont be drawn and cant be collided with)
    protected boolean hidden;
    
    
    public Sprite()
    {
        width = 0;
        height = 0;
        position = new Point(0,0);
        boundingBox = new Rectangle(0,0,0,0);
        instanceImage = null;
        hidden = false;
    }
    public Sprite(int w, int h, int x, int y)
    {
        width = w;
        height = h;
        position = new Point(x,y);
        boundingBox = new Rectangle(x,y,w,h);
        hidden = false;
    }
    
    //update the Sprite's hitbox with its current location
    public void updateBoundingBox()
    {
        boundingBox.setLocation(position);
    }
    
    public void draw(Graphics g)
    {
        if(!hidden)
        {
            g.drawImage(image,position.x,position.y,width,height,null);
        }
    }
    //useful for debugging
    public void drawBoundingBox(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
    
    //when the sprite is hidden, it won't be drawn and it's bounding box has area 0
    //so it cannot be intersected(i.e. can't be collided with)
    public void hideSprite()
    {
        hidden = true;
        boundingBox = new Rectangle(0,0,0,0);
    }
    
    //returns the center of the sprite
    public Point getCenter()
    {
        int x = position.x + width/2;
        int y = position.y + height/2;
        return new Point(x,y);
    }
    
    //returns a rectangle sitting in the center of the sprite
    public Rectangle getMidBox()
    {
       int w = width/3;
       int h = height/3;
       int x = position.x + w;
       int y = position.y + h;
       return new Rectangle(x,y,w,h);
        
    }
            
    
    
}
