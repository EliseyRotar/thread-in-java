public class ContoBancario {

    private double saldo;

    public ContoBancario(double saldoIniziale) {
        this.saldo = saldoIniziale;
    }

    public synchronized void versa(double importo) {
        saldo += importo;
        System.out.printf("[%s] Versamento: +%.2f€  -->  Saldo attuale: %.2f€%n",
                Thread.currentThread().getName(), importo, saldo);
    }

    public synchronized void preleva(double importo) {
        if (importo > saldo) {
            System.out.printf("[%s] Prelievo di %.2f€ RIFIUTATO (saldo insufficiente: %.2f€)%n",
                    Thread.currentThread().getName(), importo, saldo);
            return;
        }
        saldo -= importo;
        System.out.printf("[%s] Prelievo: -%.2f€  -->  Saldo attuale: %.2f€%n",
                Thread.currentThread().getName(), importo, saldo);
    }

    public synchronized double getSaldo() {
        return saldo;
    }
}
