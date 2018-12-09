package coding.calculate;

import coding.common.GetIndexFromArray;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class ArithmeticProcessor {
    private static final String[] operators = {"x", "÷", "+", "-"};

    public static void Arithmetic(List<String> math_num_cache, List<String> num_cache) {
        //清除无用元素
        num_cache.remove("");
        //开始按照四则运算顺序遍历
        for (String x : operators) {
            GetIndexFromArray finder = new GetIndexFromArray();
            finder.lookIndexAndAmount(math_num_cache, x);
            if (finder.getAmount() != 0) {
                for (int i = 0; finder.getIndexSize() > i; i++) {
                    //因为删除了数字，而删除的个数刚刚好与i相同，所以此处得到的元素位置直接删去了i
                    int index = finder.getIndex(i) - i;
                    BigDecimal value1 = new BigDecimal(num_cache.get(index));
                    BigDecimal value2 = new BigDecimal(num_cache.get(index + 1));
                    math_num_cache.remove(index);
                    num_cache.remove(num_cache.get(index + 1));
                    switch (x) {
                        case "x":
                            num_cache.set(index, value1.multiply(value2).toString());
                            break;
                        case "÷":
                            num_cache.set(index, value1.divide(value2, ROUND_HALF_UP, 8).toString());
                            break;
                        case "+":
                            num_cache.set(index, value1.add(value2).toString());
                            break;
                        case "-":
                            num_cache.set(index, value1.subtract(value2).toString());
                            break;
                    }
                }
            }
        }
    }
}
