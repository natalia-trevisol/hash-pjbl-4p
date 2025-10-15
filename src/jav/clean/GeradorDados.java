package jav.clean;

import java.util.Random;

public class GeradorDados {

    private static int tamanhoVetor(int n) {
        return n;
    }

    private static int valorAbsoluto(int x) {
        if (x < 0) return -x;
        return x;
    }

    public static int[] gerarCodigosUnicos(int n, long seed) {
        int[] arr = new int[n];
        int i = 0;
        while (i < n) {
            arr[i] = i;
            i++;
        }

        Random rnd = new Random(seed);

        int tam = tamanhoVetor(n);
        i = tam - 1;
        while (i > 0) {
            int j = rnd.nextInt();
            j = valorAbsoluto(j) % (i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i--;
        }
        return arr;
    }

    public static void mostrarAmostra(int[] arr, int limite, int n) {
        int tam = tamanhoVetor(n);
        int i = 0;
        while (i < limite && i < tam) {
            System.out.print(arr[i] + " ");
            i++;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        int[] dados = gerarCodigosUnicos(n, 12345L);
        mostrarAmostra(dados, 10, n);
    }
}
