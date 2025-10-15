package jav.commented;

/**
 * Endereçamento aberto usando double hashing:
 * índice = (h1(key) + i * h2(key)) mod m
 * Onde i = número de probes (tentativas)
 */
public class TabelaHashDuplo {
    private final int m;
    private final int[] tabela; // -1 = vazio
    public long contadorColisoes = 0; // soma de probes extras

    /**
     * Construtor
     * @param m tamanho da tabela
     */
    public TabelaHashDuplo(int m) {
        this.m = m;
        this.tabela = new int[m];
        for (int i = 0; i < m; i++) tabela[i] = -1;
    }

    /** Implementação própria de floorMod sem Math */
    private int meuFloorMod(int a, int b) {
        int mod = a % b;
        return (mod < 0) ? (mod + b) : mod;
    }

    /** h1 simples: resto da divisão */
    private int h1(int key) {
        return meuFloorMod(key, m);
    }

    /** h2: entre 1 e m-1 para evitar zero */
    private int h2(int key) {
        int r = meuFloorMod(key, m - 1);
        return 1 + r;
    }

    /**
     * Insere código usando double hashing
     * @param codigo código a inserir
     * @return true se inserido, false se tabela cheia
     */
    public boolean inserir(int codigo) {
        int key = codigo;
        int i = 0;
        while (i < m) {
            int idx = meuFloorMod(h1(key) + i * h2(key), m);
            if (tabela[idx] == -1) {
                tabela[idx] = codigo;
                contadorColisoes += i; // soma probes
                return true;
            } else {
                i++;
            }
        }
        return false; // tabela cheia
    }

    /**
     * Verifica se código existe na tabela
     * @param codigo código a buscar
     * @return true se encontrado
     */
    public boolean contem(int codigo) {
        int key = codigo;
        int i = 0;
        while (i < m) {
            int idx = meuFloorMod(h1(key) + i * h2(key), m);
            int cur = tabela[idx];
            if (cur == -1) return false;
            if (cur == codigo) {
                contadorColisoes += i; // opcional: conta probes durante busca
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Retorna vetor de ocupação (1 = ocupado, 0 = vazio)
     * @return int[] ocupação
     */
    public int[] ocupacao() {
        int[] oc = new int[m];
        for (int i = 0; i < m; i++) oc[i] = (tabela[i] == -1) ? 0 : 1;
        return oc;
    }
}