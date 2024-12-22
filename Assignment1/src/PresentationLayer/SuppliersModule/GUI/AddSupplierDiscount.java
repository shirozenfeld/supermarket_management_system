package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddSupplierDiscount extends JFrame
{
    JTextField giftpercentage_textField;
    JTextField unitprice_textField;
    JTextField amount_textField;
    String supplierId;
    JPanel contentPane;
    JTextField superproductID_textField;
    JComboBox type_button;
    ManufacturerController manufacturerController=ManufacturerController.getInstance();
    SuppliersController suppliersController=SuppliersController.getInstance();
    public AddSupplierDiscount()
    {
        supplierId="";
        this.suppliersController= SuppliersController.getInstance();
        setTitle("Add Supplier Discount ");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        JLabel title = new JLabel("Add Supplier Discount");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setBounds(0, 30, 613, 59);
        contentPane.add(title);

        JLabel lblsuperproductid = new JLabel("Insert product's supermarket id:");
        lblsuperproductid.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblsuperproductid.setHorizontalAlignment(SwingConstants.CENTER);
        lblsuperproductid.setBounds(10, 124, 250, 20);
        contentPane.add(lblsuperproductid);

        superproductID_textField = new JTextField();
        superproductID_textField.setBounds(280, 120, 230, 33);
        contentPane.add(superproductID_textField);
        superproductID_textField.setColumns(10);

        ///////

        JLabel lblamount = new JLabel("Insert base amount for discount:");
        lblamount.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblamount.setHorizontalAlignment(SwingConstants.CENTER);
        lblamount.setBounds(10, 164, 250, 20);
        contentPane.add(lblamount);

        amount_textField = new JTextField();
        amount_textField.setBounds(280, 160, 230, 33);
        contentPane.add(amount_textField);
        amount_textField.setColumns(10);

        ////
        JLabel lbltype = new JLabel("Insert discount type:");
        lbltype.setFont(new Font("Tahoma", Font.BOLD, 13));
        lbltype.setHorizontalAlignment(SwingConstants.CENTER);
        lbltype.setBounds(10, 244, 250, 20);
        contentPane.add(lbltype);

        String[] options={"Money Discount (Percentage)","Quantity Discount (Items)"};

        // Create a ButtonGroup and add the radio buttons to it
        type_button = new JComboBox(options);
        type_button.setBounds(280, 240, 230, 33);
        contentPane.add(type_button);
        ///////

        JLabel lblgiftpercentage = new JLabel("Insert discount gift/percentage:");
        lblgiftpercentage.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblgiftpercentage.setHorizontalAlignment(SwingConstants.CENTER);
        lblgiftpercentage.setBounds(10, 284, 250, 30);
        contentPane.add(lblgiftpercentage);

        giftpercentage_textField = new JTextField();
        giftpercentage_textField.setBounds(280, 280, 230, 33);
        contentPane.add(giftpercentage_textField);
        giftpercentage_textField.setColumns(10);

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
                    String type =(String)type_button.getSelectedItem();
                    int percentageOrGift = Integer.parseInt(giftpercentage_textField.getText());
                    if (type == null ||type.trim().isEmpty() ||supplierId == null ||supplierId.trim().isEmpty() || supermarketID==null || supermarketID.trim().isEmpty() ||percentageOrGift<=0  || amount<0)
                    {
                        JOptionPane.showMessageDialog(getComponent(0), "Invalid Input");
                        reset();
                    }
                    if (!manufacturerController.getAllSuperProducts().containsKey(supermarketID)) {
                        JOptionPane.showMessageDialog(getComponent(0), "Super Product Doesn't Exist", "Warning", 0);
                        reset();
                    }
                    else if(suppliersController.getSupplierProduct(supermarketID,supplierId)==null)
                    {
                        JOptionPane.showMessageDialog(getComponent(0), "Supplier Product Doesn't Exists", "Warning", 0);
                        reset();
                    }
                    else
                    {
                        SupplierProduct temp=suppliersController.getSupplierProduct(supermarketID,supplierId);
                        if(type.equals("Money Discount (Percentage)"))
                        {
                            suppliersController.add_discount_to_quantitiesBill(supermarketID,temp.getCatalog_number(),amount,'m',percentageOrGift,supplierId);
                            JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                        }
                        else
                        {
                            suppliersController.add_discount_to_quantitiesBill(supermarketID,temp.getCatalog_number(),amount,'q',percentageOrGift,supplierId);
                            JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                        }
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
