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
    private final String version = "v2.4-HotFix#01";


    public Displayer() {
        JFrame frame = new JFrame();
        JPanel math_num_panel = new JPanel(new GridLayout(0, 2, 0, 0));
        JPanel num_panel = new JPanel(new GridLayout(0, 3, 0, 0));
        JTextArea output = new JTextArea();
        //将文本区添加到滚动面板中
        JScrollPane scrollPane = new JScrollPane(output);
        //设置无边框
        //参考资料：https://bbs.csdn.net/topics/340235281
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 0, 0, 0));
        init(frame, container, scrollPane, num_panel, math_num_panel, output);
    }


    private void init(JFrame frame, Container gui, JScrollPane scrollPane, JPanel num_panel, JPanel math_num_panel, JTextArea output) {
        //获取词条
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        for (String value : key) {
            LanguageKeyFinder keyFinder = new LanguageKeyFinder();
            langCache.put(value, keyFinder.getLanguageKey(language, value));
        }
        //设置文本区域
        output.setEditable(false);
        //设置初始值
        output.setText("0.0");
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
            //设置无边框
            a.setBorderPainted(false);
            a.setFocusPainted(false);
            num_panel.add(a);
        }
        //运算符号
        for (String num : math_num) {
            JButton a = new JButton(num);
            //设置无边框
            a.setBorderPainted(false);
            a.setFocusPainted(false);
            math_num_panel.add(a);
        }
        //其他
        //退出按钮
        JButton exit = new JButton(langCache.get("exit"));
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        num_panel.add(exit);
        //清除按钮
        JButton clear = new JButton(langCache.get("clear"));
        clear.setBorderPainted(false);
        clear.setFocusPainted(false);
        math_num_panel.add(clear);
        //添加事件
        ButtonListenerAdder adder = new ButtonListenerAdder();
        adder.addCommonButtonListener(language, num_panel, "number", output);
        adder.addCommonButtonListener(language, math_num_panel, "math", output);
        adder.addOtherButtonListener(language, num_panel, output);
        adder.addOtherButtonListener(language, math_num_panel, output);
        //添加三连
        gui.add(scrollPane);
        gui.add(math_num_panel);
        gui.add(num_panel);
        //设置大小
        frame.setSize(500, 500);
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
