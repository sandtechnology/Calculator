package coding.gui;

import coding.calculate.ArithmeticProcessor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

class ButtonActions {
    static final JTextArea output = new JTextArea();
    private static final List<JButton> buttons = new ArrayList<>();
    private static final List<String> numbersCache = new ArrayList<>();
    private static final List<String> operatorsCache = new ArrayList<>();
    private static final StringBuilder numberCache = new StringBuilder();
    //初始化设置
    private Status status;

    void init(JPanel... jps) {
        status = Status.START;
        for (JPanel jp : jps) {
            for (int i = 0; jp.getComponentCount() - 1 >= i; i++) {
                if (jp.getComponent(i) instanceof JButton) {
                    buttons.add((JButton) jp.getComponent(i));
                }
            }
        }
    }

    enum Status {START, TYPE_NUM, TYPE_OPE}

    class OutputAction extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            String text = String.valueOf(e.getKeyChar()).replace("/", "÷").toLowerCase();
            if (text.matches("[0-9]|\\+|-|x|÷|=|c|\\.")) {
                for (JButton b : buttons) {
                    if (b.getText().equals(text)) {
                        b.doClick(30);
                    }
                    if (text.equals("c") && b.getText().equals(Displayer.langCache.get("clear"))) {
                        b.doClick(30);
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                for (JButton b : buttons) {
                    if (b.getText().equals("=")) {
                        b.doClick(30);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if (b.getText().equals(Displayer.langCache.get("exit"))) {
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }

    class ClearAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            numberCache.setLength(0);
            numbersCache.clear();
            operatorsCache.clear();
            output.setText("0.0");
            status = Status.START;
        }
    }

    class EqualAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!numberCache.toString().equals("")) {
                numbersCache.add(numberCache.toString());
                numberCache.setLength(0);
                ArithmeticProcessor.Arithmetic(operatorsCache, numbersCache);
                output.setText(numbersCache.get(0));
                status = Status.START;
            }
        }
    }

    class OperatorsAction implements ActionListener {
        final String name;

        OperatorsAction(String name) {
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (status == Status.TYPE_NUM) {
                numbersCache.add(numberCache.toString());
                numberCache.setLength(0);
                operatorsCache.add(name);
                output.append("\n" + name + "\n");
                status = Status.TYPE_OPE;
            }
        }
    }

    class UniqueAction implements ActionListener {
        final String name;

        UniqueAction(String name) {
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //第一个条件防止直接输入“.”
            //第二个条件防止输入多个“.”
            if (status == Status.TYPE_NUM && !numberCache.toString().contains(name)) {
                output.append(name);
                numberCache.append(name);
            }
        }
    }

    class NumberAction implements ActionListener {
        final String name;

        NumberAction(String number) {
            this.name = number;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //有结果时输入清除结果
            //被清除时覆盖数字
            if (status == Status.START) {
                numbersCache.clear();
                output.setText(name);
            }
            else {
                output.append(name);
            }
            status = Status.TYPE_NUM;
            numberCache.append(name);
        }
    }
}
