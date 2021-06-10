package trabgraub;

public class UC {
    //responsavel pelas operacoes que devem ser realizadas e quando devem ser realizadas (controla ula e udi)
    private Memoria mem;
    private ULA ula;
    private Teclado tc;
    private UDI udi;
    private Registrador PC;

    public UC() {
        mem = new Memoria();
        ula = new ULA();
        tc = new Teclado();
        udi = new UDI();
        PC = new Registrador();
    }

    public void rodar(){
        boolean run = true;
        while (run){
            System.out.println( "============= MENU ============");
            System.out.println("Opções: \n1 - Carregar programa na memória\n2 - Executar o programa" +
                    "\n3 - Alterar posição de memória\n4 - Alterar registrador" +
                    "\n5 - Mostrar memória e registrador\n6 - Mostrar instruções" +
                    "\n7 - Gravar programa em arquivo\n8 - Ajuda\n9 - Sair");
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
                    mostrarRegistradores();
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

    public void executarPrograma(){
        boolean executando = true;
        while (executando){
            String op = udi.decodifica(mem.getMemoria(PC.getValor()));
            if (!op.equals("NOT")) {
                switch (op) {
                    case "STA":
                        mem.setMemoria(mem.getMemoria(PC.getValor() + 1), ula.getA().getValor());
                        PC.incrementa(1);
                        break;
                    case "LDA":
                        ula.setA(mem.getMemoria(mem.getMemoria(PC.getValor() + 1)));
                        PC.incrementa(1);
                        break;
                    case "STB":
                        mem.setMemoria(mem.getMemoria(PC.getValor() + 1), ula.getB().getValor());
                        PC.incrementa(1);
                        break;
                    case "LDB":
                        ula.setB(mem.getMemoria(mem.getMemoria(PC.getValor() + 1)));
                        PC.incrementa(1);
                        break;
                    case "STC":
                        mem.setMemoria(mem.getMemoria(PC.getValor() + 1), ula.getC().getValor());
                        PC.incrementa(1);
                        break;
                    case "SUM":
                        ula.sum();
                        break;
                    case "SUB":
                        ula.sub();
                        break;
                    case "COM":
                        ula.comp();
                        break;
                    case "JMP":
                        PC.setValor(procuraInstrucao(PC.getValor()));
                        break;
                    case "JME":
                        if (ula.getC().getValor() == 0){
                            PC.setValor(procuraInstrucao(PC.getValor()));
                        }
                        break;
                    case "JMG":
                        if (ula.getC().getValor() == 2){
                            PC.setValor(procuraInstrucao(PC.getValor()));
                        }
                        break;
                    case "JML":
                        if (ula.getC().getValor() == 1){
                            PC.setValor(procuraInstrucao(PC.getValor()));
                        }
                        break;
                    case "CONA":
                        ula.setA(mem.getMemoria(PC.getValor() + 1));
                        break;
                    case "CONB":
                        ula.setB(mem.getMemoria(PC.getValor() + 1));
                        break;
                    case "STOP":
                        executando = false;
                }
            }
            PC.incrementa(1);
        }
    }

    public int procuraInstrucao(int posicaoAtual){
        boolean running = true;
        while (running){
            if (udi.decodifica(mem.getMemoria(posicaoAtual)).equals("NOT")){
                posicaoAtual++;
                posicaoAtual = posicaoAtual % 128;
            } else {
                running = false;
            }
        }
        return posicaoAtual + 1;
    }

    public void alterarPosicaoDeMemoria(int endereco, int valor){
        mem.setMemoria(endereco,valor);
    }

    public void alterarRegistrador(){

    }

    public void mostrarRegistradores(){
        System.out.print("Registrador A: " + String.format("0x%08X", ula.getA().getValor()).substring(8));
        System.out.print(" Registrador B: " + String.format("0x%08X", ula.getB().getValor()).substring(8));
        System.out.print("\nRegistrador C: " + String.format("0x%08X", ula.getC().getValor()).substring(8));
        System.out.print(" Registrador PC: "+ String.format("0x%08X", PC.getValor()).substring(8) + "\n");

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
                System.out.print(String.format("0x%08X", mem.getMemoria((i + (7 * i)) + j)).substring(8) + " ");
            }
            System.out.print("\n");
        }
    }

    public void mostrarInstrucoes(){

    }

    public void ajuda(){

    }
}



