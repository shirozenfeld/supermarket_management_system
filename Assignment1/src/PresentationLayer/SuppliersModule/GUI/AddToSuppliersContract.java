package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.SuppliersController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddToSuppliersContract extends JFrame
{
    private JPanel contentPane;
    private JTextField suppliersid_textField;
    SuppliersController suppliersController;
    public AddToSuppliersContract()
    {
        this.suppliersController= SuppliersController.getInstance();
        setTitle("Update Supplier's Contract");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //BIG LABEL
        JLabel superLiManagementSystem = new JLabel("Update Supplier's Contract");
        superLiManagementSystem.setHorizontalAlignment(SwingConstants.CENTER);
        superLiManagementSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
        superLiManagementSystem.setBounds(0, 30, 613, 59);
        contentPane.add(superLiManagementSystem);

        JLabel lblsuppliersID = new JLabel("Insert Supplier's id:");
        lblsuppliersID.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblsuppliersID.setHorizontalAlignment(SwingConstants.CENTER);
        lblsuppliersID.setBounds(10, 124, 180, 20);
        contentPane.add(lblsuppliersID);

        suppliersid_textField = new JTextField();
        suppliersid_textField.setBounds(217, 120, 230, 33);
        contentPane.add(suppliersid_textField);
        suppliersid_textField.setColumns(10);

        /////
        JButton btnAddSupplierProduct = new JButton("Add Supplier Product");
        btnAddSupplierProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                GUIForm.addSupplierProduct=new AddSupplierProduct();
                String supplierID=suppliersid_textField.getText();
                if(supplierID == null || supplierID.trim().isEmpty())
                    JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);

                else if (!suppliersController.doesSupplierExistByID(supplierID))
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Supplier ID Doesn't Exist", "Warning", 0);
                }
                else
                {
                    if(!GUIForm.addSupplierProduct.isVisible())
                    {
                        GUIForm.addSupplierProduct.setSupplierID(supplierID);
                        GUIForm.addSupplierProduct.setVisible(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                    }

                }

            }



        });
        btnAddSupplierProduct.setBounds(217, 164, 230, 33);
        contentPane.add(btnAddSupplierProduct);


        ///////


        JButton btnAddSupplierDiscount = new JButton("Add Supplier Discount");
        btnAddSupplierDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                GUIForm.addSupplierDiscount=new AddSupplierDiscount();
                String supplierID=suppliersid_textField.getText();
                if(supplierID == null || supplierID.trim().isEmpty())
                    JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);

                else if (!suppliersController.doesSupplierExistByID(supplierID))
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Supplier ID Doesn't Exist", "Warning", 0);
                }
                else
                {
                    if(!GUIForm.addSupplierDiscount.isVisible())
                    {
                        GUIForm.addSupplierDiscount.setSupplierID(supplierID);
                        GUIForm.addSupplierDiscount.setVisible(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                    }

                }



            }
        });
        btnAddSupplierDiscount.setBounds(217, 208, 230, 33);
        contentPane.add(btnAddSupplierDiscount);

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




}
