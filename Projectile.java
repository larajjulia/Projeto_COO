import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Projectile extends Game_Object implements Interface{
    protected double velocityX; // velocidade no eixo X
    protected double velocityY; // velocidade no eixo Y
    protected Color color;
    private final double radius = 2.0;
    public static List<Projectile> listProjectiles = new ArrayList<Projectile>();



    protected Projectile(double X, double Y, double velocityX, double velocityY){
        super(X, Y);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        listProjectiles.add(this);
    }

    public double getX(){return X;}
    public double getY(){return Y;}
    public double getRadius(){return radius;}


    public void updateState(){
        if(Y < 0 || Y > GameLib.HEIGHT){ // se projétil saiu da tela (para qualquer extremidade), ele desativa
            state = INACTIVE;
            listProjectiles.remove(this);
        }  
        else{
            // atualiza posições X e Y calculando o deslocamento
            X += velocityX * delta;
            Y += velocityY * delta;
        }
    
    }

    public abstract void visualProjectile();
}
