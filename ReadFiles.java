import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadFiles {
    Path inputPath = Path.of("Arquivos_Do_Jogo","ArquivoInicial.txt");
    Path fullPath = inputPath.toAbsolutePath();
    public static void main(String[] args) {
        
        int PlayerLife = 0;
        int PhasesNumber = 0;
        String Phase1 = " ";
        String Phase2 = " ";
        String Phase3 = " ";
        int count = 0;

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(fullPath, charset)) {
            String line = null;
            while (((line = reader.readLine()) != null) && count < 6) {
                if (count == 0) PlayerLife = Integer.parseInt(line);
                else if (count == 1) PhasesNumber = Integer.parseInt(line);
                else if (count == 2) Phase1 = line;
                else if (count == 3) Phase2 = line;
                else Phase3 = line;
                count++;
            }

            System.out.println(PlayerLife);
            System.out.println(PhasesNumber);
            System.out.println(Phase1);
            System.out.println(Phase2);
            System.out.println(Phase3);

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static int ReadNumber(){
        int PlayerLife = 0;
        int PhasesNumber = 0;
    }

    public static int ReadPath(){

    }

}
