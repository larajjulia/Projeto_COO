public class Game_Explodable{
    protected double state = INACTIVE;
    protected double X;
    protected double Y;
    protected double explosionStart; // instante do inicio da explosão
    protected double explosionEnd; // instante do fim da explosão


    protected void Game_Object(double X, double Y){
        state = ACTIVE;
        this.X = X;
        this.Y = Y;
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