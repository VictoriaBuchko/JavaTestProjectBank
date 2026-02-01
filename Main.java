package bank;

public class Main {
    public static void main(String[] args) throws ATMException {
        Bank bank = new Bank(4);
        bank.createAndLoadATM(0, 100, 5000, 20);
        ATM atm = bank.getATM(0);

        System.out.println("Початковий баланс: " + atm.getTotalAmount() + " грн\n");
        //InvalidAmountException
        //успішно
        atm.withdraw(2500);

        //помилка
        //atm.withdraw(1250); //не кратно 100

        //NotEnoughMoneyException
        System.out.println("Поточний баланс: " + atm.getTotalAmount() + " грн");

        //успішно
        atm.withdraw(1000);

        //помиока
        // atm.withdraw(50000);//недостатньо грошей

        System.out.println();

        //LimitExceededException

        //банкомат з малим лімітом купюр (5 купюр)
        bank.createAndLoadATM(1, 100, 5000, 5);
        ATM atm2 = bank.getATM(1);

        //успішно
        atm2.withdraw(2000); //2 купюри по 1000

        //помилка
        // atm2.withdraw(1000); //10 купюр по 100(> 5)

        System.out.println();
        System.out.println("Всього в мережі: " + bank.getTotalMoneyInNetwork() + " грн");
    }
}