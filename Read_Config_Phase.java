import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

    class Configuration{

        private String object;
        private int type, time;
        private double positionX, positionY;
    
        public Configuration(String object, int type, int time, double positionX, double positionY){

        this.object = object;
        this.type = type;
        this.time = time;
        this.positionX = positionX;
        this.positionY = positionY;

       }

       public String getObject(){ return object; };
       public int Type(){ return type; }
       public int Time(){ return time; }
       public double positionX(){ return positionX; }
       public double positionY() { return positionY; }

    }

class Enemy_Config extends Configuration{

    public Enemy_Config(String enemy, int type, int time, double positionX, double positionY){

        super(enemy, type, time, positionX, positionY);
    }

    public String getEnemy(){ return super.getObject(); }
    public int Type(){ return super.Type(); }
    public int Time(){ return super.Time(); }
    public double positionX(){ return super.positionX(); }
    public double positionY() { return super.positionY(); }

}

class PowerUp_Config extends Configuration{

    
    public PowerUp_Config(String enemy, int type, int time, double positionX, double positionY){

        super(enemy, type, time, positionX, positionY);
    }

    public String getpower(){ return super.getObject(); }
    public int Type(){ return super.Type(); }
    public int Time(){ return super.Time(); }
    public double positionX(){ return super.positionX(); }
    public double positionY() { return super.positionY(); }

}

class Boss_Config extends Configuration{


     public Boss_Config(String boss, int type, int time, double positionX, double positionY){ 

        super(boss, type, time, positionX, positionY);

    }

    public String getpower(){ return super.getObject(); }
    public int Type(){ return super.Type(); }
    public int Time(){ return super.Time(); }
    public double positionX(){ return super.positionX(); }
    public double positionY() { return super.positionY(); }

}


public class Read_Config_Phase{
    
    public void Phase_Reader(String File_Name){

        List<Enemy_Config> enemiesList = new ArrayList<>();
        List<PowerUp_Config> powerupsList = new ArrayList<>();
        Boss_Config boss;

        Path inputPath = Path.of("Projeto_COO", "Arquivos_Do_Jogo", File_Name);
        Path fullPath = inputPath.toAbsolutePath();

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(fullPath, charset)) {
            String line = reader.readLine();


            while (((line = reader.readLine()) != null)) {

                String[] words = line.split("\\s+");

                String object = words[0];
                int type = Integer.parseInt(words[1]);
                int time = Integer.parseInt(words[2]);
                Double positionX = Double.parseDouble(words[3]);
                Double positionY = Double.parseDouble(words[4]);

                switch(object){

                    case "ENEMY": 
                            Enemy_Config enemy = new Enemy_Config(object, type, time, positionX, positionY); 
                            enemiesList.add(enemy);
                            break;

                            case "POWERUP": 
                            PowerUp_Config powerup = new PowerUp_Config(object, type, time, positionX, positionY); 
                            powerupsList.add(powerup);

                            break;
                            case "BOSS": 
                             boss = new Boss_Config(object, type, time, positionX, positionY); 
    
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            }

       return new Initial_Data(PlayerLife, PhasesNumber, Phases);  
    }
}

      
