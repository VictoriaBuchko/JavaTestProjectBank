package bank;
import java.util.Map;
import java.util.LinkedHashMap;

public class ATM {
    private Map<Integer, Integer> banknotes; //номінал - кількість
    private int minAmount;
    private int maxAmount;
    private int maxNotesPerOperation;

    public ATM(int minAmount, int maxAmount, int maxNotesPerOperation) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.maxNotesPerOperation = maxNotesPerOperation;
        banknotes = new LinkedHashMap<>();

        banknotes.put(1000, 0);
        banknotes.put(500, 0);
        banknotes.put(200, 0);
        banknotes.put(100, 0);
    }

    //ініціалізація
    public void loadMoney(int denomination, int count) throws InvalidAmountException {
        if (!banknotes.containsKey(denomination) || count <= 0) {
            throw new InvalidAmountException("Неправильний номінал або кількість");
        }
        banknotes.put(denomination, banknotes.get(denomination) + count);
    }


    public int getTotalAmount() {
        int sum = 0;
        for (var entry : banknotes.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        return sum;
    }

    //внесення грошей вручну
    public void deposit(int denomination, int count) throws InvalidAmountException {
        loadMoney(denomination, count);
    }

    //зняття
    public void withdraw(int amount) throws ATMException {
        if (amount < minAmount || amount > maxAmount || amount % 100 != 0) {
            throw new InvalidAmountException("Недопустима сума для зняття");
        }

        if (amount > getTotalAmount()) {
            throw new NotEnoughMoneyException("Недостатньо коштів у банкоматі");
        }

        int remaining = amount;
        int notesUsed = 0;
        Map<Integer, Integer> temp = new LinkedHashMap<>();

        for (var entry : banknotes.entrySet()) {
            int denom = entry.getKey();
            int available = entry.getValue();

            int needed = remaining / denom;
            int used = Math.min(needed, available);

            if (used > 0) {
                temp.put(denom, used);
                remaining -= denom * used;
                notesUsed += used;
            }
        }

        if (remaining != 0) {
            throw new NotEnoughMoneyException("Неможливо видати суму наявними купюрами");
        }

        if (notesUsed > maxNotesPerOperation) {
            throw new LimitExceededException("Перевищено ліміт кількості купюр");
        }

        for (var entry : temp.entrySet()) {
            banknotes.put(entry.getKey(),
                    banknotes.get(entry.getKey()) - entry.getValue());
        }
    }
}
