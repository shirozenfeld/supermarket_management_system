package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddSupplierProduct extends JFrame
{
    JTextField unitprice_textField;
    JTextField amount_textField;
    String supplierId;
    JPanel contentPane;
    JTextField superproductID_textField;
    ManufacturerController manufacturerController=ManufacturerController.getInstance();
    SuppliersController suppliersController=SuppliersController.getInstance();
    public AddSupplierProduct()
    {
        supplierId="";
        this.suppliersController= SuppliersController.getInstance();
        setTitle("Add Supplier Product ");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        JLabel title = new JLabel("Add Supplier Product");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setBounds(0, 30, 613, 59);
        contentPane.add(title);

        JLabel lblsuperproductid = new JLabel("Insert supermarket id:");
        lblsuperproductid.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblsuperproductid.setHorizontalAlignment(SwingConstants.CENTER);
        lblsuperproductid.setBounds(10, 124, 180, 20);
        contentPane.add(lblsuperproductid);

        superproductID_textField = new JTextField();
        superproductID_textField.setBounds(217, 120, 230, 33);
        contentPane.add(superproductID_textField);
        superproductID_textField.setColumns(10);

        ///////

        JLabel lblamount = new JLabel("Insert amount:");
        lblamount.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblamount.setHorizontalAlignment(SwingConstants.CENTER);
        lblamount.setBounds(10, 164, 180, 20);
        contentPane.add(lblamount);

        amount_textField = new JTextField();
        amount_textField.setBounds(217, 160, 230, 33);
        contentPane.add(amount_textField);
        amount_textField.setColumns(10);

        ////
        ///////

        JLabel lblunitprice = new JLabel("Insert unit price:");
        lblunitprice.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblunitprice.setHorizontalAlignment(SwingConstants.CENTER);
        lblunitprice.setBounds(10, 204, 180, 20);
        contentPane.add(lblunitprice);

        unitprice_textField = new JTextField();
        unitprice_textField.setBounds(217, 200, 230, 33);
        contentPane.add(unitprice_textField);
        unitprice_textField.setColumns(10);

        ////


        JButton btnAdd = new JButton("Add");

        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String supermarketID = superproductID_textField.getText();
                    int amount = Integer.parseInt(amount_textField.getText());
                    double unit_price=Double.parseDouble(unitprice_textField.getText());
                    if (supplierId == null ||supplierId.trim().isEmpty() || supermarketID==null || supermarketID.trim().isEmpty() || unit_price<0 || amount<0)
                    {
                        JOptionPane.showMessageDialog(getComponent(0), "Invalid Input");
                        reset();


                    }

                    if (!manufacturerController.getAllSuperProducts().containsKey(supermarketID)) {
                        JOptionPane.showMessageDialog(getComponent(0), "Super Product Doesn't Exist", "Warning", 0);
                        reset();
                    }
                    else if(suppliersController.getSupplierProduct(supermarketID,supplierId)!=null)
                    {
                        JOptionPane.showMessageDialog(getComponent(0), "Supplier Product Already Exists", "Warning", 0);
                        reset();
                    }
                    else
                    {
                        suppliersController.add_supplierProduct(supermarketID,amount,unit_price,supplierId);
                        JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                    }


                }
                catch (Exception t)
                {
                    JOptionPane.showMessageDialog(getComponent(0), "There is an empty textBox. Try Again");
                    reset();
                }
            }
        });
        btnAdd.setBounds(400, 350, 89, 23);
        contentPane.add(btnAdd);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                reset();

            }
        });
        btnReset.setBounds(600, 350, 89, 23);
        contentPane.add(btnReset);

        JButton btnExit = new JButton("Back to Menu");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnExit.setBounds(120, 350, 150, 23);
        contentPane.add(btnExit);

    }
    public void reset()
    {
        amount_textField.setText(null);
        unitprice_textField.setText(null);
        superproductID_textField.setText(null);

    }

    public void setSupplierID(String id)
    {
        this.supplierId=id;
    }
}
