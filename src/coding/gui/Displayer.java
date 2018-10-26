package coding.gui;

import coding.calculate.ArithmeticProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Displayer extends JFrame {
    private final String[] num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "退出（Esc）"};
    private final String[] math_num = {"+", "-", "x", "÷", "="};
    private List<String> num_cache = new ArrayList<>();
    private List<String> math_num_cache = new ArrayList<>();
    private String ready_add_num = "";
    private String version = "V2.2";
    private boolean need_to_clear, has_result = false;

    public Displayer() {
        Container gui = getContentPane();
        gui.setLayout(new GridLayout(3, 1, 1, 1));
        JPanel num_panel = new JPanel(new GridLayout(4, 2, 1, 1));
        JPanel math_num_panel = new JPanel(new GridLayout(3, 3, 1, 1));
        JTextArea output = new JTextArea();
        output.setSize(20, 10);
        for (int i = 0; i < num.length; i++) {
            final int a = i;
            JButton button = new JButton(num[a]);
            //键盘支持
            //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
            output.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == num[a].toCharArray()[0])
                        button.doClick();
                }
            });
            button.addActionListener((ActionEvent) -> ButtonPerformer(output, "num", a));
            num_panel.add(button);
        }
        for (int i = 0; i < math_num.length; i++) {
            final int a = i;
            JButton button = new JButton(math_num[a]);
            //键盘支持
            //相关资料：http://www.cnblogs.com/KeenLeung/archive/2012/05/27/2520657.html
            output.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == math_num[a].toCharArray()[0])
                        button.doClick();
                    else
                        switch (e.getKeyChar()) {
                            case '/':
                                if ("÷".equals(math_num[a]))
                                    button.doClick();
                                break;
                            case '*':
                                if ("x".equals(math_num[a]))
                                    button.doClick();
                                break;
                            default:
                                switch (e.getKeyCode()) {
                                    case KeyEvent.VK_ENTER:
                                        if ("=".equals(math_num[a]))
                                            button.doClick();
                                        break;
                                    case KeyEvent.VK_ESCAPE:
                                        System.exit(0);
                                        break;
                                }
                                break;
                        }
                }
            });
            button.addActionListener((ActionEvent) -> ButtonPerformer(output, "math", a));
            math_num_panel.add(button);
        }
        JButton button = new JButton("清除（C）");
        button.addActionListener((ActionEvent) -> ButtonPerformer(output, "clear", 0));
        output.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'c' | e.getKeyChar() == 'C')
                    button.doClick();
            }
        });
        output.getAutoscrolls();
        output.setEditable(false);
        math_num_panel.add(button);
        output.setSize(10, 10);
        output.addFocusListener(new FocusAdapter() {
                                    public void focusGained(FocusEvent e) {
                                        setTitle("计算器" + version + " 作者：sand（可使用键盘输入）");
                                    }

                                    public void focusLost(FocusEvent e) {
                                        setTitle("计算器" + version + " 作者：sand（点击文本区域以启用键盘输入）");
                                    }
                                }
        );
        gui.add(output);
        gui.add(math_num_panel);
        gui.add(num_panel);
        setSize(500, 500);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }
    private void ButtonPerformer(JTextArea addTextArea, String type, int index) {
        switch (type) {
            case "math":
                if (has_result & !math_num[index].equals("=")) {
                    math_num_cache.add(math_num[index]);
                    addTextArea.append(math_num[index] + "\n");
                    has_result = false;
                } else if (ready_add_num.length() > 0) {
                    num_cache.add(ready_add_num);
                    ready_add_num = "";
                    if (need_to_clear) {
                        addTextArea.setText(math_num[index]);
                        need_to_clear = false;
                    } else {
                        addTextArea.append("\n" + math_num[index] + "\n");
                    }
                    if (math_num[index].equals("=")) {
                        ArithmeticProcessor result = new ArithmeticProcessor();
                        result.Arithmetic(math_num_cache, num_cache);
                        addTextArea.setText(num_cache.get(0) + "\n");
                        has_result = true;
                    } else {
                        math_num_cache.add(math_num[index]);
                    }
                }
                break;
            case "num":
                switch (num[index]) {
                    case "退出（Esc）":
                        System.exit(0);
                        break;
                    case ".":
                        if (!ready_add_num.contains(".")) {
                            ready_add_num = ready_add_num + ".";
                            addTextArea.append(".");
                        }
                        break;
                    default:
                        ready_add_num = ready_add_num + num[index];
                        if (need_to_clear) {
                            addTextArea.setText(num[index]);
                            need_to_clear = false;
                        } else {
                            addTextArea.append(num[index]);
                        }
                        break;
                }
                break;
            case "clear":
                addTextArea.setText("");
                need_to_clear = true;
                has_result = false;
                ready_add_num = "";
                num_cache.clear();
                math_num_cache.clear();
                break;
        }
    }


}