import matplotlib.pyplot as plt
import numpy as np

# ==============================
# DADOS DO RELATÓRIO
# ==============================

tamanhos = [10_000, 100_000, 1_000_000]

# --- Conjunto de 100.000 elementos ---
encadeado_insercao_100k = [10, 4, 9]
encadeado_busca_100k = [7, 3, 4]
encadeado_colisoes_100k = [452344, 11996, 0]

hashduplo_insercao_100k = [4850, 0, 2]
hashduplo_busca_100k = [4943, 0, 8]
hashduplo_colisoes_100k = [138122, 0, 0]

quadratica_insercao_100k = [3027, 5, 0]
quadratica_busca_100k = [3327, 1, 0]
quadratica_colisoes_100k = [147716, 0, 0]

# --- Conjunto de 1.000.000 elementos ---
encadeado_insercao_1M = [554, 46, 14]
encadeado_busca_1M = [1808, 63, 17]
encadeado_colisoes_1M = [49502258, 4523139, 141004]

hashduplo_insercao_1M = [45882, 418905, 6]
hashduplo_busca_1M = [45488, 424040, 5]
hashduplo_colisoes_1M = [148636, 2949568, 0]

quadratica_insercao_1M = [30847, 285927, 5]
quadratica_busca_1M = [29488, 277233, 5]
quadratica_colisoes_1M = [181826, 2682118, 0]

# --- Conjunto de 10.000.000 elementos ---
encadeado_insercao_10M = [315355, 36631, 1802]
encadeado_busca_10M = [492961, 49143, 2345]
encadeado_colisoes_10M = [4995004158, 495030359, 45197162]

hashduplo_insercao_10M = [469320, 4682697, 52046508]
hashduplo_busca_10M = [467953, 4668312, 53477912]
hashduplo_colisoes_10M = [168488, 3005398, 35393534]

quadratica_insercao_10M = [314640, 3138931, 35053603]
quadratica_busca_10M = [313975, 3136340, 37342884]
quadratica_colisoes_10M = [167132, 2468808, 32209101]

# ==============================
# 1. Tempo de Inserção vs Tamanho (por técnica)
# ==============================
plt.figure(figsize=(8,6))
plt.plot(tamanhos, encadeado_insercao_10M, marker='o', label="Encadeado")
plt.plot(tamanhos, quadratica_insercao_10M, marker='s', label="Sondagem Quadrática")
plt.plot(tamanhos, hashduplo_insercao_10M, marker='^', label="Hash Duplo")
plt.xscale("log")
plt.yscale("log")
plt.title("Tempo de Inserção vs Tamanho do Vetor (10M elementos)")
plt.xlabel("Tamanho do Vetor")
plt.ylabel("Tempo de Inserção (ms)")
plt.legend()
plt.grid(True)
plt.show()

# ==============================
# 2. Tempo de Busca vs Tamanho (por técnica)
# ==============================
plt.figure(figsize=(8,6))
plt.plot(tamanhos, encadeado_busca_10M, marker='o', label="Encadeado")
plt.plot(tamanhos, quadratica_busca_10M, marker='s', label="Sondagem Quadrática")
plt.plot(tamanhos, hashduplo_busca_10M, marker='^', label="Hash Duplo")
plt.xscale("log")
plt.yscale("log")
plt.title("Tempo de Busca vs Tamanho do Vetor (10M elementos)")
plt.xlabel("Tamanho do Vetor")
plt.ylabel("Tempo de Busca (ms)")
plt.legend()
plt.grid(True)
plt.show()

# ==============================
# 3. Colisões vs Tamanho (log scale)
# ==============================
plt.figure(figsize=(8,6))
plt.plot(tamanhos, encadeado_colisoes_10M, marker='o', label="Encadeado")
plt.plot(tamanhos, quadratica_colisoes_10M, marker='s', label="Sondagem Quadrática")
plt.plot(tamanhos, hashduplo_colisoes_10M, marker='^', label="Hash Duplo")
plt.xscale("log")
plt.yscale("log")
plt.title("Número de Colisões vs Tamanho do Vetor (10M elementos)")
plt.xlabel("Tamanho do Vetor")
plt.ylabel("Número de Colisões (escala log)")
plt.legend()
plt.grid(True)
plt.show()

# ==============================
# 4. Comparativo geral — média dos tempos
# ==============================
tecnicas = ["Encadeado", "Quadrática", "Hash Duplo"]

media_insercao = [
    np.mean(encadeado_insercao_10M),
    np.mean(quadratica_insercao_10M),
    np.mean(hashduplo_insercao_10M)
]
media_busca = [
    np.mean(encadeado_busca_10M),
    np.mean(quadratica_busca_10M),
    np.mean(hashduplo_busca_10M)
]

x = np.arange(len(tecnicas))
width = 0.35

plt.figure(figsize=(8,6))
plt.bar(x - width/2, media_insercao, width, label="Inserção")
plt.bar(x + width/2, media_busca, width, label="Busca")
plt.xticks(x, tecnicas)
plt.yscale("log")
plt.title("Comparativo Geral — Tempo Médio (Inserção e Busca)")
plt.ylabel("Tempo (ms, escala log)")
plt.legend()
plt.grid(True)
plt.show()
