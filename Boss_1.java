import java.awt.Color;

public class Boss_1 extends Enemy_Shooter{
    private long nextShot = currentTime;
    private long nextEnemy = currentTime;
    private static boolean alive = false;
    private static int life = 100;
    
    public Boss_1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        alive = true;
        this.radius = 15;
    }

    public void nextEnemy(long time){nextEnemy = currentTime + time;}


    public void readyToShoot(Player player){
        updatePosition();
        if(!onScreen()) return;
        if(currentTime > nextShot){
            new Projectile_Enemy(X, Y, Math.cos(angle) * (1.0), Math.sin(angle) * (0.3) * (-1.0));
        }
    }

    public void addEnemy(){ // adiciona de volta os inimigos depois de vencer do boss
        if(alive) return;
        new Enemy_Type1(GameLib.WIDTH / 8, -10.0, 0.0, 0.0, 0.5);
		new Powerup_1(GameLib.WIDTH / 3, -9.0, 0.0, 0.0, 0.5);
        new Enemy_Type2(GameLib.WIDTH / 2, -10.0, 0.0, 0.0, 0.5);
		new Powerup_2(GameLib.WIDTH / 4, -9.0, 0.0, 0.0, 0.5);
    }
    
    public void adjustMovement(){
        if(X < 0.0){
            X = 0.0;
            angle = Math.PI - angle;
        }
        if(X >= GameLib.WIDTH){
            X = GameLib.WIDTH - 1;
            angle = Math.PI - angle;
        }

        if(Y <= 25.0){
            Y = 25.0;
            angle = 3 * Math.PI / 2; //desce
            X = GameLib.WIDTH * Math.random() + 1;
        }
        if(Y >= GameLib.HEIGHT - radius){
            Y = GameLib.HEIGHT - radius;
            angle = Math.PI / 2; //sobe
            X = GameLib.WIDTH * Math.random() + 1;
        }
        
    }


    @Override
    public void visualEnemies(){
        if(!visualExplosion()){
            GameLib.setColor(Color.RED);
            GameLib.drawCircle(X, Y, radius);
            GameLib.drawDiamond(X, Y, radius);
        }
    }

    @Override
    public void explode(long time){
        if(life != 0) life--;
        else{
            state = EXPLODING;
            explosionStart = currentTime;
            explosionEnd = currentTime + time;
        }
    }
    
}
