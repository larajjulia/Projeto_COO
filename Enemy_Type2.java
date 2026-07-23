import java.awt.Color;
public class Enemy_Type2 extends Enemy{
    private double spawnX; // coordenada X em que o próximo inimigo 2 irá spawnar
    private long nextEnemy2 = currentTime + 7000; // próximo momento em que um inimigo 2 pode aparecer(nn sei onde muda)
    private static int enemyCount; // contagem de inimigos 2
    private final double radius = 12; // raio(tamanho) do inimigo 2
    public Enemy_Type2(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        enemyCount++;
        if(enemyCount < 10) nextEnemy2 = currentTime + 120;
        else{
            //isso aqui eu tbm nn consertei, mto confuso
            enemyCount = 0;
			spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
			nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
        }
    }
    @Override
    public boolean onScreen(){
        if(hasExploded(currentTime) || X < -10 || X > GameLib.WIDTH + 10) state = INACTIVE; // se o inimigo tiver saído da tela ou explodido, está inativo
        if(state == INACTIVE) return false;
        else return true;
    }
    private void readyToShoot(Player player, int delta, int currentTime){ // avalia se é hora de lançar um projétil
        if(!onScreen()) return;
        double previousY = Y;
        double threshold = GameLib.HEIGHT * 0.3;
        updatePosition();
         // eu nao corrigi essa parte pq nao entendi DDD; dar uma melhorada
        if(previousY < threshold && Y >= threshold){
            if(X < GameLib.WIDTH / 2) velocityRotation = 0.003;
            else velocityRotation = -0.03;
        }
        if(velocityRotation > 0 && Math.abs(angle - 3 * Math.PI) < 0.05){
            velocityRotation = 0.0;
            angle = 3 * Math.PI;
            Shoot();
            return;
        }
        if(velocityRotation < 0 && Math.abs(angle) < 0.05){
            velocityRotation = 0.0;
            angle = 0.0;
            Shoot();
            return;
        }
    }
    private void Shoot(){ // o tipo de inimigo 2 dispara 3 projéteis ao mesmo tempo, então terá que criar 3 projéteis com ângulos diferentes
        for(int i = 0; i < 3; i++){
            double angle = Math.PI/2 - Math.PI/8 + Math.PI/8 * i; //dois projéteis + e - Math.PI/8(22.5 graus) em relação a Math.PI/2(90 graus), e um exatamente em 90 graus

            double a = angle + Math.random() * Math.PI/6 - Math.PI/12; //tbm nao entendi essa conta
            Projectile newProjectile = Projectile.projectileEnemy(X, Y, Math.cos(a) * 0.3, Math.sin(a) * 0.3);
        }
    }
    public void addEnemy(){ //spawna novo inimigo se puder
        if(currentTime > nextEnemy2){
            Enemy enemy = new Enemy_Type2(spawnX, -10.0, 0.42, (3 * Math.PI) / 2, 0.0);
        }
    }
    @Override
    public void visualEnemies(){
        if(!visualExplosion()){
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(X, Y, radius);
        }
    }
}