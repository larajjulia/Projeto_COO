import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Phase_Info{
    public static List<Enemy_Info> enemies = new ArrayList<Enemy_Info>();
    public static Boss_Info boss;


    public static void Read_Phase_File(String fileName){
    Path path = Path.of("Projeto_COO", "Arquivos_Do_Jogo", fileName);
    Charset charset = Charset.forName("US-ASCII");
    String line;

    try (BufferedReader reader = Files.newBufferedReader(path.toAbsolutePath(), charset)) {
        while((line = reader.readLine()) != null){
            String[] split = line.trim().split("\\s+");
            if(split[0].equals("INIMIGO")){
                Enemy_Info e = new Enemy_Info();
                e.type = Integer.parseInt(split[1]);
                e.when = Long.parseLong(split[2]);
                e.X = Double.parseDouble(split[3]);
                e.Y = Double.parseDouble(split[4]);
                enemies.add(e);
            }

            if(split[0].equals("CHEFE")){
                Boss_Info b = new Boss_Info();
                b.type = Integer.parseInt(split[1]);
                b.life = Double.parseDouble(split[2]);
                b.when = Long.parseLong(split[3]);
                b.X = Double.parseDouble(split[4]);
                b.Y = Double.parseDouble(split[5]);
                boss = b;
            }
        }
    } 
    catch (IOException x) {
        System.err.format("IOException: %s", x);
    }
    
    }
}