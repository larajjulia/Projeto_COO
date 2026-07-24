import java.awt.Color;

public class Powerup_1 extends Enemy{
     private static long nextPowerup1 = currentTime;


    public Powerup_1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        nextPowerup1 = currentTime + 15000;
        this.radius = 9.0;
    }


    public static void powerUp(Player player){ // acelera o jogador por 10 segundos
        player.speedUp(1.0, 1.0);
        player.powerUpEnd((long)10000);
    }


   
    public void addEnemy(){ // spawna novo powerup se puder
        if(Game_Object.currentTime > nextPowerup1){
            new Powerup_1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, (0.20 + Math.random() * 0.15), (3 * Math.PI) / 2, 0.0);
        }
    }

    public void visualEnemies(){
        updatePosition();
        if(!visualExplosion()){
            GameLib.setColor(Color.YELLOW);
            GameLib.drawCircle(X, Y, radius);
        }
    }


}


