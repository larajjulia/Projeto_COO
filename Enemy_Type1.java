public class Enemy_Type1 extends Enemy{
    private long nextShot = currentTime; // próximo momento em que o inimigo 1 pode disparar
    private static long nextEnemy1 = currentTime + 2000; // próximo momento em que um inimigo 1 pode aparecer
    private final double radius = 9.0; // raio(tamanho) do inimigo 1

    public Enemy_Type1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        nextEnemy1 = currentTime + 500;
        nextShot = currentTime + 500; //pq q ele muda quando cria uma instancia??
    }

    @Override
    public boolean onScreen(){
        if(hasExploded(currentTime) || Y > GameLib.HEIGHT + 10) state = INACTIVE; // se o inimigo tiver saído da tela ou explodido, está inativo
        if(state == INACTIVE) return false;
        else return true;
    }

    public void readyToShoot(Player player, int delta, int currentTime){ // avalia se o inimigo pode atirar e atira
        if(!onScreen()) return;

        updatePosition();
        if(currentTime > nextShot && Y < player.getY){ // se o inimigo puder lançar um projétil e
            Projectile newProjectile = new Projectile(X, Y, Math.cos(angle) * (0.45), Math.sin(angle) * (-0.45)); // cria um novo projétil
        }

        nextShot = (long) (currentTime + 200 + Math.random() * 500); // atualiza o tempo de espera até o próxmo projétil
    }

    public addEnemy1(){ // spawna novo inimigo se puder
        if(currentTime > nextEnemy1){
            Enemy enemy1 = new Enemy_Type1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, (0.20 + Math.random() * 0.15), (3 * Math.PI) / 2, 0.0);
        }
    }

    @Override
    public void visualEnemies(){
        if(!visualExplosion){
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(X, Y, radius);
        }
    }

}