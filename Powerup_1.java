import java.awt.Color;

public class Powerup_1 extends Powerup{
     private static long powerupEnd;


    public Powerup_1(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        
    }

    
    public void powerUp(Player player){ // acelera o jogador por 5 segundos
        player.speedUp(0.5, 0.5);
        powerupEnd = Game_Object.currentTime + 5000;
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


