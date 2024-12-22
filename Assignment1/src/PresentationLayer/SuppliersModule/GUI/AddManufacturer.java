package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddManufacturer extends JFrame {
    ;

    private JPanel contentPane;
    private JTextField manufacturerName_textField;

    OrdersController ordersController;
    SuppliersController suppliersController;
    ManufacturerController manufacturerController;

    /**
     * Create the frame.
     */

    public AddManufacturer() {

        this.ordersController= OrdersController.getInstance();
        this.suppliersController=SuppliersController.getInstance();
        this.manufacturerController=ManufacturerController.getInstance();

        setTitle("Add Manufacturer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        JLabel addManufacturer = new JLabel("Add Manufacturer");
        addManufacturer.setFont(new Font("Tahoma", Font.BOLD, 16));
        addManufacturer.setHorizontalAlignment(SwingConstants.CENTER);
        addManufacturer.setBounds(10, 11, 414, 34);
        contentPane.add(addManufacturer);

        JLabel lblName = new JLabel("Manufacturer Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblName.setBounds(10, 45, 414, 38);
        contentPane.add(lblName);

        manufacturerName_textField = new JTextField();
        manufacturerName_textField.setBounds(144, 54, 254, 20);
        contentPane.add(manufacturerName_textField);
        manufacturerName_textField.setColumns(10);

        JButton btnAdd = new JButton("Add");

        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String name = manufacturerName_textField.getText();
                    if (name == null) {
                        JOptionPane.showMessageDialog(getComponent(0), "There is an empty textBox. Try Again");
                        reset();
                    }

                    if (manufacturerController.doesManufacturerExist(name)) {
                        JOptionPane.showMessageDialog(getComponent(0), "Manufacturer already exist", "Warning", 0);
                        reset();
                    }
                    else {
                        JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                        manufacturerController.AddManufacturer(name);
                    }
                }
                catch (Exception t)
                {
                    JOptionPane.showMessageDialog(getComponent(0), "There is an empty textBox. Try Again");
                    reset();
                }
            }
        });
        btnAdd.setBounds(100, 100, 89, 23);
        contentPane.add(btnAdd);

        JButton btnExit = new JButton("Back to Menu");
        btnExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
        btnExit.setBounds(210, 100, 130, 23);
        contentPane.add(btnExit);
    }



    public void reset()
    {
        manufacturerName_textField.setText(null);
    }

}







