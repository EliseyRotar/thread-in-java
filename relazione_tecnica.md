# Relazione Tecnica – Thread in Java
**Materia:** TPS – Tecnologie e Progettazione di Sistemi Informatici e di Telecomunicazioni  
**Traccia:** A – Simulazione di un conto bancario condiviso  

---

## 1. Introduzione

Un **thread** è un flusso di esecuzione all'interno di un processo. A differenza dei processi, i thread condividono la stessa memoria e le stesse risorse del programma che li contiene, il che li rende più leggeri da creare e gestire.

Si usano i thread ogni volta che si vuole eseguire più operazioni contemporaneamente nello stesso programma: ad esempio gestire più utenti in parallelo, aggiornare un'interfaccia grafica mentre si scaricano dati in background, oppure simulare entità indipendenti che agiscono allo stesso tempo.

In questo progetto i thread servono a simulare più utenti bancari che operano sullo stesso conto in modo concorrente.

---

## 2. Dichiarazione

Nel programma sono stati dichiarati tre oggetti di tipo `Thread` nella classe `Main`:

```java
Thread alice = new Thread(new Utente(conto, "versamento", 200.0, 3), "Alice");
Thread bob   = new Thread(new Utente(conto, "prelievo",   150.0, 4), "Bob");
Thread carlo = new Thread(new Utente(conto, "prelievo",   300.0, 2), "Carlo");
```

Ogni variabile è di tipo `Thread`, la classe standard di Java che rappresenta un singolo flusso di esecuzione.

---

## 3. Inizializzazione

Gli oggetti thread sono stati creati con il costruttore `Thread(Runnable target, String name)`, che accetta:

- un oggetto `Runnable` (in questo caso un'istanza di `Utente`) che definisce cosa deve fare il thread nel metodo `run()`;
- un nome in formato stringa, usato per identificare il thread nell'output.

La classe `Utente` implementa l'interfaccia `Runnable`, quindi il suo metodo `run()` contiene la logica eseguita da ciascun thread: un ciclo che ripete più volte un versamento oppure un prelievo sul conto condiviso.

---

## 4. Avvio: start() vs run()

Per avviare i thread è stato usato il metodo `start()`:

```java
alice.start();
bob.start();
carlo.start();
```

`start()` crea un nuovo flusso di esecuzione nella JVM e poi chiama `run()` su di esso in modo asincrono. In questo modo i tre thread partono quasi contemporaneamente e lavorano in parallelo.

Se invece si chiamasse direttamente `run()`, il metodo verrebbe eseguito nel thread corrente (il main), in sequenza, senza nessuna concorrenza. Non si avrebbe quindi alcun vantaggio dall'uso dei thread.

Dopo `start()` viene usato `join()` per far aspettare il thread main finché tutti gli altri non hanno terminato, prima di stampare il saldo finale.

---

## 5. Risorsa condivisa

La risorsa condivisa è l'oggetto `ContoBancario`, in particolare il suo attributo `saldo` di tipo `double`. Tutti e tre i thread ricevono un riferimento allo stesso oggetto `conto` e possono quindi modificarne il saldo chiamando `versa()` o `preleva()`.

```java
ContoBancario conto = new ContoBancario(1000.0);
```

Poiché in Java gli oggetti vengono passati per riferimento, ogni thread lavora sullo stesso oggetto in memoria, non su una copia.

---

## 6. Problema della concorrenza

Quando più thread accedono e modificano una variabile condivisa senza coordinazione, si rischia la **race condition**: due thread leggono il valore di `saldo` quasi contemporaneamente, ognuno lo modifica localmente e poi scrive il risultato. Una delle due modifiche viene persa.

Esempio concreto: se `saldo = 1000` e Alice preleva 200 mentre Bob preleva 300 nello stesso istante, entrambi leggono 1000. Alice scrive 800, Bob scrive 700. Il saldo finale è 700 invece di 500: si sono "persi" 200 euro.

Questo tipo di errore è difficile da riprodurre perché dipende dalla schedulazione dei thread, che non è deterministica.

---

## 7. Soluzione: synchronized

Per evitare la race condition è stata usata la parola chiave `synchronized` sui metodi `versa()`, `preleva()` e `getSaldo()` della classe `ContoBancario`:

```java
public synchronized void versa(double importo) { ... }
public synchronized void preleva(double importo) { ... }
public synchronized double getSaldo() { ... }
```

Quando un metodo è dichiarato `synchronized`, Java acquisisce automaticamente il **monitor** dell'oggetto prima di entrare nel metodo e lo rilascia quando il metodo termina. Se un secondo thread prova ad entrare in un metodo synchronized dello stesso oggetto mentre il monitor è già occupato, viene messo in attesa finché il primo non ha finito.

In questo modo le operazioni sul saldo sono **atomiche** dal punto di vista degli altri thread: nessun thread può vedere uno stato intermedio del conto.

---

## 8. Conclusione

Il programma funziona correttamente: i tre thread eseguono le loro operazioni in parallelo e il saldo rimane sempre consistente grazie a `synchronized`. L'output mostra chiaramente il nome del thread, l'operazione e il saldo aggiornato dopo ogni accesso.

Come possibile miglioramento si potrebbe usare `ReentrantLock` al posto di `synchronized` per avere più controllo: ad esempio si potrebbe impostare un timeout sul tentativo di acquisire il lock, oppure usare un `ReadWriteLock` per permettere letture concorrenti e bloccare solo le scritture. Un'altra estensione interessante sarebbe aggiungere un sistema di log su file per tenere traccia di tutte le operazioni.
