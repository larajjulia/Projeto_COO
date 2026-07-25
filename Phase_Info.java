import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Phase_Info{
    public List<Enemy_Info> enemies = new ArrayList<Enemy_Info>(); // armazena as condições do próx inimigo
    public Boss_Info boss; // armazena as condições do próx boss

    public static Charset charset = Charset.forName("US-ASCII");



    public static Phase_Info Read_Enemy_Info(String fileName){
    String line;
    Phase_Info data = new Phase_Info();
    Path path = Path.of( "Arquivos_Do_Jogo", fileName);
    
    try (BufferedReader reader = Files.newBufferedReader(path.toAbsolutePath(), charset)) {
        while((line = reader.readLine()) != null){
            String[] split = line.trim().split("\\s+");
            if(split[0].equals("ENEMY")){
                Enemy_Info e = new Enemy_Info();
                e.type = Integer.parseInt(split[1]);
                e.when = Long.parseLong(split[2]);
                e.X = Double.parseDouble(split[3]);
                e.Y = Double.parseDouble(split[4]);
                e.powerup = false;
                data.enemies.add(e);
            }

            if(split[0].equals("BOSS")){
                Boss_Info b = new Boss_Info();
                b.type = Integer.parseInt(split[1]);
                b.life = Double.parseDouble(split[2]);
                b.when = Long.parseLong(split[3]);
                b.X = Double.parseDouble(split[4]);
                b.Y = Double.parseDouble(split[5]);
                data.boss = b;
            }

            if(split[0].equals("POWERUP")){
                Enemy_Info e = new Enemy_Info();
                e.type = Integer.parseInt(split[1]);
                e.when = Long.parseLong(split[2]);
                e.X = Double.parseDouble(split[3]);
                e.Y = Double.parseDouble(split[4]);
                e.powerup = true;
                data.enemies.add(e);
            }

        }
    } 
    catch (IOException x) {
        System.err.format("IOException Phase: %s", x);
    }
    return data;

    }
}