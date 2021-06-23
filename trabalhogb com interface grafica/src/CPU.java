package trabgraub;

public class CPU {
    private UC uc;

    public CPU() {
        uc = new UC();
    }

    public void rodar() {
        uc.rodar();
    }

    public UC getUc() {
        return uc;
    }
}
