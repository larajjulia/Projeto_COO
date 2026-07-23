import java.awt.Color;

public class Background extends Game_Object{
    protected double speed;
    protected double width;
    protected double height;
    private double X;
    private double Y;
    private Color color;
    private static double count;

    private Background(double speed, double width, double height, Color color){
        this.speed = speed; 
        this.width = width; 
        this.height = height;
        this.color = color;
    }

    public static Background Background_1(){ // atalhos para a criação dos dois tipos de background
        return new Background(0.07, 2, 2, Color.DARK_GRAY);
    }
    
    public static Background Background_2(){
        return new Background(0.045, 3, 3, Color.GRAY);
    }


    public void updateDelta(){
        count += speed * delta;
    }

    public void visualBackground(){ // dado uma cor, preenche o fundo
        GameLib.setColor(color);
        GameLib.fillRect(X, (Y + count) % GameLib.HEIGHT, width, height);
    }

   
}