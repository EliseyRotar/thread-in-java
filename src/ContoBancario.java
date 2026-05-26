public class ContoBancario {

    private double saldo;

    public ContoBancario(double saldoIniziale) {
        this.saldo = saldoIniziale;
    }

    public synchronized void versa(double importo) {
        saldo += importo;
        System.out.println("[" + Thread.currentThread().getName() + "] Versamento: +" + importo + "€  -->  Saldo: " + saldo + "€");
    }

    public synchronized void preleva(double importo) {
        if (importo > saldo) {
            System.out.println("[" + Thread.currentThread().getName() + "] Prelievo di " + importo + "€ rifiutato, saldo insufficiente: " + saldo + "€");
            return;
        }
        saldo -= importo;
        System.out.println("[" + Thread.currentThread().getName() + "] Prelievo: -" + importo + "€  -->  Saldo: " + saldo + "€");
    }

    public synchronized double getSaldo() {
        return saldo;
    }
}
