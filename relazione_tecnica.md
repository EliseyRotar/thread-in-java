# Relazione Tecnica – Thread in Java
**Materia:** TPS  
**Traccia:** A – Simulazione conto bancario condiviso

---

## 1. Introduzione

Un thread è un'unità di esecuzione che gira all'interno di un processo. La differenza principale con un processo è che i thread condividono la stessa memoria, quindi sono più leggeri da gestire. Si usano quando si vogliono fare più cose contemporaneamente nello stesso programma, ad esempio gestire più utenti in parallelo.

In questo progetto ho usato i thread per simulare tre utenti che accedono allo stesso conto bancario nello stesso momento.

---

## 2. Dichiarazione

I thread sono stati dichiarati nella classe `Main` come oggetti di tipo `Thread`:

```java
Thread alice = new Thread(new Utente(conto, "versamento", 200.0, 3), "Alice");
Thread bob = new Thread(new Utente(conto, "prelievo", 150.0, 4), "Bob");
Thread carlo = new Thread(new Utente(conto, "prelievo", 300.0, 2), "Carlo");
```

---

## 3. Inizializzazione

Gli oggetti thread sono stati creati passando al costruttore un oggetto `Utente` che implementa `Runnable` e una stringa con il nome del thread. La classe `Utente` contiene il metodo `run()` con le operazioni da eseguire.

---

## 4. Avvio: start() e differenza con run()

Per avviare i thread ho usato `start()`, che crea un nuovo flusso di esecuzione e chiama `run()` in modo asincrono. Se si chiama `run()` direttamente invece, il metodo viene eseguito nel thread principale in sequenza, senza nessuna concorrenza, quindi non ha senso usarlo al posto di `start()`.

Dopo l'avvio ho usato `join()` per far aspettare il main finché tutti i thread non finiscono, in modo da stampare il saldo finale corretto.

---

## 5. Risorsa condivisa

La risorsa condivisa è l'oggetto `ContoBancario`, in particolare il campo `saldo`. Tutti e tre i thread ricevono lo stesso riferimento all'oggetto e possono modificarlo chiamando `versa()` o `preleva()`.

---

## 6. Problema della concorrenza

Se più thread leggono e modificano `saldo` senza nessuna protezione si può verificare una race condition. Per esempio: se `saldo = 1000` e Alice e Bob leggono il valore nello stesso momento, ognuno calcola il nuovo saldo partendo da 1000 e uno dei due aggiornamenti viene perso. Il saldo finale sarebbe sbagliato.

---

## 7. Soluzione con synchronized

Ho usato `synchronized` sui metodi `versa()`, `preleva()` e `getSaldo()`. Quando un metodo è synchronized, Java acquisisce il monitor dell'oggetto prima di eseguirlo e lo rilascia alla fine. Se un altro thread cerca di entrare in un metodo synchronized dello stesso oggetto mentre il monitor è occupato, viene messo in attesa.

In questo modo le operazioni sul saldo sono atomiche e il valore rimane sempre consistente.

---

## 8. Conclusione

Il programma funziona correttamente, i thread eseguono le operazioni in parallelo e il saldo rimane coerente grazie a `synchronized`. Come miglioramento si potrebbe usare `ReentrantLock` per avere più controllo, oppure aggiungere un log su file delle operazioni.
