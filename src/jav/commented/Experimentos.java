package jav.commented;

/**
 * Classe responsável por executar os testes de desempenho das três implementações
 * de tabela hash (encadeamento, hash duplo e sondagem quadrática).
 * Mede tempos de inserção e busca, conta colisões, exibe top3 buckets e analisa gaps.
 *
 * OBS: Para grandes volumes (1M, 10M) recomenda-se aumentar a memória JVM com -Xmx.
 */
public class Experimentos {

    /**
     * Função auxiliar para obter o tamanho de um vetor sem usar length.
     * Percorre o vetor até o final e conta manualmente os elementos.
     * @param vetor vetor de inteiros
     * @return número de elementos do vetor
     *
     * OBS: como não estão sendo usados try/catch nem length, o tamanho deve ser conhecido externamente.
     */
    private static int getTamanho(int[] vetor, int tamanhoConhecido) {
        return tamanhoConhecido;
    }

    /**
     * Analisa os gaps (lacunas) em um vetor de ocupação ou tamanhos de buckets.
     * Gaps são sequências de posições vazias entre elementos ocupados.
     *
     * @param ocupacao vetor representando ocupação (1+ = ocupado, 0 = vazio)
     * @param tamanho tamanho do vetor
     * Exibe no console o mínimo, máximo, média e quantidade de gaps encontrados.
     */
    private static void analisarGaps(int[] ocupacao, int tamanho) {
        int n = getTamanho(ocupacao, tamanho); // substitui uso de length
        int minGap = 2147483647; // Integer.MAX_VALUE manual
        int maxGap = 0;
        long soma = 0;
        int cont = 0;
        int cur = -1;

        for (int i = 0; i < n; i++) {
            boolean occ = ocupacao[i] > 0; // true se posição ocupada

            if (!occ) { // posição vazia: aumenta contador de gap
                if (cur == -1)
                    cur = 1;
                else
                    cur++;
            } else { // posição ocupada: finaliza gap atual se existir
                if (cur > 0) {
                    if (cur < minGap) minGap = cur;
                    if (cur > maxGap) maxGap = cur;
                    soma += cur;
                    cont++;
                }
                cur = 0; // reset contador de gap
            }
        }

        // Exibe resultado
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

        TabelaHashEncadeada ht = new jav.commented.TabelaHashEncadeada(tamanhoVetor, dadosTam);

        // Medir tempo de inserção
        long t0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.inserir(dados[i]);
        long t1 = System.nanoTime();
        long tempoInsercaoMs = (t1 - t0) / 1_000_000;

        // Medir tempo de busca
        long s0 = System.nanoTime();
        for (int i = 0; i < dadosTam; i++) ht.contem(dados[i]);
        long s1 = System.nanoTime();
        long tempoBuscaMs = (s1 - s0) / 1_000_000;

        System.out.println("Insert(ms): " + tempoInsercaoMs +
                " Search(ms): " + tempoBuscaMs +
                " Collisions: " + ht.contadorColisoes);

        // Exibir top3 buckets mais cheios
        int[] top3 = ht.top3Buckets();
        System.out.println("Top3 buckets: " + top3[0] + ", " + top3[1] + ", " + top3[2]);

        // Analisar gaps no tamanho dos buckets
        int[] sizes = ht.tamanhosBuckets();
        analisarGaps(sizes, tamanhoVetor);
    }

    /**
     * Executa o experimento para Hash Duplo (Double Hashing).
     * Inserção e busca nos elementos, medição de tempo e análise de gaps.
     */
    public static void rodarDuplo(int[] dados, int tamanhoVetor, int dadosTam) {
        System.out.println("=== Double Hashing: vetor=" + tamanhoVetor + " itens=" + dadosTam);

        jav.commented.TabelaHashDuplo ht = new jav.commented.TabelaHashDuplo(tamanhoVetor);

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

        // Gaps apenas das posições ocupadas
        analisarGaps(ht.ocupacao(), tamanhoVetor);
    }

    /**
     * Executa o experimento para Sondagem Quadrática.
     */
    public static void rodarQuadratico(int[] dados, int tamanhoVetor, int dadosTam) {
        System.out.println("=== Quadratic Probing: vetor=" + tamanhoVetor + " itens=" + dadosTam);

        jav.commented.TabelaHashQuadratico ht = new jav.commented.TabelaHashQuadratico(tamanhoVetor);

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

    /**
     * Método principal: gera conjuntos de dados, e executa os experimentos
     * para todos os tamanhos de vetor e implementações.
     */
    public static void main(String[] args) {
        int[] tamanhosVetor = {10000, 100000, 1_000_000};
        int[] tamanhosDados = {100000, 1_000_000, 10_000_000};
        long seed = 12345L; // seed para reproducibilidade

        int totalDados = 3;
        int totalVetores = 3;

        for (int i = 0; i < totalDados; i++) {
            int dadosN = tamanhosDados[i];
            System.out.println("\n--- Gerando dados n=" + dadosN + " seed=" + seed);
            int[] dados = jav.commented.GeradorDados.gerarCodigosUnicos(dadosN, seed);

            for (int j = 0; j < totalVetores; j++) {
                int vet = tamanhosVetor[j];
                rodarEncadeado(dados, vet, dadosN);
                rodarDuplo(dados, vet, dadosN);
                rodarQuadratico(dados, vet, dadosN);
            }
        }
    }
}
