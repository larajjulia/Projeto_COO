import java.awt.Color;

public class Projectile_Enemy extends Projectile{
    private double radius = 2.0;

    public Projectile_Enemy(double X, double Y, double velocityX, double velocityY){
        super(X, Y, velocityX, velocityY);
        color = Color.RED;
    }

    public void changeSize(double size){radius = size;}

    public double getRadius(){return radius;}

    public void visualProjectile(){
        GameLib.setColor(Color.RED);
        GameLib.drawCircle(X, Y, radius);
    }
}
