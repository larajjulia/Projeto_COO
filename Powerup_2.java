import java.awt.Color;

public class Powerup_2 extends Powerup{
    private static long powerupEnd;

    public Powerup_2(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
    }

    public void powerUp(Player player){ // deixa o jogador invencivel por 10 segundos
        player.invincible = true;
        powerupEnd = Game_Object.currentTime + 10000;
    }

    
    public void visualEnemies(){
        updatePosition();
        if(!visualExplosion()){
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(X, Y, radius);
        }
    }

    public void powerUpEnd(Player player){
        if(powerupEnd > 0 && Game_Object.currentTime > powerupEnd){
            player.invincible = false;
            powerupEnd = 0;
            Powerup.listPowerup.remove(this);
        }
    }
}
