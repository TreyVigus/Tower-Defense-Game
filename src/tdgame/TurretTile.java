package tdgame;
import java.awt.Graphics;

//Turret tiles are tiles that turrets can be added to
public class TurretTile extends Tile 
{
    protected Turret turret;
    
    //constructs a turret tile with area 0x0, position (0,0), and with no
    //associated turret.
    public TurretTile()
    {
        super();
        turret = null;
    }
    //constructs a turret tile with area w x h and position (x,y).
    //no associated turret.
    public TurretTile(int w, int h, int x, int y)
    {
        super(w,h,x,y);
        turret = null;
    }
    
    //constructs turret tile with area w x h and position (x,y).
    //will be associated with a turret
    public TurretTile(Turret t,int w,int h,int x,int y)
    {
        super(w,h,x,y);
        turret = t;
    }
    
    
    //add a turret to a tile.
    //the tile must not already have a turret, otherwise nothing happens.
    //If you want to add a turret to a tile that already has a turret,
    //you should call removeTurret first.
    public void addTurret(Turret t)
    {
        if(turret == null)
        {
            turret = t;
        }
    }
    
    //remove the turret from the turret tile.
    public void removeTurret()
    {
        turret = null;
    }
    
    
    
    
}
