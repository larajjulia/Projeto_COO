import java.awt.Color;

public abstract class Boss extends Enemy_Shooter{
    protected long nextEnemy = currentTime;
    protected boolean alive = false;
    protected double life;

    public Boss(double X, double Y, double velocity, double angle, double velocityRotation, double life){
        super(X, Y, velocity, angle, velocityRotation);
        alive = true;
        this.life = life;
    }

    public int getState(){return state;}
    public long getExplosionEnd(){return explosionEnd;}

    public void nextEnemy(long time){nextEnemy = currentTime + time;}


    public void addEnemy(){} // controlado pela main

    protected void visualEnemies(){ // faz a barra de vida
        GameLib.setColor(Color.BLACK);
        GameLib.drawLine(GameLib.WIDTH / 2 - 50.0, 60.0, GameLib.WIDTH/2 + 50.0, 60.0);
        GameLib.setColor(Color.RED);
        GameLib.drawLine(GameLib.WIDTH / 2 - life / 2, 60.0, GameLib.WIDTH / 2 + life / 2, 60.0);
    }

    @Override
    public void explode(long time){
        if(life != 0){
            life--;
        }
        else{
            super.explode(time);
            alive = false;
        }
    }

    protected abstract void adjustMovement();

    public static Boss bossApplication(Boss_Info bossInfo){ // bossN determina se é boss 1 ou 2
		for (Enemy item : Enemy.listEnemies){
			if(item instanceof Powerup) continue;
			item.nextEnemy((long)10000000);
		}

        Boss bossAtivo = null;

		switch(bossInfo.type){
		case 1: 
            bossAtivo = new Boss_2(bossInfo.X, bossInfo.Y, 0.2, 0.0, 0.0, bossInfo.life);
            break;
        case 2: 
            bossAtivo = new Boss_1(bossInfo.X, bossInfo.Y, 0.40, 3*Math.PI/2, 0.0, bossInfo.life);
            break;
		}

		bossAtivo.visualEnemies();
        return bossAtivo;
	}
    
}
