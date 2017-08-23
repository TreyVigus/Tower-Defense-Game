package tdgame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//Road Tiles will compose the path the enemies travel
public class RoadTile extends Tile 
{
    protected static BufferedImage roadImage;
    

    //overwrites the grass image inherited from tile with 
    //a simple road image.
    static
    {
        try
        {
            roadImage = ImageIO.read(RoadTile.class.getResource("road.png"));
        }
        catch(Exception e)
        {
            
        }
    }
    
    //constructs a turret tile with area w x h and position (x,y).
    public RoadTile(int w, int h, int x, int y)
    {
       super(w,h,x,y);
    }
    
    //override parent's draw method to draw the roadImage instead of the grass image
    public void draw(Graphics g)
    {
        g.drawImage(roadImage,position.x,position.y,width,height,null);
    }
    
    //only used by the last tile for the castle
    public void drawWithInstanceImage(Graphics g)
    {
       g.drawImage(instanceImage,position.x,position.y,width,height,null);
    }
    
    
}
