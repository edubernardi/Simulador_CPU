package trabgraub;
import java.io.*;

public class Memoria {
    private int[][] memoria = new int[16][8];


    public int[][] getMemoria() {
        return memoria;
    }

    public void setMemoria(int endereco, int valor) {
        int i = endereco / 8;
        int j = endereco % 8;
        this.memoria[i][j] = valor;
    }

    public Memoria() {
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 8; j++){
                memoria[i][j] = 0;
            }
        }
    }

    public boolean gravar(String nomeArquivo){
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < 16; i++){
                for (int j = 0; j < 8; j++){
                    out.print(memoria[i][j] + ";");
                }
                out.print("\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean carregar(String nomeArquivo){
        try (BufferedReader in = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int j = 0;
            int k = 0;
            while ((linha = in.readLine()) != null){
                k = 0;
                String numero = "";
                for (int i = 0; i < linha.length(); i++){

                    if (linha.charAt(i) != ';'){
                        numero += linha.charAt(i);
                    } else {
                        if (numero.length() > 0){
                            memoria[j][k] = Integer.parseInt(numero);
                            numero = "";
                            k++;
                        }
                    }
                }
                j++;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
