# Thread in Java – Simulazione conto bancario condiviso

Esercitazione valutata per TPS (Tecnologie e Progettazione di Sistemi Informatici e di Telecomunicazioni), Traccia A.

## Descrizione

Simulazione di un conto bancario condiviso tra più utenti. Ogni utente è rappresentato da un thread che esegue versamenti o prelievi in modo concorrente. L'accesso al saldo è protetto con `synchronized` per evitare race condition.

## Struttura del progetto

```
thread-in-java/
├── src/
│   ├── ContoBancario.java   # classe con saldo e metodi synchronized
│   ├── Utente.java          # Runnable che rappresenta un utente/thread
│   └── Main.java            # crea e avvia i thread, stampa il saldo finale
└── relazione_tecnica.md     # relazione sui thread in Java (tutti i punti richiesti)
```

## Come compilare ed eseguire

```bash
cd src
javac ContoBancario.java Utente.java Main.java
java Main
```

## Output di esempio

```
=== Simulazione conto bancario condiviso ===
Saldo iniziale: 1000.00€

[Alice] Versamento: +200.00€  -->  Saldo attuale: 1200.00€
[Carlo] Prelievo: -300.00€  -->  Saldo attuale: 900.00€
[Bob]   Prelievo: -150.00€  -->  Saldo attuale: 750.00€
...

=== Fine simulazione ===
Saldo finale: 400.00€
```

L'ordine delle righe varia ad ogni esecuzione perché i thread vengono schedulati dalla JVM in modo non deterministico.

## Thread utilizzati

| Thread | Operazione   | Importo | N. operazioni |
|--------|-------------|---------|--------------|
| Alice  | Versamento  | 200€    | 3            |
| Bob    | Prelievo    | 150€    | 4            |
| Carlo  | Prelievo    | 300€    | 2            |

## Sincronizzazione

I metodi `versa()`, `preleva()` e `getSaldo()` sono dichiarati `synchronized`. Questo garantisce che un solo thread alla volta possa modificare il saldo, evitando race condition.
