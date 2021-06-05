package trabgraub;

public class CPU {
    Memoria mem = new Memoria();
    ULA ula = new ULA();
    Teclado tc = new Teclado();


    public void rodar(){
        boolean run = true;
        for (int i = 0; i < 128; i++){
            alterarPosicaoDeMemoria(i,i);
        }
        while (run){
            System.out.println( "============= MENU ============");
            System.out.println("Opções: \n1 - Carregar programa na memória\n2 - Executar o programa\n3 - Alterar posição de memória\n4 - Alterar registrador\n5 - Mostrar memória e registrador\n6 - Mostrar instruções\n7 - Gravar programa em arquivo\n8 - Ajuda\n9 - Sair");

            int op = tc.leInt();
            switch (op){
                case 1:
                    if (mem.carregar(tc.leString("Nome do arquivo"))){
                        System.out.println("Programa carregado com sucesso");
                    } else {
                        System.out.println("Erro");
                    }
                    break;
                case 2:
                    executarPrograma();
                    break;
                case 3:
                    int endereco = tc.leInt("Digite o endereço:");
                    int valor = tc.leInt("Digite o valor");
                    alterarPosicaoDeMemoria(endereco,valor);
                    break;
                case 4:
                    alterarRegistrador();
                    break;
                case 5:
                    System.out.println("============= MEMÓRIA ============");
                    mostrarMemoria();
                    break;
                case 6:
                    mostrarInstrucoes();
                    break;
                case 7:
                    if (mem.gravar(tc.leString("Nome do arquivo:"))){
                        System.out.println("Arquivo gravado");
                    } else {
                        System.out.println("Erro");
                    }
                    break;
                case 8:
                    ajuda();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    run = false;
                    break;
            }
        }
    }

    public void carregarProgNaMemoria(){

    }

    public void executarPrograma(){

    }

    public void alterarPosicaoDeMemoria(int endereco, int valor){
        mem.setMemoria(endereco,valor);
    }

    public void alterarRegistrador(){

    }

    public void mostrarMemoria(){
        System.out.print("     ");
        for (int i = 0; i < 8; i++){
            System.out.print("0" + i + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 16; i++){
            System.out.print(String.format("0x%08X", (i + (7 * i))).substring(8) + " | ");
            for (int j = 0; j < 8; j++){
                System.out.print(String.format("0x%08X", mem.getMemoria()[i][j]).substring(8) + " ");
            }
            System.out.print("\n");
        }
    }

    public void mostrarInstrucoes(){

    }

    public void ajuda(){

    }
}
