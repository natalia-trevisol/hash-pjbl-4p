package jav.commented;

import java.util.Random;
/**
 * Classe utilitária para gerar vetores de códigos únicos.
 * Usa algoritmo Fisher-Yates com semente (seed) para embaralhamento reprodutível.
 */
public class GeradorDados {

    /**
     * Retorna o tamanho do vetor sem usar length.
     * O tamanho é controlado por um contador até alcançar o índice inválido,
     * mas sem try/catch — então, neste contexto, usamos o valor n conhecido.
     */
    private static int tamanhoVetor(int n) {
        return n; // como sabemos o tamanho na criação, retornamos o mesmo n
    }

    /**
     * Calcula o valor absoluto de um número sem usar Math.abs()
     */
    private static int valorAbsoluto(int x) {
        if (x < 0) return -x;
        return x;
    }

    /**
     * Gera vetor com valores únicos de 0 até n-1 e embaralha.
     *
     * @param n tamanho do vetor
     * @param seed semente para o Random (reprodutibilidade)
     * @return vetor int[] com códigos únicos embaralhados
     */
    public static int[] gerarCodigosUnicos(int n, long seed) {
        int[] arr = new int[n];
        int i = 0;
        while (i < n) {
            arr[i] = i; // preenche 0..n-1
            i++;
        }

        Random rnd = new Random(seed);

        // Fisher-Yates shuffle: percorre o vetor de trás para frente
        int tam = tamanhoVetor(n);
        i = tam - 1;
        while (i > 0) {
            int j = rnd.nextInt();
            j = valorAbsoluto(j) % (i + 1); // substitui Math.abs
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp; // troca elementos
            i--;
        }
        return arr;
    }

    /**
     * Exibe uma amostra inicial do vetor gerado (para conferência)
     */
    public static void mostrarAmostra(int[] arr, int limite, int n) {
        int tam = tamanhoVetor(n);
        int i = 0;
        while (i < limite && i < tam) {
            System.out.print(arr[i] + " ");
            i++;
        }
        System.out.println();
    }

    /**
     * Método principal de teste
     */
    public static void main(String[] args) {
        int n = 10;
        int[] dados = gerarCodigosUnicos(n, 12345L);
        mostrarAmostra(dados, 10, n);
    }
}
