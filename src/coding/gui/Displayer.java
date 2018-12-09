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
    static final HashMap<String, String> langCache = new HashMap<>();
    private static final JFrame frame = new JFrame();
    private final String[] key = {"name", "by", "author", "tips1", "tips2", "exit", "clear"};
    private static final JPanel mathNumPanel = new JPanel(new GridLayout(0, 2, 0, 0));
    private static final JPanel numPanel = new JPanel(new GridLayout(0, 3, 0, 0));
    private final String[] operators = {"+", "-", "x", "÷"};
    private final String[] specialOperators = {".", "=", "c"};
    private final String version = "V2.6";
    private final JTextArea output = ButtonActions.output;
    private final Container gui = frame.getContentPane();
    private final JScrollPane scrollPane = new JScrollPane(output);

    public Displayer() {
        //将文本区添加到滚动面板中
        //设置无边框
        //参考资料：https://bbs.csdn.net/topics/340235281
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        gui.setLayout(new GridLayout(3, 0, 0, 0));
        init();
    }


    private void init() {
        ButtonActions action = new ButtonActions();
        //获取词条
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        for (String value : key) {
            LanguageKeyFinder keyFinder = new LanguageKeyFinder();
            langCache.put(value, keyFinder.getLanguageKey(language, value));
        }
        //设置文本区域
        output.addKeyListener(action.new OutputAction());
        output.setEditable(false);
        output.setFont(new Font("微软雅黑", Font.PLAIN, 15));
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
        for (int i = 0; i < 10; i++) {
            String num = String.valueOf(i);
            JButton a = new JButton(num);
            //设置无边框
            a.setBorderPainted(false);
            a.setFocusPainted(false);
            a.setFont(new Font("微软雅黑", Font.BOLD, 15));
            a.addActionListener(action.new NumberAction(num));
            numPanel.add(a);
        }
        //运算符号
        for (String num : operators) {
            JButton a = new JButton(num);
            //设置无边框
            a.setBorderPainted(false);
            a.setFocusPainted(false);
            a.setFont(new Font("微软雅黑", Font.BOLD, 15));
            a.addActionListener(action.new OperatorsAction(num));
            mathNumPanel.add(a);
        }
        for (String num : specialOperators) {
            JButton a = new JButton(num);
            a.setBorderPainted(false);
            a.setFocusPainted(false);
            a.setFont(new Font("微软雅黑", Font.BOLD, 15));
            switch (num) {
                case ".":
                    a.addActionListener(action.new UniqueAction(num));
                    numPanel.add(a);
                    break;
                case "=":
                    a.addActionListener(action.new EqualAction());
                    mathNumPanel.add(a);
                    break;
            }
        }
        //其他
        //退出按钮
        JButton exit = new JButton(langCache.get("exit"));
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener((event) -> System.exit(0));
        exit.setFont(new Font("微软雅黑", Font.BOLD, 15));
        numPanel.add(exit);
        //清除按钮
        JButton clear = new JButton(langCache.get("clear"));
        clear.setBorderPainted(false);
        clear.setFocusPainted(false);
        clear.addActionListener(action.new ClearAction());
        clear.setFont(new Font("微软雅黑", Font.BOLD, 15));
        mathNumPanel.add(clear);
        //添加按钮列表
        action.init(mathNumPanel, numPanel);
        //添加三连
        gui.add(scrollPane);
        gui.add(mathNumPanel);
        gui.add(numPanel);
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
