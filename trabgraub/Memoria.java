package trabgraub;
import java.io.*;

public class Memoria {
    private int[] memoria = new int[128];

    public int getMemoria(int endereco) {
        if (endereco == 128){
            endereco = 0;
        }
        return this.memoria[endereco];
    }

    public void setMemoria(int endereco, int valor) {
        if (endereco == 128){
            endereco = 0;
        }
        this.memoria[endereco] = valor;
    }

    public Memoria() {
        for (int i = 0; i < 128; i++){
            memoria[i] = 0;
        }
    }

    public boolean gravar(String nomeArquivo){
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < 128; i++){
                if (i % 8 == 0 && i != 0){
                    out.print("\n");
                }
                out.print(memoria[i] + ";");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean carregar(String nomeArquivo){
        try (BufferedReader in = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int k = 0;
            while ((linha = in.readLine()) != null){
                String numero = "";
                for (int i = 0; i < linha.length(); i++){
                    if (linha.charAt(i) != ';'){
                        numero += linha.charAt(i);
                    } else {
                        if (numero.length() > 0){
                            memoria[k] = Integer.parseInt(numero);
                            numero = "";
                            k++;
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
