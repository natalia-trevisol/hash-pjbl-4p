# PJBL - HASH: Análise de Desempenho de Tabelas Hash em Java

## Identificação
Disciplina: Resolução de Problemas Estruturados em Computação
Turma: B - manhã
Curso: Ciência da Computação
Universidade: Pontifícia Universidade Católica do Paraná (PUCPR) 
Professor: Andrey Cabral Meira
Aluna: Natália Moritani Trevisol


## 1. Introdução
Este trabalho tem como objetivo comparar o desempenho de três implementações de tabelas hash em Java:
- Encadeamento separado (vetores de listas)
- Rehashing com Hash Duplo
- Rehashing com Sondagem Quadrática

Foram utilizados três tamanhos diferentes de vetores (10.000, 100.000 e 1.000.000) e três conjuntos de dados (100.000, 1.000.000 e 10.000.000 registros).

Os testes foram realizados em um notebook com processador Intel Core i3, 8 GB de RAM, utilizando a JVM padrão do Java 17.

---

## 2. Configurações e Metodologia
| Parâmetro | Valor |
|------------|--------|
| Linguagem | Java 17 |
| IDE | IntelliJ IDEA 2025.1.2 |
| Conjuntos de dados | 100.000, 1.000.000, 10.000.000 registros |
| Tamanhos dos vetores | 10.000, 100.000, 1.000.000 |
| Seed aleatória | `12345` (fixa para garantir comparabilidade) |
| Tempo medido em | milissegundos |
| Métricas analisadas | Tempo de inserção, tempo de busca, número de colisões, 3 maiores listas encadeadas geradas, gaps entre elementos no vetor |

---

## 3. Resultados

### 🔹 3.1. Conjunto de 100.000 elementos

| Função Hash | Tamanho Vetor | Inserção (ms) | Busca (ms) | Colisões |
|--------------|----------------|----------------|-------------|-----------|
| Encadeado | 10.000 | 8 | 7 | 452.344 |
| Hash Duplo | 10.000 | 4.551 | 4.188 | 138.122 |
| Quadrática | 10.000 | 2.897 | 2.817 | 147.716 |

| Encadeado | 100.000 | 3 | 2 | 11.996 |
| Hash Duplo | 100.000 | 0 | 0 | 0 |
| Quadrática | 100.000 | 0 | 0 | 0 |

| Encadeado | 1.000.000 | 3 | 2 | 11.996 |
| Hash Duplo | 1.000.000 | 0 | 0 | 0 |
| Quadrática | 1.000.000 | 0 | 0 | 0 |

---

### 🔹 3.2. Conjunto de 1.000.000 elementos

| Função Hash | Tamanho Vetor | Inserção (ms) | Busca (ms) | Colisões |
|--------------|----------------|----------------|-------------|-----------|
| Encadeado | 10.000 | 579 | 1.099 | 49.502.258 |
| Hash Duplo | 10.000 | 44.849 | 44.522 | 148.636 |
| Quadrática | 10.000 | 29.795 | 29.513 | 181.826 |
| Encadeado | 100.000 | 43 | 77 | 4.523.139 |
| Hash Duplo | 100.000 | 406.521 | 1.543.971 | 2.949.568 |
| Quadrática | 100.000 | 700.104 | 279.945 | 2.682.118 |

| Encadeado | 1.000.000 | 579 | 1.099 | 45.197.162 |
| Hash Duplo | 1.000.000 | 52.046.508 | 53.477.912 | 35.393.534 |
| Quadrática | 1.000.000 | 35.053.603 | 37.342.884 | 32.209.101 |

---

### 🔹 3.3. Conjunto de 10.000.000 elementos

| Função Hash | Tamanho Vetor | Inserção (ms) | Busca (ms) | Colisões |
|--------------|----------------|----------------|-------------|-----------|
| Encadeado | 10.000 | 330.148 | 440.221 | 4.995.004.158 |
| Hash Duplo | 10.000 | 457.339 | 484.032 | 168.488 |
| Quadrática | 10.000 | 309.488 | 415.347 | 167.132 |
| Encadeado | 100.000 | 53.090 | 62.405 | 495.030.359 |
| Hash Duplo | 100.000 | 5.208.865 | 4.812.200 | 3.005.398 |
| Quadrática | 100.000 | 3.275.529 | 3.253.102 | 2.468.808 |
| Encadeado | 1.000.000 | 1.809 | 2.395 | 45.197.162 |
| Hash Duplo | 1.000.000 | 52.046.508 | 53.477.912 | 35.393.534 |
| Quadrática | 1.000.000 | 35.053.603 | 37.342.884 | 32.209.101 |

---

## 4. Análise dos Resultados

### ⚙️ 4.1 Encadeamento
- Excelente desempenho quando o tamanho da tabela é grande em relação ao número de elementos (ex.: vetor = 1.000.000, dados = 10.000.000).  
- Sofre com **altas colisões** quando o vetor é pequeno, gerando listas encadeadas longas.

### ⚙️ 4.2 Hash Duplo
- **Evita clusters** (aglomerados) melhor que sondagem linear, mas é sensível ao fator de carga.  
- Para vetores grandes, o desempenho degrada com o aumento de colisões (principalmente em 10 milhões).

### ⚙️ 4.3 Sondagem Quadrática
- Geralmente mais rápida que hash duplo, mas também sofre se o vetor não for proporcionalmente grande.  
- Apresentou **menos colisões e tempos menores** que o hash duplo na maioria dos casos.

---

## 5. Conclusões
- **Encadeamento** se mostrou mais estável e escalável para grandes volumes, embora consuma mais memória.  
- **Sondagem quadrática** apresentou o melhor custo-benefício entre tempo e colisões.
- **Hash duplo** foi o mais pesado computacionalmente para grandes datasets, exigindo muito tempo de inserção e busca.
- Para cargas reais, o ideal é manter o **fator de carga ≤ 0.7** (tamanho do vetor próximo ao número de elementos/1.3).

---

## 6. Próximos Passos
- Implementar análise de memória (*overhead* de ponteiros vs vetor puro).  
- Exportar resultados automáticos para CSV (para gerar gráficos no Excel ou Python).  
- Testar diferentes funções hash (multiplicação, DJB2 etc).

---

## 7. Gráficos sugeridos
1. **Tempo de inserção vs tamanho do vetor** (um gráfico para cada técnica).  
2. **Tempo de busca vs tamanho do vetor**.  
3. **Número de colisões vs tamanho do vetor** (log scale recomendado).  
4. **Comparativo geral** das três técnicas para o mesmo conjunto de dados.

Ferr
