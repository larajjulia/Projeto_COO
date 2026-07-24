import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


class Initial_Data{

    private int Player_Life;
    private int PhasesNumber;
    private List<String> Phase;

    public Initial_Data(int Player_Life, int PhasesNumber, List<String> Phase){
        this.Player_Life = Player_Life;
        this.PhasesNumber = PhasesNumber;
        this.Phase = Phase;
    }

    public int getPlayer_Life(){ return Player_Life;}
    public int getPhasesNumber(){ return PhasesNumber;}
    public List<String> Phases(){ return Phase;}
    
}

public class ReadFiles {

public static Path inputPath = Path.of("Projeto_COO", "Arquivos_Do_Jogo","ArquivoInicial.txt");
public static Path fullPath = inputPath.toAbsolutePath();
   
    public static Initial_Data Read_Initial_File(){

        int PlayerLife = 0;
        int PhasesNumber = 0;
        List <String> Phases = new ArrayList<>();
        int count = 0;

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(fullPath, charset)) {
            String line = reader.readLine();

            if(line != null) PlayerLife = Integer.parseInt(line);

            line = reader.readLine();

            if(line != null) PhasesNumber = Integer.parseInt(line);
 
            while (((line = reader.readLine()) != null) && count < 6) {

                Phases.add(line);
                
                count++;
            }

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

           return new Initial_Data(PlayerLife, PhasesNumber, Phases);
    }
}