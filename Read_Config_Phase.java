import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Configuration {

    private String object;
    public int type, time;
    public double positionX, positionY;

    public Configuration(String object, int type, int time, double positionX, double positionY) {

        this.object = object;
        this.type = type;
        this.time = time;
        this.positionX = positionX;
        this.positionY = positionY;

    }

    public String getObject() {
        return object;
    };

    public int Type() {
        return type;
    }

    public int Time() {
        return time;
    }

    public double positionX() {
        return positionX;
    }

    public double positionY() {
        return positionY;
    }

}

class Phase_Config {
    private List<Enemy_Config> enemies = new ArrayList<>();
    private List<PowerUp_Config> powerups = new ArrayList<>();
    private Boss_Config boss;

    public Phase_Config(List<Enemy_Config> enemies, List<PowerUp_Config> powerups, Boss_Config boss) {
        this.enemies = enemies;
        this.powerups = powerups;
        this.boss = boss;
    }

    public List<Enemy_Config> getEnemy_Configs() {
        return enemies;
    }

    public List<PowerUp_Config> getPowerUp_Configs() {
        return powerups;
    }

    public Boss_Config getBoss_Configs() {
        return boss;
    }
}

class Enemy_Config extends Configuration {

    public Enemy_Config(String enemy, int type, int time, double positionX, double positionY) {

        super(enemy, type, time, positionX, positionY);
    }

    public String getEnemy() {
        return super.getObject();
    }

    public int Type() {
        return super.Type();
    }

    public int Time() {
        return super.Time();
    }

    public double positionX() {
        return super.positionX();
    }

    public double positionY() {
        return super.positionY();
    }

}

class PowerUp_Config extends Configuration {

    public PowerUp_Config(String enemy, int type, int time, double positionX, double positionY) {

        super(enemy, type, time, positionX, positionY);
    }

    public String getpower() {
        return super.getObject();
    }

    public int Type() {
        return super.Type();
    }

    public int Time() {
        return super.Time();
    }

    public double positionX() {
        return super.positionX();
    }

    public double positionY() {
        return super.positionY();
    }

}

class Boss_Config extends Configuration {
    public double life;

    public Boss_Config(String boss, int type, int time, double positionX, double positionY, double life) {
        super(boss, type, time, positionX, positionY);
        this.life = life;
    }

    public double Life() { return life; }

    public String getpower(){ return super.getObject(); }
    public int Type(){ return super.Type(); }
    public int Time(){ return super.Time(); }
    public double positionX(){ return super.positionX(); }
    public double positionY() { return super.positionY(); }
}
public class Read_Config_Phase {
    public static Phase_Config Phase_Reader(String File_Name) {

        List<Enemy_Config> enemiesList = new ArrayList<>();
        List<PowerUp_Config> powerupsList = new ArrayList<>();
        Boss_Config boss = null;

        Path inputPath = Path.of("Arquivos_Do_Jogo", File_Name);
        Path fullPath = inputPath.toAbsolutePath();

        Charset charset = Charset.forName("US-ASCII");

        try (BufferedReader reader = Files.newBufferedReader(fullPath, charset)) {
            String line;

            while (((line = reader.readLine()) != null)) {

                String[] words = line.split("\\s+");

                String object = words[0];

                switch (object) {

                    case "ENEMY": {
                        int type = Integer.parseInt(words[1]);
                        int time = Integer.parseInt(words[2]);
                        double positionX = Double.parseDouble(words[3]);
                        double positionY = Double.parseDouble(words[4]);
                        enemiesList.add(new Enemy_Config(object, type, time, positionX, positionY));
                        break;
                    }

                    case "POWERUP": {
                        int type = Integer.parseInt(words[1]);
                        int time = Integer.parseInt(words[2]);
                        double positionX = Double.parseDouble(words[3]);
                        double positionY = Double.parseDouble(words[4]);
                        powerupsList.add(new PowerUp_Config(object, type, time, positionX, positionY));
                        break;
                    }

                    case "BOSS": {
                        int type = Integer.parseInt(words[1]);
                        double life = Double.parseDouble(words[2]);
                        int time = Integer.parseInt(words[3]);
                        double positionX = Double.parseDouble(words[4]);
                        double positionY = Double.parseDouble(words[5]);
                        boss = new Boss_Config(object, type, time, positionX, positionY, life);
                        break;
                    }
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return new Phase_Config(enemiesList, powerupsList, boss);
    }
}
