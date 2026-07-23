import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Background{
    protected double speed;
    protected double width;
    protected double height;
    private double X;
    private double Y;
    private Color color;
    private double count;
    public static List<double[]> stars = new ArrayList<>();

    private Background(double speed, double width, double height, Color color, int numberStars){
        this.speed = speed; 
        this.width = width; 
        this.height = height;
        this.color = color;

        for(int i = 0; i < numberStars; i++)
            stars.add(new double[]{ Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT });
    }

    public static Background Background_1(){ // atalhos para a criação dos dois tipos de background
        return new Background(0.07, 2, 2, Color.DARK_GRAY, 20);
    }

    public static Background Background_2(){
        return new Background(0.045, 3, 3, Color.GRAY, 50);
    }

    public void updateDelta(){
        count += speed * (double) Game_Object.delta;
    }

    public void visualBackground(){ // dado uma cor, preenche o fundo
        GameLib.setColor(color);
        for(double[] star : stars){
            GameLib.fillRect(star[0], (star[1] + count) % GameLib.HEIGHT, width, height);
        }
        // GameLib.fillRect(X, (Y + count) % GameLib.HEIGHT, width, height);
    }
}