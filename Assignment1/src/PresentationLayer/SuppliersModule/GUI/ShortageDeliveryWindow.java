package PresentationLayer.SuppliersModule.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShortageDeliveryWindow extends JFrame
{
    private JPanel contentPaneShortage;
    private JTextField ShortageDelivery_textField;
    String day;
    public ShortageDeliveryWindow()
    {
        setTitle("Add Supplier's Delay Days ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPaneShortage = new JPanel();
        contentPaneShortage.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPaneShortage);
        contentPaneShortage.setLayout(null);

        JLabel addDelayDays = new JLabel("Supplier's Max Delay Days:");
        addDelayDays.setFont(new Font("Tahoma", Font.BOLD, 16));
        addDelayDays.setHorizontalAlignment(SwingConstants.CENTER);
        addDelayDays.setBounds(10, 72, 124, 20);
        contentPaneShortage.add(addDelayDays);

        ShortageDelivery_textField = new JTextField();
        ShortageDelivery_textField.setBounds(144, 69, 254, 20);
        contentPaneShortage.add(ShortageDelivery_textField);
        ShortageDelivery_textField.setColumns(10);

        JButton btnAdd= new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    day = ShortageDelivery_textField.getText();
                    if (day!=null)
                    {
                        GUIForm.addSupplier.setShortageVisitingDay(day);
                        JOptionPane.showMessageDialog(getComponent(0), "Added successfully", "Success", 0);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);
                        ShortageDelivery_textField.setText(null);
                    }

                } catch (Exception z) {
                    JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);
                    ShortageDelivery_textField.setText(null);
                }
            }

        });
        btnAdd.setBounds(86, 209, 89, 23);
        contentPaneShortage.add(btnAdd);

    }


}
