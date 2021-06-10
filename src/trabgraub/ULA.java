package trabgraub;

public class ULA {
    private Registrador A;
    private Registrador B;
    private Registrador C;


    public ULA() {
        A = new Registrador();
        B = new Registrador();
        C = new Registrador();


    }

    public void sum(){
        int resultado = A.getValor() + B.getValor();
        if (resultado > 255){
            resultado -= 255;
        }
        C.setValor(resultado);
    }

    public void sub(){
        int resultado = A.getValor() - B.getValor();
        if (resultado < 0){
            resultado += 255;
        }
        C.setValor(resultado);
    }

    public void comp(){
        int a = A.getValor();
        int b = B.getValor();
        if (a == b){
            C.setValor(0);
        } else if (a < b){
            C.setValor(1);
        } else {
            C.setValor(2);
        }
    }

    public Registrador getA() {
        return A;
    }

    public void setA(int a) {
        A.setValor(a);
    }

    public Registrador getB() {
        return B;
    }

    public void setB(int b) {
        B.setValor(b);
    }

    public Registrador getC() {
        return C;
    }

    public void setC(int c) {
        C.setValor(c);
    }
}
