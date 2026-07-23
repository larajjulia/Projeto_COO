import java.awt.Color;

public class Projectile_Player extends Projectile{
    
    public Projectile_Player(double X, double Y, double velocityX, double velocityY){
        super(X, Y, velocityX, velocityY);
        color = Color.GREEN;
    }

    public void visualProjectile(){
        GameLib.setColor(Color.GREEN);
        GameLib.drawLine(X, Y - 5, X, Y + 5);
        GameLib.drawLine(X - 1, Y - 3, X - 1, Y + 3);
        GameLib.drawLine(X + 1, Y - 3, X + 1, Y + 3);
    }
}
