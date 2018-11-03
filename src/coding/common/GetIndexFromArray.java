package coding.common;

import java.util.ArrayList;
import java.util.List;


public class GetIndexFromArray {
    private final List<Integer> index = new ArrayList<>();
    private int amount = 0;

    //方法作用：查找数组中目标元素的位置
    public void lookIndexAndAmount(List<String> array, String goal) {
        amount = 0;
        index.clear();
        if (array.size() != 0) {
            for (int x = 0; array.size() > x; x++) {
                if (array.get(x).equals(goal)) {
                    index.add(x);
                    amount++;
                }
            }
        }
    }

    //获取数组中目标元素位置构成的List中的元素
    public int getIndex(int num) {
        return index.get(num);
    }

    //获取数组中目标元素位置构成的List的大小
    public int getIndexSize() {
        return index.size();
    }

    //获取数组中目标元素的数量
    public int getAmount() {
        return amount;
    }

}
