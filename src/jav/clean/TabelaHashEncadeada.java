package jav.clean;

public class TabelaHashEncadeada {
    private final int m;
    private final int[] buckets;

    private final int[] nosKey;
    private final int[] nosNext;
    private int noTopo = 0;

    public long contadorColisoes = 0;

    private int tamanhoVetor(int[] v) {
        int count = 0;
        for (int i : v) count++;
        return count;
    }

    private double raiz(double valor) {
        if (valor <= 0) return 0;
        double x = valor;
        for (int i = 0; i < 20; i++) {
            x = 0.5 * (x + valor / x);
        }
        return x;
    }

    private int piso(double valor) {
        int inteiro = (int) valor;
        return (valor < inteiro) ? inteiro - 1 : inteiro;
    }

    private int meuMod(int a, int b) {
        int r = a % b;
        return (r < 0) ? (r + b) : r;
    }

    public TabelaHashEncadeada(int m, int capacidade) {
        this.m = m;
        this.buckets = new int[m];
        for (int i = 0; i < m; i++) buckets[i] = -1;

        this.nosKey = new int[capacidade];
        this.nosNext = new int[capacidade];
        for (int i = 0; i < capacidade; i++) nosNext[i] = -1;
    }

    private int funcaoHash(int codigo) {
        double A = (raiz(5) - 1) / 2.0;
        double frac = codigo * A - piso(codigo * A);
        return piso(m * frac);
    }

    public boolean inserir(int codigo) {
        int capacidade = tamanhoVetor(nosKey);
        if (noTopo >= capacidade) return false;
        int idx = funcaoHash(codigo);
        int head = buckets[idx];

        int tamanho = 0;
        int cur = head;
        while (cur != -1) {
            tamanho++;
            cur = nosNext[cur];
        }
        contadorColisoes += tamanho;

        int novo = noTopo++;
        nosKey[novo] = codigo;
        nosNext[novo] = head;
        buckets[idx] = novo;
        return true;
    }

    public boolean contem(int codigo) {
        int idx = funcaoHash(codigo);
        int cur = buckets[idx];
        while (cur != -1) {
            if (nosKey[cur] == codigo) return true;
            cur = nosNext[cur];
        }
        return false;
    }

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

