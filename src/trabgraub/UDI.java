package trabgraub;

import java.io.File;
import java.util.Scanner;

public class UDI {
    //recebe o codigo (OPCODE) da operacao a ser executada e decodifica, manda a UC sobre a instrucao (leitura de dados)
    public UDI() {}

    public String decodifica(int opcode){
        return switch (opcode) {
            case 0 -> "STA";
            case 1 -> "LDA";
            case 2 -> "STB";
            case 3 -> "LDB";
            case 4 -> "STC";
            case 5 -> "SUM";
            case 6 -> "SUB";
            case 7 -> "COM";
            case 8 -> "JMP";
            case 9 -> "JPE";
            case 10 -> "JPG";
            case 11 -> "JPL";
            case 12 -> "CONA";
            case 13 -> "CONB";
            case 14 -> "STOP";
            default -> "NOT";
        };
    }

    public void listarArquivos(){
        File objFile = new File(System.getProperty("user.dir"));
        System.out.printf("\nConteúdo do diretório:\n");
        String diretorio[] = objFile.list();
        for (String item: diretorio) {
            File newFile = new File(System.getProperty("user.dir") + "\\"  + item);
            if (newFile.isFile()) {
                System.out.printf("\nArquivo: %s - %d bytes", newFile.getName(),  newFile.length());
            }
        }
        System.out.print("\n");
    }

    public void removerArquivo(String arquivo){
        File objFile = new File(System.getProperty("user.dir") + "\\" + arquivo);
        if (objFile.delete()){
            System.out.println("Arquivo deletado com sucesso");
        } else {
            System.out.println("Arquivo nao encontrado");
        }
    }
}
