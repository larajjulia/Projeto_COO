public class Game_Explodable{
    protected double state = INACTIVE;
    protected double X;
    protected double Y;
    protected double explosionStart; // instante do inicio da explosão
    protected double explosionEnd; // instante do fim da explosão


<<<<<<< HEAD
    protected void Game_Object(double X, double Y){
=======
    protected Game_Explodable(double X, double Y){
>>>>>>> 3577c69b044e398b2aaa4257c70e4d926cc502cd
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
<<<<<<< HEAD
}
=======

}
>>>>>>> 3577c69b044e398b2aaa4257c70e4d926cc502cd
