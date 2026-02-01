package bank;

public class Bank {
    private ATM[] atms;

    public Bank(int atmCount) {
        atms = new ATM[atmCount];
    }

    public void createAndLoadATM(int index, int minAmount, int maxAmount, int maxNotesPerOperation) {

        ATM atm = new ATM(minAmount, maxAmount, maxNotesPerOperation);

        try {
            atm.loadMoney(1000, 10);
            atm.loadMoney(500, 20);
            atm.loadMoney(200, 30);
            atm.loadMoney(100, 50);
        } catch (ATMException e) {
            System.out.println("Помилка завантаження ATM: " + e.getMessage());
        }

        atms[index] = atm;
    }

    public ATM getATM(int index) {
        return atms[index];
    }

    public int getTotalMoneyInNetwork() {
        int total = 0;
        for (ATM atm : atms) {
            if (atm != null) {
                total += atm.getTotalAmount();
            }
        }
        return total;
    }
}
