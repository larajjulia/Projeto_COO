import java.awt.Color;

public class Boss_2 extends Boss{
    protected long nextShot = currentTime;

    public Boss_2(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        this.radius = 12;
    }

    public void adjustMovement(){
        if(X < 0.0){
            X = 0.0;
            angle = 0.0;
        }
        if(X >= GameLib.WIDTH){
            X = GameLib.WIDTH - 1;
            angle = Math.PI;
        }

        
    }

    @Override
    public void visualEnemies(){
        if(!visualExplosion()){
            GameLib.setColor(Color.WHITE);
            GameLib.drawCircle(X, Y, radius);
            GameLib.drawLine(X - radius/2, Y, X + radius/2, Y);
            super.visualEnemies();
        }
    }

    public void readyToShoot(Player player){
        updatePosition();
        if(!onScreen()) return;
        if(currentTime > nextShot){
            for(int i = 0; i < 3; i++){
            double angle = Math.PI/2 - Math.PI/8 + Math.PI/8 * i;
            Projectile_Enemy projectile = new Projectile_Enemy(X, Y, Math.cos(angle) * 0.4, Math.sin(angle) * 0.4);
            projectile.changeSize(7.0);
            }


            nextShot = currentTime + 2000;
        }
    }



}
