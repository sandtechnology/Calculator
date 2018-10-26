package coding.calculate;

import coding.common.GetIndexFromArray;

import java.util.List;

public class ArithmeticProcessor {
    private String[] operators = {"x", "÷", "+", "-"};

    public void Arithmetic(List<String> math_num_cache, List<String> num_cache) {
        for (String x : operators) {
            GetIndexFromArray finder = new GetIndexFromArray();
            finder.lookIndexAndAmount(math_num_cache, x);
            for (int index = 0; finder.getIndexSize() > index; index++) {
                //因为删除了数字，而删除的个数刚刚好与index相同，所以此处得到的元素位置直接删去了index
                int num_index = finder.getIndex(index) - index;
                double value1 = Double.valueOf(num_cache.get(num_index));
                double value2 = Double.valueOf(num_cache.get(num_index + 1));
                num_cache.remove(num_cache.get(num_index + 1));
                switch (x) {
                    case "x":
                        num_cache.set(num_index, Double.toString(value1 * value2));
                        break;
                    case "÷":
                        num_cache.set(num_index, Double.toString(value1 / value2));
                        break;
                    case "+":
                        num_cache.set(num_index, Double.toString(value1 + value2));
                        break;
                    case "-":
                        num_cache.set(num_index, Double.toString(value1 - value2));
                        break;
                }
            }
            //清除运算符号
            math_num_cache.clear();
        }
    }
}
