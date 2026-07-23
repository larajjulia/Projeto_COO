import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Projectile extends Game_Object implements Interface{
    private double velocityX; // velocidade no eixo X
    private double velocityY; // velocidade no eixo Y
    private Color color;
    private double radius = 2.0; //tamanho do projétil INIMIGO, cpa é melhor dividir em duas classes: player e inimigo
    public static List<Projectile> listProjectiles = new ArrayList<Projectile>();



    private Projectile(double X, double Y, double velocityX, double velocityY){
        super(X, Y);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        listProjectiles.add(this);
    }

    public double getX(){return X;}
    public double getY(){return Y;}
    public double getRadius(){return radius;}

    public static Projectile projectilePlayer(double X, double Y, double velocityX, double velocityY){ // atalhos para a criação dos dois tipos de projétil
        Projectile projectile = new Projectile(X, Y, velocityX, velocityY);
        projectile.color = Color.GREEN;
        return projectile; 
    }
    public static Projectile projectileEnemy(double X, double Y, double velocityX, double velocityY){
        Projectile projectile = new Projectile(X, Y, velocityX, velocityY);
        projectile.color = Color.RED;
        return projectile; 
    }


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

    public void visualProjectile(){ // faz a parte visual dos projéteis, ta feio
        
            if(color == Color.GREEN){
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
