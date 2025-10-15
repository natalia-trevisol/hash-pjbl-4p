package jav.clean;

public class Experimentos {

    private static int getTamanho(int[] vetor, int tamanhoConhecido) {
        return tamanhoConhecido;
    }

    private static void analisarGaps(int[] ocupacao, int tamanho) {
        int n = getTamanho(ocupacao, tamanho);
        int minGap = 2147483647;
        int maxGap = 0;
        long soma = 0;
        int cont = 0;
        int cur = -1;

        for (int i = 0; i < n; i++) {
            boolean occ = ocupacao[i] > 0;

            if (!occ) {
                if (cur == -1)
                    cur = 1;
                else
                    cur++;
            } else {
                if (cur > 0) {
                    if (cur < minGap) minGap = cur;
                    if (cur > maxGap) maxGap = cur;
                    soma += cur;
                    cont++;
                }
                cur = 0;
            }
        }

        if (cont == 0)
            System.out.println("Gaps: nenhum gap (ou tabela vazia)");
        else
            System.out.println("Gaps -> min: " + minGap + " max: " + maxGap +
                    " avg: " + String.format("%.2f", (double) soma / cont) +
                    " count: " + cont);
    }

    /**
     * Executa o experimento para Tabela Hash com Encadeamento Separado.
     * Insere os elementos, mede tempo, conta colisões, exibe top3 buckets e analisa gaps.
     */
    public static void rodarEncadeado(int[] dados, int tamanhoVetor, int dadosTam) {
        System.out.println("=== Encadeado: vetor=" + tamanhoVetor + " itens=" + dadosTam);

        TabelaHashEncadeada ht = new TabelaHashEncadeada(tamanhoVetor, dadosTam);

        long t0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.inserir(dados[i]);
        long t1 = System.nanoTime();
        long tempoInsercaoMs = (t1 - t0) / 1_000_000;

        long s0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.contem(dados[i]);
        long s1 = System.nanoTime();
        long tempoBuscaMs = (s1 - s0) / 1_000_000;

        System.out.println("Insert(ms): " + tempoInsercaoMs +
                " Search(ms): " + tempoBuscaMs +
                " Collisions: " + ht.contadorColisoes);

        int[] top3 = ht.top3Buckets();
        System.out.println("Top3 buckets: " + top3[0] + ", " + top3[1] + ", " + top3[2]);

        int[] sizes = ht.tamanhosBuckets();
        analisarGaps(sizes, tamanhoVetor);
    }

    public static void rodarDuplo(int[] dados, int tamanhoVetor, int dadosTam) {
        System.out.println("=== Double Hashing: vetor=" + tamanhoVetor + " itens=" + dadosTam);

        jav.clean.TabelaHashDuplo ht = new jav.clean.TabelaHashDuplo(tamanhoVetor);

        long t0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.inserir(dados[i]);
        long t1 = System.nanoTime();
        long tempoInsercaoMs = (t1 - t0) / 1_000_000;

        long s0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.contem(dados[i]);
        long s1 = System.nanoTime();
        long tempoBuscaMs = (s1 - s0) / 1_000_000;

        System.out.println("Insert(ms): " + tempoInsercaoMs +
                " Search(ms): " + tempoBuscaMs +
                " Collisions: " + ht.contadorColisoes);

        analisarGaps(ht.ocupacao(), tamanhoVetor);
    }

    public static void rodarQuadratico(int[] dados, int tamanhoVetor, int dadosTam) {
        System.out.println("=== Quadratic Probing: vetor=" + tamanhoVetor + " itens=" + dadosTam);

        jav.clean.TabelaHashQuadratico ht = new jav.clean.TabelaHashQuadratico(tamanhoVetor);

        long t0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.inserir(dados[i]);
        long t1 = System.nanoTime();
        long tempoInsercaoMs = (t1 - t0) / 1_000_000;

        long s0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.contem(dados[i]);
        long s1 = System.nanoTime();
        long tempoBuscaMs = (s1 - s0) / 1_000_000;

        System.out.println("Insert(ms): " + tempoInsercaoMs +
                " Search(ms): " + tempoBuscaMs +
                " Collisions: " + ht.contadorColisoes);

        analisarGaps(ht.ocupacao(), tamanhoVetor);
    }

    public static void main(String[] args) {
        int[] tamanhosVetor = {10000, 100000, 1_000_000};
        int[] tamanhosDados = {100000, 1_000_000, 10_000_000};
        long seed = 12345L;

        int totalDados = 3;
        int totalVetores = 3;

        for (int i = 0; i < totalDados; i++) {
            int dadosN = tamanhosDados[i];
            System.out.println("\n--- Gerando dados n=" + dadosN + " seed=" + seed);
            int[] dados = jav.clean.GeradorDados.gerarCodigosUnicos(dadosN, seed);

            for (int j = 0; j < totalVetores; j++) {
                int vet = tamanhosVetor[j];
                rodarEncadeado(dados, vet, dadosN);
                rodarDuplo(dados, vet, dadosN);
                rodarQuadratico(dados, vet, dadosN);
            }
        }
    }
}

