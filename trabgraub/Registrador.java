package trabgraub;

public class Registrador {
    private int valor;

    public Registrador() {
        this.valor = 0;
    }

    public int getValor(){
        return valor;
    }

    public void setValor(int valor){
        if (valor < 0){
            this.valor = 0;
        } else if (valor > 255){
            this.valor = 255;
        } else{
            this.valor = valor;
        }
    }

    public void incrementa(int valor){
        this.valor += valor;
        this.valor = this.valor % 128;
    }
}
