package tdgame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;


//Turrets are weapons which fire projectiles and track enemies.
//The player will purchase these in order to defend his/her base.
public class Turret extends Sprite implements ActionListener
{
    //number of milliseconds between turret firings.
    protected int fireDelay;
    //tracks how long it's been since the turret last fired.
    protected int timeSinceFired;
    //all projectiles fired will be copies of this.
    protected Projectile projectileType;
    //barrel angle in degrees
    protected double barrelAngle;
    //holds all projectiles until they leave the screen or 
    //hit an enemy.
    protected ArrayList<Projectile> projectiles;
    //turrets have to rotate and translate, so an affine transform object is needed
    protected AffineTransform at;
    //keeps track of the position of the tip of the barrel for drawing projectiles....
    protected Point barrelTip;
    //array of possible targets for ALL turrets
    public static ArrayDeque<Enemy> targets = new ArrayDeque<Enemy>();
    //the point the turret is currently aiming at
    protected Point targetPosition;
    //vector pointing straight downwards(angle between this and the enemy will be used
    //for rotation).
    Vector downwardVector;
    double translationXDistance;
    double translationYDistance;
    
    
    
    
    
    
  
    //Make sure tt is the tile the turret is attached to
    public Turret(TurretTile tt,BufferedImage bf)
    {
        super();
        instanceImage = bf;
        
        
        //default fire delay is 1000ms (once per second)
        fireDelay = 1000;
        //straight down is 0 radians.
        barrelAngle = 0;
        projectiles = new ArrayList<Projectile>();
        timeSinceFired = 0;
        
        //DRAWING STUFF
        //turret's position should be the same as the tile it's attached to
        position = tt.position;
        //Initial transformation for the first drawing of the turret
        at = new AffineTransform();
        at.setToIdentity();
        
        //this places the turret near the middle of the tile
        translationXDistance = position.x + tt.width/5;
        translationYDistance = position.y + tt.height/5;
        at.translate(translationXDistance, translationYDistance);
        
        //aproximate location of the barrel's tip (messed with these values a bit to get it perfect)
        barrelTip = new Point();
        barrelTip.x = (int)translationXDistance + instanceImage.getWidth()/2 - 4;
        barrelTip.y = (int)translationYDistance + instanceImage.getHeight()/2;
        
        //point right below the turret to construct a downward vector
        Point downwardPosition = new Point(position.x, position.y+10);
        downwardVector = new Vector(position, downwardPosition);
        
    }
    
    public void setProjectile(Projectile p)
    {
        projectileType = new Projectile(p);
    }
    
   
    
    //draw the turret and its projectiles
    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        
        
        at.setToIdentity();
        at.translate(translationXDistance, translationYDistance);
        //rotate according to barrel angle (not sure why this has to be -1*barrelAngle)
        at.rotate(-1*barrelAngle,instanceImage.getWidth()/2,instanceImage.getHeight()/2);
        g2d.drawImage(instanceImage, at, null);
        
        for(Projectile p: projectiles)
        {
            p.draw(g);
        }
       
    }
    
    
    //the magnitude of the projectile's velocity is p.velocity
    //this function gets the x and y components of the velocity 
    //for drawing.
    public void fireProjectile()
    {
        //tracking here
        trackTarget();
        //fire once every time the fire delay is reached
        if(timeSinceFired >= fireDelay)
        {
            //instantiate a new projectile. It's starting position(and where it will be
            //drawn) is the same as the turret's starting position.
            Projectile p = new Projectile(projectileType);
            p.xVelocity = (int) (p.velocityMagnitude * Math.sin(barrelAngle));
            p.yVelocity = (int) (p.velocityMagnitude * Math.cos(barrelAngle));
            projectiles.add(p);
            //update timeSinceFired
            timeSinceFired = timeSinceFired - fireDelay;
        }
        
    }
    //find the turret's target.
    //prioritizes the first enemy in the targets queue
    public void trackTarget()
    {
        //traverse the queue backwards(first target is prioritized)
        Iterator it = targets.descendingIterator();
        while(it.hasNext())
        {
            Enemy e = (Enemy)(it.next());
            if(!e.hidden)
            {
                //new target's location
                Point newTargetPosition = e.position;
                //vector between the turret and the new target.
                //Vector u = new Vector(position,newTargetPosition);
                Vector u = new Vector(barrelTip,newTargetPosition);
                
                //get the angle between the vector pointing to the enemy
                //and the turret's downward vector.
                double theta = Vector.vectorAngle(u,downwardVector);
                
                //if the target is to the right of the turret, then the angle between
                //vectors is the angle we want
                if(newTargetPosition.x >= position.x)
                {
                    barrelAngle = theta;
                }
                //if the enemy is to the left, we want the negation of the angle
                else if(newTargetPosition.x < position.x)
                {
                    barrelAngle = -1 * theta;
                }
                
            }
        }
        
    }
    
    
    //update the position of each projectile in the projectiles array list
    public void updateProjectiles()
    {
        for(Projectile p: projectiles)
        {
            p.position.x = p.position.x + p.xVelocity;
            p.position.y = p.position.y + p.yVelocity;
            detectCollisions(p);
        }
    }
    public void detectCollisions(Projectile p)
    {
        for(Enemy e: targets)
        {
            //if the projectile and the enemy collide
            if(p.isColliding(e))
            {
                //deal damage to the enemy
                e.takeDamage(p.damage);
                //a projectile can only collide once, so break the loop
                break;
            }
        }
    }
    
   //call at the end of the wave 
    public void clearProjectiles()
    {
        projectiles.clear();
    }
    
    
    //every tick of the timer causes another projectile to fire and updates
    //the current projectiles
    public void actionPerformed(ActionEvent e)
    {
        //Update the timeSinceFired with the tickrate variable used by the 
        //timer in game panel
        timeSinceFired = timeSinceFired + GamePanel.TICK_RATE;
        fireProjectile();
        updateProjectiles();
    }
}

