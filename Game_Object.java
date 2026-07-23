public class Game_Object{
    public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
    protected int state = INACTIVE;
    protected double X;
    protected double Y;
    public static long currentTime = System.currentTimeMillis();
    public static long delta = System.currentTimeMillis() - currentTime;
    


    protected Game_Object(double X, double Y){
        this.state = ACTIVE;
        this.X = X;
        this.Y = Y;
    }

}
