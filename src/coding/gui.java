package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

class gui extends JFrame {
    private final String[] num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "关于", "退出"};
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
            button.addActionListener((ActionEvent) -> {
                switch (num[a]) {
                    case "关于":
                        output.setText("作者：sand" + "\n");
                        break;
                    case "退出":
                        System.exit(0);
                        break;
                    default:
                        ready_add_num = ready_add_num + num[a];
                        output.append(num[a]);
                        break;
                }
            });
            num_panel.add(button);
        }
        for (int i = 0; i < math_num.length; i++) {
            final int a = i;
            JButton c = new JButton(math_num[a]);
            c.addActionListener((ActionEvent) -> {
                if (ready_add_num.length() > 0) {
                    num_cache.add(ready_add_num);
                    ready_add_num = "";
                }
                output.append("\n" + math_num[a] + "\n");
                if (math_num[a].equals("=")) {
                    output.setText(Count() + "\n");
                } else {
                    math_num_cache.add(math_num[a]);
                }
            });
            math_num_panel.add(c);
        }
        JButton c = new JButton("清除");
        c.addActionListener((ActionEvent) -> {
            output.setText("");
            ready_add_num = "";
            num_cache.clear();
            math_num_cache.clear();
        });
        output.setEditable(false);
        math_num_panel.add(c);
        output.setSize(10, 10);
        gui.add(output);
        gui.add(math_num_panel);
        gui.add(num_panel);
        setSize(500, 500);
        setTitle("计算器V2.0");
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
        Float F = new Float(str);
        return F.intValue();
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
            e.printStackTrace();
        }
        num_cache.clear();
        math_num_cache.clear();
        num_cache.add(0, result.toString());
        result = (float) 0;
        return num_cache.get(0);
    }
}
