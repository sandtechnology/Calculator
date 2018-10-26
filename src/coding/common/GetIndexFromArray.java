package coding.common;

import java.util.ArrayList;
import java.util.List;


public class GetIndexFromArray {
    private List<Integer> index = new ArrayList<>();
    private int amount = 0;

    public void lookIndexAndAmount(List<String> array, String goal) {
        amount = 0;
        index.clear();
        for (int x = 0; array.size() > x; x++) {
            if (array.get(x).equals(goal)) {
                index.add(x);
                amount++;
            }
        }
    }

    public int getIndex(int num) {
        return index.get(num);
    }

    public int getIndexSize() {
        return index.size();
    }

    public int getAmount() {
        return amount;
    }

}
