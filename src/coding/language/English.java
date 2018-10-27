package coding.language;

import java.util.HashMap;

class English {
    private final HashMap<String, String> lang = new HashMap<>();

    English() {
        lang.put("name", "Calculator ");
        lang.put("by", "  Author: ");
        lang.put("author", "sand");
        lang.put("tips1", "（KeyBroad Supported Enabled）");
        lang.put("tips2", "（Click TextArea to enable KeyBroad Support）");
        lang.put("exit", "Exit（Esc）");
        lang.put("clear", "Clear（C）");
    }

    String getLang(String key) {
        return lang.get(key);
    }
}
