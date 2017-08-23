
package tdgame;
import java.awt.Point;

//class for creating vector objects and performing calculations on them
//to make handling rotations easier.
public class Vector 
{
    public int xComponent;
    public int yComponent;
    public double magnitude;
    
    //construct a vector from 2 points
    public Vector(Point initial,Point terminal)
    {
        xComponent = terminal.x - initial.x;
        yComponent = terminal.y - initial.y;
        findMagnitude();
    }
    
    public void findMagnitude()
    {
        magnitude =  Math.sqrt(Math.pow(xComponent,2) + Math.pow(yComponent,2));
        
    }
    
    //find the dot product of this vectors u and v
    public static double dotProduct(Vector u, Vector v)
    {
        return u.xComponent*v.xComponent + u.yComponent*v.yComponent;
    }
    
    //find the angle between vectors u and v
    public static double vectorAngle(Vector u, Vector v)
    {
        double magnitudeProduct = u.magnitude * v.magnitude;
        if(magnitudeProduct != 0)
        {
            return Math.acos(dotProduct(u,v) / (magnitudeProduct));
        }
        //return 0 by default
        return 0;
    }
}
