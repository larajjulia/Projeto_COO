import java.awt.Color;
public class Enemy_Type1 extends Enemy_Shooter{
    private long nextShot = currentTime; // próximo momento em que o inimigo do tipo 1 pode disparar
    private static long nextEnemy1 = currentTime + 2000; // próximo momento em que um inimigo do tipo 1 pode aparecer
    

    public Enemy_Type1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        this.radius = 9.0; // raio(tamanho) do inimigo 1
        nextEnemy1 = currentTime + 500;
        nextShot = currentTime + 1000; //pq q ele muda quando cria uma instancia??
    }


    public void nextEnemy(long time){nextEnemy1 = currentTime + time;}

    @Override
    public void readyToShoot(Player player){ // avalia se o inimigo pode atirar e atira
        updatePosition();
        if(!onScreen()) return;
        if(currentTime > nextShot && Y < player.getY()){ // se o inimigo puder lançar um projétil e
            new Projectile_Enemy(X, Y, Math.cos(angle) * (0.45), Math.sin(angle) * (0.45) * (-1.0)); // cria um novo projétil
            nextShot = (long) (currentTime + 200 + Math.random() * 500); // atualiza o tempo de espera até o próxmo projétil
        }
    }

    public void addEnemy(){ // spawna novo inimigo se puder
        if(currentTime > nextEnemy1){
            new Enemy_Type1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, (0.20 + Math.random() * 0.15), (3 * Math.PI) / 2, 0.0);
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