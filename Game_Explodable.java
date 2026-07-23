public class Game_Explodable extends Game_Object{
    protected double explosionStart; // instante do inicio da explosão
    protected double explosionEnd; // instante do fim da explosão


    protected Game_Explodable(double X, double Y){
        super(X, Y);
    }

     protected void explode(int time){
        state = EXPLODING;
        explosionStart = currentTime;
        explosionEnd = currentTime + time;
     }

     protected boolean visualExplosion(){
        if(state == EXPLODING){
            GameLib.drawExplosion(X, Y, (currentTime - explosionStart) / (explosionEnd - explosionStart));
            return true;
        }
        else return false;
     }

}
