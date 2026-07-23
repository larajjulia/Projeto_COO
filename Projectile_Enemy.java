import java.awt.Color;

public class Projectile_Enemy extends Projectile{
    private final double radius = 2.0;  //tamanho do projétil INIMIGO, cpa é melhor dividir em duas classes: player e inimigo

    public Projectile_Enemy(double X, double Y, double velocityX, double velocityY){
        super(X, Y, velocityX, velocityY);
        color = Color.RED;
    }

    public double getRadius(){return radius;}

    public void visualProjectile(){
        GameLib.setColor(Color.RED);
        GameLib.drawCircle(X, Y, radius);
    }
}
