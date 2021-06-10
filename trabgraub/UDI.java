package trabgraub;

public class UDI {
    //recebe o codigo (OPCODE) da operacao a ser executada e decodifica, manda a UC sobre a instrucao (leitura de dados)
    private int opcode = 0;

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public UDI(int opcode) {
        this.opcode = opcode;
    }
    public UDI() {
    }
}
