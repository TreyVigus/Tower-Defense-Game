package tdgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//turrets fire projectiles in order to defeat enemies
public class Projectile extends Sprite
{
    //magnitude of the projectile's velocity in pixels/timer tick
    protected int velocityMagnitude;
    //xVelocity and yVeloctiy are calculated 
    //using the angle of the turret's barrel and
    //velocityMagnitude.
        //xVelocity is how far it travels in the x direction for each tick of the turret timer
        //yVelocity is how far it travels in the y direction for each tick of the turret timer.
    protected int xVelocity;
    protected int yVelocity;
    //damage the projectile deals when hitting an enemy
    protected int damage;
    
    
    //template for the projectileType variable in the Turret class
    public Projectile(int w,int h,int x,int y,int vel,int d)
    {
        super(w,h,x,y);
        velocityMagnitude = vel;
        xVelocity = 0;
        yVelocity = 0;
        damage = d;
        
    }
    
    //constructor that creates a copy of a given projectile p.
    public Projectile(Projectile p)
    {
        instanceImage = p.instanceImage;
        width = p.width;
        height = p.height;
        position.x = p.position.x;
        position.y = p.position.y;
        boundingBox = new Rectangle(position.x,position.y,width,height);
        velocityMagnitude = p.velocityMagnitude;
        xVelocity = p.xVelocity;
        yVelocity = p.yVelocity;
        damage = p.damage;
    }
    
    //return true if the projectile enters the enemy's hitbox.
    public boolean isColliding(Enemy e)
    {
        if(this.boundingBox.intersects(e.boundingBox))
        {
            //if the projectile collides, it needs to be removed from the screen
            hideSprite();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //draw the projectile
    public void draw(Graphics g)
    {
        if(!hidden)
        {
            //hitbox needs to stay with the projectile
            updateBoundingBox();
            g.setColor(Color.BLUE);
            g.fillOval(position.x,position.y,width,height);
        }
    }
    
    
}
