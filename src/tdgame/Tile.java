
package tdgame;


import javax.imageio.ImageIO;


//the entire map is made up of Tiles (either turret or road)
public class Tile extends Sprite 
{
   //only needed to initialize this image once, so doing in the constructor was
   //wasteful.
   static
   {
       //default image for a tile is a simple grass image
       try
       {
            image = ImageIO.read(Tile.class.getResource("grass.png"));
       }
       catch(Exception e)
       {
           
       }
   }
    

//construct a tile with area 0x0, and position = (0,0)
   public Tile()
   {
       super();
   }
   //use when you want to construct a tile with a given width,height, and position.
   //image will be the default grass image.
   public Tile(int w,int h,int x,int y)
   {
       super(w,h,x,y);
   }
   
}
