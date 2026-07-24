import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Game_Explodable implements Interface{
    // esses estados são comuns a todos os tipos de inimigos
    protected double velocity; // velocidade geral
    protected double angle; // ângulo de movimentação
    protected double velocityRotation; // velocidade da rotação
    protected double radius; // raio do inimigo
    

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
    public abstract void nextEnemy(long time);

    public void explode(){ // executar quando ocorrem colisões do inimigo com projéteis do jogador
        explode(500);
    }

    protected boolean hasExploded(){ // avalia se o inimigo foi explodido
        if(state == EXPLODING && Game_Object.currentTime > explosionEnd) return true;
        else return false;
    }

    protected boolean onScreen(){ // avalia se está inativo ou ativo no momento
        if(hasExploded() || Y < -10 || X > GameLib.HEIGHT + 10) state = INACTIVE; // se o inimigo tiver saído da tela ou explodido, está inativo
        if(state == INACTIVE) return false;
        else return true;
    }; 

    public void updatePosition(){ // atualiza posições X e Y e ângulo calculando o deslocamento
        if(onScreen() && state != INACTIVE){
            X += velocity * Math.cos(angle) * delta;
            Y += velocity * Math.sin(angle) * delta * (-1.0);
            angle += velocityRotation * delta;
        }
        else listEnemies.remove(this);
    }

    protected abstract void addEnemy(); 
    protected abstract void visualEnemies(); // faz a parte visual dos inimigos

    public void collisionEnemy(Interface element){
        if (state != ACTIVE) return;
        double dist = getDist(element);
	    if(dist < (getRadius() + element.getRadius())*0.8){
			explode();
		}
    }

    public void removeEnemies(){

    }

}
