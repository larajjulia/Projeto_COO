import java.util.ArrayList;
import java.util.List;

public abstract class Powerup extends Enemy{
    public static List<Powerup> listPowerup = new ArrayList<Powerup>(); 
    
    public Powerup(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        listPowerup.add(this);
    }

    public abstract void powerUp(Player player);
    public abstract void powerUpEnd(Player player);

    


}
