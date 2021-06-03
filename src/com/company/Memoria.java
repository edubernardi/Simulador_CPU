package com.company;

public class Memoria {
    private int[][] memoria = new int[16][8];

    public Memoria() {
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 8; j++){
                memoria[i][j] = 0;
            }
        }
    }

    public void mostraMemoria(){
        System.out.print("     ");
        for (int i = 0; i < 8; i++){
            System.out.print("0" + i + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 16; i++){
            System.out.print(String.format("0x%08X", (i + (7 * i))).substring(8) + " | ");
            for (int j = 0; j < 8; j++){
                System.out.print(String.format("0x%08X", memoria[i][j]).substring(8) + " ");
            }
            System.out.print("\n");
        }
    }

    public void editarMemoria(int endereco, int novoValor){
        int i = ;
        int j = ;
        memoria[i][j] = novoValor;
    }
}

