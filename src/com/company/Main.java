package com.company;

public class Main {

    public static void main(String[] args) {
        Memoria m = new Memoria();
        for (int i = 0; i < 128; i++){
            m.mudaMemoria(i, i);
        }
        m.mostraMemoria();
    }
}

