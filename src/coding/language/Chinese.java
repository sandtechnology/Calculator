package coding.language;


import java.util.HashMap;

class Chinese {
    private final HashMap<String, String> lang = new HashMap<>();

    Chinese() {
        lang.put("name", "计算器 ");
        lang.put("by", " 作者: ");
        lang.put("author", "sand");
        lang.put("tips1", "（可使用键盘输入）");
        lang.put("tips2", "（点击文本区域以启用键盘输入）");
        lang.put("exit", "退出（Esc）");
        lang.put("clear", "清除（C）");
    }

    String getLang(String key) {
        return lang.get(key);
    }
}
