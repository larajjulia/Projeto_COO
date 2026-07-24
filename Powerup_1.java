import java.awt.Color;

public class Powerup_1 extends Powerup{
     private static long nextPowerup1 = currentTime;
     private static long powerupEnd;


    public Powerup_1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        nextPowerup1 = currentTime + 5000;
        this.radius = 9.0;
        
    }

    
    public void powerUp(Player player){ // acelera o jogador por 10 segundos
        player.speedUp(0.5, 0.5);
        powerupEnd = Game_Object.currentTime + 5000;
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

     public void powerUpEnd(Player player){
        if(powerupEnd > 0 && Game_Object.currentTime > powerupEnd){
            player.speedUp(0.25, 0.25);
            powerupEnd = 0;
            Powerup.listPowerup.remove(this);
        }
    }


}


