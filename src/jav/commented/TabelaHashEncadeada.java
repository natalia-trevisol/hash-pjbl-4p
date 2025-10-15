package jav.commented;

/**
 * Implementação de encadeamento (chaining) sem LinkedList.
 *
 * Estruturas:
 *  - buckets[]: índice do primeiro nó de cada bucket (-1 se vazio)
 *  - nosKey[]: código armazenado no nó
 *  - nosNext[]: índice do próximo nó (-1 se fim)
 *
 * Capacidade de nosKey/nosNext >= número máximo de inserções esperadas.
 */
public class TabelaHashEncadeada {
    private final int m;
    private final int[] buckets; // cabeças das listas (-1 = vazio)

    private final int[] nosKey;  // código armazenado nos nós
    private final int[] nosNext; // próximo índice de nó (-1 se fim)
    private int noTopo = 0;      // próximo índice livre para alocar nó

    public long contadorColisoes = 0; // contador de colisões

    /**
     * Retorna o tamanho de um vetor
     */
    private int tamanhoVetor(int[] v) {
        // como não utilizamos try/catch nem length, assumimos que o vetor
        // está completamente preenchido até a sua capacidade real
        int count = 0;
        for (int i : v) count++;
        return count;
    }

    /**
     * Implementa uma raiz quadrada aproximada sem usar Math
     * (Método de Newton)
     */
    private double raiz(double valor) {
        if (valor <= 0) return 0;
        double x = valor;
        for (int i = 0; i < 20; i++) {
            x = 0.5 * (x + valor / x);
        }
        return x;
    }

    /**
     * Implementação própria de floor (arredonda para baixo)
     */
    private int piso(double valor) {
        int inteiro = (int) valor;
        return (valor < inteiro) ? inteiro - 1 : inteiro;
    }

    /**
     * Implementação própria de mod positivo
     */
    private int meuMod(int a, int b) {
        int r = a % b;
        return (r < 0) ? (r + b) : r;
    }

    /**
     * Construtor
     * @param m número de buckets
     * @param capacidade número máximo de nós
     */
    public TabelaHashEncadeada(int m, int capacidade) {
        this.m = m;
        this.buckets = new int[m];
        for (int i = 0; i < m; i++) buckets[i] = -1;

        this.nosKey = new int[capacidade];
        this.nosNext = new int[capacidade];
        for (int i = 0; i < capacidade; i++) nosNext[i] = -1;
    }

    /**
     * Função hash multiplicativa de Knuth
     * @param codigo código a ser mapeado
     * @return índice do bucket
     */
    private int funcaoHash(int codigo) {
        double A = (raiz(5) - 1) / 2.0; // constante sugerida
        double frac = codigo * A - piso(codigo * A); // parte fracionária
        return piso(m * frac);
    }

    /**
     * Insere código no bucket correspondente.
     * Colisões são contabilizadas como tamanho da lista antes da inserção.
     * @param codigo código a inserir
     * @return true se inseriu, false se não houver espaço
     */
    public boolean inserir(int codigo) {
        int capacidade = tamanhoVetor(nosKey);
        if (noTopo >= capacidade) return false; // sem espaço
        int idx = funcaoHash(codigo);
        int head = buckets[idx];

        // contar colisões (tamanho atual da lista)
        int tamanho = 0;
        int cur = head;
        while (cur != -1) {
            tamanho++;
            cur = nosNext[cur];
        }
        contadorColisoes += tamanho;

        // aloca novo nó no head da lista
        int novo = noTopo++;
        nosKey[novo] = codigo;
        nosNext[novo] = head;
        buckets[idx] = novo;
        return true;
    }

    /**
     * Verifica se código existe no bucket
     * @param codigo código a buscar
     * @return true se encontrado
     */
    public boolean contem(int codigo) {
        int idx = funcaoHash(codigo);
        int cur = buckets[idx];
        while (cur != -1) {
            if (nosKey[cur] == codigo) return true;
            cur = nosNext[cur];
        }
        return false;
    }

    /**
     * Retorna tamanho de cada bucket (número de nós)
     * @return int[] tamanho dos buckets
     */
    public int[] tamanhosBuckets() {
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int cnt = 0;
            int cur = buckets[i];
            while (cur != -1) {
                cnt++;
                cur = nosNext[cur];
            }
            res[i] = cnt;
        }
        return res;
    }

    /**
     * Retorna os 3 maiores buckets
     * @return int[3] maiores tamanhos (maior, 2º, 3º)
     */
    public int[] top3Buckets() {
        int[] sizes = tamanhosBuckets();
        int a = 0, b = 0, c = 0;
        for (int s : sizes) {
            if (s > a) { c = b; b = a; a = s; }
            else if (s > b) { c = b; b = s; }
            else if (s > c) { c = s; }
        }
        return new int[]{a, b, c};
    }
}
