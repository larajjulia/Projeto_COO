import java.util.ArrayList;
import java.util.List;
public abstract class Enemy<T extends Interface> extends Game_Explodable implements Interface{
    protected double velocity; // velocidade geral
    protected double angle; // ângulo de movimentação
    protected double velocityRotation; // velocidade da rotação
    protected double radius;
    // esses estados são comuns a todos os tipos de inimigos
    public static List<Enemy> listEnemies = new ArrayList<Enemy>(); 

    public Enemy(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y);
        this.velocity = velocity;
        this.angle = angle;
        this.velocityRotation = velocityRotation; 
        listEnemies.add(this);
    }

    public double getX(){return X;}
    public double getY(){return Y;}
    public double getRadius(){return radius;}

    public void explode(){ // executar quando ocorrem colisões do inimigo com projéteis do jogador
        explode(500);
    }
    protected boolean hasExploded(long currentTime){ // avalia se o inimigo foi explodido
        if(state == EXPLODING && currentTime > explosionEnd) return true;
        else return false;
    }
    protected abstract boolean onScreen(); // avalia se está inativo ou ativo no momento
    public void updatePosition(){ // atualiza posições X e Y e ângulo calculando o deslocamento
        if(onScreen()){
            X += velocity * Math.cos(angle) * delta;
            Y += velocity * Math.cos(angle) * delta * (-1.0);
            angle += velocityRotation * delta;
        }
        else listEnemies.remove(this);
    }

    protected abstract void addEnemy();
    protected abstract void readyToShoot(Player player); 

    protected abstract void visualEnemies(); // faz a parte visual dos inimigos

    public void collisionEnemy(T element){
        double dist = getDist(element);
	    if(dist < element.getRadius()){
			explode();
		}
    }
}
