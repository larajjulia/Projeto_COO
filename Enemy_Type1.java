import java.awt.Color;
public class Enemy_Type1 extends Enemy_Shooter{
    private long nextShot = currentTime; // próximo momento em que o inimigo do tipo 1 pode disparar
    

    public Enemy_Type1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        this.radius = 9.0; // raio(tamanho) do inimigo 1
        nextShot = currentTime + 1000;
    }


    @Override
    public void readyToShoot(Player player){ // avalia se o inimigo pode atirar e atira
        updatePosition();
        if(!onScreen()) return;
        if(currentTime > nextShot && Y < player.getY()){ // se o inimigo puder lançar um projétil e
            new Projectile_Enemy(X, Y, Math.cos(angle) * (0.45), Math.sin(angle) * (0.45) * (-1.0)); // cria um novo projétil
            nextShot = (long) (currentTime + 200 + Math.random() * 500); // atualiza o tempo de espera até o próxmo projétil
        }
    }

    
    @Override
    public void visualEnemies(){
        if(!visualExplosion()){
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(X, Y, radius);
        }
    }
}