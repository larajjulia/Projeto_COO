import java.awt.Color;

public class Boss_1 extends Boss{
    protected long nextShot = currentTime;
    
    
    public Boss_1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        this.radius = 15;
    }

    public void readyToShoot(Player player){
        updatePosition();
        if(!onScreen()) return;
        if(currentTime > nextShot){
            new Projectile_Enemy(X, Y, Math.cos(angle) * (1.0), Math.sin(angle) * (0.3) * (-1.0));
        }
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
            super.visualEnemies();
        }
    }

    
}
