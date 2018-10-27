package coding.gui;

import coding.calculate.ArithmeticProcessor;
import coding.language.LanguageKeyFinder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;

class ButtonListenerAdder {
    private final List<String> num_cache = new ArrayList<>();
    private final List<String> math_num_cache = new ArrayList<>();
    private String ready_add_num = "";
    private boolean isCleared, haveResult = false;


    //状态选择器大法好
    void addCommonButtonListener(String lang, JPanel panel, String type, JTextArea area) {
        for (int index = 0; panel.getComponentCount() > index; index++) {
            JButton button = (JButton) panel.getComponent(index);
            String text = button.getText();
            switch (type) {
                case "number":
                    switch (text) {
                        case ".":
                            button.addActionListener((ActionEvent) -> {
                                //第一个条件防止直接输入“.”
                                //第二个条件防止输入多个“.”
                                if (ready_add_num.length() == 0 | !ready_add_num.contains(".")) {
                                    area.append(text);
                                    ready_add_num += text;
                                }
                            });
                            area.addKeyListener(new KeyAdapter() {
                                                    //键盘支持
                                                    //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
                                                    public void keyPressed(KeyEvent e) {
                                                        if (e.getKeyChar() == '.')
                                                            button.doClick();
                                                    }
                                                }
                            );
                            break;
                        default:
                            //普通数字处理
                            //防止对特殊按钮添加一般事件
                            if (!button.getText().equals(new LanguageKeyFinder().getLanguageKey(lang, "exit"))) {
                                button.addActionListener((ActionEvent) -> {
                                    //有结果时输入清除结果
                                    //被清除时覆盖数字
                                    if (haveResult | isCleared) {
                                        num_cache.clear();
                                        area.setText(text);
                                        ready_add_num += text;
                                        haveResult = false;
                                        isCleared = false;
                                    }
                                    else {
                                        area.append(text);
                                        ready_add_num += text;
                                    }
                                });
                                area.addKeyListener(new KeyAdapter() {
                                    //键盘支持
                                    //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
                                    public void keyPressed(KeyEvent e) {
                                        if (e.getKeyChar() == text.charAt(0))
                                            button.doClick();
                                    }
                                });
                            }
                            break;
                    }
                    break;
                case "math":
                    switch (text) {
                        //处理等号
                        case "=":
                            button.addActionListener((ActionEvent) -> {
                                //1.防止出现结果时再次运算
                                //2.防止多个运算符号
                                if (!haveResult | ready_add_num.length() > 0) {
                                    num_cache.add(ready_add_num);
                                    ready_add_num = "";
                                    ArithmeticProcessor result = new ArithmeticProcessor();
                                    result.Arithmetic(math_num_cache, num_cache);
                                    area.setText(num_cache.get(0));
                                    haveResult = true;
                                }
                            });
                            area.addKeyListener(new KeyAdapter() {
                                //键盘支持
                                //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
                                public void keyPressed(KeyEvent e) {
                                    if (e.getKeyCode() == VK_ENTER | e.getKeyChar() == text.charAt(0))
                                        button.doClick();
                                }
                            });
                            break;
                        //处理一般运算符号
                        default:
                            //防止对特殊按钮添加一般事件
                            if (!button.getText().equals(new LanguageKeyFinder().getLanguageKey(lang, "clear"))) {
                                button.addActionListener((ActionEvent) -> {
                                    //1.防止出现结果时不能输入运算符号
                                    //2.防止多个运算符号
                                    if (haveResult | ready_add_num.length() > 0) {
                                        num_cache.add(ready_add_num);
                                        ready_add_num = "";
                                        math_num_cache.add(text);
                                        area.append("\n" + text + "\n");
                                        haveResult = false;
                                    }
                                });
                                //为了单独支持符号输入，此处再次switch
                                switch (text) {
                                    case "÷":
                                        area.addKeyListener(new KeyAdapter() {
                                            //键盘支持
                                            //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
                                            public void keyPressed(KeyEvent e) {
                                                if (e.getKeyChar() == text.charAt(0) | e.getKeyChar() == '/') {
                                                    button.doClick();
                                                }
                                            }
                                        });
                                        break;
                                    case "x":
                                        area.addKeyListener(new KeyAdapter() {
                                            //键盘支持
                                            //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
                                            public void keyPressed(KeyEvent e) {
                                                if (
                                                        e.getKeyChar() == 'x' |
                                                                e.getKeyChar() == 'X'
                                                ) {
                                                    button.doClick();
                                                }
                                            }
                                        });
                                        break;
                                    //对于+号和-号的处理
                                    default:
                                        area.addKeyListener(new KeyAdapter() {
                                            //键盘支持
                                            //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
                                            public void keyPressed(KeyEvent e) {
                                                if (e.getKeyChar() == text.charAt(0)) {
                                                    button.doClick();
                                                }
                                            }
                                        });
                                        break;
                                }
                            }
                            break;
                    }
                    break;
            }
        }
    }

    void addOtherButtonListener(String lang, JPanel panel, JTextArea area) {
        LanguageKeyFinder keyFinder = new LanguageKeyFinder();
        for (int index = 0; panel.getComponentCount() > index; index++) {
            JButton button = (JButton) panel.getComponent(index);
            String text = button.getText();
            String exit = keyFinder.getLanguageKey(lang, "exit");
            String clear = keyFinder.getLanguageKey(lang, "clear");
            if (exit.equals(text)) {
                button.addActionListener((ActionEvent) -> System.exit(0));
                area.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        //不知道为什么Esc键的代码为零
                        //而且用VK_ESCAPE也不行
                        if (e.getKeyCode() == VK_ESCAPE) {
                            System.exit(0);
                        }
                    }
                });
            }
            if (clear.equals(text)) {
                button.addActionListener((ActionEvent) -> {
                    num_cache.clear();
                    math_num_cache.clear();
                    ready_add_num = "";
                    area.setText("0.0");
                    isCleared = true;
                });
                area.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (e.getKeyChar() == 'c' | e.getKeyChar() == 'C') {
                            button.doClick();
                        }
                    }
                });
            }
        }
    }
}