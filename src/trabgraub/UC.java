package trabgraub;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class UC {
    //responsavel pelas operacoes que devem ser realizadas e quando devem ser realizadas (controla ula e udi)
    private Memoria mem;
    private ULA ula;
    private Teclado tc;
    private UDI udi;
    private Registrador PC;
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    private Usuario usuarioLogado;

    public UC() {
        mem = new Memoria();
        ula = new ULA();
        tc = new Teclado();
        udi = new UDI();
        PC = new Registrador();
        usuarios.add(new Usuario("admin", "admin"));
    }

    public void rodar() {
        boolean run = login();
        while (run) {
            System.out.println("============= MENU ============");
            System.out.println("Opções: \n1 - Carregar programa na memória\n2 - Executar o programa" +
                    "\n3 - Alterar posição de memória\n4 - Alterar registrador" +
                    "\n5 - Mostrar memória e registrador\n6 - Mostrar instruções" +
                    "\n7 - Gravar programa em arquivo\n8 - Ajuda\n9 - Sair (Logout)" +
                    "\n10 - Opcoes de administrador" + "\n11 - Listar arquivos" + "\n12 - Apagar arquivo");
            int op = tc.leInt();
            switch (op) {
                case 1:
                    if (mem.carregar(tc.leString("Nome do arquivo"))) {
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
                    alterarPosicaoDeMemoria(endereco, valor);
                    break;
                case 4:
                    alterarRegistrador();
                    break;
                case 5:
                    System.out.println("============= MEMÓRIA ============");
                    mostrarMemoria(-1);
                    mostrarRegistradores();
                    break;
                case 6:
                    mostrarInstrucoes();
                    break;
                case 7:
                    if (mem.gravar(tc.leString("Nome do arquivo:"))) {
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
                    run = login();
                    break;
                case 10:
                    if (usuarioLogado.isAdm()) {
                        opAdm();
                    } else {
                        System.out.println("Usuario atual nao possiu permissao de administrador");
                    }
                    break;
                case 11:
                    udi.listarArquivos();
                    break;
                case 12:
                    String arquivo = tc.leString("Nome do arquivo:");
                    String confirmacao = tc.leString("Tem certeza?S/N");
                    if (confirmacao.equals("S")){
                        udi.removerArquivo(arquivo);
                    }
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }
    }

    public void executarPrograma() {
        boolean executando = true;
        while (executando) {
            String op = udi.decodifica(mem.getMemoria(PC.getValor()));

            System.out.println("============= MEMÓRIA ============");
            mostrarMemoria(PC.getValor());
            mostrarRegistradores();

            String abortar = tc.leString("Precione Enter para continuar ou A para abortar");
            if (abortar.equals("A")){
                executando = false;
                continue;
            }

            if(PC.getValor() == 126){
                executando = false;
            }

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
                        if (ula.getC().getValor() == 0) {
                            PC.setValor(procuraInstrucao(PC.getValor()));
                        }
                        break;
                    case "JPG":
                        if (ula.getC().getValor() == 2) {
                            PC.setValor(procuraInstrucao(PC.getValor()));
                        }
                        break;
                    case "JPL":
                        if (ula.getC().getValor() == 1) {
                            PC.setValor(procuraInstrucao(PC.getValor()));
                        }
                        break;
                    case "CONA":
                        ula.setA(mem.getMemoria(PC.getValor() + 1));
                        PC.incrementa(1);
                        break;
                    case "CONB":
                        ula.setB(mem.getMemoria(PC.getValor() + 1));
                        PC.incrementa(1);
                        break;
                    case "STOP":
                        executando = false;
                }
            }
            PC.incrementa(1);
        }
    }

    public int procuraInstrucao(int posicaoAtual) {
        boolean running = true;
        while (running) {
            if (udi.decodifica(mem.getMemoria(posicaoAtual)).equals("NOT")) {
                posicaoAtual++;
                posicaoAtual = posicaoAtual % 128;
            } else {
                running = false;
            }
        }
        return posicaoAtual + 1;
    }

    public void alterarPosicaoDeMemoria(int endereco, int valor) {
        mem.setMemoria(endereco, valor);
    }

    public void alterarRegistrador() {
        String var1 = tc.leString("Qual registrador deseja alterar: [A,B,C ou PC]?");
        var1 = var1.toUpperCase();

        switch (var1) {
            case "A" -> {
                int valA = tc.leInt("Digite o valor do Registrador A:");
                ula.setA(valA);
            }
            case "B" -> {
                int valB = tc.leInt("Digite o valor do Registrador B:");
                ula.setB(valB);
            }
            case "C" -> {
                int valC = tc.leInt("Digite o valor do Registrador C:");
                ula.setC(valC);
            }
            case "PC" -> {
                int valPC = tc.leInt("Digite o valor do Registrador PC:");
                PC.setValor(valPC);
            }
            default -> System.out.println("Valor inválido!");
        }
    }

    public void mostrarRegistradores() {
        System.out.print("Registrador A: " + String.format("0x%08X", ula.getA().getValor()).substring(8));
        System.out.print(" Registrador B: " + String.format("0x%08X", ula.getB().getValor()).substring(8));
        System.out.print("\nRegistrador C: " + String.format("0x%08X", ula.getC().getValor()).substring(8));
        System.out.print(" Registrador PC: " + String.format("0x%08X", PC.getValor()).substring(8) + "\n");
    }

    public void mostrarMemoria(int localizacao) {
        System.out.print("     ");
        for (int i = 0; i < 8; i++) {
            System.out.print("0" + i + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 16; i++) {
            System.out.print(String.format("0x%08X", (i + (7 * i))).substring(8) + " | ");
            for (int j = 0; j < 8; j++) {
                if (localizacao == (i + (7 * i) + j)){
                    System.out.print("<" + String.format("0x%08X", mem.getMemoria((i + (7 * i)) + j)).substring(8) + ">");
                } else {
                    System.out.print(String.format("0x%08X", mem.getMemoria((i + (7 * i)) + j)).substring(8) + " ");
                }

            }
            System.out.print("\n");
        }
    }

    public void mostrarInstrucoes() {
        for (int i = 0; i < 128; i++) {
            System.out.println("[" + String.format("0x%08X", i).substring(8) + "]: " + udi.decodifica(mem.getMemoria(i)));
        }
    }

    public void ajuda() {
        System.out.println("STA : armazena o conteudo do reg. A na posicao de memoria indicadapelo conteudo da posicao de memoria seguinte a instrucao.opcode da instrucao: 0 (zero)\n" +
                "LDA : carrega para o reg. A o conteudo da posicao de memoria indicadana posicao de memoria seguinte a instrucao. opcode da instrucao: 1\n" +
                "STB : armazena o conteudo do reg. B na posicao de memoria indicadapelo conteudo da posicao de memoria seguinte a instrucao.opcode da instrucao: 2\n" +
                "LDB : carrega para o reg. B o conteudo da posicao de memoria indicadana posicao de memoria seguinte a instrucao.opcode da instrucao: 3\n" +
                "STC : armazena o conteudo do reg. C na posicao de memoria indicadapelo conteudo da posicao de memoria seguinte a instrucao.opcode da instrucao: 4\n" +
                "SUM : soma o conteudo dos registradores A e B e armazena o resultadono registrador C.opcode da instrucao: 5\n" +
                "SUB : subtrai do conteudo do registrador A o valor do reg. B e armazenao resultado no registrador C.opcode da instrucao: 6\n" +
                "COM : compara o conteudo dos registradores A e B e armazena o resultadono registrador C.Se A=B entao C=0;se A<B entao C=1;se A>B entao C=2.opcode da instrucao: 7\n" +
                "JMP: desvia o fluxo de execucao para o endereco contido na posicao dememoria seguinte a instrucao. É um salto (desvio) incondicional.Esta instrucao carrega para o registrador PC o valor da posicao dememoria seguinte a instrucao. Opcode da instrucao: 8\n" +
                "JPE: desvia o fluxo de execucao para o endereco contido na posicao dememoria seguinte a instrucao. É um salto (desvio) condicional.So ira ocorrer se A=B (registrador C=0). Deve ser precedida pelainstrucao COM. Opcode da instrucao: 9\n" +
                "JPG: desvia o fluxo de execucao para o endereco contido na posicao dememoria seguinte a instrucao. É um salto (desvio) condicional.So ira ocorrer se A>B (registrador C=2). Deve ser precedida pelainstrucao COM. Opcode da instrucao: A (em hexa)\n" +
                "JPL: desvia o fluxo de execucao para o endereco contido na posicao dememoria seguinte a instrucao. É um salto (desvio) condicional.So ira ocorrer se A<B (registrador C=1). Deve ser precedida pelainstrucao COM. Opcode da instrucao: B (em hexa)\n" +
                "CONA: carrega o registrador A com o valor da posicao seguinte dememoria.opcode da instrucao: C (em hexa)\n" +
                "CONB: carrega o registrador B com o valor da posicao seguinte dememoria.opcode da instrucao: D (em hexa)\n" +
                "STOP: encerra a execucao.opcode da instrucao: E (em hexa)");
    }

    private boolean login() {
        while (true) {
            String username = tc.leString("Nome de usuario: ");
            String password = tc.leString("Senha: ");
            for (Usuario u : usuarios) {
                if (u.login(username, password)) {
                    usuarioLogado = u;
                    return true;
                }
            }
            System.out.println("Usuario nao encontrado, tente novamente");
        }
    }

    private void opAdm() {
        switch (tc.leInt("Opcoes\n1. Cadastrar usuario\n2. Remover usuario\n3. Listar usuario")) {
            case 1:
                cadastrarUsuario(new Usuario(tc.leString("Nome: "), tc.leString("Senha: ")));
                break;
            case 2:
                removerUsuario(new Usuario(tc.leString("Nome: "), ""));
                break;
            case 3:
                listarUsuarios();
                break;
            default:
                System.out.println("Opcao invalida");
        }

    }

    private void cadastrarUsuario(Usuario novoUsuario) {
        for (Usuario usuario : usuarios) {
            if (novoUsuario.getUsername().equals(usuario)) {
                System.out.println("Erro, nome de usuario ja cadastrado");
                return;
            }
        }
        usuarios.add(novoUsuario);
    }

    private void removerUsuario(Usuario usuario) {
        usuarios.removeIf(u -> usuario.getUsername().equals(u.getUsername()));
    }

    private void listarUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.toString());
        }
    }
}

