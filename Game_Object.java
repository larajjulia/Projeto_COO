public class Game_Object<T extends Interface>{
    public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
    protected int state = INACTIVE;
    protected double X;
    protected double Y;
    /* Usada para atualizar o estado dos elementos do jogo    */
	/* (player, projéteis e inimigos) "delta" indica quantos  */
	/* ms se passaram desde a última atualização.             */

		
			
	/* Já a variável "currentTime" nos dá o timestamp atual.  */
    
    public static long currentTime = System.currentTimeMillis();
    public static long delta = System.currentTimeMillis() - currentTime;

    public static void updateTime(){
        long now = System.currentTimeMillis();
    	Game_Object.delta = now - Game_Object.currentTime;
    	Game_Object.currentTime = now;
    }

    // public long getCurrentTime(){return currentTime;}
    // public long getDelta(){return delta;}

    
    protected Game_Object(double X, double Y){
        this.state = ACTIVE;
        this.X = X;
        this.Y = Y;
    }

    public double getDist(T element){
        double dx = element.getX() - X;
		double dy = element.getY() - Y;
		double dist = Math.sqrt(dx * dx + dy * dy);

        return dist;
    }

}
