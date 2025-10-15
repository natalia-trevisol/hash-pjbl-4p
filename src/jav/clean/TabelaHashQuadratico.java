package jav.clean;

public class TabelaHashQuadratico {

    private final int m;
    private final int[] tabela;
    public long contadorColisoes = 0;
    private final int c1 = 1;
    private final int c2 = 3;

    public TabelaHashQuadratico(int m) {
        this.m = m;
        this.tabela = new int[m];
        for (int i = 0; i < m; i++) tabela[i] = -1;
    }

    private int meuFloorMod(int a, int b) {
        int mod = a % b;
        return (mod < 0) ? (mod + b) : mod;
    }

    private int h(int key) {
        return meuFloorMod(key, m);
    }

    public boolean inserir(int codigo) {
        int key = codigo;
        int i = 0;
        while (i < m) {
            int idx = meuFloorMod(h(key) + c1 * i + c2 * i * i, m);
            if (tabela[idx] == -1) {
                tabela[idx] = codigo;
                contadorColisoes += i;
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean contem(int codigo) {
        int key = codigo;
        int i = 0;
        while (i < m) {
            int idx = meuFloorMod(h(key) + c1 * i + c2 * i * i, m);
            int cur = tabela[idx];
            if (cur == -1) return false;
            if (cur == codigo) {
                contadorColisoes += i;
                return true;
            }
            i++;
        }
        return false;
    }

    public int[] ocupacao() {
        int[] oc = new int[m];
        for (int i = 0; i < m; i++) oc[i] = (tabela[i] == -1) ? 0 : 1;
        return oc;
    }
}

