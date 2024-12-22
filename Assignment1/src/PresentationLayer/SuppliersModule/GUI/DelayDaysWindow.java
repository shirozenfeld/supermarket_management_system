package PresentationLayer.SuppliersModule.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelayDaysWindow extends JFrame {
    private JPanel contentPaneOrderly;
    private JTextField delayDays_textField;
    int delayDays;

    public DelayDaysWindow()
        {
        setTitle("Add Supplier's Delay Days ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPaneOrderly = new JPanel();
        contentPaneOrderly.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPaneOrderly);
        contentPaneOrderly.setLayout(null);

        JLabel addDelayDays = new JLabel("Supplier's Max Delay Days:");
        addDelayDays.setFont(new Font("Tahoma", Font.BOLD, 16));
        addDelayDays.setHorizontalAlignment(SwingConstants.CENTER);
        addDelayDays.setBounds(10, 72, 124, 20);
        contentPaneOrderly.add(addDelayDays);

        delayDays_textField = new JTextField();
        delayDays_textField.setBounds(144, 69, 254, 20);
        contentPaneOrderly.add(delayDays_textField);
        delayDays_textField.setColumns(10);

        JButton btnAddDelayDays = new JButton("Add");
        btnAddDelayDays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int number = Integer.parseInt(delayDays_textField.getText());
                    if (number >= 0)
                    {
                        delayDays = number;
                        GUIForm.addSupplier.setDelayDays(number);
                        JOptionPane.showMessageDialog(getComponent(0), "Added successfully", "Success", 0);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);
                        delayDays_textField.setText(null);
                    }

                } catch (Exception z) {
                    JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);
                    delayDays_textField.setText(null);
                }
            }

        });
        btnAddDelayDays.setBounds(86, 209, 89, 23);
        contentPaneOrderly.add(btnAddDelayDays);

    }




}

