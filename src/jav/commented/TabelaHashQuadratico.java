package jav.commented;

/**
 * Implementação de endereçamento aberto usando sondagem quadrática:
 * índice = (h(key) + c1*i + c2*i*i) mod m
 * Onde i é o número de tentativas (probes) em caso de colisão.
 *
 * Usamos int[] tabela, onde -1 indica posição vazia.
 */
public class TabelaHashQuadratico {

    private final int m;        // tamanho da tabela
    private final int[] tabela; // -1 = posição vazia
    public long contadorColisoes = 0; // soma dos probes extras durante inserção/busca
    private final int c1 = 1;   // coeficiente linear
    private final int c2 = 3;   // coeficiente quadrático

    /**
     * Construtor: inicializa tabela com -1
     * @param m tamanho da tabela
     */
    public TabelaHashQuadratico(int m) {
        this.m = m;
        this.tabela = new int[m];
        for (int i = 0; i < m; i++) tabela[i] = -1;
    }

    /** Implementação própria de floorMod sem Math */
    private int meuFloorMod(int a, int b) {
        int mod = a % b;
        return (mod < 0) ? (mod + b) : mod;
    }

    /**
     * Função hash simples (modular)
     * @param key valor a ser mapeado
     * @return índice na tabela
     */
    private int h(int key) {
        return meuFloorMod(key, m);
    }

    /**
     * Insere código na tabela usando sondagem quadrática.
     * @param codigo código a inserir
     * @return true se inserido com sucesso, false se tabela cheia
     */
    public boolean inserir(int codigo) {
        int key = codigo;
        int i = 0;
        while (i < m) {
            int idx = meuFloorMod(h(key) + c1 * i + c2 * i * i, m);
            if (tabela[idx] == -1) {
                tabela[idx] = codigo;
                contadorColisoes += i; // soma de probes (0 se inserido na primeira tentativa)
                return true;
            }
            i++;
        }
        return false; // tabela cheia
    }

    /**
     * Verifica se código existe na tabela
     * @param codigo código a buscar
     * @return true se encontrado, false caso contrário
     */
    public boolean contem(int codigo) {
        int key = codigo;
        int i = 0;
        while (i < m) {
            int idx = meuFloorMod(h(key) + c1 * i + c2 * i * i, m);
            int cur = tabela[idx];
            if (cur == -1) return false; // posição vazia → não existe
            if (cur == codigo) {
                contadorColisoes += i; // opcional: conta probes durante busca
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Retorna vetor de ocupação da tabela (1 = ocupado, 0 = vazio)
     * @return int[] ocupação
     */
    public int[] ocupacao() {
        int[] oc = new int[m];
        for (int i = 0; i < m; i++) oc[i] = (tabela[i] == -1) ? 0 : 1;
        return oc;
    }
}
