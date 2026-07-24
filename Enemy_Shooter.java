import java.util.ArrayList;
import java.util.List;

public abstract class Enemy_Shooter extends Enemy{
    public static List<Enemy_Shooter> listEnemyShooters = new ArrayList<Enemy_Shooter>(); 
    
    protected Enemy_Shooter(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        listEnemyShooters.add(this);
    }

    protected abstract void readyToShoot(Player player);

}
