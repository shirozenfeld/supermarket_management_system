package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.Supplier;
import BusinessLayer.SuppliersModule.SuppliersController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContract extends JFrame
{
    private  JTextField unitPrice_textField;
    private  JTextField amount_textField;
    private JPanel contentPane;
    private JTextField suppliersid_textField;
    private JTextField supermarketid_textField;
    SuppliersController suppliersController;
    public UpdateContract()
    {
        this.suppliersController= SuppliersController.getInstance();
        setTitle("Update Existing Supplier's Products");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //BIG LABEL
        JLabel superLiManagementSystem = new JLabel("Update Existing Supplier's Products");
        superLiManagementSystem.setHorizontalAlignment(SwingConstants.CENTER);
        superLiManagementSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
        superLiManagementSystem.setBounds(0, 30, 613, 59);
        contentPane.add(superLiManagementSystem);

        JLabel lblsuppliersID = new JLabel("Insert Supplier's id:");
        lblsuppliersID.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblsuppliersID.setHorizontalAlignment(SwingConstants.CENTER);
        lblsuppliersID.setBounds(10, 124, 350, 20);
        contentPane.add(lblsuppliersID);

        suppliersid_textField = new JTextField();
        suppliersid_textField.setBounds(360, 120, 230, 33);
        contentPane.add(suppliersid_textField);
        suppliersid_textField.setColumns(10);

        JLabel lblsupermarketid = new JLabel("Insert catalog number:");
        lblsupermarketid.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblsupermarketid.setHorizontalAlignment(SwingConstants.CENTER);
        lblsupermarketid.setBounds(10, 164, 350, 20);
        contentPane.add(lblsupermarketid);

        supermarketid_textField = new JTextField();
        supermarketid_textField.setBounds(360, 160, 230, 33);
        contentPane.add(supermarketid_textField);
        supermarketid_textField.setColumns(10);

        JLabel lblunit_price = new JLabel("Insert the new Unit Price value, else type -1:");
        lblunit_price.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblunit_price.setHorizontalAlignment(SwingConstants.CENTER);
        lblunit_price.setBounds(10, 204, 350, 20);
        contentPane.add(lblunit_price);

        unitPrice_textField = new JTextField();
        unitPrice_textField.setBounds(360, 200, 230, 33);
        contentPane.add(unitPrice_textField);
        unitPrice_textField.setColumns(10);
        /////

        JLabel lblamount = new JLabel("Insert the new Amount value, else type -1:");
        lblamount.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblamount.setHorizontalAlignment(SwingConstants.CENTER);
        lblamount.setBounds(10, 244, 350, 20);
        contentPane.add(lblamount);

        amount_textField = new JTextField();
        amount_textField.setBounds(360, 240, 230, 33);
        contentPane.add(amount_textField);
        amount_textField.setColumns(10);

        JButton btnAddSupplierProduct = new JButton("Update");
        btnAddSupplierProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String supermarketid = supermarketid_textField.getText();
                    String supplierID = suppliersid_textField.getText();
                    String amount = amount_textField.getText();
                    String unitprice = unitPrice_textField.getText();
                    if (supplierID.trim().isEmpty() || supermarketid.trim().isEmpty() || amount.trim().isEmpty() || unitprice.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);
                        reset();

                    }
                    else
                    {
                        boolean problem = false;
                        if (!suppliersController.doesSupplierExistByID(supplierID)) {
                            JOptionPane.showMessageDialog(getComponent(0), "Supplier doesn't exist", "Warning", 0);
                            reset();
                            problem = true;
                        } else {
                            Supplier supplier = suppliersController.getSupplierByID(supplierID);
                            if (!supplier.getContract().getProducts().containsKey(supermarketid)) {
                                JOptionPane.showMessageDialog(getComponent(0), "Supplier doesn't supply this product", "Warning", 0);
                                reset();
                                problem = true;
                            }
                        }
                        if (!problem) {
                            if (!(amount.equals("-1")) && !(unitprice.equals("-1"))) {
                                int intamount = Integer.parseInt(amount);
                                double doubleunitprice = Double.parseDouble(unitprice);
                                suppliersController.update_supplierProduct(supermarketid, intamount, doubleunitprice, supplierID);
                                JOptionPane.showMessageDialog(getComponent(0), "Updates were successfuly done!", "Success", 0);
                            } else if ((amount.equals("-1")) && !(unitprice.equals("-1"))) {
                                double doubleunitprice = Double.parseDouble(unitprice);
                                suppliersController.update_supplierProduct(supermarketid, doubleunitprice, 'u', supplierID);
                                JOptionPane.showMessageDialog(getComponent(0), "Updates were successfuly done!", "Success", 0);
                            } else if ((amount.equals("-1")) && !(unitprice.equals("-1"))) {
                                double doubleamount = Double.parseDouble(amount);
                                suppliersController.update_supplierProduct(supermarketid, doubleamount, 'a', supplierID);
                                JOptionPane.showMessageDialog(getComponent(0), "Updates were successfuly done!", "Success", 0);
                            }
                            else if((amount.equals("-1")) && (unitprice.equals("-1")))
                            {
                                    JOptionPane.showMessageDialog(getComponent(0), "Invalid input", "Warning", 0);
                                reset();
                            }
                        }
                    }

                } catch (Exception f)
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);
                    reset();
                }

            }

        });
        btnAddSupplierProduct.setBounds(217, 350, 230, 33);
        contentPane.add(btnAddSupplierProduct);


        ///////

        //////////

        JButton btnExit = new JButton("Back to Menu");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnExit.setBounds(217, 296, 230, 36);
        contentPane.add(btnExit);
    }

    public void reset()
    {
        amount_textField.setText(null);
        supermarketid_textField.setText(null);
        unitPrice_textField.setText(null);
        suppliersid_textField.setText(null);


    }





}
