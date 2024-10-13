import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator;
    private double firstOperand;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        mainPanel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        String[] buttonLabels = {
            "7", "8", "9", "C",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "<-", "0", "+", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            buttonPanel.add(button);
            if (label.equals("=")) {
                button.setBackground(new Color(139, 0, 0));
                button.setForeground(Color.WHITE);
            }
        }

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if ("0123456789".contains(command)) {
                display.setText(display.getText() + command);
            } else if (command.equals("C")) {
                display.setText("");
                operator = null;
                firstOperand = 0;
            } else if (command.equals("<-")) {
                String currentText = display.getText();
                if (currentText.length() > 0) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (command.equals("=")) {
                double secondOperand = Double.parseDouble(display.getText());
                double result = performOperation(firstOperand, secondOperand, operator);
                display.setText(String.valueOf(result));
                operator = null;
            } else {
                firstOperand = Double.parseDouble(display.getText());
                operator = command;
                display.setText("");
            }
        } catch (ArithmeticException ex) {
            display.setText("Error: Division by zero");
        } catch (NumberFormatException ex) {
            display.setText("Error: Invalid input");
        }
    }

    private double performOperation(double first, double second, String op) {
        switch (op) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                if (second == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return first / second;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
