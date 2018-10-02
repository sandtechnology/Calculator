package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class gui extends JFrame {
    private final String[] num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "退出"};
    private final String[] math_num = {"+", "-", "x", "÷", "="};
    private List<String> num_cache = new ArrayList<>();
    private List<String> math_num_cache = new ArrayList<>();
    private String ready_add_num = "";
    private Float result = (float) 0;

    gui() {
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
                            case 'c' | 'C':
                                if ("清除".equals(math_num[a]))
                                    button.doClick();
                                break;
                            default:
                                switch (e.getKeyCode()) {
                                    case KeyEvent.VK_ENTER:
                                        if ("=".equals(math_num[a]))
                                            button.doClick();
                                        break;
                                    case KeyEvent.VK_ESCAPE:
                                        if ("退出".equals(math_num[a]))
                                            button.doClick();
                                        break;
                                }
                                break;
                        }
                }
            });
            button.addActionListener((ActionEvent) -> ButtonPerformer(output, "math", a));
            math_num_panel.add(button);
        }
        JButton button = new JButton("清除");
        button.addActionListener((ActionEvent) -> ButtonPerformer(output, "clear", 0));
        output.getAutoscrolls();
        output.setEditable(false);
        math_num_panel.add(button);
        output.setSize(10, 10);
        output.addFocusListener(new FocusAdapter() {
                                    public void focusGained(FocusEvent e) {
                                        setTitle("计算器V2.0 作者：sand（可使用键盘输入）");
                                    }

                                    public void focusLost(FocusEvent e) {
                                        setTitle("计算器V2.0 作者：sand（点击文本区域以启用键盘输入）");
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

    private float StringConvert(String str) {
        return new Float(str);
    }

    private void ButtonPerformer(JTextArea addTextArea, String type, int index) {
        switch (type) {
            case "math":
                if (ready_add_num.length() > 0) {
                    num_cache.add(ready_add_num);
                    ready_add_num = "";
                }
                addTextArea.append("\n" + math_num[index] + "\n");
                if (math_num[index].equals("=")) {
                    addTextArea.setText(Count() + "\n");
                } else {
                    math_num_cache.add(math_num[index]);
                }
                break;
            case "num":
                switch (num[index]) {
                    case "关于":
                        addTextArea.setText("作者：sand" + "\n");
                        break;
                    case "退出":
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
                        addTextArea.append(num[index]);
                        break;
                }
                break;
            case "clear":
                addTextArea.removeAll();
                ready_add_num = "";
                num_cache.clear();
                math_num_cache.clear();
                break;
        }
    }

    private String Count() {
        try {
            if (math_num_cache.toArray().length > 0) {
                for (int i = 0; i < math_num_cache.toArray().length; i++) {
                    if (i == 0) {
                        result = StringConvert(num_cache.get(i));
                    }
                    switch (math_num_cache.get(i)) {
                        case "+":
                            result = result + StringConvert(num_cache.get(i + 1));
                            break;
                        case "-":
                            result = result - StringConvert(num_cache.get(i + 1));
                            break;
                        case "x":
                            result = result * StringConvert(num_cache.get(i + 1));
                            break;
                        case "÷":
                            result = result / StringConvert(num_cache.get(i + 1));
                            break;
                    }
                }
            } else {
                result = StringConvert(num_cache.get(num_cache.toArray().length - 1));
            }
        } catch (Exception e) {
            if (!e.toString().contains("Index"))
                e.printStackTrace();
        }
        num_cache.clear();
        math_num_cache.clear();
        num_cache.add(0, result.toString());
        result = (float) 0;
        return num_cache.get(0);
    }
}
