import java.awt.Color;

public class Player<T extends Interface> extends Game_Explodable {
    private double velocityX = 0.25; // velocidade no eixo X
    private double velocityY = 0.25; // velocidade no eixo Y
    private long nextShot = currentTime; // próximo momento em que o jogador pode disparar
    private final double radius = 12.0; // raio(tamanho) do jogador
    
    public Player(){
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);
    }

    public double getState(){return state;}
    public double getX(){return X;}
    public double getY(){return Y;}
    public double getVelocityX(){return velocityX;}
    public double getVelocityY(){return velocityY;}
    public double getRadius(){return radius;}
    

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
            Projectile newProjectile = Projectile.projectilePlayer(X, (Y - 2 * radius), 0.0, -1.0);
            nextShot = currentTime + 100;
        }
    }

    public void visualPlayer(){ // faz a parte visual do jogador
        if(!visualExplosion()){
            GameLib.setColor(Color.BLUE);
		    GameLib.drawPlayer(X, Y, radius);
        }
    }

    public void collisionPlayer(Interface element){
        double dist = (double) getDist(element);
	    if(dist < (getRadius() + element.getRadius()) * 0.8){
			explode();
        }
    }

    public void movement(){
        if(GameLib.iskeyPressed(GameLib.KEY_UP)) Y -= delta * velocityY;
		if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) Y += delta * velocityY;
		if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) X -= delta * velocityX;
		if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) X += delta * velocityX;
    }
}