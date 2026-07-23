public class Player extends Game_Explodable{
    private int state = ACTIVE; // estado do jogador
    private double X = GameLib.WIDTH / 2; // coordenada X
    private double Y = GameLib.HEIGHT * 0.90;  // coordenada Y
    private double velocityX = 0.25; // velocidade no eixo X
    private double velocityY = 0.25; // velocidade no eixo Y
    private long nextShot = currentTime; // próximo momento em que o jogador pode disparar
    private final double radius = 12.0; // raio(tamanho) do jogador
    

    public double getState(){return state;}
    public double getX(){return X;}
    public double getY(){return Y;}

    public void updateX(){ // conserta a coordenada X se o jogador estiver fora da cena do jogo (após input)
        if(X < 0.0) X = 0.0;
        if(X >= GameLib.WIDTH) X = GameLib.WIDTH - 1;
    }
    public void updateY(){ // conserta a coordenada Y se o jogador estiver fora da cena do jogo (após input)
        if(Y < 25.0) Y = 25.0;
        if(Y >= GameLib.HEIGHT) Y = GameLib.HEIGHT - 1;
    }


    public void explode(){ // executar quando ocorrem colisões do jogador com inimigo ou projétil inimigo
        explode(2000);
    }

    public void hasExploded(){ // depois de passar do fim da explosão, o jogador volta a ficar ativo
        if(state == EXPLODING && currentTime > explosionEnd) state = ACTIVE;
    }

    public void readyToShoot(){ // avalia se o inimigo pode atirar e atira
        if(currentTime > nextShot){
            Projectile newProjectile = new Projectile(X, (Y - 2 * radius), 0.0, -1.0);
            nextShot = currentTime + 100;
        }
    }

    public void visualPlayer(){ // faz a parte visual do jogador
        if(state == EXPLODING) GameLib.drawExplosion(X, Y, (currentTime - explosionStart) / (explosionEnd - explosionStart));
        else{
            GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(X, Y, radius);
        }
    }
}