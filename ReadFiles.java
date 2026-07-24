import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFiles {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("Arquivos_Do_Jogo/ArquivoInicial.txt");
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while (true) {
                line = buffer.readLine();
                if (line == null) break;
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
