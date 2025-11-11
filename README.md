# 🧪 Benchmark de performances des Web Services REST

## 📘 Description du projet
Cette étude a pour objectif de **comparer les performances de trois implémentations REST** d’un même domaine applicatif (A, C, D), afin d’évaluer l’impact architectural sur la latence, le débit, la consommation de ressources et la stabilité.

Les trois variantes testées :

| Variante | Technologie / Architecture | Description |
|-----------|----------------------------|--------------|
| **A** | Spring Boot (Contrôleurs manuels – Jersey style) | Architecture légère, traitement manuel des requêtes HTTP |
| **C** | Spring Boot (@RestController classique) | Architecture REST standard avec sérialisation JSON |
| **D** | Spring Data REST | Endpoints auto-générés avec format HAL, sérialisation automatique |

---

## ⚙️ Environnement de test

| Élément | Valeur |
|----------|--------|
| Machine | Intel Core i7-11700K, 32 Go RAM |
| OS | Windows 11 Pro 64 bits |
| Java | OpenJDK 17 |
| Base de données | PostgreSQL 16.2 |
| Outil de test | Apache JMeter 5.6.3 |
| Monitoring | Grafana 10.2 + Prometheus 2.47 |
| JVM Flags | `-Xms1G -Xmx2G -XX:+UseG1GC` |

---

## 🧩 Scénarios de test

| Scénario | Description | Threads | Durée |
|-----------|--------------|----------|--------|
| **READ-heavy** | 80% GET / 20% autres | 200 | 10 min |
| **JOIN-filter** | Requêtes avec jointures et filtres | 120 | 8 min |
| **MIXED** | GET/POST/PUT/DELETE sur deux entités | 100 | 10 min |
| **HEAVY-body** | POST/PUT avec payloads volumineux (5–10 KB) | 60 | 8 min |

---

## 📊 Métriques collectées
Les mesures ont été réalisées à l’aide de **JMeter**, **PostgreSQL** (stockage des résultats), et **Grafana** (visualisation temps réel).

| Catégorie | Outil | Détails collectés |
|------------|--------|------------------|
| **Performance** | JMeter | RPS, p50, p95, p99, % erreurs |
| **Ressources JVM** | Prometheus | CPU, mémoire, GC, threads |
| **Base de données** | PostgreSQL | Insertion en temps réel des résultats |
| **Visualisation** | Grafana | Dashboards comparatifs par scénario |

---

## 🖼️ Exemple de visualisation Grafana


<img width="1455" height="752" alt="Screenshot 2025-11-11 223628" src="https://github.com/user-attachments/assets/af1318a6-b4d7-4513-8700-56fde11efa9d" />
<img width="741" height="380" alt="Screenshot 2025-11-11 223705" src="https://github.com/user-attachments/assets/24d08676-20f8-427b-bd91-1883d68dcd1d" />

> _Capture du tableau de bord Grafana montrant le débit (RPS), la latence et le taux d’erreur par variante._

---

## 🔬 Méthodologie
1. **Exécution des tests JMeter** pour chaque variante (A, C, D) sur les quatre scénarios.  
2. **Stockage automatique** des résultats dans PostgreSQL via un script Groovy intégré à JMeter.  
3. **Collecte de métriques JVM** via Prometheus.  
4. **Visualisation** et analyse comparative des performances dans Grafana.  
5. **Export et synthèse** des résultats dans un rapport Word (T0 → T7).

---

## 📈 Résumé des résultats (exemples simulés)

| Scénario | Variante A | Variante C | Variante D |
|-----------|-------------|-------------|-------------|
| **RPS moyen** | 520 req/s | 480 req/s | 340 req/s |
| **Latence p95 (ms)** | 120 | 145 | 190 |
| **% erreurs** | 0.3% | 0.6% | 1.1% |
| **CPU moyen (%)** | 62 | 68 | 77 |

> **Conclusion :**  
> - **Variante A** est la plus performante (faible latence, consommation modérée).  
> - **Variante C** offre un bon compromis entre performance et maintenabilité.  
> - **Variante D** présente une surcharge HAL et une latence plus élevée, mais simplifie l’exposition des endpoints.

---

## 📁 Structure du dépôt

```bash
├── /jmeter/
│   ├── test_plan_variant_A.jmx
│   ├── test_plan_variant_C.jmx
│   └── test_plan_variant_D.jmx
├── /scripts/
│   └── insert_results_postgres.groovy
├── /grafana/
│   ├── dashboard.json
│   └── grafana_dashboard_placeholder.png
├── Benchmark_Tables_Resultats.docx
└── README.md
