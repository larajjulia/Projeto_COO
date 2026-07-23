import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Projectile{
    protected double state = INACTIVE;
    protected double X;
    protected double Y;
    private double velocityX; // velocidade no eixo X
    private double velocityY; // velocidade no eixo Y
    private Color color;
    public static List<Projectile> listProjectiles = new ArrayList<Projectiles>();


    private Projectile(double X, double Y, double velocityX, double velocityY, Color color){
        state = ACTIVE;
        this.X = X;
        this.Y = Y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        listProjectiles.add(this);
    }

    public static Projectile projectilePlayer(double X, double Y, double velocityX, double velocityY, Color color){ // atalhos para a criação dos dois tipos de projétil
        return new Projectile(X, Y, velocityX, velocityY, Color.GREEN);
    }
    public static Projectile projectileEnemy(double X, double Y, double velocityX, double velocityY, Color color){
        return new Projectile(X, Y, velocityX, velocityY, Color.RED);
    }


    public void updateState(int delta){
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

    public void visualProjectile(){ // faz a parte visual dos projéteis, ta feio
        
            if(projectile.color = Color.GREEN){
                GameLib.setColor(Color.GREEN);
                GameLib.drawLine(X, Y - 5, X, Y + 5);
                GameLib.drawLine(X - 1, Y - 3, X - 1, Y + 3);
                GameLib.drawLine(X + 1, Y - 3, X + 1, Y + 3);
            }
            else{
                GameLib.setColor(Color.RED);
                GameLib.drawCircle(X, Y, radius);
            }
        
    }

}