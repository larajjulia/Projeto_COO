import java.awt.Color;

public abstract class Boss extends Enemy_Shooter{
    protected long nextEnemy = currentTime;
    protected static boolean alive = false;
    protected static double life;

    public Boss(double X, double Y, double velocity, double angle, double velocityRotation){
        super(X, Y, velocity, angle, velocityRotation);
        alive = true;
        life = 100.0;
    }

    public void nextEnemy(long time){nextEnemy = currentTime + time;}


    public void addEnemy(){ // adiciona de volta os inimigos depois de vencer do boss
        if(alive) return;
        setupEnemies();
    }

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

    public static Boss bossApplication(int bossN){ // bossN determina se é boss 1 ou 2
		for (Enemy item : Enemy.listEnemies){
			if(item instanceof Powerup) continue;
			item.nextEnemy((long)10000000);
		}

        Boss bossAtivo = null;

		switch(bossN){
		case 1: 
            bossAtivo = new Boss_1(GameLib.WIDTH/2, -10.0, 0.40, Math.PI/2, 0.0);
            break;
		case 2: 
            bossAtivo = new Boss_2(0.0, GameLib.HEIGHT/3, 0.2, 0.0, 0.0);
            break;
		}
		bossAtivo.visualEnemies();
        return bossAtivo;
	}
    
}
