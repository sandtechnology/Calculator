package coding.language;


public class LanguageKeyFinder {
    public String getLanguageKey(String lang, String key) {
        String result;
        switch (lang) {
            case "zh":
                Chinese zh = new Chinese();
                result = zh.getLang(key);
                break;
            default:
                English en = new English();
                result = en.getLang(key);
                break;
        }
        return result;
    }
}
