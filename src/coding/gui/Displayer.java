package coding.gui;

import coding.language.LanguageKeyFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Locale;

public class Displayer {
    private final String[] num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};
    private final String[] math_num = {"+", "-", "x", "÷", "="};
    private final String[] key = {"name", "by", "author", "tips1", "tips2", "exit", "clear"};
    private final HashMap<String, String> langCache = new HashMap<>();
    private final String version = "V2.2";


    public Displayer() {
        JFrame frame = new JFrame();
        JPanel math_num_panel = new JPanel(new GridLayout(3, 3, 1, 1));
        JPanel num_panel = new JPanel(new GridLayout(3, 3, 1, 1));
        JTextArea output = new JTextArea();
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 1, 1, 1));
        init(frame, container, num_panel, math_num_panel, output);
    }


    private void init(JFrame frame, Container gui, JPanel num_panel, JPanel math_num_panel, JTextArea output) {
        //获取词条
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        for (String value : key) {
            LanguageKeyFinder keyFinder = new LanguageKeyFinder();
            langCache.put(value, keyFinder.getLanguageKey(language, value));
        }
        //设置文本区域
        output.getAutoscrolls();
        output.setEditable(false);
        output.addFocusListener(new FocusAdapter() {
                                    public void focusGained(FocusEvent e) {
                                        frame.setTitle(langCache.get("name") + version + langCache.get("by") + langCache.get("author") + langCache.get("tips1"));
                                    }

                                    public void focusLost(FocusEvent e) {
                                        frame.setTitle(langCache.get("name") + version + langCache.get("by") + langCache.get("author") + langCache.get("tips2"));
                                    }
                                }
        );
        //添加按钮
        //数字
        for (String num : num) {
            JButton a = new JButton(num);
            num_panel.add(a);
        }
        //运算符号
        for (String num : math_num) {
            JButton a = new JButton(num);
            math_num_panel.add(a);
        }
        //其他
        //退出按钮
        JButton exit = new JButton(langCache.get("exit"));
        num_panel.add(exit);
        //清除按钮
        JButton clear = new JButton(langCache.get("clear"));
        math_num_panel.add(clear);
        //添加事件
        ButtonListenerAdder adder = new ButtonListenerAdder();
        adder.addCommonButtonListener(language, num_panel, "number", output);
        adder.addCommonButtonListener(language, math_num_panel, "math", output);
        adder.addOtherButtonListener(language, num_panel, output);
        adder.addOtherButtonListener(language, math_num_panel, output);
        //添加三连
        gui.add(output);
        gui.add(math_num_panel);
        gui.add(num_panel);
        //设置大小
        //设置可见性
        frame.setVisible(true);
        //设置窗口事件
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }
}
